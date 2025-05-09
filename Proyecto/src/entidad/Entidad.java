package entidad;

import java.awt.Graphics2D;

import Main.GamePanel;
import Main.ManejadorTeclas;

public abstract class Entidad 
{
	protected int mundoX, mundoY, velocidad;
	protected GamePanel gP;
	protected ManejadorTeclas mT;
	protected final int pantallaX, pantallaY;
	protected final int tileSize;
	
	public Entidad(GamePanel gP, ManejadorTeclas mT)
	{
		this.gP = gP;
		this.mT = mT;
		this.tileSize = gP.getTamanioTile();
		this.pantallaX = gP.getAnchoPantalla() / 2 - (gP.getTamanioTile() / 2);
		this.pantallaY = gP.getAltoPantalla() / 2  - (gP.getTamanioTile() / 2);
		this.mundoX = (gP.getMaxColMundo() / 2) * gP.getTamanioTile();
	    this.mundoY = (gP.getMaxRenMundo() / 2) * gP.getTamanioTile();
		this.velocidad = 4;
	}
	
	public abstract void update(); 
    public abstract void draw(Graphics2D g2);
    
    public int getMundoX() {return this.mundoX;}
    public int getMundoY() {return this.mundoY;}
    public int getVelocidad() {return this.velocidad;}

    public GamePanel getGamePanel() {return this.gP;}
    public ManejadorTeclas getManejadorTeclas() {return this.mT;}
    public int getPantallaX() {return this.pantallaX;}
    public int getPantallaY() {return this.pantallaY;}
    public int getTileSize() {return this.tileSize;}
    
    public void setMundoX(int val) {this.mundoX=val;}
    public void setMundoY(int val) {this.mundoY=val;}
    public void setVelocidad(int val) {this.velocidad=val;}
    
}
