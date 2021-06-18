package game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Klasa przechowująca parametry napisów wyświetlanych w oknie gry
 *
 * @author Paweł Rutkowski
 */
public class Word extends GraphicsObject {

    /**
     * Konstruktor ustawiający wysokość, szerokość i obraz zawierający napis
     *
     * @param height       Wysokość napisu
     * @param width        Szerokość napisu
     * @param object_image Obraz zawierający napis
     */
    public Word(int height, int width, BufferedImage object_image) {
        this.height = height;
        this.width = width;
        this.object_image = object_image;
    }

    /**
     * Funkcja rysująca napis
     *
     * @param graphics Pozwala na narysowanie obiektu
     * @param x        Współrządna X obiektu
     * @param y        Współrzędna Y obiektu
     * @param width    Szerokość obiektu
     * @param height   Wysokość obiektu
     */
    public void draw(Graphics2D graphics, double x, double y, int width, int height) {
        graphics.drawImage(object_image, (int) (x * width / Configuration.width), (int) (y * height / Configuration.height), this.width * width / Configuration.width, this.height * height / Configuration.height, null);
    }
}
