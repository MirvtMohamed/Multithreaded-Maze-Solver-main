import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.HashMap;


public class Mazet extends JPanel {
   private static HashMap<Integer, Color> idColors = new HashMap<>();
    private int[][] matrice; // Instance variable

    public Mazet() {
        //this.matrice = matrice;
        this.setBackground(Color.BLACK);
    }
    public Mazet(int[][] matrice) {
        this.matrice = matrice;
        this.setBackground(Color.BLACK);
    }

    public void setMatrice(int[][] matrice) {
        this.matrice = matrice;
    }

    public void paintComponent(Graphics g) {
    if (matrice != null) {  // Add null check here
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice.length; j++) {
                int factori = i * 50;
                int factorj = j * 50;
                int colorId = matrice[i][j];

                switch (colorId) {
                    case 0: {
                        g.setColor(Color.BLACK);
                        g.fillRect(factori, factorj, 50, 50);
                        break;
                    }
                    case 1: {
                        g.setColor(Color.WHITE);
                        g.fillRect(factori, factorj, 50, 50);
                        break;
                    }
                    case -22: {
                        g.setColor(Color.GRAY);
                        g.fillRect(factori, factorj, 50, 50);
                        break;
                    }
                    case 2: {
                        g.setColor(Color.YELLOW);
                        g.fillRect(factori, factorj, 50, 50);
                        break;
                    }
                    default: {
                        g.setColor(Color.getHSBColor((colorId % 360) / 360.0f, 1.0f, 1.0f));
                        g.fillRect(factori, factorj, 50, 50);
                        break;
                    }
                }
            }
        }
    }
}

}