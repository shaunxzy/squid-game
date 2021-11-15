package ui;

import model.Direction;
import model.Game;
import model.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyBoardInput extends KeyAdapter {

    private static final double SPEED = 3;

    private final Set<Integer> pressedKeys = new HashSet<>();
    private Direction horizontalAim = Direction.LEFT;
    Game game;
    Main main;

    Player player;

    public KeyBoardInput(Main main) {
        this.main = main;
        this.game = main.getGame();
        this.player = game.getPlayer();
    }

    public synchronized void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            System.out.println("Stop!");
            main.getPause();
            main.setPause(true);
            System.out.println(main.getPause());
            // System.out.println(main.getPause());
        }
        // System.out.println(key);
        pressedKeys.add(key);
        // System.out.println(pressedKeys.size());
        if (!pressedKeys.isEmpty()) {
            for (int singleKey : pressedKeys) {
                // System.out.println(singleKey);
                motion(singleKey, SPEED);
                fire(singleKey);
                saveGame(singleKey);
            }
        }

    }

    public void motion(int key, double speed) {
        switch (key) {
            case KeyEvent.VK_K:
                // System.out.println(player.getPosY());
                if (!player.getIsJump()) {
                    player.setVelY(-10);
                    player.setIsJump(true);
                }
                break;
            // System.out.println(player.getPosY());
            case KeyEvent.VK_W:
                player.setAim(Direction.UP);
                break;
            case KeyEvent.VK_A:
                player.setAim(Direction.LEFT);
                player.setVelX(-speed);
                horizontalAim = Direction.LEFT;
                break;
            case KeyEvent.VK_D:
                player.setAim(Direction.RIGHT);
                player.setVelX(speed);
                horizontalAim = Direction.RIGHT;
                break;
        }
    }

    public void fire(int key) {
        if (key == KeyEvent.VK_I) {
            player.fireBullets();
        } else if (key == KeyEvent.VK_J) {
            player.meleeAttack();
        }
    }

    public void saveGame(int key) {
        if (key == KeyEvent.VK_Q) {
            main.saveGame();
        }
    }

    public synchronized void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys.remove(key);
        motion(key, 0);
        if (!pressedKeys.contains(KeyEvent.VK_W)) {
            player.setAim(horizontalAim);
        }
        if (!pressedKeys.contains(KeyEvent.VK_J)) {
            player.getMelee().setAttack(false);
        }
        // System.out.println(player.getMelee().getAttack());
        if (!pressedKeys.isEmpty()) {
            for (int singleKey : pressedKeys) {
                //System.out.println(singleKey);
                motion(singleKey, SPEED);
                fire(singleKey);
            }
        }
    }

}
