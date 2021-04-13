package businessLayer;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dataAccess.ProdusDAO;
import model.Clienti;
import model.Produs;
import validator.ValidatorProdus;

public class ProdusBLL {
	ValidatorProdus validator = new ValidatorProdus();
	private ProdusDAO produsDAO = new ProdusDAO();
	static int iProdus = 111;
	static int iPdfProdus = 1;
	
	public static int getiPdfProdus() {
		return iPdfProdus;
	}

	public static void setiPdfProdus(int iPdfProdus) {
		ProdusBLL.iPdfProdus = iPdfProdus;
	}

	public static int getiProdus() {
		return iProdus;
	}

	public static void setiProdus(int iProdus) {
		ProdusBLL.iProdus = iProdus;
	}

	public ProdusBLL() {
	}
	/**
	 * validate the product
	 * @param produs = the produs which will be validate
	 * @return false if the data is incorrect and else true
	 */
	public boolean testValidatorsProdus(Produs produs) throws Exception {
		if (validator.validatorProdusNume(produs) != true)
			return false;
		return true;
	}
	/**
	 * 
	 * the method finds a product in DB
	 * @param id = product id to found
	 * @return = the found product
	 */
	public Produs findById(int id) throws NoSuchElementException {
		Produs produs = produsDAO.findById(id);
		if (null == produs) {
			throw new NoSuchElementException("ERROR: Product " + id + "doesn't exists!");
		}

		return produs;
	}
	/**
	 * 
	 * @param field = field name in the table product from DB  
	 * @param stringNume = product name to be found
	 * @return = the found product
	 */
	public Produs findByName(String field, String stringNume) throws NoSuchElementException {
		Produs produs = produsDAO.findByName(field, stringNume);
		if (produs.getNumeProdus() == null)
			throw new NoSuchElementException("ERROR:Product " + stringNume + " doesn't exists!");
		return produs;
	}
	/**
	 * 
	 * @return ArrayList with all products which have founded in DB
	 */
	public ArrayList<Produs> findAll() throws NoSuchElementException {
		ArrayList<Produs> produs = produsDAO.findAll();
		if (null == produs)
			throw new NoSuchElementException("ERROR: product not existed!");
		return produs;
	}
	/**
	 *  insert a product into the DB
	 * @param iProdus = the product's id
	 * @param fld1 = the product's name
	 * @param fld2 = the product's quantity
	 * @param fld3 = the product's price
	 */
	public void insertProdus(int iProdus, String fld1, String fld2, String fld3) {
		Produs produs = new Produs();
		try {
			produs = produsDAO.findByName("numeProdus", fld1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (produs != null) {
			String update = "";
			update = update + (produs.getCantitate() + Integer.parseInt(fld2));
			try {
				updateProdus(update, fld1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				String s = iProdus + ", '" + fld1 + "', " + fld2 + ", " + fld3;
				produsDAO.insert(s);
				iProdus++;
			} catch (Exception e) {
				System.out.println("The element have been inserted!");
			}
		}
	}

	public void showReportPR(int iPdfP) throws Exception {
		String path = "C:\\Users\\Pc\\eclipse-workspace\\Project3TP\\report\\reportProduse\\reportProduse" + iPdfP;
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(path));
		doc.open();
		ArrayList<Produs> produs = findAll();
		for (Produs pr : produs) {
			PdfPTable t = new PdfPTable(4);
			String dataId = pr.getId() + "";
			Stream.of(dataId, pr.getNumeProdus(), pr.getCantitate() + "", pr.getPret() + "").forEach(title -> {
				PdfPCell row = new PdfPCell();
				row.setBorderWidth(3);
				row.setPhrase(new Phrase(title));
				row.setBackgroundColor(BaseColor.ORANGE);
				t.addCell(row);
			});
			doc.add(t);
		}
		doc.close();
	}
	/**
	 * delete a product from DB
	 * @param fld1 = the product's name
	 */
	public void deleteProdus(String fld1) {
		produsDAO.delete("numeProdus", fld1);
	}
	/**
	 * update a product in DB
	 * @param seted = the field name from DB table
	 * @param cond = the name of the product which will be updated
	 */
	public void updateProdus(String seted, String cond) {
		produsDAO.update(seted, cond);
	}

}
