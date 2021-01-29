package game;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Klasa czytająca i prasująca dane z pliku konfiguracyjnego
 * @author Paweł Rutkowski
 *
 */
public class Configuration {
	
	/**
	 * Określa domyślną szerokość okna
	 */
	static int width=0;
	/**
	 * Okresla domyślną wysokość okna
	 */
	static int height=0;
	/**
	 * Okresla minimalną szerokość okna
	 */
	static int minimum_width=0;
	/**
	 * Określa minimalną wysokość okna
	 */
	static int minimum_height=0;
	/**
	 * Określa liczbę poziomów
	 */
	static int levels = 0;
	/**
	 * Określa liczbę pozycji w liście najlepszych wyników
	 */
	static int number_of_scores = 0;
	/**
	 * Zmienna pozwalająca regulować liczbę klatek na sekundę animacji
	 */
	static int timer = 0;
	/**
	 * Zmienna całkowicie wyciszająca dźwięki
	 */
	static boolean mute = false;
	
	/**
	 * Konstruktor klasy Configuration
	 */
	public Configuration(){
		initialSettings();
	}

	/**
	 * Funkcja ustawiająca parametry początkowe gry odczytując dane z pliku konfiguracyjnego
	 */
	static void initialSettings(){
		
		FileData config = null;
		
		try {
			config = new FileData("config/game.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			Configuration.width = Integer.parseInt(config.getSetting("PREFERRED WINDOW WIDTH"));
			Configuration.height = Integer.parseInt(config.getSetting("PREFERRED WINDOW HEIGHT"));
			Configuration.minimum_width = Integer.parseInt(config.getSetting("MINIMUM WINDOW WIDTH"));
			Configuration.minimum_height = Integer.parseInt(config.getSetting("MINIMUM WINDOW HEIGHT"));
			Configuration.levels = Integer.parseInt(config.getSetting("LEVELS"));
			Configuration.number_of_scores = Integer.parseInt(config.getSetting("SCORES"));
			Configuration.timer = Integer.parseInt(config.getSetting("TIMER"));
			Configuration.mute = Boolean.parseBoolean(config.getSetting("MUTE"));
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();	
		}
	}
}