import java.applet.Applet;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;


/**
 * @author Mateusz
 *
 */

/**Klasa Level*/
public class Level {
	/** Zmienna statyczna SCIANA przyjmuj¹ca kolor RED*/ 
	public static final int SCIANA = Color.RED.getRGB();
	/** Zmienna statyczna POD£OGA przyjmuj¹ca kolor GREEN*/ 
	public static final int PODLOGA = Color.GREEN.getRGB();
	/** Zmienna statyczna SKRZYNKA przyjmuj¹ca kolor BLUES*/ 
	public static final int SKRZYNKA = Color.BLUE.getRGB();
	/** Zmienna statyczna CEL przyjmuj¹ca kolor 1f,0f,1f*/ 
	public static final int CEL = (new Color(1f, 0f, 1f)).getRGB();
	/** Zmienna statyczna GRACZ przyjmuj¹ca kolor 1f,1f,1f*/ 
	public static final int GRACZ = (new Color(1f, 1f, 1f)).getRGB();
	/** Zmienna statyczna RESET przyjmuj¹ca kolor 1f,1f,0f*/ 
	public static final int RESET = (new Color(1f, 1f, 0f)).getRGB();
	
	/**Za³adowanie appletu i path */
	public static int[][] load(Applet applet, String path)
	{
		/**tablica dwuwymiarowa lvll z rozmiarem planszy do gry */
		int[][] lvll = new int[10][14];
		
		BufferedImage bi;
		try {
			/** £adowanie obrazu */
			bi = ImageIO.read(new URL(applet.getCodeBase(), path));
			
			for(int x = 0; x < 14; x ++)
			{
				for(int y = 0; y < 10; y ++)
				{
					/**Jesli pobrany kolor zgadza siê z wczeœniej zaimplementowanym to SCIANA przyjmuje wartosc 1 */
					if(bi.getRGB(x, y) == Level.SCIANA)
						lvll[y][x] = 1;
					/**Jesli pobrany kolor zgadza siê z wczeœniej zaimplementowanym to POD£OGA przyjmuje wartosc 0 */
					else if(bi.getRGB(x, y) == Level.PODLOGA)
						lvll[y][x] = 0;
					/**Jesli pobrany kolor zgadza siê z wczeœniej zaimplementowanym to SKZRYNKA przyjmuje wartosc 3 */
					else if(bi.getRGB(x, y) == Level.SKRZYNKA)
					{
						lvll[y][x] = 3;
					}
					/**Jesli pobrany kolor zgadza siê z wczeœniej zaimplementowanym to CEL przyjmuje wartosc 2 */
					else if(bi.getRGB(x, y) == Level.CEL)
					{
						lvll[y][x] = 2;
					}
					/**Jesli pobrany kolor zgadza siê z wczeœniej zaimplementowanym toGRACZ przyjmuje wartosc 4 */
					else if(bi.getRGB(x, y) == Level.GRACZ)
					{
						lvll[y][x] = 4;
					}/**Jesli pobrany kolor zgadza siê z wczeœniej zaimplementowanym to RESET przyjmuje wartosc 5 */
					else if(bi.getRGB(x, y) == Level.RESET)
					{
						lvll[y][x] = 5;
					}
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lvll;
	}
	
	

}
