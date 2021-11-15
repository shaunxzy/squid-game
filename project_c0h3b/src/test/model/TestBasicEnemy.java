package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;


class TestBasicEnemy {
    private BasicEnemy basicEnemy;
    private Player player;
    private double[] testDirection;

    @BeforeEach
    public void setUp() {
        player = new Player(3.0, 4.0);
        basicEnemy = new BasicEnemy(0.0,0.0, player);
        testDirection = basicEnemy.getPlayerDirection();
    }

    @Test
    public void testBasicEnemy() {
        assertEquals(basicEnemy.getPosY(), 0.0);
        assertEquals(basicEnemy.getPosX(), 0.0);
        assertEquals(basicEnemy.getVelX(), 0.0);
        assertEquals(basicEnemy.getVelY(), 0.0);
        assertEquals(basicEnemy.getHealth(), 100);
        assertEquals(basicEnemy.isDefeated(), false);
    }

    @Test
    public void testMove() {
        double beforeX = basicEnemy.getPosX();
        double beforeY = basicEnemy.getPosY();
        basicEnemy.move();
        assertEquals(basicEnemy.getPosX(), beforeX + testDirection[0] * 0.1);
        assertEquals(basicEnemy.getPosY(), beforeY + testDirection[1] * 0.1);
    }

    @Test
    public void testChangeSpeed() {
        basicEnemy.changeSpeed();
        double[] testDirection = basicEnemy.getPlayerDirection();
        assertEquals(basicEnemy.getVelX(), testDirection[0] * 0.1);
        assertEquals(basicEnemy.getVelY(), testDirection[1] * 0.1);

    }

    @Test
    public void testFireBullets() {
        basicEnemy.fireBullets();
        double[] testDirection = basicEnemy.getPlayerDirection();
        assertEquals(basicEnemy.getBulletsNumber(), 1);
        assertEquals(basicEnemy.getBullets().get(0).getPosX(), 30.0);
        assertEquals(basicEnemy.getBullets().get(0).getPosY(), 25.0);
        assertEquals(basicEnemy.getBullets().get(0).getDamage(), 5);
        assertEquals(basicEnemy.getBullets().get(0).getDirection()[0], testDirection[0]);
        assertEquals(basicEnemy.getBullets().get(0).getDirection()[1], testDirection[1]);
    }

    @Test
    public void testGetPlayerDirection() {
        double[] distance = new double[] { 3.0 / 5.0, 4.0 / 5.0};
        assertEquals(basicEnemy.getPlayerDirection()[0], 0.00423127, 0.00001);
        assertEquals(basicEnemy.getPlayerDirection()[1], 0.99999105, 0.00001);
    }

    @Test
    public void testGetBullets() {
        assertEquals(basicEnemy.getBullets().size(), 0);
        Bullet bullet = new Bullet(0.0,0.0, 0, new double[] {0.0, 0.0});
        basicEnemy.addBullets(bullet);
        LinkedList<Bullet> testBullets = new LinkedList<>();
        testBullets.add(bullet);
        assertEquals(basicEnemy.getBullets(), testBullets);
    }

    @Test
    public void testGetBulletsNumber() {
        assertEquals(basicEnemy.getBulletsNumber(), 0);
        basicEnemy.fireBullets();
        assertEquals(basicEnemy.getBulletsNumber(), 1);
    }

    @Test
    public void testTick() {
        basicEnemy.tick(2000);
        assertEquals(0, basicEnemy.getBullets().size());
        basicEnemy.setX(100.0);
        basicEnemy.setY(100.0);
        player.setX(0.0);
        player.setY(100.0);
        basicEnemy.tick(8000);
        assertEquals(basicEnemy.getBullets().size(), 1);
        Bullet bullet = basicEnemy.getBullets().get(0);
        assertEquals(bullet.getDirection()[0], -1.0);
        assertEquals(bullet.getDirection()[1], 0.0);
        for (int i = 0; i < 8; i++) {
            basicEnemy.tick(2000);
            assertEquals(basicEnemy.getBullets().size(), 1);
        }

        basicEnemy.tick(2000);
        assertEquals(basicEnemy.getBullets().size(), 1);

        double[] direction = new double[] {1.0, 0.0};
        Bullet bulletInvalid = new Bullet(-1, -1, 10, direction);
        basicEnemy.getBullets().add(bulletInvalid);
        basicEnemy.tick(123123);
        assertEquals(basicEnemy.getBullets().size(), 2);
    }

