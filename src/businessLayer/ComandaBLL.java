package businessLayer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dataAccess.*;
import model.Clienti;
import model.Comanda;
import model.Produs;
import model.ProdusDorit;

public class ComandaBLL {
	private ComandaDAO comandaDAO = new ComandaDAO();
	private ProdusDoritDAO pdDAO = new ProdusDoritDAO();
	private ProdusBLL produsBLL = new ProdusBLL();
	private ClientBLL clientBLL = new ClientBLL();
	private ArrayList<ProdusDorit> listProducts = new ArrayList<ProdusDorit>();
	static int iComanda = 1;
	static int iPdfComanda = 1;
	static int iPdfBon = 1;

	public ArrayList<ProdusDorit> getListProducts() {
		return listProducts;
	}

	public static int getiPdfComanda() {
		return iPdfComanda;
	}

	public static void setiPdfComanda(int iPdfComanda) {
		ComandaBLL.iPdfComanda = iPdfComanda;
	}

	public static int getiComanda() {
		return iComanda;
	}

	public static void setiComanda(int iComanda) {
		ComandaBLL.iComanda = iComanda;
	}

	/**
	 * finds the Order by id in the DB
	 * 
	 * @param id = order id
	 * @return the found order
	 */
	public Comanda findById(int id) throws NoSuchElementException {
		Comanda comanda = comandaDAO.findById(id);
		if (null == comanda) {
			throw new NoSuchElementException("ERROR: Order " + id + "not exists");
		}
		return comanda;
	}

	/**
	 * finds all the orders into the DB
	 * 
	 * @return all the found orders into the ArrayList
	 */
	public ArrayList<Comanda> findAll() throws NoSuchElementException {
		ArrayList<Comanda> comenzi = comandaDAO.findAll();
		if (null == comenzi)
			throw new NoSuchElementException("ERROR: orders not exists!");
		return comenzi;
	}

	/**
	 * creates a order
	 * 
	 * @param iComanda = the order id
	 * @param fld1     = the client name
	 * @param fld2     = the product name
	 * @param fld3     = the product quantity
	 * @return the product
	 */
	public ProdusDorit selectOrder(int iComanda, String fld1, String fld2, String fld3) {
		Clienti client = clientBLL.findByName("nume", fld1);
		Produs produs = produsBLL.findByName("numeProdus", fld2);
		String c = iComanda + ", " + client.getId();
		String update = "";
		ProdusDorit p = null;
		update = update + (produs.getCantitate() - Integer.parseInt(fld3));
		if (produs.getCantitate() - Integer.parseInt(fld3) >= 0) {
			try {
				comandaDAO.insert(c);
				double suma = Integer.parseInt(fld3) * produs.getPret();
				pdDAO.insert(iComanda + ", " + produs.getId() + ", " + fld3 + "," + suma);
				p = new ProdusDorit(iComanda, produs.getId(), Integer.parseInt(fld3), suma);
				releaseReceipt(produs.getNumeProdus(), fld1, Integer.parseInt(fld3),
						produs.getPret() * Integer.parseInt(fld3));
			} catch (NumberFormatException | DocumentException | FileNotFoundException e) {
				System.out.println("ERROR: Receipt haven't been released!");
			}
			produsBLL.updateProdus(update, produs.getNumeProdus());
		} else {
			try {
				releaseEmptyReceipt();
			} catch (DocumentException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	/**
	 * generates the report for orders
	 * 
	 * @param iPdfC        = index of the pdf report
	 * @param listProducts = the list of orders
	 * @throws FileNotFoundException
	 */
	public void showReportCO(int iPdfC, ArrayList<ProdusDorit> listProducts)
			throws FileNotFoundException, DocumentException {
		String path = "C:\\Users\\Pc\\eclipse-workspace\\Project3TP\\report\\reportComenzi\\reportComenzi" + iPdfC;
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(path));
		doc.open();
		for (ProdusDorit pr : listProducts) {
			PdfPTable t = new PdfPTable(4);
			String dataId = pr.getIdComanda() + "";
			Stream.of(dataId, pr.getIdProdus() + "", "" + pr.getCantitate(), "" + pr.getSumaTotala()).forEach(title -> {
				PdfPCell row = new PdfPCell();
				row.setBorderWidth(3);
				row.setPhrase(new Phrase(title));
				row.setBackgroundColor(BaseColor.LIGHT_GRAY);
				t.addCell(row);
			});
			doc.add(t);
		}
		doc.close();
	}

	/**
	 * generates the bill with orders
	 * 
	 * @param name        = the name of product
	 * @param quantity    = the quantity of product
	 * @param price       = the price of product
	 * @param isAvailable = check if the order exists
	 */
	public void releaseReceipt(String name, String cl, int quantity, double price)
			throws DocumentException, FileNotFoundException {
		String path = "C:\\Users\\Pc\\eclipse-workspace\\Project3TP\\report\\bon\\bon" + iPdfBon;
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(path));
		doc.open();
		Paragraph p = new Paragraph("Comanda Client: " + cl);
		Paragraph p1 = new Paragraph("Produs: " + name);
		Paragraph p2 = new Paragraph("Cantitate: " + quantity);
		Paragraph p3 = new Paragraph("Pret: " + price + " RON");
		doc.add(p);
		doc.add(p1);
		doc.add(p2);
		doc.add(p3);
		iPdfBon++;
		doc.close();
	}

	public void releaseEmptyReceipt() throws DocumentException, FileNotFoundException {
		String path = "C:\\Users\\Pc\\eclipse-workspace\\Project3TP\\report\\bon\\bon" + iPdfBon;
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(path));
		doc.open();
		Paragraph paragraph = new Paragraph("There's not enough produce!");
		doc.add(paragraph);
		iPdfBon++;
		doc.close();
	}

	public void delete(String cond, String id) {
		comandaDAO.delete(cond, id);
	}

}
