package observer0;

public class Incrementar {

	private Incrementar() {
	}

	private static Incrementar instancia;
	
	public static Incrementar getInstancia() {
		if (instancia == null)
			instancia = new Incrementar();
		
		return instancia;
	}
	
	private int conta;
	
	public int getConta() {
		return conta;
	}

	public void inc() {
		conta++;
	}

}
