package businessLayer;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dataAccess.ClientDAO;
import model.Clienti;
import model.Comanda;
import model.ProdusDorit;
import validator.ValidatorClient;

public class ClientBLL {

	private ValidatorClient validator = new ValidatorClient();
	private ClientDAO clientDAO = new ClientDAO();
	static int iClienti = 111;
	static int iPdfClienti = 1;

	public static int getiClienti() {
		return iClienti;
	}

	public static void setiClienti(int iClienti) {
		ClientBLL.iClienti = iClienti;
	}

	public static int getiPdfClienti() {
		return iPdfClienti;
	}

	public static void setiPdfClienti(int iPdfClienti) {
		ClientBLL.iPdfClienti = iPdfClienti;
	}

	public ClientBLL() {
		validator = new ValidatorClient();
	}
	/**
	 * validates the client's dates
	 * @param client = the client
	 * @return false if the data is incorrect and else true
	 */
	public boolean testValidatorsClient(Clienti client) throws Exception {
		if (validator.validatorClientNume(client) != true)
			return false;
		if (validator.validatorClientAdresa(client) != true)
			return false;
		return true;
	}
	/**
	 * the method finds a client in DB
	 * @param id = client id to found
	 * @return = the found client
	 */
	public Clienti findById(int id) throws NoSuchElementException {
		Clienti client = clientDAO.findById(id);
		if (client.getId() == 0)
			throw new NoSuchElementException("ERROR:Client " + id + " doesn't exists!");
		return client;
	}
	/**
	 * 
	 * @param field = client field in DB with name
	 * @param stringNume = client name to be found
	 * @return = the found client
	 */
	public Clienti findByName(String field, String stringNume) throws NoSuchElementException {
		Clienti client = clientDAO.findByName(field, stringNume);
		if (client.getNume() == null)
			throw new NoSuchElementException("ERROR:The Client " + stringNume + " doesn't exists!");
		return client;
	}

	/**
	 * 
	 * @return ArrayList with all clients who have founded in DB 
	 */
	public ArrayList<Clienti> findAll() throws NoSuchElementException {
		ArrayList<Clienti> cl = clientDAO.findAll();
		if (null == cl)
			throw new NoSuchElementException("ERROR: No clients!");
		return cl;
	}
	/**
	 * insert a client into the DB
	 * @param iClient = the client's id
	 * @param fld1 = the client's name
	 * @param fld2 = the client's adress
	 */
	public void insertClienti(int iClient, String fld1, String fld2) {
		Clienti cl = null;
		try {
			cl = clientDAO.findByName("nume", fld1);
		} catch (Exception e) {
			System.out.println("The Client doesn't exists!");
		}
		if (cl != null) {
			try {
				clientDAO.updateClient(fld2, fld1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			try {
				String s = iClient + ", '" + fld1 + "', '" + fld2 + "'";
				clientDAO.insert(s);
				iClient++;
			} catch (Exception e) {
				System.out.println("The element have been inserted!");
			}
	}
	/**
	 * delete a client from the DB
	 * @param fld = the client's name
	 */
	public void deleteClienti(String fld) {
		clientDAO.delete("nume", fld);
	}
	/**
	 * generates the clients pdf report
	 * @param iPdfClienti = the index of the pdf report
	 */
	public void showReportCL(int iPdfClienti) throws Exception {
		String path = "C:\\Users\\Pc\\eclipse-workspace\\Project3TP\\report\\reportClienti\\reportClienti" + iPdfClienti;
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(path));
		doc.open();
		ArrayList<Clienti> clienti = findAll();
		for (Clienti cl : clienti) {
			PdfPTable t = new PdfPTable(3);
			String id = cl.getId() + "";
			Stream.of(id, cl.getNume(), cl.getAdresa()).forEach(title -> {
				PdfPCell row = new PdfPCell();
				row.setBorderWidth(3);
				row.setPhrase(new Phrase(title));
				row.setBackgroundColor(BaseColor.CYAN);
				t.addCell(row);
			});
			doc.add(t);
		}
		iPdfClienti++;
		doc.close();
	}

}
