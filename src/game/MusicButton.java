package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Obiekt przycisku odpowiedzialnego za wyciszanie efektów dźwiękowych
 *
 * @author Paweł Rutkowski
 */

public class MusicButton extends JPanel {

    /**
     * Przycisk wyciszający/wznawiający efekty dźwiękowe
     */
    private final JButton music_button;
    /**
     * Ikona wznowienia efektów dźwiękowych
     */
    private final Icon music_play;
    /**
     * Ikona wyciszenia efektów dźwiękowych
     */
    private final Icon music_stop;

    /**
     * Konstruktor opisujący wygląd i działanie przycisku
     *
     * @param parent Sprawia, że ten panel jest w stanie wywołać metody jego rodzica
     * @throws IOException
     */
    public MusicButton(MainMenu parent) throws IOException {
        music_play = new ImageIcon(ImageIO.read(new File("res/music_play.gif")));
        music_stop = new ImageIcon(ImageIO.read(new File("res/music_stop.gif")));

        setBackground(new Color(0, 0, 0));
        if (!Configuration.mute) {
            if (!SoundEffect.muted) {
                music_button = new JButton(music_stop);
            } else {
                music_button = new JButton(music_play);
            }
        } else {
            music_button = new JButton(music_stop);
        }

        if (!Configuration.mute) {
            music_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {

                    if (!SoundEffect.muted) {
                        SoundEffect.muted = true;
                    } else {
                        SoundEffect.muted = false;
                        SoundEffect.CLICK.play();
                    }

                    setIcon();
                }
            });
        }

        music_button.setPreferredSize(new Dimension(25, 25));

        music_button.setContentAreaFilled(false);
        music_button.setBorderPainted(false);
        setBorder(null);

        add(music_button);
    }

    /**
     * Funkcja ustawiająca odpowiednią ikonę przycisku
     */
    void setIcon() {
        if (!Configuration.mute) {
            if (!SoundEffect.muted) {
                music_button.setIcon(music_play);
            } else {
                music_button.setIcon(music_stop);
            }
        } else {
            music_button.setIcon(music_stop);
        }
    }
}
