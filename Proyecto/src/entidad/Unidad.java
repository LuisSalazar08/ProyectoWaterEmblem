package entidad;

import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	
	private List<int[]> movimientoTiles;
    private int startX, startY, minX, maxX, minY, maxY;
    private boolean hasActed = false;
    private boolean hasMoved=false;
	
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
	
    public void initMovimientoBounds() 
    {
        startX = getMundoX();
        startY = getMundoY();
        int movTiles = stats.getMOV();
        int ts = gP.getTamanioTile();
        
        movimientoTiles = new ArrayList<>();
        int unitCol = startX / ts;
        int unitRow = startY / ts;
        for(int dx = -movTiles; dx <= movTiles; dx++) 
            for(int dy = -(movTiles - Math.abs(dx)); dy <= (movTiles - Math.abs(dx)); dy++) 
            {
                int targetCol = unitCol + dx;
                int targetRow = unitRow + dy;
                
                // Convertir a coordenadas mundiales
                int wx = targetCol * ts;
                int wy = targetRow * ts;
                
                movimientoTiles.add(new int[]{wx, wy});
            }
    }
	
	@Override
	public void update() 
	{
		if(hasActed) return;
	    if (this.seleccionada) 
	    {
	        boolean anyArrow = mT.getTeclaArriba() 
	                        || mT.getTeclaAbajo()
	                        || mT.getTeclaIzquierda() 
	                        || mT.getTeclaDerecha();

	        mMV.updateMV(anyArrow);

	        if (mT.getTeclaEnter()) {
	            this.seleccionada = false;
	            this.hasMoved=true;
	        }
	        gP.getJugador().setMundoX(this.mundoX);
	        gP.getJugador().setMundoY(this.mundoY);
	    
	        return;
	    }

	    contadorFrames++;
	    if (contadorFrames > velocidadAnimacion) 
	    {
	        frameActual = (frameActual + 1) % idleFrames.length;
	        contadorFrames = 0;
	    }
	}
	
	public void drawHighlight(Graphics2D g2) 
	{
	    int ts   = this.tileSize;
	    int camX = gP.getJugador().getMundoX();
	    int camY = gP.getJugador().getMundoY();
	    ManejadorTiles mt = gP.getManejadorTiles();
	    
	    for(int[] tile : this.movimientoTiles)
	    {
	    	int px = tile[0];
	        int py = tile[1];
	        
	        int col = px / ts;
	        int fila = py / ts;
	        
	        boolean pasable = mt.isPassable(fila, col);
	        
	        boolean ocupado = gP.getME().getEntidades().stream()
	            .anyMatch(e -> e != this && 
	                e.getMundoX() == px && 
	                e.getMundoY() == py);
	        
	        int sx = px - camX + pantallaX;
	        int sy = py - camY + pantallaY;
	        
	        g2.setColor(new Color(
	            pasable && !ocupado ? 0 : 255, // R
	            pasable && !ocupado ? 255 : 0, // G
	            0, // B
	            pasable && !ocupado ? 80 : 100 // Alpha
	        ));
	        
	        g2.fillRect(sx, sy, ts, ts);
	    }
	}
	
    @Override
    public void draw(Graphics2D g2) 
    {
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
    public List<int[]> getTilesMovimiento() {
        return Collections.unmodifiableList(movimientoTiles);
    }
    public boolean puedeMoverseA(int x, int y) 
    {
        int ts = gP.getTamanioTile();
        return movimientoTiles.stream()
            .anyMatch(t -> t[0] == x && t[1] == y);
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
    public boolean isViva() {return this.stats.getHP()>0;}
    public boolean hasMoved() {return hasMoved;}
    public void setHasMoved(boolean moved) {this.hasMoved=moved;}
    public boolean hasActed() { return hasActed; }
    public void setHasActed(boolean acted) { this.hasActed = acted; }
    public int getMinX() { return minX; }
    public int getMaxX() { return maxX; }
    public int getMinY() { return minY; }
    public int getMaxY() { return maxY; }
}
