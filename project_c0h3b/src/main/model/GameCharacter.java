package model;

import persistence.Saveable;

public abstract class GameCharacter {
    protected double  posX;
    protected double posY;
    protected double velX;
    protected double velY;

    protected double height;
    protected double width;

    protected int health;
    protected boolean defeated;

    public GameCharacter(double x, double y) {
        posX = x;
        posY = y;

        health = 100;
        defeated = false;
    }

    //EFFECT: determine whether or not the character is in game panel
    public static boolean inBoundary(double posX, double posY) {
        return 0 <= posX && Game.WIDTH >= posX
                && 0 <= posY && Game.HEIGHT >= posY;
    }

    public abstract void tick(long timer);

    // public abstract void render(Graphics graphics);

    //REQUIRES damage >= 0
    //MODIFY: this
    //EFFECT: deduct health from the character according to the damage value.
    public void receiveDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.defeated = true;
        }
    }

    //REQUIRES: x > 0
    //MODIFY: this
    //EFFECT: change the x position of character
    public void setX(double x) {
        this.posX = x;
    }

    //REQUIRES: y > 0
    //MODIFY: this
    //EFFECT: change the y position of character
    public void setY(double y) {
        this.posY = y;
    }

    //MODIFY: this
    //EFFECT: change the x-axis speed of character
    public void setVelX(double velX) {
        this.velX = velX;
    }

    //MODIFY: this
    //EFFECT: change the y-axis speed of character
    public void setVelY(double velY) {
        this.velY = velY;
    }

    //MODIFY: this
    //EFFECT: change the health of current character
    public void setHealth(int health) {
        this.health = health;
    }

    //EFFECT: return the value of x position
    public double getPosX() {
        return posX;
    }

    //EFFECT: return the value of y position
    public double getPosY() {
        return posY;
    }

    //EFFECT: return the value of x-axis speed
    public double getVelX() {
        return velX;
    }

    ////EFFECT: return the value of y-axis speed
    public double getVelY() {
        return velY;
    }

    //EFFECT: return the value of width
    public double getWidth() {
        return width;
    }

    //EFFECT: return the value of height
    public double getHeight() {
        return height;
    }

    //EFFECT: return health of the current character
    public int getHealth() {
        return health;
    }

    //EFFECT: return the value of defeated
    public boolean isDefeated() {
        return this.defeated;
    }

    //EFFECT: set the width of character
    public void setWidth(double width) {
        this.width = width;
    }

    //EFFECT: set if the character is defeated
    public void setIsDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    //EFFECT: set the height of character
    public void setHeight(double height) {
        this.height = height;
    }
}
