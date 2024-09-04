package singleton2;

public class CalcImposto {

	private float aliquota;
	
	public float getAliquota() {
		return aliquota;
	}
	
	public void setAliquota(float aliquota) {
		this.aliquota = aliquota;
	}
	
	public float calcImposto(float valor) {
		return valor * aliquota / 100;
	}
	
}
