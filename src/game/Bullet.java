package game;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Klasa zawierająca parametry pocisku
 *
 * @author Paweł Rutkowski
 */
public class Bullet extends GraphicsObject {

    /**
     * Zmienna określająca kierunek poruszania się pocisku
     */
    Direction bullet_direction;

    /**
     * Konstruktor klasy Bullet
     *
     * @param direction Kierunek w którym porusza się pocisk
     */
    public Bullet(String direction) {
        try {
            initialSettings(direction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda ustawiająca ustawienia początkowe pocisku
     *
     * @param direction Kierunek poruszania się pocisku
     * @throws IOException
     */
    private void initialSettings(String direction) throws IOException {

        FileData config = new FileData("config/objects/bullet.txt");

        object_image = ImageIO.read(new File("res/bullet.gif"));

        vertical_speed = Integer.parseInt(config.getSetting("BULLET SPEED"));
        height = Integer.parseInt(config.getSetting("BULLET HEIGHT"));
        width = Integer.parseInt(config.getSetting("BULLET WIDTH"));

        x = 0;
        y = 0;
        bullet_direction = Direction.valueOf(direction);
    }
}