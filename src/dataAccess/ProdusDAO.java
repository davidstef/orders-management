package dataAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connection.ConnectionFactory;
import model.Produs;

public class ProdusDAO extends CreateQueriesDAO<Produs> {
	private ArrayList<Produs> produse = new ArrayList<Produs>();
	
	
	public ArrayList<Produs> getProduse() {
		return produse;
	}

	public void setProduse(ArrayList<Produs> produse) {
		this.produse = produse;
	}

	private String createSelectPrice(String stringNume) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append("id FROM Produs");
		sql.append(" WHERE ");
		sql.append("numeProdus = " + stringNume);
		return sql.toString();
	}
	
	public double findByPrice(String stringNume) {
		Connection conn = null;
		String q = createSelectPrice(stringNume);
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement state = conn.prepareStatement(q);
			state.setString(2, stringNume);
			ResultSet result = state.executeQuery();
			if(result.next())
				return result.getDouble(4);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: the element with price can't be finded!");
		}
		ConnectionFactory.close(conn);
		return (Double) null;
	}
	
}
