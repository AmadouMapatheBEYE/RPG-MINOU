import javax.swing.*;

public class GameWindow {

    private JFrame   fenetre;
    private GamePanel gamePanel;

    public GameWindow() {
        fenetre = new JFrame("Minou & Moi \uD83D\uDC31");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setResizable(false);

        gamePanel = new GamePanel();
        fenetre.add(gamePanel);
        fenetre.pack();
    }

    public void afficher() {
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        gamePanel.demarrerBoucle();
    }
}