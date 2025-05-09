package entidad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Main.GamePanel;
import Main.ManejadorTeclas;

public class Unidad extends Entidad
{
	private boolean seleccionada;
	
	ManejadorMovimiento mMV;
	
	public Unidad(GamePanel gP, ManejadorTeclas mT, int mundoX, int mundoY)
	{
		super(gP,mT);
		this.mundoX = mundoX;
        this.mundoY = mundoY;
        CalculaMovimiento cmv = new CalculaMovimiento(this,this.mT,this.tileSize);
		this.mMV = new ManejadorMovimiento(cmv,100,100);
        this.seleccionada = false;
	}
	
	@Override
	public void update() 
    {
		if(this.seleccionada)
		{
			boolean keyPressed = this.mT.getMovimiento();
	    	mMV.updateMV(keyPressed);
		}
    }
	@Override
	public void draw(Graphics2D g2) 
	{
		
		int pantallaX = mundoX - gP.getJugador().getMundoX() + this.pantallaX;
        int pantallaY = mundoY - gP.getJugador().getMundoY() + this.pantallaY;

        g2.setColor(seleccionada ? Color.YELLOW : Color.BLUE);
        g2.fillRect(pantallaX, pantallaY, tileSize, tileSize);
		
	}
	public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
	public boolean estaSeleccionada() {
        return this.seleccionada;
    }
}
