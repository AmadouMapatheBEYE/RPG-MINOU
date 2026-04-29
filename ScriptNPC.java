import entity.NPC;

public class ScriptNPC {

    private GamePanel gp;

    public ScriptNPC(GamePanel gp) { this.gp = gp; }

    public void update() {
        int heure = gp.horloge.getHeures();
        int t     = gp.tailleTuile;
        for (NPC npc : gp.npcs) {
            if (npc == null) continue;
            appliquerRoutine(npc, heure, t);
        }
    }

    private void appliquerRoutine(NPC npc, int heure, int t) {
        switch (npc.nom) {
            case "Sara":
                if      (heure >= 8  && heure < 12) deplacerVers(npc, t*6,  t*5,  "bas");
                else if (heure >= 12 && heure < 14) deplacerVers(npc, t*10, t*11, "droite");
                else if (heure >= 14 && heure < 18) deplacerVers(npc, t*6,  t*5,  "bas");
                else                                deplacerVers(npc, t*4,  t*7,  "gauche");
                break;
            case "Lena":
                if      (heure >= 9  && heure < 13) deplacerVers(npc, t*24, t*5,  "bas");
                else if (heure >= 13 && heure < 17) deplacerVers(npc, t*22, t*6,  "droite");
                else if (heure >= 17 && heure < 22) deplacerVers(npc, t*14, t*15, "gauche");
                else                                deplacerVers(npc, t*24, t*5,  "bas");
                break;
            case "Yuki":
                if      (heure >= 9  && heure < 12) deplacerVers(npc, t*40, t*5,  "droite");
                else if (heure >= 12 && heure < 16) deplacerVers(npc, t*15, t*15, "bas");
                else if (heure >= 16 && heure < 20) deplacerVers(npc, t*22, t*11, "droite");
                else                                deplacerVers(npc, t*10, t*20, "haut");
                break;
            case "Maya":
                if      (heure >= 7  && heure < 18) deplacerVers(npc, t*40, t*12, "haut");
                else if (heure >= 18 && heure < 20) deplacerVers(npc, t*10, t*11, "gauche");
                else                                deplacerVers(npc, t*35, t*8,  "bas");
                break;
            case "Ellie":
                if      (heure >= 8  && heure < 13) deplacerVers(npc, t*25, t*4,  "droite");
                else if (heure >= 13 && heure < 17) deplacerVers(npc, t*33, t*5,  "bas");
                else                                deplacerVers(npc, t*22, t*5,  "gauche");
                break;
            case "Noa":
                if (heure >= 20 || heure < 3)       deplacerVers(npc, t*24, t*20, "haut");
                else if (heure >= 3 && heure < 6)   deplacerVers(npc, t*20, t*22, "gauche");
                else { npc.mondeX = -1000; npc.mondeY = -1000; }
                break;
            case "Minou":
                suivreJoueur(npc);
                break;
        }
    }

    private void deplacerVers(NPC npc, int cx, int cy, String dirFace) {
        int dx = cx - npc.mondeX;
        int dy = cy - npc.mondeY;
        if (Math.abs(dx) < 4 && Math.abs(dy) < 4) { npc.direction = dirFace; return; }
        if (Math.abs(dx) > Math.abs(dy)) {
            npc.mondeX += dx > 0 ? 1 : -1;
            npc.direction = dx > 0 ? "droite" : "gauche";
        } else {
            npc.mondeY += dy > 0 ? 1 : -1;
            npc.direction = dy > 0 ? "bas" : "haut";
        }
    }

    private void suivreJoueur(NPC minou) {
        int dx   = gp.joueur.mondeX - minou.mondeX;
        int dy   = gp.joueur.mondeY - minou.mondeY;
        int dist = (int) Math.sqrt(dx*dx + dy*dy);
        if (dist > gp.tailleTuile * 3)
            deplacerVers(minou, gp.joueur.mondeX - gp.tailleTuile, gp.joueur.mondeY, "bas");
        else if (dist < gp.tailleTuile * 2)
            minou.direction = Math.abs(dx) > Math.abs(dy)
                ? (dx > 0 ? "droite" : "gauche")
                : (dy > 0 ? "bas" : "haut");
    }
}