package game;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Klasa zawierająca parametry piłki odczytane z pliku
 *
 * @author Paweł Rutkowski
 */
public class Ball extends GraphicsObject {

    /**
     * Zmienna mówiąca o tym ile razy dana piłka będzie rozpadać się na kolejne piłki
     */
    int split;
    /**
     * Zmienna decydująca, czy piłka będzie strzelała do gracza po zniszczeniu
     */
    boolean shoots;
    /**
     * Zmienna pomocnicza, określa, czy piłka przed chwilą trafiła w gracza
     */
    boolean hit_flag;

    /**
     * Zmienna określająca pionowy kierunek ruchu piłki
     */
    Direction ball_vertical_direction;
    /**
     * Zmienna określająca poziomy kierunek ruchu piłki
     */
    Direction ball_horizontal_direction;

    /**
     * Konstruktor klasy Ball
     *
     * @param ball  Numer piłki w danym poziomie
     * @param level Numer poziomu w którym znajduje się dana piłka
     */
    public Ball(int ball, int level) {
        initialSettings(ball, level);
    }

    /**
     * Konstruktor kopiujący klasy Ball
     *
     * @param b Obiekt Ball z którego parametrów zostanie utworzony nowy obiekt Ball
     */
    public Ball(Ball b) {
        x = b.x;
        y = b.y;
        horizontal_speed = b.horizontal_speed;
        vertical_speed = b.vertical_speed;
        width = b.width;
        height = b.height;
        ball_vertical_direction = b.ball_vertical_direction;
        ball_horizontal_direction = b.ball_horizontal_direction;
        split = b.split;
        shoots = b.shoots;
        hit_flag = b.hit_flag;

        object_image = b.object_image;
    }

    /**
     * Funkcja ustawiająca początkowe parametry piłki
     *
     * @param ball  Numer danej piłki w poziomie
     * @param level Numer poziomu w którym znajduje się dana piłka
     */
    void initialSettings(int ball, int level) {

        try {
            FileData config = new FileData("config/levels/level" + level + ".txt");

            width = Integer.parseInt(config.readOneLine(config.findInFile("$BALL " + ball) + 4));
            height = Integer.parseInt(config.readOneLine(config.findInFile("$BALL " + ball) + 4));
            horizontal_speed = Double.parseDouble(config.readOneLine(config.findInFile("$BALL " + ball) + 3));
            ball_horizontal_direction = Direction.valueOf(config.readOneLine(config.findInFile("$BALL " + ball) + 5));
            split = Integer.parseInt(config.readOneLine(config.findInFile("$BALL " + ball) + 6));
            shoots = Boolean.parseBoolean(config.readOneLine(config.findInFile("$BALL " + ball) + 7));

            if (Double.parseDouble(config.readOneLine(config.findInFile("$BALL " + ball) + 1)) >= 1) {
                x = (Configuration.width - this.width);
            } else {
                x = (Configuration.width - this.width) * Double.parseDouble(config.readOneLine(config.findInFile("$BALL " + ball) + 1));
            }

            if (Double.parseDouble(config.readOneLine(config.findInFile("$BALL " + ball) + 2)) >= 1) {
                y = (Configuration.height - this.height);
            } else {
                y = (Configuration.height - this.height) * Double.parseDouble(config.readOneLine(config.findInFile("$BALL " + ball) + 2));
            }

            setObjectImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        hit_flag = false;
        ball_vertical_direction = Direction.DOWN;
    }

    /**
     * Funkcja ustawiająca obraz piłki w zależności od jej rodzaju
     *
     * @throws IOException
     */
    void setObjectImage() throws IOException {

        if (this.split > 0 && this.shoots) {
            this.object_image = ImageIO.read(new File("res/ball_split_shoots.gif"));
        } else if (this.split > 0) {
            this.object_image = ImageIO.read(new File("res/ball_split.gif"));
        } else if (this.shoots) {
            this.object_image = ImageIO.read(new File("res/ball_shoots.gif"));
        } else {
            this.object_image = ImageIO.read(new File("res/ball.gif"));
        }
    }
}