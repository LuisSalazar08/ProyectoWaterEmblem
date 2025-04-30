package armas;

import unidades.Clases;
import efectos.*;

public enum Armas 
{
	//Espadas
	IronSword(TipoArma.Espada,1,5,5,90,0,460,NoEffect.INSTANCE),
	SteelSword(TipoArma.Espada,1,10,8,75,0,600,NoEffect.INSTANCE),
	SilverSword(TipoArma.Espada,1,8,13,80,0,1500,NoEffect.INSTANCE),
	ArmorSlayer(TipoArma.Espada,1,11,8,80,0,1260,new ClassEffective(Clases.Acorazado)),
	Zanbato(TipoArma.Espada,1,11,6,85,0,1260,new ClassEffective(Clases.Jinete)),
	LightBrand(TipoArma.Espada,2,9,9,70,0,1250,MagicDamageEffect.INSTANCE),
	KillingEdge(TipoArma.Espada,1,7,9,75,30,1300,NoEffect.INSTANCE),
	//Lanzas
	IronLance(TipoArma.Lanza,1,8,7,80,0,360,NoEffect.INSTANCE),
	SteelLance(TipoArma.Lanza,1,13,10,70,0,480,NoEffect.INSTANCE),
	SilverLance(TipoArma.Lanza,1,10,14,75,0,1200,NoEffect.INSTANCE),
	HorseSlayer(TipoArma.Lanza,1,13,7,70,0,1040,new ClassEffective(Clases.Jinete)),
	Javelin(TipoArma.Lanza,2,11,6,65,0,400,NoEffect.INSTANCE),
	Spear(TipoArma.Lanza,2,10,12,70,5,9000,NoEffect.INSTANCE),
	KillerLance(TipoArma.Lanza,1,9,10,70,30,1200,NoEffect.INSTANCE),
	//Hachas
	IronAxe(TipoArma.Hacha,1,10,8,75,0,270,NoEffect.INSTANCE),
	SteelAxe(TipoArma.Hacha,1,15,11,65,0,360,NoEffect.INSTANCE),
	SilverAxe(TipoArma.Hacha,1,12,15,70,0,1000,NoEffect.INSTANCE),
	HandAxe(TipoArma.Hacha,2,12,7,60,0,300,NoEffect.INSTANCE),
	Tomahawk(TipoArma.Hacha,2,14,13,65,0,3000,NoEffect.INSTANCE),
	Hammer(TipoArma.Hacha,1,15,10,55,0,800,new ClassEffective(Clases.Acorazado)),
	Halberd(TipoArma.Hacha,1,15,10,60,0,810,new ClassEffective(Clases.Jinete)),
	DevilAxe(TipoArma.Hacha,1,18,18,55,0,880,SelfDamageEffect.INSTANCE),
	KillerAxe(TipoArma.Hacha,1,11,11,65,30,1000,NoEffect.INSTANCE),
	//Arcos
	IronBow(TipoArma.Arco,2,5,6,85,0,540,NoEffect.INSTANCE),
	SteelBow(TipoArma.Arco,2,9,9,70,0,720,NoEffect.INSTANCE),
	SilverBow(TipoArma.Arco,2,6,13,75,0,1600,NoEffect.INSTANCE),
	LongBow(TipoArma.Arco,3,10,5,65,0,2000,NoEffect.INSTANCE),
	KillerBow(TipoArma.Arco,2,7,9,75,30,1400,NoEffect.INSTANCE),
	//Grimorios
	Fire(TipoArma.Grimorio,2,4,5,90,0,560,NoEffect.INSTANCE),
	Thunder(TipoArma.Grimorio,2,6,8,80,5,700,NoEffect.INSTANCE),
	Elfire(TipoArma.Grimorio,2,10,10,85,0,1200,NoEffect.INSTANCE),
	Fimbulvetr(TipoArma.Grimorio,2,12,13,80,0,6000,NoEffect.INSTANCE),
	Bolting(TipoArma.Grimorio,10,20,12,60,0,2500,NoEffect.INSTANCE),
	Excalibur(TipoArma.Grimorio,2,13,18,90,10,0,NonSellableEffect.INSTANCE);
	
	private final TipoArma tipo;
	private final int Alcance,Peso,Poder,Golpe,Critico,Costo;
	private final Effect efecto;
	private Armas(TipoArma tipo,int Alc,int Pe,int Po,
			int Gp,int Crit,int Cost,Effect eff)
	{
		this.tipo=tipo;
		this.Alcance=Alc;
		this.Peso=Pe;
		this.Poder=Po;
		this.Golpe=Gp;
		this.Critico=Crit;
		this.Costo=Cost;
		this.efecto=eff;
	}
	public TipoArma getTipo()   { return tipo; }
    public int getAlcance()     { return Alcance; }
    public int getPeso()        { return Peso; }
    public int getPoder()       { return Poder; }
    public int getGolpe()       { return Golpe; }
    public int getCritico()     { return Critico; }
    public int getCosto()       { return Costo; }
    public Effect getEfecto()   { return efecto; }
}
