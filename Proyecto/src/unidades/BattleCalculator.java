package unidades;

import armas.Armas;
import armas.TipoArma;
import entidad.Unidad;

public class BattleCalculator 
{
	private int AttackSpeed;
	private int HitRate;
	private int Avoid;
	private int CriticalRate;
	private int CriticalEvade;
	
	private int Accuracy;
	private int CriticalChance;
	private int Damage;
	public void calcularStats(Unidad u)
	{
		Armas arma = u.getArmaEquipada();
		Stats s= u.getStats();
		AttackSpeed = s.getSPD() - (arma.getPeso()-s.getCON());
		HitRate = arma.getGolpe()+s.getSKILL()*2+s.getLUCK()/2;
		Avoid = AttackSpeed*2+s.getLUCK();
		CriticalRate = arma.getCritico()+s.getSKILL()/2;
		CriticalEvade = s.getLUCK();
	}
	public void calcularStatsUnidad(Unidad atacante,Unidad defensor,BattleCalculator def)
	{
		this.Accuracy = this.getHitRate()-def.getAvoid();
		this.Accuracy = Math.max(Accuracy, 0);
		this.Accuracy = Math.min(Accuracy, 100);
		this.CriticalChance = this.getCriticalRate() - def.getCriticalEvade();
		this.CriticalChance = Math.max(CriticalChance, 0);
		this.CriticalChance = Math.min(CriticalChance, 100);
		int DefensePower=0;
		if(atacante.getArmaEquipada().getTipo()==TipoArma.Grimorio)
			DefensePower = defensor.getStats().getRES();
		DefensePower = defensor.getStats().getDEF();
		this.Damage = atacante.getStats().getSTR()+atacante.getArmaEquipada().getPoder()-DefensePower;
		this.Damage = Math.max(Damage, 1);
	}
	public int getAccuracy() {return this.Accuracy;}
	public int getCriticalChance() {return this.CriticalChance;}
	public int getDamage() {return this.Damage;}
	
	
	public int getHitRate() {return this.HitRate;}
	public int getAvoid() {return this.Avoid;}
	public int getCriticalRate() {return this.CriticalRate;}
	public int getCriticalEvade() {return this.CriticalEvade;}
}
