public class CollisionChecker {

    private GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void verifier(entity.Entity entity) {

        // Position réelle de la hitbox dans le monde
        int gaucheEntite  = entity.mondeX + entity.collision.x;
        int droiteEntite  = gaucheEntite  + entity.collision.width;
        int hautEntite    = entity.mondeY + entity.collision.y;
        int basEntite     = hautEntite    + entity.collision.height;

        // Tuiles touchées selon la direction
        int col1, col2, ligne1, ligne2;

        switch (entity.direction) {
            case "haut":
                hautEntite -= entity.vitesse;
                ligne1 = hautEntite   / gp.tailleTuile;
                ligne2 = hautEntite   / gp.tailleTuile;
                col1   = gaucheEntite / gp.tailleTuile;
                col2   = droiteEntite / gp.tailleTuile;
                verifierTuile(entity, col1, ligne1, col2, ligne2);
                break;

            case "bas":
                basEntite += entity.vitesse;
                ligne1 = basEntite    / gp.tailleTuile;
                ligne2 = basEntite    / gp.tailleTuile;
                col1   = gaucheEntite / gp.tailleTuile;
                col2   = droiteEntite / gp.tailleTuile;
                verifierTuile(entity, col1, ligne1, col2, ligne2);
                break;

            case "gauche":
                gaucheEntite -= entity.vitesse;
                col1   = gaucheEntite / gp.tailleTuile;
                col2   = gaucheEntite / gp.tailleTuile;
                ligne1 = hautEntite   / gp.tailleTuile;
                ligne2 = basEntite    / gp.tailleTuile;
                verifierTuile(entity, col1, ligne1, col2, ligne2);
                break;

            case "droite":
                droiteEntite += entity.vitesse;
                col1   = droiteEntite / gp.tailleTuile;
                col2   = droiteEntite / gp.tailleTuile;
                ligne1 = hautEntite   / gp.tailleTuile;
                ligne2 = basEntite    / gp.tailleTuile;
                verifierTuile(entity, col1, ligne1, col2, ligne2);
                break;
        }
    }

    private void verifierTuile(entity.Entity entity,
                                int col1, int ligne1,
                                int col2, int ligne2) {
        // Récupère les numéros de tuiles
        int tuile1 = gp.gestionnaireTuiles.carte[ligne1][col1];
        int tuile2 = gp.gestionnaireTuiles.carte[ligne2][col2];

        // Si l'une des tuiles est solide → collision !
        if (gp.gestionnaireTuiles.tuiles[tuile1].solide ||
            gp.gestionnaireTuiles.tuiles[tuile2].solide) {
            entity.collisionActive = true;
        }
    }
}