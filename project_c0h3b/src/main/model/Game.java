package model;

import java.util.ArrayList;

public class Game {
    public static final int WIDTH = 1440;
    public static final int HEIGHT = WIDTH / 16 * 9;

    private Player player;
    private ArrayList<BasicEnemy> enemies;
    private GameHandler gameHandler;

    // EFFECT: initiate the game class, set up player, list of enemies and handler
    public Game(double x,double y, Player player, ArrayList<BasicEnemy> enemies) {
        this.player = player;
        this.enemies = enemies;
        this.gameHandler = new GameHandler(player, enemies);
    }

    // MODIFY: this
    // EFFECT: add an enemy to the list of enemy
    public void addEnemy(BasicEnemy enemy) {
        enemies.add(enemy);
    }

    // EFFECT: return the number of enemies in the enemy list
    public int enemiesNumber() {
        return enemies.size();
    }

    // EFFECT: return the player
    public Player getPlayer() {
        return this.player;
    }

    // EFFECT: return the list of enemies.
    public ArrayList<BasicEnemy> getEnemies() {
        return this.enemies;
    }

    // EFFECT: tick once
    public void tickGame(long timer) {
        player.tick(timer);
        for (BasicEnemy enemy : enemies) {
            enemy.tick(timer);
        }
        gameHandler.handleTouchDamage();
        gameHandler.handleEnemyBullets();
        gameHandler.handleMeleeAttack();
        gameHandler.handlePlayerBullets();
        gameHandler.handleDefeated();
    }

}
