package game;

import java.awt.Dimension;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/**
 * Klasa wywołująca główne okno gry
 * 
 * @author Paweł Rutkowski
 */
public class Game extends JFrame{
	
	/**
	 * Główne okno gry
	 *
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException{
		JFrame frame = new JFrame("PANG");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Configuration.initialSettings();
		Score.readScore();
		Score.writeScore();
		SoundEffect.initialSetting();
		frame.setMinimumSize(new Dimension(Configuration.minimum_width, Configuration.minimum_height));
		frame.add(new MainMenu());
		frame.pack();
		frame.setVisible(true);
		frame.repaint();
	}
}