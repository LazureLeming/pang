package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zajmująca się odczytywaniem i parsowaniem danych dotyczących poziomu gry
 *
 * @author Paweł Rutkowski
 */
public class Level {

    /**
     * Lista przechowująca wszystkie piłki w danym poziomie
     */
    List<Ball> ball_list;

    /**
     * Numer danego poziomu
     */
    int level_number;
    /**
     * Liczba piłek w danym poziomie
     */
    int balls_to_load;
    /**
     * Grawitacja występująca w danym poziomie
     */
    double gravity;
    /**
     * Szansa na wypadnięcie bonusu z piłki w danym poziomie
     */
    int bonus_chance;

    /**
     * Tło danego poziomu
     */
    BufferedImage background;

    /**
     * Konstruktor domyślny
     *
     * @param n Number poziomu który chcemy utworzyć
     */
    public Level(int n) {
        initialSettings(n);
    }

    /**
     * Funkcja ustawiająca parametry początkowe i odczytująca dane poziomu z pliku
     *
     * @param n Number danego poziomu
     */
    void initialSettings(int n) {

        this.level_number = n;

        try {
            FileData config = new FileData("config/levels/level" + n + ".txt");
            background = ImageIO.read(new File("res/background_level_" + level_number + ".jpg"));

            balls_to_load = config.ballsInLevel();
            gravity = Double.parseDouble(config.getSetting("GRAVITY"));
            bonus_chance = Integer.parseInt(config.getSetting("BONUS CHANCE"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ball_list = new ArrayList<Ball>();

        if (balls_to_load > 0) {
            for (int i = 1; i <= this.balls_to_load; i++) {
                Ball ball;
                ball = new Ball(i, level_number);
                ball_list.add(ball);
            }
        }
    }
}