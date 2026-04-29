import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.HashMap;
import javax.imageio.*;

public class DialogueManager {

    private GamePanel gp;

    public boolean dialogueOuvert   = false;
    private String texteComplet     = "";
    private String texteAffiche     = "";
    private int    indexLettre      = 0;
    private int    compteurTexte    = 0;
    private int    vitesseTexte     = 2;
    private boolean texteFini       = false;

    private boolean modeChoix       = false;
    private DialogueChoix.Choix[] choixActuels = null;
    private int    choixSelectionne = 0;
    private boolean enAttenteChoix  = false;
    private boolean modeReponse     = false;

    private String     nomLocuteur  = "";
    private entity.NPC npcActuel    = null;

    private HashMap<String, BufferedImage> portraits = new HashMap<>();

    private final int BOX_X      = 40;
    private final int BOX_Y      = 380;
    private final int BOX_W      = 688;
    private final int BOX_H      = 170;
    private final int PORTRAIT_S = 130;

    public DialogueManager(GamePanel gp) {
        this.gp = gp;
        chargerPortraits();
    }

    private void chargerPortraits() {
        String[] persos = {"minou","sara","lena","yuki","maya","ellie","noa"};
        for (String p : persos) {
            try {
                portraits.put(p, ImageIO.read(new File("assets/portraits/" + p + ".png")));
            } catch (Exception e) {
                portraits.put(p, creerPortraitPlaceholder(p));
            }
        }
    }

    private BufferedImage creerPortraitPlaceholder(String nom) {
        BufferedImage img = new BufferedImage(PORTRAIT_S, PORTRAIT_S, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        Color[] couleurs = {
            new Color(210,105,30), new Color(100,80,200), new Color(200,60,20),
            new Color(220,100,130), new Color(200,50,50), new Color(180,210,180), new Color(80,60,120)
        };
        String[] noms = {"minou","sara","lena","yuki","maya","ellie","noa"};
        Color c = new Color(100,100,150);
        for (int i = 0; i < noms.length; i++) if (noms[i].equals(nom)) { c = couleurs[i]; break; }
        g.setColor(c.darker()); g.fillRect(0, 0, PORTRAIT_S, PORTRAIT_S);
        g.setColor(c); g.fillOval(5, 5, PORTRAIT_S-10, PORTRAIT_S-10);
        g.setColor(Color.WHITE); g.setFont(new Font("Serif", Font.BOLD, 50));
        FontMetrics fm = g.getFontMetrics();
        String l = nom.substring(0,1).toUpperCase();
        g.drawString(l, PORTRAIT_S/2 - fm.stringWidth(l)/2, PORTRAIT_S/2 + fm.getAscent()/2 - 5);
        g.dispose(); return img;
    }

    public void ouvrirDialogue(entity.NPC npc) {
        if (dialogueOuvert) return;
        npcActuel = npc; nomLocuteur = npc.nom;
        texteComplet = npc.getDialogueSuivant();
        reinitialiserEtat();
    }

    public void ouvrirDialogueChoix(DialogueChoix.DialogueAvecChoix dialogue) {
        if (dialogueOuvert) return;
        nomLocuteur = dialogue.nomNPC; texteComplet = dialogue.texteNPC;
        choixActuels = dialogue.choix; choixSelectionne = 0;
        modeChoix = true;
        reinitialiserEtat();
    }

    public void ouvrirDialogueTexte(String nom, String texte) {
        if (dialogueOuvert) return;
        nomLocuteur = nom; npcActuel = null; texteComplet = texte;
        modeChoix = false;
        reinitialiserEtat();
    }

    private void reinitialiserEtat() {
        texteAffiche = ""; indexLettre = 0; compteurTexte = 0;
        texteFini = false; enAttenteChoix = false; modeReponse = false;
        dialogueOuvert = true;
    }

    public void update() {
        if (!dialogueOuvert || texteFini) return;
        compteurTexte++;
        if (compteurTexte >= vitesseTexte) {
            compteurTexte = 0;
            if (indexLettre < texteComplet.length()) {
                texteAffiche += texteComplet.charAt(indexLettre);
                indexLettre++;
            } else {
                texteFini = true;
                enAttenteChoix = modeChoix && !modeReponse;
            }
        }
    }

    public void naviguerChoix(boolean versLeBas) {
        if (!enAttenteChoix || choixActuels == null) return;
        choixSelectionne = versLeBas
            ? (choixSelectionne + 1) % choixActuels.length
            : (choixSelectionne - 1 + choixActuels.length) % choixActuels.length;
    }

    public void avancer() {
        if (!dialogueOuvert) return;
        if (!texteFini) {
            texteAffiche = texteComplet;
            indexLettre  = texteComplet.length();
            texteFini    = true;
            enAttenteChoix = modeChoix && !modeReponse;
            return;
        }
        if (enAttenteChoix && choixActuels != null) {
            DialogueChoix.Choix choix = choixActuels[choixSelectionne];
            gp.systemeAffection.modifier(choix.personnage, choix.effetAffection);
            texteComplet = choix.reponseNPC; texteAffiche = "";
            indexLettre = 0; compteurTexte = 0; texteFini = false;
            enAttenteChoix = false; modeReponse = true;
            return;
        }
        fermerDialogue();
    }

    public void fermerDialogue() {
        dialogueOuvert = false; npcActuel = null;
        texteAffiche = ""; texteComplet = "";
        modeChoix = false; modeReponse = false; enAttenteChoix = false;
    }

    public void draw(Graphics2D g2) {
        if (!dialogueOuvert) return;

        g2.setColor(new Color(10, 5, 20, 220));
        g2.fillRoundRect(BOX_X, BOX_Y, BOX_W, BOX_H, 15, 15);
        g2.setColor(new Color(255, 200, 100));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(BOX_X, BOX_Y, BOX_W, BOX_H, 15, 15);

        BufferedImage portrait = portraits.get(nomLocuteur.toLowerCase());
        if (portrait != null)
            g2.drawImage(portrait, BOX_X+10, BOX_Y+10, PORTRAIT_S, PORTRAIT_S, null);
        g2.setColor(new Color(255,200,100,150));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(BOX_X+10, BOX_Y+10, PORTRAIT_S, PORTRAIT_S, 6, 6);

        int textX      = BOX_X + PORTRAIT_S + 25;
        int maxLargeur = BOX_W - PORTRAIT_S - 45;

        g2.setColor(new Color(255, 220, 100));
        g2.setFont(new Font("Serif", Font.BOLD, 18));
        g2.drawString(nomLocuteur, textX, BOX_Y + 30);

        if (!nomLocuteur.equals("Minou") && !nomLocuteur.isEmpty()) {
            int aff   = gp.systemeAffection.getAffection(nomLocuteur);
            String et = gp.systemeAffection.getEtat(nomLocuteur);
            g2.setFont(new Font("Arial", Font.PLAIN, 11));
            g2.setColor(gp.systemeAffection.getCouleurEtat(nomLocuteur));
            g2.drawString(et + " (" + aff + "/100)", textX + 100, BOX_Y + 30);
        }

        g2.setColor(new Color(255,200,100,100));
        g2.drawLine(textX, BOX_Y+38, BOX_X+BOX_W-15, BOX_Y+38);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.PLAIN, 15));
        dessinerTexteMultiLigne(g2, texteAffiche, textX, BOX_Y+58, maxLargeur, 21);

