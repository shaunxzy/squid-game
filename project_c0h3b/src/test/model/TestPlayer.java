package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestPlayer {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(0.0, 0.0);
    }

    @Test
    public void testPlayer() {
        assertFalse(player == null);
    }

    @Test
    public void testMove() {
        player.setVelX(1.0);
        player.setVelY(709.0);
        double tempX = player.getPosX();
        double tempY = player.getPosY();
        player.move();
        assertEquals(player.getPosX(), tempX + 1.0);
        assertEquals(player.getPosY(), tempY);
    }

    @Test
    public void testMoveBoundaryLeft() {
        player.setVelX(-1.0);
        player.setVelY(1.0);
        double tempY = player.getPosY();
        player.move();
        assertEquals(player.getPosX(), 0.0);
        assertEquals(player.getPosY(), tempY);
    }

    @Test
    public void testMoveBoundaryRight() {
        player.setVelX(10000.0);
        player.setVelY(1.0);
        double tempY = player.getPosY();
        player.move();
        assertEquals(player.getPosX(), Game.WIDTH - player.getWidth());
        assertEquals(player.getPosY(), tempY);
    }

    @Test
    public void testMoveBoundaryUp() {
        player.setVelX(1.0);
        player.setVelY(-1.0);
        double tempX = player.getPosX();
        double tempY = player.getPosY();
        player.move();
        assertEquals(player.getPosX(), tempX + 1.0);
        assertEquals(player.getPosY(), 708.0);
    }

    @Test
    public void testMoveBoundaryDown() {
        player.setVelX(1.0);
        player.setVelY(1000.0);
        double tempX = player.getPosX();
        double tempY = player.getPosY();
        player.move();
        assertEquals(player.getPosX(), tempX + 1.0);
        assertEquals(player.getPosY(), 709.0);
    }

    @Test
    public void testSetAim() {
        assertEquals(player.getAim(), Direction.LEFT);
        player.setAim(Direction.UP);
        assertEquals(player.getAim(), Direction.UP);
    }

    @Test
    public void testFireBulletsLeft() {
        assertEquals(player.getBullets().size(), 0);
        player.fireBullets();
        assertEquals(player.getBullets().size(), 1);
        Bullet bullet = player.getBullets().get(0);
        assertEquals(bullet.getDirection()[0], -1.0);
        assertEquals(bullet.getDirection()[1], 0.0);
        assertEquals(bullet.getDamage(), 5);
        assertEquals(bullet.getPosX(), player.getPosX());
        assertEquals(bullet.getPosY(), player.getPosY());
    }

    @Test
    public void testFireBulletsRight() {
        assertEquals(player.getBullets().size(), 0);
        player.setAim(Direction.RIGHT);
        player.fireBullets();
        assertEquals(player.getBullets().size(), 1);
        Bullet bullet = player.getBullets().get(0);
        assertEquals(bullet.getDirection()[0], 1.0);
        assertEquals(bullet.getDirection()[1], 0.0);
        assertEquals(bullet.getDamage(), 5);
        assertEquals(bullet.getPosX(), player.getPosX());
        assertEquals(bullet.getPosY(), player.getPosY());
    }

    @Test
    public void testFireBulletsUp() {
        assertEquals(player.getBullets().size(), 0);
        player.setAim(Direction.UP);
        player.fireBullets();
        assertEquals(player.getBullets().size(), 1);
        Bullet bullet = player.getBullets().get(0);
        assertEquals(bullet.getDirection()[0], 0.0);
        assertEquals(bullet.getDirection()[1], -1.0);
        assertEquals(bullet.getDamage(), 5);
        assertEquals(bullet.getPosX(), player.getPosX());
        assertEquals(bullet.getPosY(), player.getPosY());
    }

    @Test
    public void testFireBulletsDown() {
        assertEquals(player.getBullets().size(), 0);
        player.setAim(Direction.DOWN);
        player.fireBullets();
        assertEquals(player.getBullets().size(), 1);
        Bullet bullet = player.getBullets().get(0);
        assertEquals(bullet.getDirection()[0], 0.0);
        assertEquals(bullet.getDirection()[1], 1.0);
        assertEquals(bullet.getDamage(), 5);
        assertEquals(bullet.getPosX(), player.getPosX());
        assertEquals(bullet.getPosY(), player.getPosY());
    }

    @Test
    public void testGetAim() {
        assertEquals(player.getAim(), Direction.LEFT);
        player.setAim(Direction.UP);
        assertEquals(player.getAim(), Direction.UP);
    }

    @Test
    public void testTick() {
        player.tick(2000);
        assertEquals(0.0, player.getPosX());
        assertEquals(709.0, player.getPosY());
        player.fireBullets();
        player.tick(2000);
        assertEquals(player.getBullets().size(), 1);
        player.setY(100.0);
        player.setX(100.0);
        player.fireBullets();
        player.tick(2000);
        assertEquals(player.getBullets().size(), 2);
        for (int i = 0; i < 9; i++) {
            player.tick(2000);
            assertEquals(player.getBullets().size(), 2);
        }

        player.tick(2000);
        assertEquals(player.getBullets().size(), 2);

        player.meleeAttack();
        Melee melee = player.getMelee();
        player.tick(2000);
        assertEquals(136.8, melee.getPosY());
        assertEquals(90.0, melee.getPosX());
    }

    @Test
    public void testGetBullets() {
        assertEquals(player.getBullets().size(), 0);
        player.fireBullets();
        assertEquals(player.getBullets().size(), 1);
    }

    @Test
    public void testMelee() {
        assertFalse(player.getIsJump());
        Melee melee = player.getMelee();
        player.setAim(Direction.LEFT);
        player.meleeAttack();
        assertTrue(melee.getAttack());
        assertEquals(player.getPosX() - melee.getWidth(), melee.getPosX());
        assertEquals(Direction.LEFT, melee.getDirection());
        player.setAim(Direction.RIGHT);
        assertEquals(player.getAim(), Direction.RIGHT);
        player.meleeAttack();
        assertTrue(melee.getAttack());
        assertEquals(player.getPosX() + player.getWidth(), melee.getPosX());
        assertEquals(Direction.RIGHT, melee.getDirection());
        assertEquals(player.getPosY() + player.getHeight() * 0.5, melee.getPosY());
    }
}
