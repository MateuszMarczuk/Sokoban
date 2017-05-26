import java.util.Random;
import java.util.TimerTask;
/**  
 *Klasa obliczenia
 *
 */

/**
 * @author Mateusz
 *
 */
public class Obliczenia extends TimerTask

{
	/**
	 * @param plansza dwuwymiarowa tablica do rysowania planszy
	 * @param max_lvl maksymalny level jaki mozna osiagnac w grze.
	 * @param poz1 Pozycja x gracza
	 * @param poz2 Pozycja y gracza
	 */
	int plansza[][] = new int[10][14];
	int wygrana[][] = new int[10][14];
	int planszaB[][] = new int[10][14];
	int poz1;
	int poz2;
	/**@koniec Ustawia flagê koniec na wartosc false */
	boolean koniec = false;
	int max_lvl = 3;
	int lvl = 0;
	int celi = -1;
	boolean tmp = false;
	/** @param reset wraca flase jesli nie jest klikniety reset mapki*/
	boolean reset = false;
	int xBox;

	public void run() 
	{	
		/**
		 * Jesli gracz przeszedl poziom, ale nie osiagnal maksymalnego poziomu
		 * to gra przechodzi w stan 2, zwieksza sie poziom i zostaje narysowany.
		 * @see 
		 */
		if(koniec() == true && lvl < max_lvl)
		{
			Window.stan=2;
			
			//Window.t = System.currentTimeMillis();
			this.lvl++;
			this.zrobPlansze(lvl);
		}
		/**
		 * Jesli gracz osiagnal maksymalny poziom to gra sie konczy
		 * i zostaje wyswietlony ekrean koncowy gry.
		 */
		else if(koniec() == true && lvl == max_lvl)
		{
			Window.stan=3;
		}
		/**
		 * restem mapki jesli gracz nacisnie odpowiedni przycisk.
		 */
		else if(reset)
		{
			Window.stan=1;
			this.zrobPlansze(lvl);
			reset = false;
		}
		Window.applet.repaint();
	}
	/**
	 * Sprawdz czy jest koniec jesli wszystkie skrzynki zostaly ustawione na miejsca
	 * w ktorych powinny byc aby przejsc poziom
	 * @return c == celi
	 * @see koniec
	 */
	
	/*public void szukaj()
	{
		if(celi < 2 ) return;
		
		Random rand = new Random();
		for(int i=0; i <plansza.length;i++)
		{
			for(int j=0; j<plansza[0].length;j++)
			{
				if(plansza[i][j] == 3 && wygrana[i][j] != 2 )
				{
					int x = rand.nextInt(10);
					int y = rand.nextInt(14);
					
					while(plansza[x][y] != 0)
					{
						x = rand.nextInt(10);
						y = rand.nextInt(14);
					}
					
					plansza[x][y] = 3;
					plansza[i][j] = 0;
				}
			}
		}
	*/
	public boolean koniec()
	{
		int c = 0;
		for(int i=0; i <plansza.length;i++)
		{
			for(int j=0; j<plansza[0].length;j++)
			{
				/** Sprawdzenie warunku koñca
				 * Jeœli wszystkie skrzynki s¹ na pozycji 2 to plansza ukoñczona */
				if((wygrana[i][j]) == 2 && (plansza[i][j]==3))
				{
					c ++;
				}
			}
		}
	return c == celi;
	}
	
	/**Metoda rysuj¹ca plansze */
	public void zrobPlansze(int n)
	{
		/** Za³dowanie kolejnych obrazków do levela */
		int plansza2[][] = Level.load(Window.applet, "texture/lvl" + n + ".png");
		
		
		for(int i=0; i <plansza.length;i++)
		{
			for(int j=0; j<plansza[0].length;j++)
			{
				/**pozycja Gracza */
				plansza[i][j] = plansza2[i][j];
				if(plansza2[i][j] == 4)
				{
					this.poz1 = i;
					this.poz2 = j;
				}
			
			}
		}
		celi = 0;
		/**  */
		for(int i=0; i <plansza.length;i++)
		{
			for(int j=0; j<plansza[0].length;j++)
			{
				if(plansza[i][j] ==2)
				{
					plansza[i][j] = 0;
					wygrana[i][j] = 2;
					celi ++;
				}
				else
				{
					wygrana[i][j] = 0;
				}
			}
		}
	}
	
	/** Metoda obs³uguj¹ca ruch w grze */
	public void ruch(char pozycja)
	{
		switch(pozycja)
		{
		/**poruszanie w lewo 
		 * @param l
		 * @see case 'l'*/
		case 'l':
			/** Jesli pozycja na lewo od gracza jest pust to gracz mo¿e siê poruszyæ w lewo  */ 
			if(plansza[poz1][poz2-1] == 0)
			{
				/** Pozycja z 0 zmienia siê na 4 */ 
				plansza[poz1][poz2-1] =4;
				/** Pozycja na której sta³ gracz zmienia siê na 0 */
				plansza[poz1][poz2]=0;
				poz2--;
			}
			/** Jeœli na lewo od gracza jest skrzynka i za skrzynk¹ jest wolne miejsce to j¹ przesuwa*/
			else if((plansza[poz1][poz2-1] == 3) &&(plansza[poz1][poz2-2] == 0) )
			{
				plansza[poz1][poz2-2] = 3;
				plansza[poz1][poz2-1] = 4;
				plansza[poz1][poz2] = 0;
						poz2--;
			}
			break;
		case 'p':
			if(plansza[poz1][poz2+1] == 0)
			{
				plansza[poz1][poz2+1] =4;
				plansza[poz1][poz2]=0;
				poz2++;
			}
			else if((plansza[poz1][poz2+1] == 3) &&(plansza[poz1][poz2+2] == 0) )
			{
				plansza[poz1][poz2+2] = 3;
				plansza[poz1][poz2+1] = 4;
				plansza[poz1][poz2] = 0;
						poz2++;
			}
			break;
		case 'g':
			if(plansza[poz1-1][poz2] == 0)
			{
				plansza[poz1-1][poz2] =4;
				plansza[poz1][poz2]=0;
				poz1--;
			}
			else if((plansza[poz1-1][poz2] == 3) &&(plansza[poz1-2][poz2] == 0) )
			{
				plansza[poz1-2][poz2] = 3;
				plansza[poz1-1][poz2] = 4;
				plansza[poz1][poz2] = 0;
						poz1--;
			}
			break;
		case 'd':
			if(plansza[poz1+1][poz2] == 0)
			{
				plansza[poz1+1][poz2] =4;
				plansza[poz1][poz2]=0;
				poz1++;
			}
			else if((plansza[poz1+1][poz2] == 3) &&(plansza[poz1+2][poz2] == 0) )
			{
				plansza[poz1+2][poz2] = 3;
				plansza[poz1+1][poz2] = 4;
				plansza[poz1][poz2] = 0;
						poz1++;
			}
			break;
		}
	}
	
}

