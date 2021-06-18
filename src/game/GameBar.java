package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


/**
 * Tworzy pasek zawierający przycisk powrotu do głównego menu, pausy i wyciszenia efektów dźwiękowych
 *
 * @author Paweł Rutkowski
 */
public class GameBar extends JPanel {

    /**
     * Przycisk pausy i wznowienia gry
     */
    private final JButton play_pause_button;
    /**
     * Przycisk powrotu do menu głównego
     */
    private final JButton menu_button;
    /**
     * Przycisk wyciszający i wznawiający efekty dźwiękowe
     */
    private final JButton music_button;

    /**
     * Ikona przycisku powrotu do menu głównego
     */
    private final Icon menu;
    /**
     * Ikona zatrzymania gry przycisku pausy
     */
    private final Icon pause;
    /**
     * Ikona wznowienia gry przycisku pausy
     */
    private final Icon play;
    /**
     * Ikona wznowienia efektów dźwiękowych
     */
    private final Icon music_play;
    /**
     * Ikona wyciszenia efektów dźwiękowych
     */
    private final Icon music_stop;

    /**
     * Konstruktor opisujący wygląd oraz działanie przycisków
     *
     * @param parent     Sprawia, że ten panel jest w stanie wywołać metody jego rodzica
     * @param game_panel Pozwala na wywołanie funckji playPause klasy GamePanel
     * @throws IOException
     */
    public GameBar(MainMenu parent, GamePanel game_panel) throws IOException {
        menu = new ImageIcon(ImageIO.read(new File("res/menu.gif")));
        music_play = new ImageIcon(ImageIO.read(new File("res/music_play.gif")));
        music_stop = new ImageIcon(ImageIO.read(new File("res/music_stop.gif")));

        menu_button = new JButton(menu);
        menu_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parent.goToMenu();
                SoundEffect.CLICK.play();
            }
        });

        menu_button.setPreferredSize(new Dimension(25, 25));

        menu_button.setContentAreaFilled(false);
        menu_button.setBorderPainted(false);


        pause = new ImageIcon(ImageIO.read(new File("res/pause.gif")));
        play = new ImageIcon(ImageIO.read(new File("res/play.gif")));


        play_pause_button = new JButton(pause);
        play_pause_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg1) {
                play_pause_button.setIcon(play_pause_button.getIcon() == play ? pause : play);
                game_panel.playPause();
                SoundEffect.CLICK.play();
            }
        });

        play_pause_button.setPreferredSize(new Dimension(25, 25));

        play_pause_button.setContentAreaFilled(false);
        play_pause_button.setBorderPainted(false);


        if (!Configuration.mute) {
            if (!SoundEffect.muted) {
                music_button = new JButton(music_stop);
            } else {
                music_button = new JButton(music_play);
            }

            music_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {

                    if (!SoundEffect.muted) {
                        SoundEffect.muted = true;
                    } else {
                        SoundEffect.muted = false;
                        SoundEffect.CLICK.play();
                    }

                    setMusicIcon();
                }
            });
        } else {
            music_button = new JButton(music_stop);
        }


        music_button.setPreferredSize(new Dimension(25, 25));

        music_button.setContentAreaFilled(false);
        music_button.setBorderPainted(false);

        setOpaque(false);
        setBorder(null);

        add(menu_button);
        add(play_pause_button);
        add(music_button);
    }

    /**
     * Funkcja zmieniająca przycisk Pasue/Play na Pause
     */
    void changeIconToPause() {
        play_pause_button.setIcon(pause);
    }

    /**
     * Funkcja zmieniająca przycisk Pasue/Play na Play
     */
    void changeIconToPlay() {
        play_pause_button.setIcon(play);
    }

    /**
     * Funkcja ustawiająca poprawną ikonę przyskisku wyciszającego i wznawiającego efekty dźwiękowe
     */
    void setMusicIcon() {
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