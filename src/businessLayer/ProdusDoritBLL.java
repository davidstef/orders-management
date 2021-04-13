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

import dataAccess.ProdusDoritDAO;
import model.Produs;
import model.ProdusDorit;

public class ProdusDoritBLL {
	
	private ProdusDoritDAO pdDAO = new ProdusDoritDAO();
	/**
	 *  the method finds an ordered item
	 * @param id = the ordered item's id
	 * @return = the found ordered item
	 * @throws NoSuchElementException
	 */
	public ProdusDorit findById(int id) throws NoSuchElementException {
		ProdusDorit pd = pdDAO.findById(id);
		if(null == pd)
			throw new NoSuchElementException("ERROR: ProdusDorit " + id + " doesn't exists!");
		return pd;
	}
	/**
	 * 
	 * @return ArrayList with all orderet items which have founded in DB
	 * @throws NoSuchElementException
	 */
	public ArrayList<ProdusDorit> findAll() throws NoSuchElementException {
		ArrayList<ProdusDorit> produse = pdDAO.findAll();
		if(null == produse)
			throw new NoSuchElementException("ERROR: ProdusDorit doesn't exists!");
		return produse;
	}
	/**
	 *  update the data of ordered item
	 * @param produs = the name of the field in DB table
	 * @param id = the it of the ordered item 
	 */
	public void update(String produs, String id) {
		pdDAO.update(produs, id);
	}
	/**
	 * 	delete an ordered item from DB
	 * @param cond = the field of item from DB table
	 * @param id = the id of the ordered item
	 */
	public void delete(String cond, String id) {
		pdDAO.delete(cond, id);
	}
	/**
	 *  insert an new ordered item into the DB
	 * @param produs = the name of the ordered item
	 */
	public void insert(String produs) {
		pdDAO.insert(produs);
	}
	
}
