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
	public void mover()
	{
		if (mT.getTeclaArriba()) entidad.setMundoY(entidad.getMundoY() - tileSize);
        if (mT.getTeclaAbajo()) entidad.setMundoY(entidad.getMundoY() + tileSize);
        if (mT.getTeclaIzquierda()) entidad.setMundoX(entidad.getMundoX() - tileSize);
        if (mT.getTeclaDerecha()) entidad.setMundoX(entidad.getMundoX() + tileSize);
	}
}
