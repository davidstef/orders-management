package model;

public class Comanda {
	private int idComanda;
	private int idC;
	
	public Comanda(int idComanda, int idC) {
		super();
		this.idComanda = idComanda;
		this.idC = idC;
	}

	public int getIdComanda() {
		return idComanda;
	}

	public void setIdComanda(int idComanda) {
		this.idComanda = idComanda;
	}
	
	public int getIdC() {
		return idC;
	}

	public void setIdC(int idC) {
		this.idC = idC;
	}

	public Comanda() {
	}
	
}
