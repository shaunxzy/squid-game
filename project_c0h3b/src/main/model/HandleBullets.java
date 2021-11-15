package model;

import java.util.ArrayList;
import java.util.BitSet;

public class HandleBullets {
    ArrayList<Bullet> bullets;

    public HandleBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void bulletAttack(GameCharacter gc) {
        for (Bullet bullet : bullets) {
            if (GameHandler.isTouched(gc, bullet)) {
                gc.receiveDamage(bullet.getDamage());
            }
        }
    }

    public void updateBullets(ArrayList<Bullet> toRemove) {
        for (Bullet bullet : toRemove) {
            bullets.remove(bullet);
        }
    }

    public ArrayList<Bullet> touchedBullets(GameCharacter gc) {
        ArrayList<Bullet> touched = new ArrayList<>();
        for (Bullet bullet : bullets) {
            if (GameHandler.isTouched(gc, bullet)) {
                touched.add(bullet);
            }
        }

        return touched;
    }

    public ArrayList<Bullet> outBullets() {
        ArrayList<Bullet> outBoundary = new ArrayList<>();

        for (Bullet bullet : bullets) {
            double posX = bullet.getPosX();
            double posy = bullet.getPosY();
            if (GameCharacter.inBoundary(posX, posy)) {
                outBoundary.add(bullet);
            }
        }

        return outBoundary;
    }

}
