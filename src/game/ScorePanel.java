package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.swing.JPanel;

/**
 * Tworzy okno tabeli najlepszych wyników
 * @author Paweł Rutkowski
 *
 */
public class ScorePanel extends JPanel{
	
	/**
	 * Obiekt klasy MenuButton
	 */
	MenuButton menu_button;
	/**
	 * Obiekt klasy MusicButton
	 */
	MusicButton music_button;
	
	/**
	 * Konstruktor określający wygląd okna
	 * @param parent Sprawia, że ten panel jest w stanie wywołać metody jego rodzica
	 * @throws IOException
	 */
	public ScorePanel(MainMenu parent) throws IOException{
		
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
	 * Określa rozmiar okna
	 */
	public Dimension getPreferredSize() {
        return new Dimension(getParent().getSize());
    }
	
	/**
	 * Funkcja rysująca elementy okna
	 */
	public void paint(Graphics g){
		menu_button.repaint();
		music_button.repaint();
		
		Graphics2D name = (Graphics2D) g;
		Graphics2D score = (Graphics2D) g;
		Graphics2D text = (Graphics2D) g;
		
		name.setColor(Color.WHITE);
		score.setColor(Color.WHITE);
		text.setColor(Color.WHITE);
		
		name.scale(1.2, 1.2);
		for(int i=0; i<Score.number_of_scores; i++){
			text.drawString(i+1 + ". ", 100, 200 + i*20);
			name.drawString(Score.score_list.get(i).name, 120, 200 + i*20);
			score.drawString("" + Score.score_list.get(i).score, 250, 200 + i*20);
		}
		
		text.scale(2, 2);
		
		text.drawString("Lista najlepszych wyników:", 50, 50);
		

	}
}