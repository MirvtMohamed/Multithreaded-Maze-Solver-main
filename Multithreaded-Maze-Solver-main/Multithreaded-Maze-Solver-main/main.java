import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class main {
    public static int[] exit;
    public static boolean goalAchieved = false;
    int[][] matrice;
    private JFrame frame;

    public main(int[][] matrice) {
        this.matrice = matrice;
    }

    public static void setStatic() {
        goalAchieved = true;
    }

    public void createAndShowGUI() {
        frame = new JFrame("Maze Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel sizeLabel = new JLabel("Enter Maze Size (N x N):");
        sizeLabel.setBounds(10, 20, 150, 25);
        panel.add(sizeLabel);

        JTextField sizeTextField = new JTextField(20);
        sizeTextField.setBounds(170, 20, 80, 25);
        panel.add(sizeTextField);

        JButton createMazeButton = new JButton("Generate Maze");
        createMazeButton.setBounds(260, 20, 150, 25);
        panel.add(createMazeButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(420, 20, 80, 25);
        panel.add(exitButton);

        createMazeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sizeText = sizeTextField.getText();
                if (!sizeText.isEmpty()) {
                    int size = Integer.parseInt(sizeText);

                    // Initialize exit within maze boundaries
                    exit = new int[] { size - 1, size - 1 };

                    matrice = createRandomMaze(size);

                    int[] start = { 0, 0 };
                    Thread mazeSolver = new MazeSolver(matrice, start, "main");
                    mazeSolver.start();

                    Mazet mazePanel = new Mazet(matrice);
                    mazePanel.setBounds(20, 70, 50 * size, 50 * size);
                    mazePanel.setBackground(Color.BLACK);
                    mazePanel.setLayout(null);
                    mazePanel.add(MazeSolver.tt);

                    frame.add(mazePanel);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        main mainInstance = new main(new int[0][0]);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mainInstance.createAndShowGUI();
            }
        });

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available processors: " + availableProcessors);

        int numberOfThreads = Math.min(availableProcessors, 4);
        System.out.println("Number of threads: " + numberOfThreads);

    }

    private static int[][] createRandomMaze(int size) {
        int[][] maze = new int[size][size];
        // Initialize the maze with random 0s and 1s
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[i][j] = Math.random() < 0.6 ? 1 : 0; // 70% chance of 1 (cell), 30% chance of 0 (wall)
            }
        }
        // Mark the exit block with a different color
        exit = new int[] { size - 1, size - 1 }; // Initialize exit within maze boundaries
        maze[exit[0]][exit[1]] = 2; // 2 represents the exit block

        return maze;
    }
}