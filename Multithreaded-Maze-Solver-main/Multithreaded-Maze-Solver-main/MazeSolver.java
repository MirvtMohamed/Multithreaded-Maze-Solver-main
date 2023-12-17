import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class MazeSolver extends Thread {
    int[][] matrice; // Remove static keyword
    private int xPos, yPos;
    private int n, m;
    private String label;
    private int threadId;
    private static HashMap<Integer, Color> idColors = new HashMap<>();
    private static final Color[] predefinedColors = {
            Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.PINK
    };
    static Mazet tt = new Mazet();

    MazeSolver(int[][] matrice, int[] start, String label) {
        this.matrice = matrice; // Remove static keyword
        this.xPos = start[0];
        this.yPos = start[1];
        this.m = matrice.length;
        this.n = matrice[0].length;
        this.label = label;
        this.threadId = getNextColorId();
        idColors.put(threadId, getColorForThreadId(threadId));
        tt.setBounds(20, 20, 650, 650);
        tt.setBackground(Color.WHITE);
    }

    private synchronized int getNextColorId() {
        return ThreadLocalRandom.current().nextInt();
    }

    private Color getColorForThreadId(int id) {
        id = Math.abs(id); // Ensure non-negative value

        if (id < predefinedColors.length) {
            return predefinedColors[id];
        } else {
            // Use a fallback color for IDs beyond the predefinedColors array length
            return Color.getHSBColor((id % 360) / 360.0f, 1.0f, 1.0f);
        }
    }
    public void run() {
        System.out.println(this.label + " Thread N:" + threadId + " starts from: " + this.xPos + "," + this.yPos);
        int fin = 1;
        while (fin == 1 && !main.goalAchieved) {
            synchronized (matrice) {
                matrice[this.xPos][this.yPos] = -22 - threadId;
            }
            fin = move();
            if (this.xPos == main.exit[0] && this.yPos == main.exit[0]) {
                main.setStatic();
                System.out.println("on est a la sortie ...");
                synchronized (matrice) {
                    matrice[this.xPos][this.yPos] = 2;
                }
            }

            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            tt.setMatrice(matrice); // Set the matrice before calling repaint()
            tt.repaint();
        }
    }

    private int move() {
        ArrayList<int[]> array = checkNeigbours(this.xPos, this.yPos);
        if (array.size() == 1) {
            this.xPos = array.get(0)[0];
            this.yPos = array.get(0)[1];
            System.out.println(
                    this.label + " Thread N: " + threadId + " continue:" + array.get(0)[0] + "," + array.get(0)[1]);
            return 1;
        } else if (array.size() == 2) {
            this.xPos = array.get(0)[0];
            this.yPos = array.get(0)[1];
            System.out.println(
                    this.label + " Thread N: " + threadId + " continue:" + array.get(0)[0] + "," + array.get(0)[1]);

            int[] start1 = { array.get(1)[0], array.get(1)[1] };
            Thread thread1 = new MazeSolver(matrice, start1, "created");
            thread1.start();

            return 1;
        } else {
            System.out.println("stop " + threadId + " thread ");
            return 0;
        }
    }

    private ArrayList<int[]> checkNeigbours(int xPos, int yPos) {
        ArrayList<int[]> array = new ArrayList<int[]>();

        // Check right neighbor
        if (yPos + 1 < m && matrice[xPos][yPos + 1] == 1) {
            int[] rightNeighbor = { xPos, yPos + 1 };
            array.add(rightNeighbor);
        }

        // Check down neighbor
        if (xPos + 1 < n && matrice[xPos + 1][yPos] == 1) {
            int[] downNeighbor = { xPos + 1, yPos };
            array.add(downNeighbor);
        }

        return array;
    }

    
}
