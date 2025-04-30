package efectos;

import unidades.Clases;

public final class ClassEffective implements Effect
{
	private final Clases targetClass;
	public ClassEffective(Clases tc) {this.targetClass=tc;}
	
	public Clases getTargetClass() {return this.targetClass;}
}
