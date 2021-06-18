package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Sprawia, że elementem z załadowanego obrazu 'logo' mają być wyświetlane w menu.
 *
 * @author Paweł Rutkowski
 */
public class MenuLogo extends Component {

    /**
     * Logo gry
     */
    private final BufferedImage logo;

    /**
     * Konstruktor wczytujący plik z logiem i jego wymiary
     *
     * @throws IOException
     */
    public MenuLogo() throws IOException {
        logo = ImageIO.read(new File("res/logo.gif"));
        setPreferredSize(new Dimension(logo.getWidth(), logo.getHeight()));
    }

    /**
     * Metoda rysująca logo w menu
     */
    public void paint(Graphics g) {
        g.drawImage(logo, 0, 0, getWidth(), getHeight(), this);
    }
}