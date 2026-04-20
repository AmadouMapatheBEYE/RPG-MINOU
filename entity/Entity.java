package entity;

import java.awt.*;
import java.awt.image.*;

public class Entity {

    // Position dans le MONDE (pas à l'écran)
    public int mondeX, mondeY;

    // Vitesse de déplacement
    public int vitesse;

    // Direction actuelle
    public String direction = "bas";

    // Image actuelle affichée
    public BufferedImage image;

    // Rectangle de collision (plus petit que le sprite)
    public Rectangle collision;
    public boolean collisionActive = false;

    // Pour les animations (on verra ça à l'étape 4)
    public BufferedImage haut1, haut2;
    public BufferedImage bas1,  bas2;
    public BufferedImage gauche1, gauche2;
    public BufferedImage droite1, droite2;

    public int compteurAnimation = 0;
    public int numeroAnimation   = 1;

    // Nom du personnage (pour les dialogues)
    public String nom = "";
}