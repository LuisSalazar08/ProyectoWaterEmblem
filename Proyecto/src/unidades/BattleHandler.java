package unidades;

import java.util.Random;

import entidad.Unidad;
public class BattleHandler 
{
	private BattleCalculator atacante,defensor;
	public void batalla(Unidad atacante,Unidad defensor)
	{
		Random rng1 = new Random();
		Random rng2 = new Random();
		int rngA = rng1.nextInt(0,101)+rng2.nextInt(0,101);
		rngA/=2;
		int rngB = rng1.nextInt(0,101)+rng2.nextInt(0,101);
		rngB/=2;
		this.calcularBatalla(atacante,defensor);
		if(rngA<=this.getHitAtacante())
		{
			if(rngA<=this.getCritAtacante())
				defensor.getStats().setHP(this.getDamageAtacante()*3);
			else defensor.getStats().setHP(this.getDamageAtacante());
		}
		if(defensor.getStats().getHP()==0)
			return;
		if(rngB<=this.getHitDefensor())
		{
			if(rngB<=this.getCritDefensor())
				atacante.getStats().setHP(this.getDamageDefensor()*3);
			else atacante.getStats().setHP(this.getDamageDefensor());
		}
		if(atacante.getStats().getHP()==0)
			return;
		if(atacante.getStats().getSPD()>=defensor.getStats().getSPD()+5)
		if(rngA<=this.getHitAtacante())
		{
			if(rngA<=this.getCritAtacante())
				defensor.getStats().setHP(this.getDamageAtacante()*3);
			else defensor.getStats().setHP(this.getDamageAtacante());
		}
		if(defensor.getStats().getSPD()>=atacante.getStats().getSPD()+5)
		if(rngB<=this.getHitDefensor())
		{
			if(rngB<=this.getCritDefensor())
				atacante.getStats().setHP(this.getDamageDefensor()*3);
			else atacante.getStats().setHP(this.getDamageDefensor());
		}
	}
	public void calcularBatalla(Unidad atacante,Unidad defensor)
	{
		this.atacante.calcularStats(atacante);
		this.defensor.calcularStats(defensor);
		this.atacante.calcularStatsUnidad(atacante, defensor, this.defensor);
		this.defensor.calcularStatsUnidad(defensor, atacante, this.atacante);
	}
	public int getHitAtacante() {return this.atacante.getAccuracy();}
	public int getCritAtacante() {return this.atacante.getCriticalChance();}
	public int getDamageAtacante() {return this.atacante.getDamage();}
	
	public int getHitDefensor() {return this.defensor.getAccuracy();}
	public int getCritDefensor() {return this.defensor.getCriticalChance();}
	public int getDamageDefensor() {return this.defensor.getDamage();}
	
	public BattleCalculator getAtacante() {return this.atacante;}
	public BattleCalculator getDefensor() {return this.defensor;}
}
