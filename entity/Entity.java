package entity;

import java.awt.*;
import java.awt.image.*;

public class Entity {

    // Position dans le monde
    public int mondeX, mondeY;

    // Vitesse
    public int vitesse;

    // Direction
    public String direction = "bas";

    // Nom (pour les dialogues)
    public String nom = "";

    // Rectangle de collision
    public Rectangle collision = new Rectangle(0, 0, 48, 48);
    public boolean collisionActive = false;

    // Images d'animation
    public BufferedImage haut1,   haut2;
    public BufferedImage bas1,    bas2;
    public BufferedImage gauche1, gauche2;
    public BufferedImage droite1, droite2;

    // Compteur d'animation
    public int compteurAnimation = 0;
    public int numeroAnimation   = 1;
}