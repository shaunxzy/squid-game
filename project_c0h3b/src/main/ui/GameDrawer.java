package ui;

import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameDrawer extends Canvas {

    private GraphicsDevice graphicsDevice;
    private JFrame frame;
    private static final String ENEMYPATH = "D:\\UBC\\cpsc210\\Lec\\project_c0h3b\\texture\\jellyfish.png";

    public GameDrawer(int width, int height, String title, Main main) throws InterruptedException {
        frame = new JFrame(title);
        // frame.setUndecorated(true);
        // frame.setIgnoreRepaint(true);
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(main);
        frame.setVisible(true);

        main.start();

    }

    //EFFECT: draw game
    public void renderGame(Graphics graphics, Game game) throws IOException {
        Player player = game.getPlayer();
        ArrayList<BasicEnemy> enemies = game.getEnemies();
        renderPlayer(graphics, player);
        for (int i = 0; i < enemies.size(); i++) {
            BasicEnemy enemy = enemies.get(i);
            renderEnemy(graphics, enemy);
        }
    }

    public void renderPlayer(Graphics graphics, Player player) {
        graphics.setColor(Color.red);
        graphics.fillRect(15, 10, 160, 16);
        graphics.setColor(Color.yellow);
        graphics.fillRect(15, 10, 160 * player.getHealth() / 100, 16);
        graphics.setColor(Color.white);
        graphics.fillRect((int) player.getPosX(), (int) player.getPosY(),
                (int) player.getWidth(), (int) player.getHeight());

        ArrayList<Bullet> bullets = player.getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            try {
                Bullet bullet = bullets.get(i);
                renderBullet(graphics, bullet);
            } catch (NullPointerException e) {
                System.out.println("error");
                System.out.println("i is " + Integer.toString(i));
                System.out.println("size is " + Integer.toString(bullets.size()));
                System.out.println(bullets);
                e.printStackTrace();
                return;
            }
        }


        Melee melee = player.getMelee();
        renderMelee(graphics, melee);
    }

    public void renderBullet(Graphics graphics, Bullet bullet) {
        graphics.setColor(Color.yellow);
        graphics.fillRect((int) bullet.getPosX(), (int) bullet.getPosY(),
                (int) bullet.getWidth(), (int) bullet.getHeight());
    }

    public void renderMelee(Graphics graphics, Melee melee) {
        if (melee.getAttack()) {
            graphics.setColor(Color.white);
            graphics.fillRect(
                    (int) melee.getPosX(),
                    (int) melee.getPosY(),
                    (int) melee.getWidth(),
                    (int) melee.getHeight());
        }
    }

    public void renderEnemy(Graphics graphics, BasicEnemy enemy) throws IOException {
        Image enemyImage = loadImage(ENEMYPATH);
        graphics.drawImage(enemyImage, (int) enemy.getPosX(), (int) enemy.getPosY(),
                (int) enemy.getWidth(), (int) enemy.getWidth(),null);

        ArrayList<Bullet> bullets = enemy.getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            // System.out.println(i < bullets.size());
            Bullet bullet = bullets.get(i);
            renderBullet(graphics, bullet);
        }
    }

    public void renderStartMenu(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(MouseInput.POSITIONX[0], MouseInput.POSITIONY[0], MouseInput.BOXWIDTH, MouseInput.BOXHEIGHT);
        graphics.fillRect(MouseInput.POSITIONX[0], MouseInput.POSITIONY[1], MouseInput.BOXWIDTH, MouseInput.BOXHEIGHT);
        graphics.fillRect(MouseInput.POSITIONX[0], MouseInput.POSITIONY[2], MouseInput.BOXWIDTH, MouseInput.BOXHEIGHT);
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Elephant", 1, 48));
        graphics.drawString("New Game", MouseInput.POSITIONX[0] + 50, MouseInput.POSITIONY[0] + 70);
        graphics.drawString("Continue", MouseInput.POSITIONX[0] + 50, MouseInput.POSITIONY[1] + 70);
        graphics.drawString("Exit", MouseInput.POSITIONX[0] + 50, MouseInput.POSITIONY[2] + 70);
    }

    public void renderPuaseMenu(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(MouseInput.POSITIONX[0], MouseInput.POSITIONY[0], MouseInput.BOXWIDTH, MouseInput.BOXHEIGHT);
        graphics.fillRect(MouseInput.POSITIONX[0], MouseInput.POSITIONY[1], MouseInput.BOXWIDTH, MouseInput.BOXHEIGHT);
        graphics.fillRect(MouseInput.POSITIONX[0], MouseInput.POSITIONY[2], MouseInput.BOXWIDTH, MouseInput.BOXHEIGHT);
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Elephant", 1, 48));
        graphics.drawString("Add Enemy", MouseInput.POSITIONX[0] + 50, MouseInput.POSITIONY[0] + 70);
        graphics.drawString("Resume", MouseInput.POSITIONX[0] + 50, MouseInput.POSITIONY[1] + 70);
        graphics.drawString("Exit", MouseInput.POSITIONX[0] + 50, MouseInput.POSITIONY[2] + 70);
    }


    //get all compatible DM
    public DisplayMode[] getCompatibleDisplayModes() {
        return graphicsDevice.getDisplayModes();
    }

    //compares DM passed into graphics DM and see if they match
    public DisplayMode findFirstCompatibleMode(DisplayMode[] modes) {
        DisplayMode[] goodModes = graphicsDevice.getDisplayModes();
        for (int i = 0; i < modes.length; i++) {
            for (int j = 0; j < goodModes.length; j++) {
                if (displayModesMatch(modes[i], goodModes[j])) {
                    return modes[i];
                }
            }
        }
        return null;
    }

    //EFFECT: get current DM
    public DisplayMode getCurrentDisplayMode() {
        return graphicsDevice.getDisplayMode();
    }

    //EFFECT: check if two modes match each other
    public boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2) {
        if ((mode1.getWidth() != mode2.getWidth())
                || (mode1.getHeight() != mode2.getHeight())) {
            return false;
        }

        if ((mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI)
                && (mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI)
                && (mode1.getBitDepth() != mode2.getBitDepth())) {
            return false;
        }

        if ((mode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN)
                && (mode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN)
                && (mode1.getRefreshRate() != mode2.getRefreshRate())) {
            return false;
        }

        return true;
    }

    // make frame full screen
    public void setScreen(DisplayMode displayMode) {
        graphicsDevice.setFullScreenWindow(frame);

        if (displayMode != null && graphicsDevice.isDisplayChangeSupported()) {
            try {
                graphicsDevice.setDisplayMode(displayMode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        frame.createBufferStrategy(2);
    }

    //we will set Graphics object = to this;
    public Graphics2D getGraphics() {
        Window w = graphicsDevice.getFullScreenWindow();
        if (w != null) {
            BufferStrategy s = w.getBufferStrategy();
            return (Graphics2D) s.getDrawGraphics();
        } else {
            return null;
        }
    }

    //EFFECT: updates display
    public void update() {

        if (frame != null) {
            BufferStrategy s = frame.getBufferStrategy();

        }
    }

    //returns full screen window
    public Window getFullScreenWindow() {
        return graphicsDevice.getFullScreenWindow();
    }

    public int getWidth() {
        Window w = graphicsDevice.getFullScreenWindow();
        if (w != null) {
            return w.getWidth();
        } else {
            return 0;
        }
    }

    public int getHeight() {
        Window w = graphicsDevice.getFullScreenWindow();
        if (w != null) {
            return w.getHeight();
        } else {
            return 0;
        }
    }

    // get out full screen
    public void restoreScreen() {
        Window w = graphicsDevice.getFullScreenWindow();
        if (w != null) {
            w.dispose();
        }
        graphicsDevice.setFullScreenWindow(null);
    }

    //create image compatible with monitor
    public BufferedImage createCompatibleImage(int w, int h, int t) {
        Window win = graphicsDevice.getFullScreenWindow();
        if (win != null) {
            GraphicsConfiguration gc = win.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h);
        }
        return null;
    }

    public Image loadImage(String savepath) throws IOException {
        File enemyIcon = new File(savepath);
        return ImageIO.read(enemyIcon);
    }
}
