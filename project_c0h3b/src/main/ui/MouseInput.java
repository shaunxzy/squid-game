package ui;

import model.BasicEnemy;
import model.Player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class MouseInput implements MouseListener {
    public static final int BOXWIDTH = 600;
    public static final int BOXHEIGHT = 120;
    public static final int[] POSITIONX = {420, 1020};
    public static final int[] POSITIONY = {45, 285, 525};
    private Main main;
    private boolean isStartMenu;


    public MouseInput(Main main) {
        this.main = main;
        isStartMenu = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("clicked");
        int x = e.getX();
        int y = e.getY();
        System.out.println(Integer.toString(x) + "\t" + Integer.toString(y));
        if (isStartMenu) {
            switch (clickBoxStart(x, y)) {
                case 0:
                    break;
                case 1:
                    System.out.println("start");
                    main.initialize();
                    main.setStart(true);
                    isStartMenu = false;
                    break;
                case 2:
                    main.setStart(true);
                    isStartMenu = false;
                    break;
                case 3:
                    main.stop();
                    break;
            }
        }

        handlePause(x, y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("pressed");
        int x = e.getX();
        int y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("released");
        int x = e.getX();
        int y = e.getY();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("entered");
        int x = e.getX();
        int y = e.getY();
    }

    public boolean isStartMenu() {
        return isStartMenu;
    }

    public void setStartMenu(boolean startMenu) {
        isStartMenu = startMenu;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("exited");
        int x = e.getX();
        int y = e.getY();
    }

    public int clickBoxStart(int x, int y) {
        if ((POSITIONX[0] <= x) && (x <= POSITIONX[1])) {
            System.out.println("Enter X");
            if ((POSITIONY[0] <= y) && (y <= POSITIONY[0] + BOXHEIGHT)) {
                return 1;
            } else if ((POSITIONY[1] <= y) && (y <= POSITIONY[1] + BOXHEIGHT)) {
                return 2;
            } else if ((POSITIONY[2] <= y) && (y <= POSITIONY[2] + BOXHEIGHT)) {
                return 3;
            } else {
                return 0;
            }
        }

        return 0;
    }

    public void handlePause(int x, int y) {
        if (main.getPause()) {
            switch (clickBoxStart(x, y)) {
                case 1:
                    Random random = new Random();
                    Player player = main.getGame().getPlayer();
                    BasicEnemy basicEnemy = new BasicEnemy(main.getGame().WIDTH * random.nextDouble(),
                            main.getGame().HEIGHT * random.nextDouble(), player);
                    main.getGame().getEnemies().add(basicEnemy);
                    main.setPause(false);
                    break;
                case 2:
                    main.setPause(false);
                    break;
                case 3:
                    main.stop();
            }
        }
    }


}
