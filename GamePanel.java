import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    public final int tailleTuileBase = 16;
    public final int echelle         = 3;
    public final int tailleTuile     = tailleTuileBase * echelle;
    public final int colonnesEcran   = 16;
    public final int lignesEcran     = 12;
    public final int largeurEcran    = tailleTuile * colonnesEcran;
    public final int hauteurEcran    = tailleTuile * lignesEcran;
    public final int colonnesMonde   = 50;
    public final int lignesMonde     = 50;
    final int FPS = 60;

    public KeyHandler           keyH;
    public CollisionChecker     verificateurCollision;
    public GestionnaireTuiles   gestionnaireTuiles;
    public Player               joueur;
    public entity.NPC[]         npcs;
    public DialogueManager      dialogueManager;
    public SystemeAffection     systemeAffection;
    public HorlogeJeu           horloge;
    public ScriptNPC            scriptNPC;
    public GestionnaireQuetes   gestionnaireQuetes;
    public HistoirePrincipale   histoire;

    private boolean journalOuvert = false;
    Thread threadJeu;

    public GamePanel() {
        setPreferredSize(new Dimension(largeurEcran, hauteurEcran));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        keyH = new KeyHandler();
        addKeyListener(keyH);
        setFocusable(true);
        initialiser();
    }

    private void initialiser() {
        try {
            systemeAffection      = new SystemeAffection();
            horloge               = new HorlogeJeu();
            gestionnaireTuiles    = new GestionnaireTuiles(this);
            verificateurCollision = new CollisionChecker(this);
            joueur                = new Player(this, keyH);
            dialogueManager       = new DialogueManager(this);
            npcs                  = NPCFactory.creerTousLesNPC(this);
            scriptNPC             = new ScriptNPC(this);
            gestionnaireQuetes    = new GestionnaireQuetes(this);
            histoire              = new HistoirePrincipale(this);
            System.out.println("Initialisation OK !");
        } catch (Exception e) {
            System.out.println("ERREUR : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void demarrerBoucle() {
        threadJeu = new Thread(this);
        threadJeu.start();
    }

    @Override
    public void run() {
        double intervalleNS = 1_000_000_000.0 / FPS;
        double delta = 0;
        long dernierTemps = System.nanoTime();
        while (threadJeu != null) {
            try {
                long t = System.nanoTime();
                delta += (t - dernierTemps) / intervalleNS;
                dernierTemps = t;
                if (delta >= 1) { update(); repaint(); delta--; }
            } catch (Exception e) {
                System.out.println("ERREUR boucle : " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

    public void update() {
        if (horloge == null || joueur == null) return;
        horloge.update();
        if (gestionnaireQuetes != null) gestionnaireQuetes.update();
        if (histoire != null) histoire.update();

        if (keyH.journalPresse) { journalOuvert = !journalOuvert; keyH.journalPresse = false; }
        if (keyH.tempsPresse)   { horloge.avancerHeure(); keyH.tempsPresse = false; }
        if (journalOuvert) return;

        if (dialogueManager != null && dialogueManager.isDialogueOuvert()) {
            dialogueManager.update();
            if (keyH.haut) { dialogueManager.naviguerChoix(false); keyH.haut = false; }
            if (keyH.bas)  { dialogueManager.naviguerChoix(true);  keyH.bas  = false; }
            if (keyH.interagirPresse) { dialogueManager.avancer(); keyH.resetInteragir(); }
            return;
        }

        joueur.update();
        if (scriptNPC != null) scriptNPC.update();

        if (keyH.interagirPresse) {
            entity.NPC proche = dialogueManager.getNPCProche();
            if (proche != null) {
                int dx = proche.mondeX - joueur.mondeX;
                int dy = proche.mondeY - joueur.mondeY;
                joueur.direction = Math.abs(dx) > Math.abs(dy)
                    ? (dx > 0 ? "droite" : "gauche")
                    : (dy > 0 ? "bas" : "haut");
                DialogueChoix.DialogueAvecChoix[] dialogues = getDialoguesPerso(proche.nom);
                if (dialogues != null && dialogues.length > 0) {
                    int aff = systemeAffection.getAffection(proche.nom);
                    int index = Math.min(aff / 35, dialogues.length - 1);
                    dialogueManager.ouvrirDialogueChoix(dialogues[index]);
                } else {
                    dialogueManager.ouvrirDialogue(proche);
                }
            }
            keyH.resetInteragir();
        }

        if (npcs != null) {
            for (entity.NPC npc : npcs) {
                if (npc == null) continue;
                npc.compteurAnimation++;
                if (npc.compteurAnimation > 12) {
                    npc.numeroAnimation = (npc.numeroAnimation == 1) ? 2 : 1;
                    npc.compteurAnimation = 0;
                }
            }
        }
    }

    private DialogueChoix.DialogueAvecChoix[] getDialoguesPerso(String nom) {
        switch (nom) {
            case "Sara":  return DialogueChoix.getDialoguesSara();
            case "Lena":  return DialogueChoix.getDialoguesLena();
            case "Yuki":  return DialogueChoix.getDialoguesYuki();
            case "Maya":  return DialogueChoix.getDialoguesMaya();
            case "Ellie": return DialogueChoix.getDialoguesEllie();
            case "Noa":   return DialogueChoix.getDialoguesNoa();
            default:      return null;
        }
    }

    private void dessinerMiniJauges(Graphics2D g2) {
        String[] persos = {"Sara","Lena","Yuki","Maya","Ellie","Noa"};
        int x = largeurEcran - 135, y = 15;
        g2.setColor(new Color(0,0,0,160));
        g2.fillRoundRect(x-5, y-12, 130, persos.length*18+14, 8, 8);
        for (String p : persos) {
            int aff = systemeAffection.getAffection(p);
            Color c = systemeAffection.getCouleurEtat(p);
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.setColor(Color.WHITE); g2.drawString(p, x, y);
            g2.setColor(new Color(50,50,50)); g2.fillRect(x+38, y-9, 72, 8);
            g2.setColor(c); g2.fillRect(x+38, y-9, (int)(72*aff/100.0), 8);
            y += 18;
        }
    }

    private void dessinerUI(Graphics2D g2) {
        if (horloge == null || joueur == null) return;

        String heure = horloge.getHeureFormatee();
        g2.setFont(new Font("Serif", Font.BOLD, 13));
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(new Color(0,0,0,150));
        g2.fillRoundRect(largeurEcran/2-35, 5, 70, 22, 8, 8);
        g2.setColor(new Color(255,230,150));
        g2.drawString(heure, largeurEcran/2 - fm.stringWidth(heure)/2, 21);

        g2.setFont(new Font("Arial", Font.BOLD, 13));
        g2.setColor(new Color(0,0,0,150));
        g2.fillRoundRect(10, hauteurEcran-40, 160, 28, 10, 10);
        g2.setColor(Color.WHITE);
        g2.drawString("Solevert", 20, hauteurEcran-20);

        if (histoire != null) histoire.drawActe(g2, largeurEcran);
        if (systemeAffection != null) dessinerMiniJauges(g2);

        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        g2.setColor(new Color(255,255,255,150));
        g2.drawString("X:"+joueur.mondeX/tailleTuile+" Y:"+joueur.mondeY/tailleTuile
            +"  [J] Journal  [T] Heure", 10, 20);

        String periode = "";
        switch (horloge.getPeriode()) {
            case MATIN:      periode = "Matin";      break;
            case APRES_MIDI: periode = "Apres-midi"; break;
            case SOIR:       periode = "Soir";       break;
            case NUIT:       periode = "Nuit";       break;
        }
        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        g2.setColor(new Color(255,255,200,200));
        g2.drawString(periode, largeurEcran/2+45, 20);

        if (dialogueManager != null && !dialogueManager.isDialogueOuvert()) {
            entity.NPC proche = dialogueManager.getNPCProche();
            if (proche != null) {
                int ex = proche.mondeX - joueur.mondeX + joueur.ecranX;
                int ey = proche.mondeY - joueur.mondeY + joueur.ecranY;
                g2.setColor(new Color(255,230,50));
                g2.fillOval(ex+tailleTuile/2-8, ey-28, 16, 16);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.drawString("!", ex+tailleTuile/2-3, ey-16);
                g2.setColor(new Color(0,0,0,180));
                g2.fillRoundRect(largeurEcran/2-100, hauteurEcran-75, 200, 24, 8, 8);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.PLAIN, 12));
                g2.drawString("[Entree] Parler a "+proche.nom,
                    largeurEcran/2-92, hauteurEcran-57);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (gestionnaireTuiles != null) gestionnaireTuiles.draw(g2);

        if (npcs != null && joueur != null) {
            for (entity.NPC npc : npcs) {
                if (npc != null) npc.draw(g2,
                    joueur.mondeX, joueur.mondeY,
                    joueur.ecranX, joueur.ecranY,
                    tailleTuile, largeurEcran, hauteurEcran);
            }
        }

        if (joueur != null) joueur.draw(g2);

        if (horloge != null) {
            g2.setColor(horloge.getCouleurAmbiance());
            g2.fillRect(0, 0, largeurEcran, hauteurEcran);
        }

        dessinerUI(g2);

        if (journalOuvert && gestionnaireQuetes != null)
            gestionnaireQuetes.draw(g2, largeurEcran, hauteurEcran);

        if (dialogueManager != null) dialogueManager.draw(g2);

        g2.dispose();
    }
}