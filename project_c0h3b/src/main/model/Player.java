package model;

import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import static model.Direction.LEFT;
import static model.Direction.RIGHT;

import persistence.Reader;
import persistence.Saveable;
import ui.Main;

public class Player extends GameCharacter implements Saveable, CanFireBullets {
    private static final double HEIGHT = 20.0;
    private static final double WIDTH = 10.0;
    private static final int BULLETDAMAGE = 5;
    private static final int MELEEDAMAGE = 30;
    private static final double GROUND = Main.HEIGHT * 9 / 10 - HEIGHT;

    private ArrayList<Bullet> bullets;
    private Direction aim;
    private Melee melee;

    private boolean isJump;

    //EFFECT: initiate player, set position, velocity, size, bullets and its aim
    public Player(double x, double y) {
        super(x, y);
        initializeXY();
        this.velX = 0;
        this.velY = 0;
        this.height = HEIGHT;
        this.width = WIDTH;
        bullets = new ArrayList<>();
        aim = LEFT;
        melee = new Melee(x, y, MELEEDAMAGE, aim);
        isJump = false;
    }

    // MODIFY: this
    // EFFECT: Initialize the position of player, make it stand above the ground
    public void initializeXY() {
        setX(posX);
        setY(GROUND);
    }

    // MODIFY: this
    // EFFECT: move the position of our character, if touched boundary, set the position to boundary value
    public void move() {
        double tempX = posX + velX;
        double tempY = posY + velY;
        // System.out.println(tempX);
        if (tempY > GROUND) {
            setY(GROUND);
            setVelY(0);
            setIsJump(false);
        } else {
            setY(tempY);
            velY = velY + 0.4;
        }
        if (tempX > (Game.WIDTH - width)) {
            posX = Game.WIDTH - width;
        } else if (tempX < 0) {
            posX = 0;
        } else {
            posX += velX;
        }
    }

    //MODIFY: this
    //EFFECT: set the aim of player's gun
    public void setAim(Direction direction) {
        aim = direction;
    }

    // MODIFY: this
    // EFFECT: fire a bullet according to player's aim
    public void fireBullets() {
        double[] direction;
        switch (aim) {
            case LEFT:
                direction = new double[] {-1.0, 0.0};
                bullets.add(new Bullet(posX, posY, BULLETDAMAGE, direction));
                break;
            case RIGHT:
                direction = new double[] {1.0, 0.0};
                bullets.add(new Bullet(posX, posY, BULLETDAMAGE, direction));
                break;
            case UP:
                direction = new double[] {0.0, -1.0};
                bullets.add(new Bullet(posX, posY, BULLETDAMAGE, direction));
                break;
            case DOWN:
                direction = new double[] {0.0, 1.0};
                bullets.add(new Bullet(posX, posY, BULLETDAMAGE, direction));
                break;
        }
    }

    // EFFECT: return the aim of current player
    public Direction getAim() {
        return aim;
    }

    // MODIFY: LinkedList<Bullet>
    // EFFECT: tick once, move the character, if bullets fly off the screen, delete it
    public void tick(long timer) {
        LinkedList<Bullet> indexToRemove = new LinkedList<>();
        move();

        //tick bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.tick(timer);
            // System.out.println(Double.toString(bullet.getPosX()) + " " + Double.toString(getPosY()));
            if (!inBoundary(bullet.getPosX(), bullet.getPosY())) {
                indexToRemove.add(bullet);
            }
        }

        //tick melee
        if (melee.getAttack()) {
            melee.setVelX(velX);
            melee.setVelY(velY);
            melee.tick(timer);
        }
    }



    public ArrayList<Bullet> getBullets() {
        return bullets;
    }


    public boolean getIsJump() {
        return isJump;
    }

    public void setIsJump(boolean isJump) {
        this.isJump = isJump;
    }

    public Melee getMelee() {
        return this.melee;
    }

    public void meleeAttack() {
        if (aim == LEFT) {
            melee.setX(posX);
            melee.setDirection(LEFT);
        } else if (aim == RIGHT) {
            melee.setX(posX + WIDTH);
            melee.setDirection(RIGHT);
        }
        melee.setY(posY + 0.5 * HEIGHT);
        melee.setAttack(true);
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print("Player");
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
        for (int i = 0; i < bullets.size(); i++) {
            printWriter.print(Reader.SPLITTER);
            bullets.get(i).save(printWriter);
        }
        printWriter.print("\n");
    }
}
