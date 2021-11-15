package model;

import java.util.LinkedList;

public class PlayerList {
    private LinkedList<Player> listPlayer;

    public PlayerList() {
        listPlayer = new LinkedList();
    }

    // MODIFY: this
    // EFFECT: Add a player to the list
    public void addPlayer(Player player) {
        listPlayer.add(player);
    }

    //EFFECT: return the length of the list
    public int getSize() {
        return listPlayer.size();
    }

    // REQUIRE: the player list is not empty
    // MODIFY: this
    // EFFECT: remove one player from the list
    public void removePlayer(Player player) {
        listPlayer.remove(player);
    }

    // REQUIRE: the player list is not empty
    // EFFECT: return the player given the index
    public Player getPlayer(int index) {
        return listPlayer.get(index);
    }






}
