package persistence;

import model.BasicEnemy;
import model.Bullet;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestWriter {
    private static final String TEST_FILE = "./data/gamesavetestwriter.txt";
    private Writer testWriter;
    private Player player;
    private LinkedList<BasicEnemy> enemies = new LinkedList<>();

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        double[] direction = new double[] {1.0, 2.0};
        Bullet bullet = new Bullet(1.0, 2.0, 10, direction);
        bullet.setVelX(1.0);
        bullet.setVelY(2.0);
        player = new Player(1.0, 2.0);
        player.setVelX(1.0);
        player.setVelY(2.0);
        player.getBullets().add(bullet);

        BasicEnemy enemy1 = new BasicEnemy(2.0, 3.0, player);
        enemy1.setVelX(10.0);
        enemy1.setVelY(20.0);
        enemy1.setHealth(20);
        enemy1.getBullets().add(bullet);
        BasicEnemy enemy2 = new BasicEnemy(3.0, 4.0, player);
        enemy2.setVelX(11.0);
        enemy2.setVelY(21.0);
        enemy2.setHealth(10);
        enemies.add(enemy1);
        enemies.add(enemy2);
    }

    @Test
    void testWritePlayer() {
        testWriter.write(player);
        testWriter.close();

        // now read them back in and verify that the accounts have the expected values
        try {
            Reader reader = new Reader(new File(TEST_FILE));
            Player player = reader.parsePlayer();
            assertEquals(1.0, player.getPosX());
            assertEquals(709.0, player.getPosY());
            assertEquals(100, player.getHealth());
            assertEquals(0.0, player.getVelX());
            assertEquals(2.0, player.getVelY());

            ArrayList<Bullet> bullets = player.getBullets();
            Bullet bullet = bullets.get(0);
            assertEquals(1.0, bullet.getPosX());
            assertEquals(2.0, bullet.getPosY());
            assertEquals(10, bullet.getDamage());
            assertEquals(1.0, bullet.getVelX());
            assertEquals(2.0, bullet.getVelY());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriteEnemies() {
        // save chequing and savings accounts to file
        for (BasicEnemy enemy : enemies) {
            testWriter.write(enemy);
        }
        testWriter.close();

        // now read them back in and verify that the accounts have the expected values
        try {
            Reader reader = new Reader(new File(TEST_FILE));
            ArrayList<BasicEnemy> enemies = reader.parseEnemies();
            BasicEnemy enemy1 = enemies.get(0);
            assertEquals(2.0, enemy1.getPosX());
            assertEquals(3.0, enemy1.getPosY());
            assertEquals(20, enemy1.getHealth());
            assertEquals(10.0, enemy1.getVelX());
            assertEquals(20.0, enemy1.getVelY());

            ArrayList<Bullet> bullets = enemy1.getBullets();
            Bullet bullet = bullets.get(0);
            assertEquals(1.0, bullet.getPosX());
            assertEquals(2.0, bullet.getPosY());
            assertEquals(10, bullet.getDamage());
            assertEquals(1.0, bullet.getVelX());
            assertEquals(2.0, bullet.getVelY());

            BasicEnemy enemy2 = enemies.get(1);
            assertEquals(3.0, enemy2.getPosX());
            assertEquals(4.0, enemy2.getPosY());
            assertEquals(10, enemy2.getHealth());
            assertEquals(11.0, enemy2.getVelX());
            assertEquals(21.0, enemy2.getVelY());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
