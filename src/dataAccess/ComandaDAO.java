package dataAccess;
import java.util.ArrayList;

import model.Comanda;
public class ComandaDAO extends CreateQueriesDAO<Comanda> {
	ArrayList<Comanda> comenzi = new ArrayList<Comanda>();

	public ArrayList<Comanda> getComenzi() {
		return comenzi;
	}

	public void setComenzi(ArrayList<Comanda> comenzi) {
		this.comenzi = comenzi;
	}
	
}
