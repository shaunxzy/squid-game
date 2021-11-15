package model;

import persistence.Saveable;

import java.awt.*;
import java.io.PrintWriter;

public class Bullet extends GameCharacter implements Saveable {
    private static final int SPEED = 5;
    private static final double HEIGHT = 5;
    private static final double WIDTH = 5;

    private int damage;
    private double[] direction;

    // EFFECT: initiate the bullet class, assign its position, speed, damage and the direction its moving
    public Bullet(double x, double y, int damage, double[] direction) {
        super(x, y);
        this.damage = damage;
        this.direction = direction;
        velX = direction[0] * SPEED;
        velY = direction[1] * SPEED;
        this.height = HEIGHT;
        this.width = WIDTH;
    }

    // MODIFY: this
    // EFFECT: move the bullet according to its direction
    public void move() {
        posX += velX;
        posY += velY;
    }

    // MODIFY: this
    // EFFECT: change the damage of this bullet
    public void setDamage(int damage) {
        this.damage = damage;
    }

    //EFFECT: return the damage of this bullet
    public int getDamage() {
        return damage;
    }

    //EFFECT: return the flying direction of current bullet
    public double[] getDirection() {
        return this.direction;
    }

    @Override
    // EFFECT: tick once
    public void tick(long timer) {
        move();
    }



    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(posX);
        printWriter.print(";");
        printWriter.print(posY);
        printWriter.print(";");
        printWriter.print(damage);
        printWriter.print(";");
        printWriter.print(velX);
        printWriter.print(";");
        printWriter.print(velY);
    }
}
