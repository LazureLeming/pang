package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa zawierająca listę najlepszych wynikós
 * @author Paweł Rutkowski
 *
 */
public class Score{
	
	/**
	 * Lista obiektów typu HighScore przechowująca najlepsze wyniki i nazwy graczy którzy je zdobyli
	 */
	static List<HighScore> score_list = new ArrayList<HighScore>();
	
	/**
	 * Zmienna określająca obecną liczbę wyników na liście
	 */
	static int number_of_scores = 0;	
	
	/**
	 * Konstruktor domyślny
	 */
	public Score(){
		readScore();
	}
	
	/**
	 * Funckja zapisująca najlepszy wynik i nazwę gracza do pliku
	 */
	static void writeScore(){
		PrintWriter out;
		try {
			int count = 0;
			out = new PrintWriter(new File("config/score.txt"));
			
			while(count < number_of_scores){
				out.print(score_list.get(count).name + "$" + score_list.get(count).score + "\n");
				count++;
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Funkcja odczytująca najlepszy wynik i nazwę gracza z pliku
	 */
	static void readScore(){
		
		try {
			score_list.clear();
			
			FileData config = new FileData("config/score.txt");
			
			number_of_scores = config.linesInFile();
			if(number_of_scores > Configuration.number_of_scores){
				number_of_scores = Configuration.number_of_scores;
			}
			
			for(int i=1; i<=number_of_scores; i++){
				String full_score = config.readOneLine(i);
				String[] split_full_score = full_score.split("\\$");
				HighScore high_score = new HighScore(split_full_score[0], Integer.parseInt(split_full_score[1]));
				score_list.add(high_score);
			}
			
			sortScore();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();	
		}
	}
	
	/**
	 * Funkcja sortująca wyniki od najwyższego
	 */
	static void sortScore(){
		Collections.sort(score_list);
	}
}