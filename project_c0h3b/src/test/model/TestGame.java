package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import java.util.ArrayList;

public class TestGame {

    private Game game;
    private Player player;
    private ArrayList<BasicEnemy> enemies;
    private GameHandler gameHandler;
    private BasicEnemy enemy;
    private Main main;

    @BeforeEach
    public void setUp() {
        player = new Player(0.0, 0.0);
        enemies = new ArrayList<>();
        gameHandler = new GameHandler(player, enemies);
        game = new Game(0.0, 0.0, player, enemies);
        enemy = new BasicEnemy(0.0, 0.0, player);
    }

    @Test
    public void testGame() {
        assertFalse(game.getPlayer() == null);
        assertFalse(game.getEnemies() == null);
    }

    @Test
    public void testAddEnemy () {

        game.addEnemy(enemy);
        assertEquals(game.getEnemies().get(0), enemy);
    }

    @Test
    public void testEnemiesNumber() {
        assertEquals(game.enemiesNumber(), 0);
        game.addEnemy(enemy);
        assertEquals(game.enemiesNumber(), 1);
    }

    @Test
    public void testGetPlayer() {
        assertEquals(game.getPlayer(), player);
    }

    @Test
    public void testGetEnemies() {
        assertEquals(game.getEnemies(), enemies);
    }

    @Test
    public void tick() {
        game.tickGame(2000);
        assertEquals(0, game.getEnemies().size());
        enemies.add(enemy);
        game.tickGame(2000);
        assertEquals(enemy.getPosX(), 0.0);
        assertEquals(enemy.getPosY(), 0.1);
        assertEquals(player.getPosX(), 0.0);
        assertEquals(player.getPosY(), 709.0);
        BasicEnemy enemy = enemies.get(0);
        enemy.setHealth(0);
        game.tickGame(2000);
        assertEquals(0, game.getEnemies().size());

    }






}
