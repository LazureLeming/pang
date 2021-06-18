package game;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Tworzy główne menu gry oraz przechowuje wszystkie panele (w tym okno gry)
 *
 * @author Paweł Rutkowski
 */
public class MainMenu extends Container {

    /**
     * Logo gry
     */
    private MenuLogo logo;
    /**
     * Obiekt klasy MenuPanel
     */
    private MenuPanel menu;
    /**
     * Obiekt klasy GamePanel inicjalizujący główne okno gry
     */
    private final GamePanel game_panel = new GamePanel(this);
    /**
     * Obiekt klasy InstructionPanel inicjalizujący okno instrukcji
     */
    private final InstructionPanel instr_panel = new InstructionPanel(this);
    /**
     * Obiekt klasy ScorePanel inicjalizujący okno listy najlepszych wyników
     */
    private final ScorePanel score_panel = new ScorePanel(this);

    /**
     * Obraz zawierający tło meny
     */
    private final BufferedImage background_menu;

    /**
     * Obiekt klasy MusicButton
     */
    private MusicButton music_button;

    /**
     * Tworzenie wyglądu menu, wczytywanie z pliku tła gry a także tworzenie niewidocznego okno gry
     *
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public MainMenu() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        setLayout(new GridBagLayout());

        background_menu = ImageIO.read(new File("res/background_menu.jpg"));

        Dimension window_size = new Dimension(Configuration.width, Configuration.height);
        setPreferredSize(window_size);

        game_panel.setVisible(false);
        score_panel.setVisible(false);
        instr_panel.setVisible(false);

        createMenu();
    }

    /**
     * Tworzenie loga i panelu menu
     *
     * @throws IOException
     */
    void createMenu() throws IOException {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 0, 0);

        logo = new MenuLogo();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(logo, constraints);

        menu = new MenuPanel(this);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(menu, constraints);

        music_button = new MusicButton(this);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        add(music_button, constraints);

        music_button.setIcon();

        setFocusable(true);
        requestFocusInWindow();

    }

    /**
     * Funkcja rysująca tło
     */
    public void paint(Graphics g) {
        Graphics2D back = (Graphics2D) g;
        back.drawImage(background_menu, 0, 0, getWidth(), getHeight(), null);
        super.paint(g);
    }

    /**
     * Ukrycie okna menu gry i uwidocznienie okna gry
     */
    void showGame() {
        add(game_panel);
        logo.setVisible(false);
        menu.setVisible(false);
        music_button.setVisible(false);
        instr_panel.setVisible(false);
        score_panel.setVisible(false);
        game_panel.setVisible(true);
        game_panel.game_bar.changeIconToPause();
        game_panel.startNewGame();
        game_panel.timer.start();
        game_panel.player.initialSettings();
        game_panel.requestFocusInWindow();
        game_panel.game_bar.setMusicIcon();
        SoundEffect.CLICK.play();
        repaint();
    }

    /**
     * Ukrycie okna menu gry i uwidocznienie okna instrukcji
     */
    void showInstruction() {
        add(instr_panel);
        logo.setVisible(false);
        menu.setVisible(false);
        music_button.setVisible(false);
        game_panel.setVisible(false);
        score_panel.setVisible(false);
        instr_panel.setVisible(true);
        instr_panel.music_button.setIcon();
        SoundEffect.CLICK.play();
        repaint();
    }

    /**
     * Ukrycie okna menu gry i uwidocznienie tabeli najlepszych wyników
     */
    void showScores() {
        add(score_panel);
        logo.setVisible(false);
        menu.setVisible(false);
        music_button.setVisible(false);
        game_panel.setVisible(false);
        score_panel.setVisible(false);
        score_panel.setVisible(true);
        score_panel.music_button.setIcon();
        SoundEffect.CLICK.play();
        repaint();
    }

    /**
     * Ukrycie okna gry oraz okna instrukcji i powrót do menu (uwidocznienie okna menu).
     */
    void goToMenu() {
        game_panel.setVisible(false);
        game_panel.timer.stop();
        instr_panel.setVisible(false);
        score_panel.setVisible(false);
        logo.setVisible(true);
        menu.setVisible(true);
        music_button.setVisible(true);
        music_button.setIcon();
        repaint();
    }
}