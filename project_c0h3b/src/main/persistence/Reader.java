package persistence;

import model.BasicEnemy;
import model.Bullet;
import model.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Reader {
    public static final String SPLITTER = ",";
    private static String[] fileContent;
    private static Player player;

    public Reader(File file) throws IOException {
        fileContent = readFile(file).toArray(new String[0]);
    }

    // EFFECTS: Return the player loaded from the previous data
    public static Player parsePlayer() {
        for (String line : fileContent) {
            if (playerLine(line)) {
                List<String> components = splitString(line, SPLITTER);
                double x = Double.parseDouble(components.get(1));
                double y = Double.parseDouble(components.get(2));
                int health = Integer.parseInt(components.get(3));
                double velX = Double.parseDouble(components.get(4));
                double velY = Double.parseDouble(components.get(5));
                ArrayList<Bullet> bullets = parseBullets(components);
                player = new Player(x, y);
                player.setX(x);
                player.setY(y);
                player.setHealth(health);
                player.setVelY(velY);
                player.setBullets(bullets);
            }
        }

        return player;
    }

    public static ArrayList<BasicEnemy> parseEnemies() {
        ArrayList<BasicEnemy> enemies = new ArrayList<>();
        for (String line : fileContent) {
            if (enemyLine(line)) {
                BasicEnemy enemy;
                List<String> components = splitString(line, SPLITTER);
                double x = Double.parseDouble(components.get(1));
                double y = Double.parseDouble(components.get(2));
                int health = Integer.parseInt(components.get(3));
                double velX = Double.parseDouble(components.get(4));
                double velY = Double.parseDouble(components.get(5));
                ArrayList<Bullet> bullets = parseBullets(components);
                enemy = new BasicEnemy(x, y, player);
                enemy.setHealth(health);
                enemy.setVelX(velX);
                enemy.setVelY(velY);
                enemy.setBullets(bullets);
                enemies.add(enemy);
            }
        }
        return enemies;
    }

    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    private static boolean enemyLine(String line) {
        List<String> components = splitString(line, SPLITTER);
        return ("Enemy".equals(components.get(0)));
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line, String splitter) {
        String[] splits = line.split(splitter);
        return new ArrayList<>(Arrays.asList(splits));
    }

    private static boolean playerLine(String line) {
        List<String> components = splitString(line, SPLITTER);
        return ("Player".equals(components.get(0)));
    }

    private static ArrayList<Bullet> parseBullets(List<String> components) {
        int index = 0;
        int size;
        ArrayList<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < components.size(); i++) {
            if ("Bullets".equals(components.get(i))) {
                index = i;
            }
        }

        size = Integer.parseInt(components.get(index + 1));

        for (int j = index + 2; j < size + index + 2; j++) {
            Bullet singleBullet = getBullet(components.get(j));
            bullets.add(singleBullet);
        }

        return bullets;
    }

    private static Bullet getBullet(String component) {
        List<String> componentList = splitString(component, ";");
        double x = Double.parseDouble(componentList.get(0));
        double y = Double.parseDouble(componentList.get(1));
        double velX = Double.parseDouble(componentList.get(3));
        double velY = Double.parseDouble(componentList.get(4));
        int damage = Integer.parseInt(componentList.get(2));
        double[] direction = new double[] {0.0, 0.0};
        Bullet bullet = new Bullet(x, y, damage, direction);
        bullet.setVelX(velX);
        bullet.setVelY(velY);
        return bullet;
    }


}
