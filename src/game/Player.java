package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Klasa zawierająca parametry gracza odczytane z pliku
 *
 * @author Paweł Rutkowski
 */
public class Player extends GraphicsObject {

    /**
     * Liczba żyć gracza
     */
    int lives;
    /**
     * Liczba punktów gracza
     */
    int points;
    /**
     * Zmienna mówiąca o tym, czy gracz posiada tarczę
     */
    boolean immunity;
    /**
     * Maksymalna liczba żyć
     */
    int max_lives;

    /**
     * Obraz zawierający postać gracza z tarczą
     */
    BufferedImage player_immune;

    /**
     * Konstruktor klasy Player, ustawia parametry początkowe graczas
     */
    public Player() {
        initialSettings();
    }

    /**
     * Funkcja ustawiająca parametry początkowe i odczytywanie danych z plików gracza
     */
    void initialSettings() {

        FileData config = null;

        try {
            config = new FileData("config/player.txt");
            object_image = ImageIO.read(new File("res/player.gif"));
            player_immune = ImageIO.read(new File("res/player_immune.gif"));

            horizontal_speed = Integer.parseInt(config.getSetting("PLAYER SPEED"));
            width = Integer.parseInt(config.getSetting("PLAYER WIDTH"));
            height = Integer.parseInt(config.getSetting("PLAYER HEIGHT"));
            lives = Integer.parseInt(config.getSetting("LIVES"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        x = Configuration.width / 2 - this.width / 2;
        y = Configuration.height - this.height;
        points = 0;
        immunity = false;
        max_lives = lives;
    }

    /**
     * Funkcja modyfikująca rysowanie obiektu
     */
    public void draw(Graphics2D player, int width, int height) {

        BufferedImage player_type;

        if (immunity) {
            player_type = player_immune;
        } else {
            player_type = object_image;
        }

        player.drawImage(player_type, (int) (x * width / Configuration.width), (int) (y * height / Configuration.height), this.width * width / Configuration.width, this.height * height / Configuration.height, null);
    }
}