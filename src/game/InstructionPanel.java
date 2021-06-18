package game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Tworzy okno instrukcji gry
 *
 * @author Paweł Rutkowski
 */
public class InstructionPanel extends JPanel {

    /**
     * Obiekt klasy MenuButton
     */
    MenuButton menu_button;
    /**
     * Obiekt klasy MusicButton
     */
    MusicButton music_button;

    /**
     * Tworzy przycisk, dzięki któremu możemy powrócić do głownego menu gry.
     *
     * @param parent Sprawia, że ten panel jest w stanie wywołać metody jego rodzica.
     * @throws IOException
     */
    public InstructionPanel(MainMenu parent) throws IOException {


        /**
         * Tworzenie przycisku odpowiedzialnego za powrót do menu
         */
        menu_button = new MenuButton(parent);
        menu_button.setBounds(0, 0, 30, 30);
        menu_button.setLocation(32, 0);

        music_button = new MusicButton(parent);
        music_button.setBounds(0, 0, 30, 30);
        music_button.setLocation(2, 0);

        super.setLayout(null);
        add(music_button);
        add(menu_button);

        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * Funkcja tworząca napisy w oknie
     */
    public void paint(Graphics g) {
        menu_button.repaint();
        music_button.repaint();

        Graphics2D instruction = (Graphics2D) g;

        instruction.setColor(Color.WHITE);

        instruction.scale(1.3, 1.3);

        instruction.drawString("Poruszaj się za pomocą strzałek aby uniknąć piłek i pocisków", 50, 120);
        instruction.drawString("Trafienie w Ciebie bez tarczy spowoduje powtórzenie poziomu!", 50, 140);
        instruction.drawString("Strzelaj strzałką w górę, zestrzel wszystkie piłki aby przejść do następnego poziomu", 50, 160);
        instruction.drawString("Zniszczone piłki mogą zostawić dla Ciebie bonusy do zebrania, lub zestrzelenia", 50, 180);
        instruction.drawString("Niebieskie piłki są zwyczajnymi piłkami", 50, 210);
        instruction.drawString("Zielone piłki rozpadną się na dwie gdy je zniszczysz", 50, 230);
        instruction.drawString("Fioletowe piłki strzelą do Ciebie gdy je zniszczysz", 50, 250);
        instruction.drawString("Żółte piłki i rozpaną się na dwie i wystrzelną w Twoją stronę pocisk!", 50, 270);
        instruction.drawString("Zyskujesz punkty za zestrzelenie piłki, zdobycie odpowiedniego bonusu", 50, 300);
        instruction.drawString("ukończenie gry i liczbę żyć na końcu gry", 50, 320);
        instruction.drawString("Tracisz punkty za każde trafienie w Ciebie piłki lub pocisku i za każdy chybiony strzał!", 50, 340);
        instruction.drawString("Zestrzel wszystkie piłki aby wygrać grę!", 50, 370);

        instruction.scale(2, 2);

        instruction.drawString("Witaj w instrukcji do gry PANG!", 25, 40);
    }

    /**
     * Ustala rozmiar okna
     */
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getSize());
    }
}