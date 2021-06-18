package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Tworzy przycik powrotu do menu głównego
 *
 * @author Paweł Rutkowski
 */
public class MenuButton extends JPanel {
    /**
     * Konstruktor domyślny
     *
     * @param parent Sprawia, że ten panel jest w stanie wywołać metody jego rodzica
     * @throws IOException
     */
    public MenuButton(MainMenu parent) throws IOException {
        Icon menu = new ImageIcon(ImageIO.read(new File("res/menu.gif")));

        setBackground(new Color(0, 0, 0));

        JButton menu_button = new JButton(menu);
        menu_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parent.goToMenu();
                SoundEffect.CLICK.play();
            }
        });

        menu_button.setPreferredSize(new Dimension(25, 25));

        menu_button.setContentAreaFilled(false);
        menu_button.setBorderPainted(false);

        setBorder(null);

        add(menu_button);
    }
}