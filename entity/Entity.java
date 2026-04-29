package entity;

import java.awt.*;
import java.awt.image.*;

public class Entity {

    public int mondeX, mondeY;
    public int vitesse;
    public String direction = "bas";
    public String nom = "";

    public Rectangle collision = new Rectangle(0, 0, 48, 48);
    public boolean collisionActive = false;

    public BufferedImage haut1,   haut2;
    public BufferedImage bas1,    bas2;
    public BufferedImage gauche1, gauche2;
    public BufferedImage droite1, droite2;

    public int compteurAnimation = 0;
    public int numeroAnimation   = 1;
}