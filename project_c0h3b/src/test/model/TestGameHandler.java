package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

public class TestGameHandler {
    private GameHandler gameHandler;
    private Player player;
    private BasicEnemy enemy;
    private ArrayList<BasicEnemy> enemies;

    @BeforeEach
    public void setUp() {
        enemies = new ArrayList<>();
        player = new Player(0.0, 0.0);
        enemy = new BasicEnemy(1.0, 709.0, player);
        enemies.add(enemy);
        gameHandler = new GameHandler(player, enemies);
    }

    @Test
    public void testGameHandler() {
        assertFalse(gameHandler.getPlayer() == null);
        assertFalse(gameHandler.getEnemies() == null);
    }

    @Test
    public void testHandleTouchDamage() {
        gameHandler.handleTouchDamage();
        assertEquals(90, player.getHealth());
        player.setX(100.0);
        player.setY(100.0);
        gameHandler.handleTouchDamage();
        assertEquals(90, player.getHealth());
    }

    @Test
    public void testHandleEnemyBullets() {
        double[] direction = enemy.getPlayerDirection();
        Bullet bullet = new Bullet(100.0, 100.0, 10, direction);
        enemy.addBullets(bullet);
        gameHandler.handleEnemyBullets();
        assertEquals(100, player.getHealth());
        Bullet bulletShot = new Bullet(0.0, 709.0, 10, direction);
        enemy.addBullets(bulletShot);
        gameHandler.handleEnemyBullets();
        assertEquals(90, player.getHealth());


    }

    @Test
    public void testHandlePlayerBullets() {
        player.fireBullets();
        gameHandler.handlePlayerBullets();
        assertEquals(95, enemy.getHealth());
        enemy.setX(1.0);
        enemy.setY(1.0);
        gameHandler.handlePlayerBullets();
        assertEquals(95, enemy.getHealth());
    }

    @Test
    public void testIsTouched() {
        GameCharacter tempPlayer = player;
        GameCharacter tempEnemy = enemy;
        assertTrue(gameHandler.isTouched(tempPlayer, tempEnemy));
        player.setX(100.0);
        player.setY(100.0);
        assertFalse(gameHandler.isTouched(tempPlayer, tempEnemy));
        enemy.setX(100.0);
        enemy.setY(100.0);
        player.setY(150.0);
        assertTrue(gameHandler.isTouched(tempPlayer, tempEnemy));
        player.setY(125.0);
        assertTrue(gameHandler.isTouched(tempPlayer, tempEnemy));
        player.setY(100.0);
        assertTrue(gameHandler.isTouched(tempPlayer, tempEnemy));
        player.setY(80.0);
        assertTrue(gameHandler.isTouched(tempPlayer, tempEnemy));
        player.setX(90);
        assertTrue(gameHandler.isTouched(tempPlayer, tempEnemy));
        player.setX(95);
        assertTrue(gameHandler.isTouched(tempPlayer, tempEnemy));
        player.setX(160);
        assertTrue(gameHandler.isTouched(tempPlayer, tempEnemy));
        player.setX(170);
        assertFalse(gameHandler.isTouched(tempPlayer, tempEnemy));
        player.setX(190);
        assertFalse(gameHandler.isTouched(tempPlayer, tempEnemy));
    }

    @Test
    public void testGetPlayer() {
        assertEquals(gameHandler.getPlayer(), player);
    }

    @Test
    public void testGetEnemies() {
        assertEquals(gameHandler.getEnemies(), enemies);
    }

    @Test
    public void testHandleMeleeAttack() {
        this.enemy = enemies.get(0);
        player.meleeAttack();
        gameHandler.handleMeleeAttack();
        assertEquals(100, enemy.getHealth());
        enemy.setX(0.0);
        enemy.setY(709.0);
        player.setX(0.0);
        player.setY(709.0);
        player.meleeAttack();
        assertTrue(player.getMelee().getAttack());
        gameHandler.handleMeleeAttack();
        assertEquals(70, enemy.getHealth());

    }
}
