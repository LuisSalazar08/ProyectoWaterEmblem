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

	    if (entidad instanceof Unidad) 
	    {
            Unidad unidad = (Unidad) entidad;
            if (unidad.estaSeleccionada()) 
            {
                if (!validarMovimientoUnidad(unidad, nx, ny))
                    return;
            } else 
            {
                // Movimiento normal si no está seleccionada
                entidad.setMundoX(nx);
                entidad.setMundoY(ny);
            }
        } else 
        {
            // Movimiento para entidades no-unidad
            entidad.setMundoX(nx);
            entidad.setMundoY(ny);
        }
	}
	private boolean validarMovimientoUnidad(Unidad unidad, int nx, int ny) 
	{
        boolean enAreaMovimiento = unidad.puedeMoverseA(nx, ny);
        
        int col = nx / tileSize;
        int fila = ny / tileSize;
        boolean pasable = entidad.getGamePanel()
                            .getManejadorTiles()
                            .isPassable(fila, col);
        
        boolean ocupado = entidad.getGamePanel().getME().getEntidades().stream()
            .anyMatch(e -> e != entidad 
                && e.getMundoX() == nx 
                && e.getMundoY() == ny);

        if (enAreaMovimiento && pasable && !ocupado) 
        {
            unidad.setMundoX(nx);
            unidad.setMundoY(ny);
            return true;
        }
        return false;
    }
}
