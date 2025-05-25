package entidad;

import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Main.GamePanel;
import Main.ManejadorTeclas;
import armas.Armas;
import tile.ManejadorTiles;
import unidades.Clases;
import unidades.Inventario;
import unidades.Stats;

public class Unidad extends Entidad
{
	private String nombre;
	private boolean seleccionada;
	private Stats stats;
	private Inventario inventario;
	private Armas armaEquipada;
	private Clases clase;
	private BufferedImage[] idleFrames;
	private int frameActual = 0;
	private int contadorFrames = 0;
	private final int velocidadAnimacion = 10;
	ManejadorMovimiento mMV;
    private int startX, startY, minX, maxX, minY, maxY;
	
	public Unidad(String nombre, GamePanel gP, ManejadorTeclas mT, int mundoX, int mundoY, Stats statsbase, Clases clase)
	{
		super(gP,mT);
		this.nombre = nombre;
		this.mundoX = mundoX;
        this.mundoY = mundoY;
        CalculaMovimiento cmv = new CalculaMovimiento(this,this.mT,this.tileSize);
		this.mMV = new ManejadorMovimiento(cmv,100,100);
        this.seleccionada = false;
        this.stats = statsbase;
        this.clase = clase;
        
        idleFrames = new BufferedImage[6];
        try {
            for (int i = 0; i < 6; i++) {
                idleFrames[i] = ImageIO.read(getClass().getResourceAsStream(
                    "/spritesUnidad/" + this.getTipoUnidad() + i + ".png"
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.inventario = new Inventario(this);
        this.armaEquipada = this.inventario.getAllArmas()[0];
	}
	
    public void initMovimientoBounds() {
        startX = getMundoX();
        startY = getMundoY();
        int movTiles = stats.getMOV();
        int ts = gP.getTamanioTile();
        minX = startX - movTiles * ts;
        maxX = startX + movTiles * ts;
        minY = startY - movTiles * ts;
        maxY = startY + movTiles * ts;
    }
	
	@Override
	public void update() {
	    if (this.seleccionada) {
	        boolean anyArrow = mT.getTeclaArriba() 
	                        || mT.getTeclaAbajo()
	                        || mT.getTeclaIzquierda() 
	                        || mT.getTeclaDerecha();

	        mMV.updateMV(anyArrow);

	        if (mT.getTeclaEnter()) {
	            this.seleccionada = false;
	        }
	        gP.getJugador().setMundoX(this.mundoX);
	        gP.getJugador().setMundoY(this.mundoY);
	    
	        return;
	    }

	    contadorFrames++;
	    if (contadorFrames > velocidadAnimacion) {
	        frameActual = (frameActual + 1) % idleFrames.length;
	        contadorFrames = 0;
	    }
	}
	
	public void drawHighlight(Graphics2D g2) {
	    int ts   = this.tileSize;
	    int camX = gP.getJugador().getMundoX();
	    int camY = gP.getJugador().getMundoY();

	    ManejadorTiles mt = gP.getManejadorTiles();
	    for (int px = minX; px <= maxX; px += ts) {
	        for (int py = minY; py <= maxY; py += ts) {
	            int col  = px / ts;
	            int fila = py / ts;
	            
	            boolean pasable = mt.isPassable(fila, col);

	            boolean ocupado = false;
	            for (Entidad otra : gP.getME().getEntidades()) {
	                if (otra != this
	                 && otra.getMundoX() == px
	                 && otra.getMundoY() == py) {
	                    ocupado = true;
	                    break;
	                }
	            }

	            if (!pasable || ocupado) {
	                g2.setColor(new Color(255, 0, 0, 100));
	            } else {
	                g2.setColor(new Color(0, 255, 0, 80));
	            }

	            int sx = px - camX + pantallaX;
	            int sy = py - camY + pantallaY;
	            g2.fillRect(sx, sy, ts, ts);
	        }
	    }
	}
	
    @Override
    public void draw(Graphics2D g2) {
        int ts   = this.tileSize;
        int camX = gP.getJugador().getMundoX();
        int camY = gP.getJugador().getMundoY();

        int sx = mundoX - camX + pantallaX;
        int sy = mundoY - camY + pantallaY;

        if (idleFrames != null && idleFrames[frameActual] != null) {
            g2.drawImage(idleFrames[frameActual], sx, sy, ts, ts, null);
        } else {
            g2.setColor(seleccionada ? Color.YELLOW : Color.BLUE);
            g2.fillRect(sx, sy, ts, ts);
        }
    }
    public Inventario getInventario() {return this.inventario;}
	public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
	public boolean estaSeleccionada() {
        return this.seleccionada;
    }
	public Stats getStats() {
		return this.stats;
	}
	public void setArmaEquipada(Armas arma) {
        this.armaEquipada = arma;
    }
    public Armas getArmaEquipada() {
        return this.armaEquipada;
    }
    public String getTipoUnidad() {
        return clase.toString();
    }
    public String getNombre() {
    	return this.nombre;
    }
    public BufferedImage getIdleFrameActual() {
        return idleFrames != null ? idleFrames[frameActual] : null;
    }
    public int getMinX() { return minX; }
    public int getMaxX() { return maxX; }
    public int getMinY() { return minY; }
    public int getMaxY() { return maxY; }
}
