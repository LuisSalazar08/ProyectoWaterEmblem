package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entidad.Unidad;
import entidad.Cursor;
import entidad.Entidad;
import tile.ManejadorTiles;
import ui.InfoBox;
import ui.OptionMenu;
import ui.TurnBox;
import entidad.Unidad;

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
	Cursor cursor = new Cursor(this, mT);
	ManejadorTiles mTi = new ManejadorTiles(this);
	ManejadorEntidades mE = new ManejadorEntidades(this);
	InfoBox infoBox = new InfoBox(10, 10, 150, 100);
	TurnBox turnBox = new TurnBox(this.anchoPantalla, 60, 40);
	OptionMenu menu = new OptionMenu(this, 100, 0, "Mover", "Atacar","Esperar" ,"Salir");
	TurnManager turnManager = new TurnManager(this, cursor);
	
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
	    if(this.turnManager.isPlayerTurn())
	    	this.logicaJugador();
	    else this.logicaEnemigos();	
	}
	public void logicaEnemigos()
	{
		
	}
	public void logicaJugador()
	{
		Unidad u = cursor.getUnidadSeleccionada();
	    mE.updateAll();
	    if (u != null && u.estaSeleccionada()) {
	        u.update();      
	        return;          
	    }

	    if (menu.isVisible()) {
	        menu.update(mT.getFlechaArriba(), mT.getFlechaAbajo(),
	                    mT.getTeclaEnter(),   mT.getTeclaEsc());
	    } else {
	        if (mT.getTeclaArriba() || mT.getTeclaAbajo() ||
	            mT.getTeclaIzquierda() || mT.getTeclaDerecha()) {
	            cursor.update();
	            cursor.actualizarSeleccion(this);
	        }
	        if (u != null && mT.getTeclaEnter()) {
	            int cx = (anchoPantalla - menu.getWidth()) / 2;
	            int cy = (altoPantalla  - menu.getHeight()) / 2;
	            menu.show(cx, cy);
	        }
	    }
	}
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;

	    mTi.draw(g2);
	    mE.drawALL(g2); 

	    for (Entidad e : getME().getEntidades()) {
	        if (e instanceof Unidad) {
	            Unidad u = (Unidad) e;
	            if (u.estaSeleccionada()) {
	                u.drawHighlight(g2);
	            }
	        }
	    }

	    cursor.draw(g2);
	    turnBox.draw(g2, turnManager);
	    infoBox.draw(g2, cursor.getUnidadSeleccionada());
	    menu.draw(g2);
	    g2.dispose();
	}
	
	public TurnManager getTM() {return this.turnManager;}
	public ManejadorTeclas getMT()
	{
		return this.mT;
	}
	
	public ManejadorEntidades getME()
	{
		return this.mE;
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
	public Cursor getJugador() {
		return this.cursor;
	}
	public ManejadorTiles getManejadorTiles() {
		return this.mTi;
	}
	
	
}
