package model;

import persistence.Reader;
import persistence.Saveable;

import java.awt.*;
import java.io.PrintWriter;
import java.lang.Math;
import java.util.ArrayList;
import java.util.LinkedList;

public class BasicEnemy extends GameCharacter implements Saveable, CanFireBullets {
    private static final double SPEED = 0.1;
    private static final int BULLETDAMAGE = 5;

    protected Player player;
    protected ArrayList<Bullet> bullets;


    public BasicEnemy(double x, double y, Player player) {
        super(x, y);
        this.velX = 0.0;
        this.velY = 0.0;
        this.player = player;
        this.height = 50;
        this.width = 60;
        bullets = new ArrayList<>();
    }

    // MODIFY: this
    // EFFECT: move the position of the enemy
    public void move() {
        changeSpeed();
        posX += velX;
        posY += velY;

    }

    // MODIFY: this
    // EFFECT: change the velocity of enemy according to player's position
    public void changeSpeed() {
        double[] direction = getPlayerDirection();
        double tempX = direction[0];
        double tempY = direction[1];
        this.velX = tempX * SPEED;
        this.velY = tempY * SPEED;
    }

    // MODIFY: this
    // EFFECT: add a new bullet, which aims at the player into enemies bullets
    public void fireBullets() {
        double[] direction = getPlayerDirection();
        double bulletPosX = posX + width / 2;
        double bulletPosY = posY + height / 2;
        Bullet bullet = new Bullet(bulletPosX, bulletPosY, BULLETDAMAGE, direction);
        bullets.add(bullet);
    }

    // MODIFY: LinkedList<Bullet>
    // EFFECT: add a bullet to bullets
    public void addBullets(Bullet bullet) {
        // System.out.println(bullets.size());
        bullets.add(bullet);
    }

    // EFFECT: update the status of the enemy, if timer is right,
    // fire a bullet,their bullets are out of boundary, delete them
    public void tick(long timer) {
        move();
        LinkedList<Bullet> indexToRemove = new LinkedList<>();
        if (timer % 8000 == 0) {
            fireBullets();
        }
        for (Bullet bullet : bullets) {
            // System.out.println(bullets.size());
            bullet.tick(timer);
            if (!inBoundary(bullet.getPosX(), bullet.getPosY())) {
                indexToRemove.add(bullet);
            }
        }
    }


    // EFFECT: return the position of player relative to the enemy
    public double[] getPlayerDirection() {
        double tempX = player.getPosX() - posX;
        double tempY = player.getPosY() - posY;
        double distance = Math.pow(Math.pow(tempX, 2) + Math.pow(tempY, 2), 0.5);
        return new double[] {tempX / distance, tempY / distance};
    }

    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }

    // EFFECT: return the number of bullets this enemy fired
    public int getBulletsNumber() {
        return bullets.size();
    }


    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public Bullet getBullet(int i) {
        return bullets.get(i);
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print("Enemy");
        printWriter.print(Reader.SPLITTER);
        printWriter.print(posX);
        printWriter.print(Reader.SPLITTER);
        printWriter.print(posY);
        printWriter.print(Reader.SPLITTER);
        printWriter.print(health);
        printWriter.print(Reader.SPLITTER);
        printWriter.print(velX);
        printWriter.print(Reader.SPLITTER);
        printWriter.print(velY);
        printWriter.print(Reader.SPLITTER);
        printWriter.print("Bullets");
        printWriter.print(Reader.SPLITTER);
        printWriter.print(bullets.size());
        for (Bullet bullet : bullets) {
            printWriter.print(Reader.SPLITTER);
            bullet.save(printWriter);
        }
        printWriter.print("\n");
    }
}
