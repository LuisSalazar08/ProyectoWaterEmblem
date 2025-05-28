package unidades;
import armas.Armas;
import armas.TipoArma;
import entidad.Enemigo;
import entidad.Unidad;
import java.util.Random;
public class Inventario 
{
	private Armas[] armas = new Armas[4];
	public Inventario(Unidad u)
	{
		TipoArma cl = u.getStats().getArma();
		this.asignarArmas(cl);
	}
	public Inventario(Enemigo u)
	{
		TipoArma cl = u.getStats().getArma();
		this.asignarArmas(cl);
	}
	private void asignarArmas(TipoArma cl)
	{
		for(int i=0;i<armas.length;i++)
		{
			Random r = new Random();
			if(TipoArma.Lanza==cl)
			{
				int n= r.nextInt(7, 14);
				this.armas[i]=Armas.values()[n];
			}
			if(TipoArma.Espada==cl)
			{
				int n= r.nextInt(0, 7);
				this.armas[i]=Armas.values()[n];
			}
			if(TipoArma.Hacha==cl)
			{
				int n= r.nextInt(14, 23);
				this.armas[i]=Armas.values()[n];
			}
			if(TipoArma.Grimorio==cl)
			{
				int n= r.nextInt(28, 34);
				this.armas[i]=Armas.values()[n];
			}
		}
	}
	public Armas[] getAllArmas() {return this.armas;}
}
