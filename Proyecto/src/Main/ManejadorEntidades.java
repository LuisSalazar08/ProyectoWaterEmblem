package Main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entidad.Entidad;
import entidad.Unidad;

public class ManejadorEntidades 
{
	private List<Entidad> entidades = new ArrayList<>();
	private final GamePanel gp;
	public ManejadorEntidades(GamePanel gp)
	{
		this.gp=gp;
		this.generarEntidades();
	}
	public void generarEntidades()
	{
		Random random = new Random();
		for(int i=0;i<5;i++)
		{
			int x = random.nextInt(this.gp.getMaxColMundo())*this.gp.getTamanioTile();
			int y = random.nextInt(this.gp.getMaxRenMundo())*this.gp.getTamanioTile();
			Unidad unidad = new Unidad(this.gp,this.gp.getMT(),x,y);
			this.add(unidad);
		}
	}
	public void add(Entidad e)
	{
		entidades.add(e);
	}
	public void remove(Entidad e)
	{
		entidades.remove(e);
	}
	public void updateAll()
	{
		for(Entidad e : entidades)
			e.update();
	}
	public void drawALL(Graphics2D g2)
	{
		for(Entidad e : entidades)
			e.draw(g2);
	}
	public List<Entidad> getEntidades()
	{
		return this.entidades;
	}
}
