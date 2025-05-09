package entidad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.ManejadorTeclas;

public class Cursor extends Entidad 
{
	private BufferedImage sprite;
	
	private Unidad unidadSeleccionada;
	
	private ManejadorMovimiento mMV;
	private boolean enterPresionadoAnterior = false;
	
	public Cursor(GamePanel gP, ManejadorTeclas mT)
	{
		super(gP,mT);
		CalculaMovimiento cmv = new CalculaMovimiento(this,this.mT,this.tileSize);
		this.mMV = new ManejadorMovimiento(cmv,100,100);
		this.getSpritesJugador();
	}
	
	public void getSpritesJugador() 
	{
		try {
			this.sprite= ImageIO.read(getClass().getResourceAsStream("/spritesCursor/CursorPlayer.png"));
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	@Override
    public void update() 
    {
		
		boolean keyPressed = this.mT.getMovimiento();
		mMV.updateMV(keyPressed);
		actualizarSeleccion(gP);
		boolean enterActual=this.mT.getTeclaEnter();
		this.enterPresionadoAnterior=enterActual;
		if(this.unidadSeleccionada==null)
		{
			if(this.enterPresionadoAnterior)
				for(Entidad e : gP.getME().getEntidades())
					if(e instanceof Unidad u)
						if((u.getMundoX() == this.mundoX) 
								&& (u.getMundoY()==this.mundoY))
						{
							this.unidadSeleccionada=u;
							u.setSeleccionada(true);
							break;
						}
		}
		if(this.unidadSeleccionada!=null)
		{
			this.unidadSeleccionada.update();
		}
    	
    }
	
	public void actualizarSeleccion(GamePanel gP) {
	    int tileCursorX = mundoX / gP.getTamanioTile();
	    int tileCursorY = mundoY / gP.getTamanioTile();
	    
	    unidadSeleccionada = null;
	    for (Entidad u : gP.getME().getEntidades()) {
	        int tilePX = u.getMundoX() / gP.getTamanioTile();
	        int tilePY = u.getMundoY() / gP.getTamanioTile();
	        if (tilePX == tileCursorX && tilePY == tileCursorY) {
	        	unidadSeleccionada = (Unidad) u;
	            break;
	        }
	    }
	}
	@Override
	public void draw(Graphics2D g2) 
	{
		int pantallaX = mundoX - gP.getJugador().getMundoX() + this.pantallaX;
        int pantallaY = mundoY - gP.getJugador().getMundoY() + this.pantallaY;
		g2.drawImage(sprite, pantallaX, pantallaY, gP.getTamanioTile(), gP.getTamanioTile(), null);
	}
	
	public Unidad getUnidadSeleccionada() {
		return this.unidadSeleccionada;
	}
}