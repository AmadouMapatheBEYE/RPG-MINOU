import java.awt.event.*;

public class KeyHandler implements KeyListener {

    public boolean haut, bas, gauche, droite;
    public boolean interagirPresse = false;
    public boolean journalPresse   = false;
    public boolean tempsPresse     = false;

    private boolean interagirTenu = false;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP    || code == KeyEvent.VK_W) haut   = true;
        if (code == KeyEvent.VK_DOWN  || code == KeyEvent.VK_S) bas    = true;
        if (code == KeyEvent.VK_LEFT  || code == KeyEvent.VK_A) gauche = true;
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) droite = true;

        if ((code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) && !interagirTenu) {
            interagirPresse = true;
            interagirTenu   = true;
        }
        if (code == KeyEvent.VK_J) journalPresse = true;
        if (code == KeyEvent.VK_T) tempsPresse   = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP    || code == KeyEvent.VK_W) haut   = false;
        if (code == KeyEvent.VK_DOWN  || code == KeyEvent.VK_S) bas    = false;
        if (code == KeyEvent.VK_LEFT  || code == KeyEvent.VK_A) gauche = false;
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) droite = false;

        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            interagirTenu   = false;
            interagirPresse = false;
        }
        if (code == KeyEvent.VK_J) journalPresse = false;
        if (code == KeyEvent.VK_T) tempsPresse   = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void resetInteragir() {
        interagirPresse = false;
    }
}