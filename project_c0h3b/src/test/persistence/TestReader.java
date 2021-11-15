package persistence;

import model.BasicEnemy;
import model.Bullet;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class TestReader {
    private File file;
    private Reader reader;

    @Test
    public void testConstructorFail1() {
        try {
            reader = new Reader(new File("./data/gamesavefail1.txt"));
        } catch(IOException e) {
            System.out.println("Should not fail");
            fail();
        }
    }

    @Test
    public void testPlayerFail1() {
        try {
            reader = new Reader(new File("./data/gamesavefail1.txt"));
            Player player = Reader.parsePlayer();
            assertEquals(0.0, player.getPosX());
            assertEquals(709.0, player.getPosY());
            assertEquals(100, player.getHealth());
            assertEquals(0.0, player.getVelX());
            assertEquals(0.0, player.getVelY());

            ArrayList<Bullet> bullets = player.getBullets();
            assertEquals(0, bullets.size());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testPlayerFailWithBullets() {
        try {
            reader = new Reader(new File("./data/gamesavefail2.txt"));
            Player player = Reader.parsePlayer();
            assertEquals(0.0, player.getPosX());
            assertEquals(709.0, player.getPosY());
            assertEquals(100, player.getHealth());
            assertEquals(0.0, player.getVelX());
            assertEquals(0.0, player.getVelY());

            ArrayList<Bullet> bullets = player.getBullets();
            assertEquals(1, bullets.size());
            Bullet bullet = bullets.get(0);
            assertEquals(10.0, bullet.getPosX());
            assertEquals(15.0, bullet.getPosY());
            assertEquals(10, bullet.getDamage());
            assertEquals(20.0, bullet.getVelX());
            assertEquals(25.0, bullet.getVelY());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testEnemiesfail() {
        try {
            reader = new Reader(new File("./data/gamesavefail3.txt"));
            ArrayList<BasicEnemy> enemies = Reader.parseEnemies();
            BasicEnemy enemy1 = enemies.get(0);
            assertEquals(50.0, enemy1.getPosX());
            assertEquals(10.0, enemy1.getPosY());
            assertEquals(100, enemy1.getHealth());
            assertEquals(2.0, enemy1.getVelX());
            assertEquals(3.0, enemy1.getVelY());

            ArrayList<Bullet> bullets1 = enemy1.getBullets();
            assertEquals(1, bullets1.size());
            Bullet bullet1 = bullets1.get(0);
            assertEquals(10.0, bullet1.getPosX());
            assertEquals(15.0, bullet1.getPosY());
            assertEquals(10, bullet1.getDamage());
            assertEquals(20.0, bullet1.getVelX());
            assertEquals(25.0, bullet1.getVelY());

            BasicEnemy enemy2 = enemies.get(1);
            assertEquals(60.0, enemy2.getPosX());
            assertEquals(70.0, enemy2.getPosY());
            assertEquals(10, enemy2.getHealth());
            assertEquals(9.0, enemy2.getVelX());
            assertEquals(45.0, enemy2.getVelY());

            ArrayList<Bullet> bullets = enemy2.getBullets();
            assertEquals(1, bullets.size());
            Bullet bullet2 = bullets.get(0);
            assertEquals(12.0, bullet2.getPosX());
            assertEquals(14.0, bullet2.getPosY());
            assertEquals(10, bullet2.getDamage());
            assertEquals(25.0, bullet2.getVelX());
            assertEquals(23.0, bullet2.getVelY());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            reader = new Reader(new File("./path/does/not/exist/test.txt"));
            fail();
        } catch (IOException e) {
            // expected
        }
    }
}
