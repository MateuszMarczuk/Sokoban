import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;


/**
 * @author Mateusz Marczuk
 * Grupa H4X4S1
 * Projekt JTP
 * 
 */

/**
 * Główna klasa gry.
 */

public class Window extends Applet implements KeyListener, MouseListener

{
	private static final long serialVersionUID = 1L;
	/** 
	 * Zmienne statyczne do obsługi animacji postaci.
	 */
	private static final int DIR_LEFT = 0;
	private static final int DIR_RIGHT = 1;
	private static final int DIR_TOP = 2;
	private static final int DIR_DOWN = 3;

	//private static final Color Image = null;
	
	private int width = 560;
	private int height = 400;
	int rozmiar1= 14*40;
	int rozmiar2 = 10*40;
	Obliczenia obliczenia = new Obliczenia();
	static Applet applet;
	Timer timer = new Timer();
	Image bufor;
	Graphics bg;
	static int stan = 0;
	float frame = 0;
	int dir = 0;
	/**
	 * Flaga mówiąca o tym czy postać się rusza
	 * 
	 */
	boolean isWalking = false;
	int plansza[][] = new int[10][14];
	/**
	 * Flaga mówiąca o tym czy jest aktywna animacja posatci.
	 */
	boolean walkAnim = false;
	boolean boxAnim = false;
	
	static long t = System.currentTimeMillis();
	static long czas = System.currentTimeMillis();
	Image[][] gracz = new Image[4][4];
	Image background;
	Image ground;
	Image win;
	Image box;
	Image mur;
	Image player;
	Image ywin;
	Image start;
	Image level;
	Image reset;
	
	
	
	/** Metoda Init tworzy wszystkie obiekty, ktore sa w niej zawarte.
	 * Ustawia odpowiedni rozmiar, dodaje obslugę myszy i klawiatury,
	 * dodaje buforowanie oraz laduje wszystkie obrazki wykorzystywane w grze.
	 * @see java.applet.Applet#init()
	 */
	public void init()
	{
		applet=this;
		applet.setSize(width,height);
		applet.addKeyListener(this);
		applet.addMouseListener(this);
		//applet.setBackground(background);
		applet.setFocusable(true);
		bufor = createImage(rozmiar1,rozmiar2);
		bg = bufor.getGraphics();
		timer.scheduleAtFixedRate(obliczenia, 15, 15);
		
		obliczenia.zrobPlansze(0);
		/**Załądowanie obrazów */
		level = getImage(getDocumentBase(),"texture/level.png");
		background = getImage(getDocumentBase(), "texture/background.png");
		ground = getImage(getDocumentBase(), "texture/ground.png");
		win = getImage(getDocumentBase(), "texture/win.png");
		box = getImage(getDocumentBase(),"texture/box.png");
		mur = getImage(getDocumentBase(),"texture/mur.png");
		player = getImage(getDocumentBase(),"texture/player.png");
		ywin = getImage(getDocumentBase(),"texture/ywin.png");
		start = getImage(getDocumentBase(),"texture/start.png");
		reset = getImage(getDocumentBase(),"texture/reset.png");

		gracz[0][0] = getImage(getCodeBase(),"texture/lewo1.png");
		gracz[0][1] = getImage(getCodeBase(),"texture/lewo2.png");
		gracz[0][2] = getImage(getCodeBase(),"texture/lewo3.png");
		gracz[0][3] = getImage(getCodeBase(),"texture/lewo4.png");
		
		gracz[1][0] = getImage(getCodeBase(),"texture/prawo1.png");
		gracz[1][1] = getImage(getCodeBase(),"texture/prawo2.png");
		gracz[1][2] = getImage(getCodeBase(),"texture/prawo3.png");
		gracz[1][3] = getImage(getCodeBase(),"texture/prawo4.png");
		
		gracz[2][0] = getImage(getCodeBase(),"texture/tył1.png");
		gracz[2][1] = getImage(getCodeBase(),"texture/tył2.png");
		gracz[2][2] = getImage(getCodeBase(),"texture/tył3.png");
		gracz[2][3] = getImage(getCodeBase(),"texture/tył4.png");
		
		gracz[3][0] = getImage(getCodeBase(),"texture/przód1.png");
		gracz[3][1] = getImage(getCodeBase(),"texture/przód2.png");
		gracz[3][2] = getImage(getCodeBase(),"texture/przód3.png");
		gracz[3][3] = getImage(getCodeBase(),"texture/przód4.png");
	}
	/** Metoda podwójnego buforowania.
	 * @see {@link}java.awt.Container#update(java.awt.Graphics)
	 */
	public void update(Graphics g)
	{
		bg.clearRect(0, 0, rozmiar1, rozmiar2);
		paint(bg);
		g.drawImage(bufor, 0, 0,this);
	}
	/** Metoda paint odpowiedzialna jest za rysowanie ekranu startowego,
	 *  pierwszego i kolejnego poziomu gry oraz ekranu koncowego gry.
	 *   g obiekt metody Graphics
	 *   ekranStartowy : jest rysowany jesli stan gry wynosi 0
	 *   rysujPlansze1 : jest rysowana gdy stan gry wynosi 1
	 *   nowyLevel : jest rysowany gdy stan gry wynosi 2
	 *   koniec : jest rysowany gdy stan gry przedzie na stan równy 3
	 * @see paint
	 */
	
	public void paint(Graphics g)
	{
		switch(stan)
		{
		case 0:
			ekranStartowy(g);
			break;
		case 1:
			if(System.currentTimeMillis() - czas > 10000)
			{
				
				//obliczenia.szukaj();
				czas = System.currentTimeMillis();
				
			}
			rysujPlansze1(g);
			break;
		case 2:
			nowyLevel(g);
			break;
		case 3:
			koniec(g);
			break;
		}
		
	}
	
	
	/**
	 * Metoda Background rysuje tlo gry.
	 * @param g Rysowanie tla.
	 */
	public void Background(Graphics g)
	{
		
		if(stan == 1)
		{
			g.drawImage(background,0,0,this);
		}
	}
	
