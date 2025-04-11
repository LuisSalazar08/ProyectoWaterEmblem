package entidad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.ManejadorTeclas;

public class Cursor extends Entidad {
	private GamePanel gP;
	private ManejadorTeclas mT;
	private final int pantallaX, pantallaY;
	private final int tileSize;
	private boolean seMovio;
	
	public Cursor(GamePanel gP, ManejadorTeclas mT)
	{
		this.gP = gP;
		this.mT = mT;
		this.tileSize = gP.getTamanioTile();
		this.pantallaX = gP.getAnchoPantalla() / 2 - (gP.getTamanioTile() / 2);
		this.pantallaY = gP.getAltoPantalla() / 2  - (gP.getTamanioTile() / 2);
		configuracionInicial();
		getSpritesJugador();
		seMovio = false;
	}
	public void configuracionInicial() 
	{
		this.mundoX = (gP.getMaxColMundo() / 2) * gP.getTamanioTile();
	    this.mundoY = (gP.getMaxRenMundo() / 2) * gP.getTamanioTile();
		this.velocidad = 4;
		this.direccion = "abajo";
	}
	
	public void getSpritesJugador() 
	{
		try {
			this.arriba1 = ImageIO.read(getClass().getResourceAsStream("/spritesCursor/CursorPlayer.png"));
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
    public void update() {
        if (!seMovio) {
            if (mT.getTeclaArriba()) {
                this.mundoY -= tileSize;
                seMovio = true;
            } else if (mT.getTeclaAbajo()) {
                this.mundoY += tileSize;
                seMovio = true;
            } else if (mT.getTeclaIzquierda()) {
                this.mundoX -= tileSize;
                seMovio = true;
            } else if (mT.getTeclaDerecha()) {
                this.mundoX += tileSize;
                seMovio = true;
            }
        }
        if (!mT.getTeclaArriba() && !mT.getTeclaAbajo() && !mT.getTeclaIzquierda() && !mT.getTeclaDerecha()) {
            seMovio = false;
        }
    }
	public void draw(Graphics2D g2) {
		BufferedImage sprite = this.arriba1;
		g2.drawImage(sprite, this.pantallaX, this.pantallaY, gP.getTamanioTile(), gP.getTamanioTile(), null);
	}
	public int getX() {
		return this.mundoX;
	}
	public int getY() {
		return this.mundoY;
	}
	public int getVelocidad() {
		return this.velocidad;
	}
	public void setX(int valor) {
		this.mundoX = valor;
	}
	public void setY(int valor) {
		this.mundoY = valor;
	}
	public void setSpeed(int valor) {
		this.velocidad = valor;
	}
	public int getPantallaX() {
		return this.pantallaX;
	}
	public int getPantallaY() {
		return this.pantallaY;
	}
	public int getMundoX() {
		return this.mundoX;
	}
	public int getMundoY() {
		return this.mundoY;
	}
}