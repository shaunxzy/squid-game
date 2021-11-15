package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlayerList {
    private Player player;
    private PlayerList playerList;

    @BeforeEach
    public void setUp() {
        player = new Player(0.0, 0.0);
        playerList = new PlayerList();
    }

    @Test
    public void testPlayerList() {
        assertTrue(playerList != null);
    }

    @Test
    public void tetAddPlayer() {
        assertEquals(playerList.getSize(), 0);
        playerList.addPlayer(player);
        assertEquals(playerList.getSize(), 1);
        assertEquals(playerList.getPlayer(0), player);
    }

    @Test
    public void testGetSize() {
        assertEquals(playerList.getSize(), 0);
        playerList.addPlayer(player);
        assertEquals(playerList.getSize(), 1);
    }

    @Test
    public void testRemovePlayer() {
        playerList.addPlayer(player);
        playerList.removePlayer(player);
        assertEquals(playerList.getSize(), 0);
    }

    @Test
    public void testGetPlayer() {
        assertEquals(playerList.getSize(), 0);
        playerList.addPlayer(player);
        assertEquals(playerList.getSize(), 1);
    }

}