        if (enAttenteChoix && choixActuels != null) {
            int choixY = BOX_Y + 95;
            for (int i = 0; i < choixActuels.length; i++) {
                boolean sel = (i == choixSelectionne);
                if (sel) {
                    g2.setColor(new Color(255,200,50,60));
                    g2.fillRoundRect(textX-5, choixY-14, maxLargeur, 18, 5, 5);
                }
                g2.setFont(new Font("Serif", Font.PLAIN, 13));
                g2.setColor(sel ? new Color(255,230,100) : new Color(200,200,200));
                g2.drawString((sel ? "> " : "  ") + choixActuels[i].texte, textX, choixY);
                choixY += 22;
            }
        }

        if (texteFini && !enAttenteChoix) {
            if ((System.currentTimeMillis() / 500) % 2 == 0) {
                g2.setColor(new Color(255,220,100));
                g2.setFont(new Font("Arial", Font.BOLD, 11));
                g2.drawString("Entree", BOX_X+BOX_W-60, BOX_Y+BOX_H-10);
            }
        }
    }

    private void dessinerTexteMultiLigne(Graphics2D g2, String texte,
            int x, int y, int largeurMax, int hauteurLigne) {
        if (texte == null || texte.isEmpty()) return;
        String[] mots = texte.split(" ");
        StringBuilder l = new StringBuilder();
        int ly = y;
        FontMetrics fm = g2.getFontMetrics();
        for (String mot : mots) {
            String test = l + (l.length() > 0 ? " " : "") + mot;
            if (fm.stringWidth(test) > largeurMax && l.length() > 0) {
                g2.drawString(l.toString(), x, ly);
                l = new StringBuilder(mot); ly += hauteurLigne;
            } else {
                if (l.length() > 0) l.append(" ");
                l.append(mot);
            }
        }
        if (l.length() > 0) g2.drawString(l.toString(), x, ly);
    }

    public entity.NPC getNPCProche() {
        if (gp.npcs == null) return null;
        for (entity.NPC npc : gp.npcs) {
            if (npc == null) continue;
            int dx = Math.abs(npc.mondeX - gp.joueur.mondeX);
            int dy = Math.abs(npc.mondeY - gp.joueur.mondeY);
            if (dx < gp.tailleTuile * 1.5 && dy < gp.tailleTuile * 1.5) return npc;
        }
        return null;
    }

    public boolean isDialogueOuvert()  { return dialogueOuvert; }
    public boolean isEnAttenteChoix()  { return enAttenteChoix; }
}