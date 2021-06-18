package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Klasa zawierająca serca reprezentujące liczbę żyć gracza
 *
 * @author Paweł Rutkowski
 */
public class Heart extends GraphicsObject {

    /**
     * Serce reprezentujące życie gracza
     */
    BufferedImage heart;
    /**
     * Tło serc
     */
    BufferedImage heart_background;

    /**
     * Konstruktor domyślny klasy Heart
     *
     * @param type Rodzaj tworzonego serca
     */
    public Heart(HeartType type) {
        try {
            initialSettings(type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funkcja ustawiająca parametry serca z plików
     *
     * @param type Rodzaj serca
     * @throws IOException
     */
    void initialSettings(HeartType type) throws IOException {

        heart = ImageIO.read(new File("res/heart.gif"));

        heart_background = ImageIO.read(new File("res/heart_background.gif"));

        height = 30;
        width = 30;
        x = 10;
        y = 10;

        if (type == HeartType.LIFE) {
            object_image = heart;
        } else {
            object_image = heart_background;
        }
    }
}
