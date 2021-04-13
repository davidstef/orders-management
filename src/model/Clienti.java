package model;

public class Clienti {

	private String nume;
	private int id;		 // id-ul cumparatorului
	private String adresa;
	
	public Clienti(int id, String nume, String adresa) {
		super();
		this.nume = nume;
		this.id = id;
		this.adresa = adresa;
	}
	public Clienti(String nume, String adresa) {
		super();
		this.nume = nume;
		this.adresa = adresa;
	}
	
	public Clienti() {
		//
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}



	
	
	
	
	
}
