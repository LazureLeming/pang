package game;

import java.io.*;
import javax.sound.sampled.*;

/**
 * Typ wyliczeniowy odpowiedzialny za efekty dźwiękowe
 * @author Paweł Rutkowski
 *
 */
public enum SoundEffect {
	/**
	 * Efekt dźwiękowy występujący przy zniszeniu piłki przez gracza
	 */
	EXPLOSION("explosion"),
	/**
	 * Efekt dźwiękowy występujący przy przejściu na następny poziom
	 */
	NEXT_LEVEL("next_level"),
	/**
	 * Efekt dźwiękowy występujący przy zdobyciu bonusu przez gracza
	 */
	BONUS("bonus"),
	/**
	 * Efekt dźwiękowy występujący przy przegraniu gry
	 */
	LOSE("lose"),
	/**
	 * Efekt dźwiękowy występujący przy trafieniu pocisku, lub piłki w gracza
	 */
	PLAYER_HIT("player_hit"),
	/**
	 * Efekt dźwiękowy występujący przy wygraniu gry
	 */
	END_GAME("end_game"),
	/**
	 * Efekt dźwiękowy występujący przy wystrzeliwaniu pocisku przez gracza
	 */
	SHOOT("shoot"),
	/**
	 * Efekt dźwiękowy występujący przy naciskaniu przycisków interfejsu
	 */
	CLICK("click");
	  
	/**
	 * Zmienna przechowująca plik z efektem dźwiękowym
	 */
	Clip clip;
	
	/**
	 * Zmienna wyciszająca efekty dźwiękowe
	 */
	static boolean muted = false;

	/**
	 * Konstruktor wczytujący odpowiedni efekt dźwiękowy
	 * @param soundFileName Nazwa pliku z danym efektem dźwiękowym
	 */
	SoundEffect(String soundFileName){
		if(!Configuration.mute){
			try {
				File soundFile = new File("sound/" + soundFileName + ".wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				clip = AudioSystem.getClip();
				clip.open(audioIn);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Funkcja odtwarzająca dany efekt dźwiękowy
	 */
	void play() {
		if(!Configuration.mute){
			if(!muted){
				if (clip.isRunning()){
					clip.stop();
				}	
				clip.setFramePosition(0);
				clip.start();
			}
		}
	}
	
	/**
	 * Funkcja ustawiająca parametry początkowe
	 */
	static void initialSetting(){
		if(!Configuration.mute){
			values();
		}
	}
}
