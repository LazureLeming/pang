package game;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Sprawia, że elementem z załadowanego obrazu 'logo' mają być wyświetlane w menu.
 * 
 * @author Paweł Rutkowski
 */
public class MenuLogo extends Component{
	
	/**
	 * Logo gry
	 */
	private BufferedImage logo;
	
	/**
	 * Konstruktor wczytujący plik z logiem i jego wymiary
	 * @throws IOException
	 */
	public MenuLogo() throws IOException{
		logo = ImageIO.read(new File("res/logo.gif"));
		setPreferredSize(new Dimension(logo.getWidth(), logo.getHeight()));
	}
	
	/**
	 * Metoda rysująca logo w menu
	 */
	public void paint(Graphics g){
		g.drawImage(logo, 0, 0, getWidth(), getHeight(), this);
	}
}