package dataAccess;
import java.util.ArrayList;

import connection.ConnectionFactory;
import model.ProdusDorit;
public class ProdusDoritDAO extends CreateQueriesDAO<ProdusDorit> {
	private ArrayList<ProdusDorit> produsDorit = new ArrayList<ProdusDorit>();

	public ArrayList<ProdusDorit> getProdusDorit() {
		return produsDorit;
	}

	public void setProdusDorit(ArrayList<ProdusDorit> produsDorit) {
		this.produsDorit = produsDorit;
	}
	
}
