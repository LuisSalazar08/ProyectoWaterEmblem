package unidades;

import armas.TipoArma;

public class Stats 
{
	private Clases UnitType;
	private TipoArma arma;
	private int HPMAX,HP,STR,MAG,SKILL,SPD,LUCK,DEF,RES,CON;
	private int MOV;
	public Stats(int hpmax, int hp,int st,int mg,int sk,int sp,int lc,
			int df,int rs,int cn,int mv,Clases ty,TipoArma wp)
	{
		this.HPMAX = hpmax;
		this.HP=hp;
		this.STR=st;
		this.MAG=mg;
		this.SKILL=sk;
		this.SPD=sp;
		this.LUCK=lc;
		this.DEF=df;
		this.RES=rs;
		this.CON=cn;
		this.MOV=mv;
		this.UnitType=ty;
		this.arma=wp;
	}
	public void setHP(int Damage)
	{
		if(Damage>this.HP)
			this.HP=0;
		this.HP-=Damage;
	}
	public int getHP() {return this.HP;}
	public int getMAXHP() {return this.HPMAX;}
	public int getSTR() {return this.STR;}
	public int getMAG() {return this.MAG;}
	public int getSKILL() {return this.SKILL;}
	public int getSPD() {return this.SPD;}
	public int getLUCK() {return this.LUCK;}
	public int getDEF() {return this.DEF;}
	public int getRES() {return this.RES;}
	public int getCON() {return this.CON;}
	public int getMOV() {return this.MOV;}
	public TipoArma getArma() {return this.arma;}
}
