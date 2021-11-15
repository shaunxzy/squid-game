package model;


import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;

public class GameHandler {
    public static final int TOUCHDAMAGE = 10;
    
    private final Player player;
    private final ArrayList<BasicEnemy> enemies;
    private HandleBullets handlePlayer;
    private HandleBullets handleEnemy;


    public GameHandler(Player player, ArrayList<BasicEnemy> enemies) {
        this.player = player;
        this.enemies = enemies;
        handlePlayer = new HandleBullets(player.getBullets());
    }

    public void handleTouchDamage() {
        for (BasicEnemy enemy : enemies) {
            if (isTouched(player, enemy)) {
                player.receiveDamage(TOUCHDAMAGE);
            }
        }
    }


    public void handleEnemyBullets() {
        for (BasicEnemy enemy : enemies) {
            handleEnemy = new HandleBullets(enemy.getBullets());
            handleEnemy.bulletAttack(player);
            ArrayList<Bullet> touched = handleEnemy.touchedBullets(player);
            ArrayList<Bullet> outBoundary = handleEnemy.outBullets();
            handleEnemy.updateBullets(touched);
            handleEnemy.updateBullets(outBoundary);
        }

    }

    public void handlePlayerBullets() {

        for (BasicEnemy enemy : enemies) {
            handlePlayer = new HandleBullets(player.getBullets());
            handlePlayer.bulletAttack(enemy);
            ArrayList<Bullet> touched = handlePlayer.touchedBullets(enemy);
            handlePlayer.updateBullets(touched);
        }

        ArrayList<Bullet> outBoundary = handlePlayer.outBullets();
        handlePlayer.updateBullets(outBoundary);

    }

    public void handleMeleeAttack() {

        Melee melee = player.getMelee();
        for (BasicEnemy enemy : enemies) {
            if (melee.getAttack()) {
                if (isTouched(melee, enemy)) {
                    enemy.receiveDamage(melee.getDamage());
                }
            }
        }


    }

    public static boolean isTouched(GameCharacter chara1, GameCharacter chara2) {
        double chara1Right = chara1.getPosX() + chara1.getWidth();
        double chara1Left = chara1.getPosX();
        double chara1Up = chara1.getPosY();
        double chara1Down = chara1.getPosY() + chara1.getHeight();
        double chara2Right = chara2.getPosX() + chara2.getWidth();
        double chara2Left = chara2.getPosX();
        double chara2Up = chara2.getPosY();
        double chara2Down = chara2.getPosY() + chara2.getHeight();
        return (((chara1Right <= chara2Right && chara1Right >= chara2Left)
                || (chara1Left <= chara2Right && chara1Left >= chara2Left))
                && ((chara1Up >= chara2Up && chara1Up <= chara2Down)
                || (chara1Down >= chara2Up && chara1Down <= chara2Down)))
                || (((chara2Right <= chara1Right && chara2Right >= chara1Left)
                || (chara2Left <= chara1Right && chara2Left >= chara1Left))
                && ((chara2Up >= chara1Up && chara2Up <= chara1Down)
                || (chara2Down >= chara1Up && chara2Down <= chara1Down)));


    }

    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<BasicEnemy> getEnemies() {
        return enemies;
    }

    public void handleDefeated() {
        ArrayList<BasicEnemy> toRemove = new ArrayList<>();
        for (BasicEnemy enemy : enemies) {
            if (enemy.getHealth() <= 0) {
                toRemove.add(enemy);
            }
        }

        for (BasicEnemy enemy : toRemove) {
            enemies.remove(enemy);
        }
    }
}
