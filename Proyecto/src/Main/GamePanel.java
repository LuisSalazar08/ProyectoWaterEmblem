package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entidad.Jugador;
import tile.ManejadorTiles;

public class GamePanel extends JPanel implements Runnable
{
	//configutacion pantalla
	private final int tamanioOriginalTile = 16;
	private final int escala = 3;
	private final int tamanioTile = tamanioOriginalTile * escala;
	private final int maxRenPantalla = 15;
	private final int maxColPantalla = 26;
	private final int anchoPantalla = tamanioTile * maxColPantalla;
	private final int altoPantalla = tamanioTile * maxRenPantalla;
	
	Thread hebraJuego;
	ManejadorTeclas mT = new ManejadorTeclas();
	Jugador jugador = new Jugador(this, mT);
	ManejadorTiles mTi = new ManejadorTiles(this);
	int playerX = 100, playerY = 100, velocidadJugador = 4;
	
	int	FPS = 60;
	
	//Configuración del mundo
	private final int maxRenMundo = 45;
	private final int maxColMundo = 78;
	private final int anchoMundo = this.tamanioTile * this.maxColMundo;
	private final int altoMundo = this.tamanioTile * this.maxRenMundo;
	
	public GamePanel() 
	{
		this.setPreferredSize(new Dimension(this.anchoPantalla, this.altoPantalla));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(mT);
		this.setFocusable(true);
	}
	public void iniciaHebraJuego() 
	{
		hebraJuego = new Thread(this);
		hebraJuego.start();
	}
	@Override
	public void run() 
	{
		double intervaloDibujo = 1000000000 / FPS;
		double delta = 0;
		long ultimaVez = System.nanoTime();
		long tiempoActual;
		while(hebraJuego != null) 
		{
			tiempoActual =  System.nanoTime();
			delta += (tiempoActual - ultimaVez) / intervaloDibujo;
			ultimaVez = tiempoActual;
			if(delta >= 1) 
			{
				update();
				repaint();
				delta--;
			}
		}
	}
	public void update() {
		jugador.update();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		mTi.draw(g2);
		jugador.draw(g2);
		//g2.setColor(Color.GREEN);
		//g2.fillRect(playerX, playerY, tamanioTile, tamanioTile);
		g2.dispose();
	}
	
	public int getTamanioOriginalTile() {
		return this.tamanioOriginalTile;
	}
	public int getEscala() {
		return this.escala;
	}
	public int getTamanioTile() {
		return this.tamanioTile;
	}
	public int getMaxRenPantalla() {
		return this.maxRenPantalla;
	}
	public int getMaxColPantalla() {
		return this.maxColPantalla;
	}
	public int getAnchoPantalla() {
		return this.anchoPantalla;
	}
	public int getAltoPantalla() {
		return this.altoPantalla;
	}
	public int getMaxRenMundo() {
		return this.maxRenMundo;
	}
	public int getMaxColMundo() {
		return this.maxColMundo;
	}
	public int getAnchoMundo() {
		return this.anchoMundo;
	}
	public int getAltoMundo() {
		return this.altoMundo;
	}
	public Jugador getJugador() {
		return this.jugador;
	}
	
	
}
