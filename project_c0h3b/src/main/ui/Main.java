package ui;

import model.*;
import persistence.Reader;
import persistence.Writer;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Canvas implements Runnable {
    public static final int WIDTH = 1440;
    public static final int HEIGHT = WIDTH / 16 * 9;
    private static final String ACCOUNTS_FILE = "./data/gamesave.txt";
    private static final DisplayMode[] modes1 = {
            new DisplayMode(800,600,32,0),
            new DisplayMode(800,600,24,0),
            new DisplayMode(800,600,16,0),
            new DisplayMode(640,480,32,0),
            new DisplayMode(640,480,24,0),
            new DisplayMode(640,480,16,0),
    };

    private KeyBoardInput keyBoardInput;
    private Player player;
    private Game game;
    private BasicEnemy enemy;
    private ArrayList<BasicEnemy> enemies;
    private Random random = new Random();
    private GameHandler gameHandler;
    private PlayerList playerList;
    private boolean pause = false;
    private BackGround backGround;
    private Reader reader;
    private Image bg;
    private GameDrawer gameDrawer;
    private MouseInput mouseStartMenu;
    private boolean start;

    private Thread thread;
    private boolean running = false;

    public Main() throws InterruptedException {
        boolean canLoad = loadGame();
        backGround = new BackGround();
        gameHandler = new GameHandler(player, enemies);
        game = new Game(WIDTH, HEIGHT, player, enemies);
        keyBoardInput = new KeyBoardInput(this);
        this.addKeyListener(keyBoardInput);
        mouseStartMenu = new MouseInput(this);
        mouseStartMenu.setStartMenu(true);
        this.addMouseListener(mouseStartMenu);
        start = false;

        try {
            gameDrawer = new GameDrawer(WIDTH, HEIGHT, "a game by shaun", this);
            DisplayMode dm = gameDrawer.findFirstCompatibleMode(modes1);
            // gameDrawer.setScreen(dm);
            this.start();
        } finally {
            gameDrawer.restoreScreen();
        }


    }

    public synchronized void start() throws InterruptedException {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {

            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        try {
            gameLoop(delta, lastTime, ns, frames, timer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stop();
        saveGame();
    }

    public void gameLoop(double delta, long lastTime, double ns, int frames, long timer)
            throws InterruptedException, IOException {
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick(now);
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }

            thread.sleep(15);
        }
    }

    public void tick(long timer) {
        if (!pause && start) {
            game.tickGame(timer);
        }
    }


    public void render() throws IOException {

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (!start) {
            gameDrawer.renderStartMenu(g);
        } else if (pause) {
            gameDrawer.renderPuaseMenu(g);
        } else {
            backGround.render(g);
            gameDrawer.renderGame(g, game);
        }

        g.dispose();
        if (!bs.contentsLost()) {
            bs.show();
        }

    }



    public void saveGame() {
        try {
            Writer writer = new Writer(new File(ACCOUNTS_FILE));
            writer.write(player);
            // System.out.println(enemies.size());
            for (int i = 0; i < enemies.size(); i++) {
                writer.write(enemies.get(i));
            }
            writer.close();
            System.out.println("Game saved to file " + ACCOUNTS_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save Game to " + ACCOUNTS_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    private boolean loadGame() {
        try {
            reader = new Reader(new File(ACCOUNTS_FILE));
            player = Reader.parsePlayer();
            enemies = Reader.parseEnemies();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean getPause() {
        return pause;
    }

    public Game getGame() {
        return this.game;
    }

    public static void main(String[] args) throws InterruptedException {
        new Main();
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isStart() {
        return start;
    }

    public void initialize() {
        this.player = new Player(game.WIDTH * random.nextDouble(), game.HEIGHT * random.nextDouble());
        this.enemy = new BasicEnemy(game.WIDTH * random.nextDouble(), game.HEIGHT * random.nextDouble(), player);
        this.enemies = new ArrayList<>();
        enemies.add(enemy);
        this.gameHandler = new GameHandler(player, enemies);
        this.game = new Game(WIDTH, HEIGHT, player, enemies);
        this.keyBoardInput = new KeyBoardInput(this);
        this.addKeyListener(keyBoardInput);
    }
}
