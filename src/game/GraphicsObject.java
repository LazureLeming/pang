package game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Klasa abstrakcyjna przechowująca podstawowe parametry obiektów
 *
 * @author Paweł Rutkowski
 */
public abstract class GraphicsObject {
    /**
     * Współrzędna X obiektu
     */
    double x = 0;
    /**
     * Współrzędna Y obiektu
     */
    double y = 0;
    /**
     * Pozioma szybkość poruszania się obiektu
     */
    double horizontal_speed = 0;
    /**
     * Pionowa szybkość poruszania się obiektu
     */
    double vertical_speed = 0;
    /**
     * Wysokość obiektu
     */
    int height = 0;
    /**
     * Szerokość obiektu
     */
    int width = 0;

    /**
     * Wyswietlany obraz obiektu
     */
    BufferedImage object_image = null;

    /**
     * Funkcja rysująca dany obiekt
     *
     * @param graphics Pozwala na narysowanie obiektu
     * @param width    Obecna szerokość okna programu
     * @param height   Obecna wysokość okna programu
     */
    public void draw(Graphics2D graphics, int width, int height) {
        graphics.drawImage(object_image, (int) (x * width / Configuration.width), (int) (y * height / Configuration.height), this.width * width / Configuration.width, this.height * height / Configuration.height, null);
    }
}