	/**
	 * Metoda ekranStartowy rysuje poczatkowy obrazek w grze.
	 * @param g obiekt metody graphics
	 * @see ekranStartowy
	 */
	public void ekranStartowy(Graphics g)
	{
		if(stan == 0)
		{
			g.drawImage(start,0,0,this);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, WIDTH, HEIGHT);
		}
	}
	
	/**
	 * Metoda nowyLevel rysuje kolejny level w grze.
	 * @param g - obiekt metody graphics 
	 */
	
	
	public void nowyLevel(Graphics g)
	{
		if(stan == 2)
		{
			g.drawImage(level,0,0,this);
			
			//if(System.currentTimeMillis()-t > 1000)
				//stan = 1;
		}
	}
	
	/**
	 * Metoda koniec rysuje obrazek koncowy w grze.
	 * @param g - obiekt metody Graphics
	 */
	public void koniec(Graphics g)
	{
		
		if(stan == 3)
		{
			g.drawImage(ywin,0,0,this);
		}
		
	}
	
	
	
	/**
	 * Metoda rysujPalnsze1  odpowiedzialna jest za rysowanie planszy.
	 * @param g - obiekt metody Graphics
	 */
	
	public void rysujPlansze1(Graphics g)
	{
		frame += 0.1f;
		frame = frame%4;
		
		for(int i = 0; i< obliczenia.plansza.length;i++)
		{
			for(int j=0; j< obliczenia.plansza[0].length;j++)
			{
				switch(obliczenia.plansza[i][j])
				{
				case 0:
					/** Rysowanie tła*/
					g.drawImage(background,40*j,40*i,this);
					break;
				
				case 1:
					/** Rysowanie muru */
					g.drawImage(mur,40*j,40*i,this);
					break;
				
					
				}
				
				if(obliczenia.wygrana[i][j] ==2)
				{
					/**Rysowanie pola WIN */
					g.drawImage(win,40*j,40*i,this);
				}
				
			switch(obliczenia.plansza[i][j])
			{
			
			case 3:
				/** Rysowanie skrzynek */
					g.drawImage(box,40*j,40*i,this);
					break;
			case 4:
				/**Rysowanie gracza  */
				if(this.walkAnim)
					g.drawImage(gracz[dir][(int) frame],40*j,40*i,this);
				else
					g.drawImage(gracz[dir][0],40*j,40*i,this);
				break;
				case 5:
					/**Rysowanie przycisku reset */
					g.drawImage(reset,40*j,40*i,this);
					break;
				
			}
		}
	}
}	
	
	
	/** Obsluga klawiatury.
	 * @param 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			/**Pobranie ruchu z klasy Obliczenia */
			obliczenia.ruch('l');
			/** zmienna statyczna do obsługi animacji */
			this.dir = Window.DIR_LEFT;
			/**Ustawienie wartości walkAnim true */
			this.walkAnim = true;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			obliczenia.ruch('p');
			this.dir = Window.DIR_RIGHT;
			this.walkAnim = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			obliczenia.ruch('g');
			this.dir = Window.DIR_TOP;
			this.walkAnim = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			obliczenia.ruch('d');
			this.dir = Window.DIR_DOWN;
			this.walkAnim = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F1)
		{
			//obliczenia.szukaj();
			
		}
		/**Warunek dzieki któremu jeżeli na erkanie startowym naciśniemy spacje załoduje się nowy level */
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if(stan == 0)
			{
				stan = 1;
			}
		}
	}
	
	@Override
	/**Wywołanie gdy póścimy przycisk klawiatury
	 * Jeśli póścimy przycisk we wszystki warunkach zwarca wartość flase. */
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			this.walkAnim = false;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			this.walkAnim = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			this.walkAnim = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			this.walkAnim = false;
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) 
	{
		
		
	}
	@Override
	/**
	 * Obsługa Myszki
	 */
	public void mouseClicked(MouseEvent e) {
		if(stan == 1)
		{
			/**
			 *  Współrzędne punktu w któr nalezy kliknąć
			 *  */
			int pos_x = 13*40;
			int pos_y = 9*40;
			int w = 40;
			int h = 40;
			/**
			 * Pobranie aktualnego położenia myszki
			 */
			int mouse_x = e.getX();
			int mouse_y = e.getY();
			/** Warunek sprawdzający czy aktualne położenie jest w odrębie naszego przycisku
			 * jeśli tak to zwraca wartość true.*/
			if(mouse_x >= pos_x && mouse_x <= pos_x+w &&
			   mouse_y >= pos_y && mouse_y <= pos_y+h)
			{
				this.obliczenia.reset = true;
			}
		}
		/** 
		 * Kolejny warunek do ładowanie kolejnej mapki poprzez kliknięcie
		 * */
		else if(stan == 2)
		{
			/**Współrzędne punktu w który należy kliknąć */
			int pos_x = 200;
			int pos_y = 200;
			int w = 160;
			int h = 50;
			/**Pobranie aktualnego położenia myszki */
			int mouse_x = e.getX();
			int mouse_y = e.getY();
			/** Warunek sprawdzający czy aktualne położenie jest w odrębie naszego przycisku
			 * jeśli tak to załąduje kolejny poziom gry.*/
			if(mouse_x >= pos_x && mouse_x <= pos_x+w &&
			   mouse_y >= pos_y && mouse_y <= pos_y+h)
			{
				stan = 1;
			}
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
