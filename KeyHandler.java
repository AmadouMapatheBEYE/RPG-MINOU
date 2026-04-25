import java.awt.event.*;

public class KeyHandler implements KeyListener {

    public boolean haut, bas, gauche, droite;
    public boolean interagirPresse = false;  // true UNE SEULE frame

    private boolean interagirTenu = false;   // évite la répétition

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP    || code == KeyEvent.VK_W)
            haut = true;
        if (code == KeyEvent.VK_DOWN  || code == KeyEvent.VK_S)
            bas = true;
        if (code == KeyEvent.VK_LEFT  || code == KeyEvent.VK_A)
            gauche = true;
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
            droite = true;

        // Entrée/Espace : détecté UNE seule fois par appui
        if ((code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE)
                && !interagirTenu) {
            interagirPresse = true;
            interagirTenu   = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP    || code == KeyEvent.VK_W)
            haut = false;
        if (code == KeyEvent.VK_DOWN  || code == KeyEvent.VK_S)
            bas = false;
        if (code == KeyEvent.VK_LEFT  || code == KeyEvent.VK_A)
            gauche = false;
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
            droite = false;

        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            interagirTenu   = false;
            interagirPresse = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // Appelé après traitement pour réinitialiser le "pressé"
    public void resetInteragir() {
        interagirPresse = false;
    }
}