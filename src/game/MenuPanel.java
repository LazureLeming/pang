package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tworzenie panelu przechowującego wszystki przyciski menu
 *
 * @author Paweł Rutkowski
 */
public class MenuPanel extends JPanel {

    /**
     * Przycisk do okna nowej gry
     */
    JButton new_game;
    /**
     * Przycisk przechodzący do okna z instrukcją
     */
    JButton instructions;
    /**
     * Przycisk przechodzący do okna z listą najlepszych wyników
     */
    JButton scores;
    /**
     * Przycisk zamykający grę
     */
    JButton exit;

    /**
     * Dodanie przycisków pcji menu oraz zainicjowanie ich działania
     *
     * @param parent Sprawia, że ten panel jest w stanie wywołać metody jego rodzica
     */
    public MenuPanel(MainMenu parent) {
        new_game = new JButton("NOWA GRA");
        instructions = new JButton("INSTRUKCJA");
        scores = new JButton("WYNIKI");
        exit = new JButton("ZAKOŃCZ");


        new_game.setPreferredSize(new Dimension(150, 40));
        new_game.setBorderPainted(false);
        new_game.setBackground(new Color(29, 200, 29));
        new_game.setForeground(new Color(0, 0, 0));
        new_game.setFocusPainted(false);
        new_game.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parent.showGame();
            }
        });

        new_game.requestFocusInWindow();

        instructions.setPreferredSize(new Dimension(150, 40));
        instructions.setBorderPainted(false);
        instructions.setBackground(new Color(29, 200, 29));
        instructions.setForeground(new Color(0, 0, 0));
        instructions.setFocusPainted(false);
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parent.showInstruction();
            }
        });

        scores.setPreferredSize(new Dimension(150, 40));
        scores.setBorderPainted(false);
        scores.setBackground(new Color(29, 200, 29));
        scores.setForeground(new Color(0, 0, 0));
        scores.setFocusPainted(false);
        scores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parent.showScores();
            }
        });

        exit.setPreferredSize(new Dimension(150, 40));
        exit.setBorderPainted(false);
        exit.setBackground(new Color(29, 200, 29));
        exit.setForeground(new Color(0, 0, 0));
        exit.setFocusPainted(false);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Runtime.getRuntime().exit(0);
            }
        });

        GridLayout layout = new GridLayout(1, 4);
        layout.setVgap(15);
        layout.setHgap(15);
        setLayout(layout);
        setOpaque(false);
        add(new_game);
        add(instructions);
        add(scores);
        add(exit);
    }
}