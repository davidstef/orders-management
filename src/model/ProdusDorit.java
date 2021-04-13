package model;

public class ProdusDorit {
	
	private int idComanda;
	private int idProdus;
	private int cantitate;
	private double sumaTotala;
	
	public ProdusDorit(int idComanda, int idProdus, int cantitate, double sumaTotala) {
		super();
		this.idComanda = idComanda;
		this.idProdus = idProdus;
		this.cantitate = cantitate;
		this.sumaTotala = sumaTotala;
	}

	public ProdusDorit() {
		super();
	}

	public int getIdComanda() {
		return idComanda;
	}

	public void setIdComanda(int idComanda) {
		this.idComanda = idComanda;
	}

	public int getIdProdus() {
		return idProdus;
	}

	public void setIdProdus(int idProdus) {
		this.idProdus = idProdus;
	}

	public int getCantitate() {
		return cantitate;
	}

	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}

	public double getSumaTotala() {
		return sumaTotala;
	}

	public void setSumaTotala(double sumaTotala) {
		this.sumaTotala = sumaTotala;
	}
	
	
	
}