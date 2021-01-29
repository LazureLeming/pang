package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa odpowiedzialna za kontrolowanie ruchu gracz za pomocą klawiszy klawiatury
 * @author Paweł Rutkowski
 */
public class Controller implements KeyListener{
	/**
	 * Parametr określający ruch gracza w lewo
	 */
	 boolean left = false;
	 /**
	  * Parametr określający ruch gracza w prawo
	  */
	 boolean right = false;
	 /**
	  * Parametr określający strzelanie gracza
	  */
	 boolean shoot = false;

	 /**
	  * Metoda wywoływana, gdy naciśniemy odpowiedni przycisk na klawiaturze związany z ruchem gracza
	  */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			right = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP){
			shoot = true;
		}
	}
	 
	 /**
	  * Metoda wywoływana, gdy puścimy odpowiedni przycisk na klawiaturze związany z ruchem gracza
	  */
	 public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			right = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP){
			shoot = false;
		}
	}
	 
	/**
	 * Metoda wywoływana, gdy nastąpi naciśnięcie przycisku
	 */
	public void keyTyped(KeyEvent arg0) {
		
	}
}