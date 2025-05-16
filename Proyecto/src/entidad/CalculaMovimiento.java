package entidad;

import Main.ManejadorTeclas;

public class CalculaMovimiento 
{
	private final Entidad entidad;
	private final ManejadorTeclas mT;
	private final int tileSize;
	
	public CalculaMovimiento(Entidad e, ManejadorTeclas mt,int ts)
	{
		this.entidad=e;
		this.mT=mt;
		this.tileSize=ts;
	}
	public void mover() {
	    int ts = tileSize;
	    int x = entidad.getMundoX();
	    int y = entidad.getMundoY();
	    int nx = x, ny = y;

	    if (mT.getTeclaArriba())    ny = y - ts;
	    if (mT.getTeclaAbajo())     ny = y + ts;
	    if (mT.getTeclaIzquierda()) nx = x - ts;
	    if (mT.getTeclaDerecha())   nx = x + ts;

	    int col = nx / ts;
	    int fila= ny / ts;
	    boolean pasable = entidad.getGamePanel()
	                      .getManejadorTiles()
	                      .isPassable(fila, col);

	    boolean ocupado = false;
	    for (Entidad otra : entidad.getGamePanel().getME().getEntidades()) {
	        if (otra != entidad
	         && otra.getMundoX() == nx
	         && otra.getMundoY() == ny) {
	            ocupado = true;
	            break;
	        }
	    }

	    if (entidad instanceof Unidad) {
	        Unidad u = (Unidad) entidad;
	        if (u.estaSeleccionada()) {
	            if (!pasable || ocupado) {
	                return; 
	            }
	            // límites de MOV
	            if (nx >= u.getMinX() && nx <= u.getMaxX()) {
	                entidad.setMundoX(nx);
	            }
	            if (ny >= u.getMinY() && ny <= u.getMaxY()) {
	                entidad.setMundoY(ny);
	            }
	            return;
	        }
	    }

	    entidad.setMundoX(nx);
	    entidad.setMundoY(ny);
	}

}
