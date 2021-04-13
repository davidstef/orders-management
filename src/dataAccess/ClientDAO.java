package dataAccess;
import java.util.ArrayList;

import model.Clienti;

public class ClientDAO extends CreateQueriesDAO<Clienti> {
	private ArrayList<Clienti> clienti = new ArrayList<Clienti>();

	public ArrayList<Clienti> getClienti() {
		return clienti;
	}

	public void setClienti(ArrayList<Clienti> clienti) {
		this.clienti = clienti;
	}
}
