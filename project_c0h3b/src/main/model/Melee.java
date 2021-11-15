package model;


public class Melee extends GameCharacter {
    private static final double HEIGHT = 15;
    private static final double WIDTH = 10;

    private int damage;
    private Direction direction;
    private boolean attack;

    public Melee(double x, double y, int damage, Direction direction) {
        super(x, y);
        this.damage = damage;
        this.direction = direction;
        attack = false;
        this.height = HEIGHT;
        this.width = WIDTH;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public boolean getAttack() {
        return attack;
    }




    @Override
    public void tick(long timer) {
        if (attack) {
            posX += velX;
            posY += velY;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        if (direction == Direction.LEFT) {
            setX(getPosX() - WIDTH);
        }
    }

    public int getDamage() {
        return damage;
    }
}
