import java.awt.event.*;

public class KeyHandler implements KeyListener {

    // true = touche enfoncée, false = relâchée
    public boolean haut, bas, gauche, droite;
    public boolean interagir;  // touche Entrée pour parler aux PNJ

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
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE)
            interagir = true;
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
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE)
            interagir = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}  // on n'utilise pas ça
}