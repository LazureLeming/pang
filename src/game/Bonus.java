package game;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Klasa zawierająca dane bonusu
 * @author Paweł Rutkowski
 *
 */
public class Bonus extends GraphicsObject{
	
	/**
	 * Zmienna określająca rodzaj bonusu
	 */
	BonusType bonus_type;
	
	/**
	 * Konstruktor domyślny klasy Bonus
	 * @param type Rodzaj bonusu
	 */
	public Bonus(String type){
		try {
			initialSettings(type);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Funkcja ustawiająca parametry początkowe i odczytująca dane z pliku
	 * @param type Typ bonusu
	 * @throws IOException 
	 */
	private void initialSettings(String type) throws IOException{
		
		FileData config = new FileData("config/objects/bonus.txt");
		
		bonus_type = BonusType.valueOf(type);
		
		if(bonus_type == BonusType.LIFE){
			object_image = ImageIO.read(new File("res/bonus_life.gif"));
		}
		else if(bonus_type == BonusType.POINTS){
			object_image = ImageIO.read(new File("res/bonus_points.gif"));
		}
		else if(bonus_type == BonusType.IMMUNITY){
			object_image = ImageIO.read(new File("res/bonus_immunity.gif"));
		}
		
		vertical_speed = Integer.parseInt(config.getSetting("BONUS SPEED"));
		width = Integer.parseInt(config.getSetting("BONUS WIDTH"));
		height = Integer.parseInt(config.getSetting("BONUS HEIGHT"));	
	}

	/**
	 * Funkcja przyznająca dany bonus graczowi
	 * @param player Obiekt Player któremu przyznawany jest dany bonus
	 */
	void grantBonus(Player player, GamePanel gamepanel){
		
		switch(this.bonus_type){
		case LIFE:
			if(player.lives < player.max_lives){
				player.lives++;
			}
			break;
			
		case POINTS:
			player.points += 20;
			gamepanel.points_number.updateList(player.points);
			break;
			
		case IMMUNITY:
			player.immunity = true;
			break;
		}
	}
}
