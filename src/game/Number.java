package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Obiekt przechowujący parametry liczb wyświetlanych w grze
 *
 * @author Paweł Rutkowski
 */
public class Number extends GraphicsObject {
    /**
     * Liczba którą chcemy przedstawić zapisana jako String
     */
    private String numberString;

    /**
     * Lista przechowująca cyfry w liczbie
     */
    private final List<Integer> int_list;
    /**
     * Lista przechowująca obrazy cyfr w liczbie
     */
    private final List<BufferedImage> img_list;

    /**
     * Konstruktor zmieniający podaną liczbę na listę obrazów reprezentujących cyfry w tej liczbie
     *
     * @param number Liczba która ma zostać zamieniona na listę cyfr
     */
    public Number(int number) {

        height = 20;
        width = 20;

        int_list = new ArrayList<Integer>();
        img_list = new ArrayList<BufferedImage>();
        for (int i = 0; i < 10; i++) {
            try {
                BufferedImage tmp = ImageIO.read(new File("res/number/" + i + ".gif"));
                img_list.add(tmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updateList(number);
    }

    /**
     * Funkcja odświerzająca wyświetlaną liczbę
     *
     * @param number Liczba która ma zostać wyświetlona
     */
    void updateList(int number) {
        int_list.clear();
        numberString = String.valueOf(number);
        for (int i = 0; i < numberString.length(); i++) {
            int tmp = Character.getNumericValue(numberString.charAt(i));
            int_list.add(tmp);
        }
    }

    /**
     * Funckja rysująca obiekt
     *
     * @param graphics Pozwala na narysowanie obiektu
     * @param x        Współrzędna X obiektu
     * @param y        Współrzędna Y obiektu
     * @param width    Szerokość jednej cyfry
     * @param height   Wysokość jednej cyfry
     */
    public void draw(Graphics2D graphics, double x, double y, int width, int height) {
        double current_x = x;
        for (int i = 0; i < int_list.size(); i++) {
            for (int j = 0; j < img_list.size(); j++) {
                if (int_list.get(i) == j) {
                    graphics.drawImage(img_list.get(j), (int) (current_x * width / Configuration.width), (int) (y * height / Configuration.height), this.width * width / Configuration.width, this.height * height / Configuration.height, null);
                }
            }

            current_x += 22;
        }
    }
}