    @Test
    public void testReceiveDamage() {
        basicEnemy.receiveDamage(10);
        assertEquals(basicEnemy.getHealth(), 90);
        basicEnemy.receiveDamage(90);
        assertEquals(basicEnemy.getHealth(), 0);
        basicEnemy.receiveDamage(10);
        assertEquals(basicEnemy.getHealth(), 0);
    }

    @Test
    public void testGetHealth() {
        assertEquals(basicEnemy.getHealth(), 100);
    }

    @Test
    public void testSetX() {
        basicEnemy.setX(1.0);
        assertEquals(basicEnemy.getPosX(), 1.0);
    }

    @Test
    public void testSetY() {
        basicEnemy.setY(1.0);
        assertEquals(basicEnemy.getPosY(), 1.0);
    }

    @Test
    public void testSetVelX() {
        basicEnemy.setVelX(1.0);
        assertEquals(basicEnemy.getVelX(), 1.0);
    }

    @Test
    public void testSetVelY() {
        basicEnemy.setVelY(1.0);
        assertEquals(basicEnemy.getVelY(), 1.0);
    }

    @Test
    public void testSetHealth() {
        basicEnemy.setHealth(80);
        assertEquals(basicEnemy.getHealth(), 80);
    }

    @Test
    public void testGetPosX() {
        assertEquals(basicEnemy.getPosX(), 0.0);
        basicEnemy.setX(1.0);
        assertEquals(basicEnemy.getPosX(), 1.0);
    }

    @Test
    public void testGetPosY() {
        assertEquals(basicEnemy.getPosY(), 0.0);
        basicEnemy.setY(1.0);
        assertEquals(basicEnemy.getPosY(), 1.0);
    }

    @Test
    public void testGetVelX() {
        basicEnemy.setVelX(1.0);
        assertEquals(basicEnemy.getVelX(), 1.0);
    }

    @Test
    public void testGetVelY() {
        basicEnemy.setVelY(1.0);
        assertEquals(basicEnemy.getVelY(), 1.0);
    }

    @Test
    public void testSetWidth() {
        basicEnemy.setWidth(10.0);
        assertEquals(basicEnemy.getWidth(), 10.0);
    }

    @Test
    public void testSetHeight() {
        basicEnemy.setHeight(10.0);
        assertEquals(basicEnemy.getHeight(), 10.0);
    }

    @Test
    public void testGetHeight() {
        assertEquals(basicEnemy.getHeight(), 50.0);
    }

    @Test
    public void testGetWidth() {
        assertEquals(basicEnemy.getWidth(), 60.0);
    }

    @Test
    public void testInboundary() {
        assertFalse(basicEnemy.inBoundary(-1.0, 0.0));
        assertFalse(basicEnemy.inBoundary(0.0, -1.0));
        assertFalse(basicEnemy.inBoundary(-1.0, -1.0));
        assertTrue(basicEnemy.inBoundary(1440.0, 0.0));
        assertFalse(basicEnemy.inBoundary(1441.0, 0.0));
        assertTrue(basicEnemy.inBoundary(1440.0, 810.0));
        assertFalse(basicEnemy.inBoundary(1440.0, 811.0));
        assertFalse(basicEnemy.inBoundary(1441.0, 810.0));
        assertFalse(basicEnemy.inBoundary(1441.0, 811.0));
        assertTrue(basicEnemy.inBoundary(0.0, 0.0));

    }

}