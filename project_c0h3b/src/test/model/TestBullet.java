package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBullet {
    private Bullet bullet;
    private int damage;
    private double[] direction;
    @BeforeEach
    public void setUp() {
        this.damage = 1;
        this.direction = new double[] { 3.0, 4.0 };
        bullet = new Bullet(0.0, 0.0, damage, direction);
    }

    @Test
    public void testBullet() {
        assertEquals(bullet.getPosX(), 0.0);
        assertEquals(bullet.getPosY(), 0.0);
        assertEquals(bullet.getDamage(), 1);
        assertEquals(bullet.getDirection(), direction);
    }

    @Test
    public void testMove() {
        bullet.move();
        assertEquals(bullet.getPosX(), 0.0 + bullet.getVelX());
        assertEquals(bullet.getPosY(), 0.0 + bullet.getVelY());
    }

    @Test
    public void testSetDamage() {
        bullet.setDamage(10);
        assertEquals(bullet.getDamage(), 10);
    }

    @Test
    public void testGetDamage() {
        assertEquals(bullet.getDamage(), damage);
    }

    @Test
    public void testGetDirection() {
        assertEquals(bullet.getDirection(), direction);
    }

    @Test
    public void testTick() {
        bullet.tick(2000);
        assertEquals(bullet.getPosX(), 15.0);
        assertEquals(bullet.getPosY(), 0.0 + bullet.getVelY());
    }

    @Test
    public void testInBoundary() {
        assertTrue(bullet.inBoundary(1.0, 1.0));
        assertTrue(bullet.inBoundary(0.0, 0.0));
        assertTrue(bullet.inBoundary(Game.WIDTH, Game.HEIGHT));
        assertFalse(bullet.inBoundary(-1.0, -1.0));
        assertFalse(bullet.inBoundary(Game.WIDTH + 1.0, Game.HEIGHT + 1.0));
    }

}
