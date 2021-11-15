package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMelee {
    Melee melee;

    @BeforeEach
    public void setMelee() {
        melee = new Melee(1.0,2.0, 50, Direction.LEFT);
    }

    @Test
    public void testMelee() {
        melee.setAttack(false);
        melee.tick(1000);
        assertEquals(1.0, melee.getPosX());
        assertEquals(2.0, melee.getPosY());
        assertEquals(50, melee.getDamage());
        assertEquals(Direction.LEFT, melee.getDirection());
        melee.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, melee.getDirection());
        assertFalse(melee.getAttack());
        melee.setAttack(true);
        assertTrue(melee.getAttack());
        melee.tick(10000);
        assertEquals(1.0, melee.getPosX());
        assertEquals(2.0, melee.getPosY());
        melee.setDirection(Direction.LEFT);
        assertEquals(-9.0, melee.getPosX());
        assertEquals(2.0, melee.getPosY());
    }
}
