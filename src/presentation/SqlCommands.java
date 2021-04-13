package presentation;

import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.log.SysoLogger;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.xdevapi.Client;

import businessLayer.*;
import dataAccess.*;
import model.Clienti;
import model.Produs;
import model.ProdusDorit;

public class SqlCommands {
	private ArrayList<String> file = new ArrayList<String>();
	private ClientBLL clientBLL = new ClientBLL();
	private ProdusBLL produsBLL = new ProdusBLL();
	private ComandaBLL comandaBLL = new ComandaBLL();
	private ProdusDoritDAO pdDAO = new ProdusDoritDAO();
	static public ArrayList<ProdusDorit> listProducts = new ArrayList<ProdusDorit>();

	public SqlCommands() {
	}
	/**
	 * 
	 * @param f = the input string for processing
	 */
	public void parseInput(String f) {
		String[] fileRead = f.split("[:,]");
		int i = 0;
		while (fileRead != null && i < fileRead.length) {
			if (fileRead[i].charAt(0) == ' ') {
				fileRead[i] = fileRead[i].substring(1);
			}
			file.add(fileRead[i]);
			i++;
		}
	}

	/**
	 * Select which method to execute accordint to user input 
	 * @param
	 */
	public void command() throws Exception {
		if (file.get(0).contains("client")) {
			commandClient();
		} else if (file.get(0).contains("product") || file.get(0).contains("Product")) {
			commandProduct();
		} else if (file.get(0).contains("Order") || file.get(0).contains("order")) {
			commandOrder();
		}
	}
	/**
	 * Selects the command for clients to be processed according to the input data
	 */
	public void commandClient() {
		if (file.get(0).equals("Report client")) {
			try {
				clientBLL.showReportCL(clientBLL.getiPdfClienti());
			} catch (Exception e) {
				System.out.println("ERROR: Report client!");
			}
			clientBLL.setiPdfClienti(clientBLL.getiPdfClienti() + 1);
		} else {
			Clienti cl = new Clienti(file.get(1), file.get(2));
			try {
				if (clientBLL.testValidatorsClient(cl)) {
					if (file.get(0).equals("Insert client")) {
						clientBLL.insertClienti(clientBLL.getiClienti(), file.get(1), file.get(2));
						clientBLL.setiClienti(clientBLL.getiClienti() + 1);
					} else if (file.get(0).compareTo("Delete client") == 0) {
						clientBLL.deleteClienti(file.get(1));
						clientBLL.setiClienti(clientBLL.getiClienti() + 1);
					}
				}
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
		}
	}
	/**
	 * Selects the command for products to be processed according to the input data
	 */
	public void commandProduct() {
		if (file.get(0).compareTo("Report product") == 0) {
			try {
				produsBLL.showReportPR(produsBLL.getiPdfProdus());
			} catch (Exception e) {
				System.out.println("ERROR: Report product!");
			}
			produsBLL.setiPdfProdus(produsBLL.getiPdfProdus() + 1);
		} else {
			try {
				Produs p = new Produs(file.get(1), 2, 3);
				produsBLL.testValidatorsProdus(p);
				if (file.get(0).compareTo("Insert product") == 0) {
					Double d = Double.parseDouble(file.get(3));
					Integer i = Integer.parseInt(file.get(2));
					produsBLL.insertProdus(produsBLL.getiProdus(), file.get(1), file.get(2), file.get(3));
					produsBLL.setiProdus(produsBLL.getiProdus() + 1);
				} else if (file.get(0).equals("Delete Product") || file.get(0).equals("Delete product")) {
					produsBLL.deleteProdus(file.get(1));
					produsBLL.setiProdus(produsBLL.getiProdus() + 1);
				}
			} catch (Exception e) {
				System.out.println("Invalid parameters at definition of product!");
			}
		}
	}
	/**
	 * Selects the command for orders to be processed according to the input data
	 */
	public void commandOrder() {
		if (file.get(0).equals("Report order")) {
			try {
				comandaBLL.showReportCO(comandaBLL.getiPdfComanda(), listProducts);
			} catch (Exception e) {
				System.out.println("ERROR: Report order!");
			}
			comandaBLL.setiPdfComanda(comandaBLL.getiPdfComanda() + 1);
		} else {
			Clienti cl = new Clienti(file.get(1), "adress");
			try {
				Integer i = Integer.parseInt(file.get(3));
				Produs p = new Produs(file.get(2), i, 0.0);
				produsBLL.testValidatorsProdus(p);
				clientBLL.testValidatorsClient(cl);
				if (file.get(0).equals("Order")) {
					ProdusDorit pd = comandaBLL.selectOrder(comandaBLL.getiComanda(), file.get(1), file.get(2), file.get(3));
					if (pd != null) {	
						listProducts.add(pd);
						comandaBLL.setiComanda(comandaBLL.getiComanda() + 1);
					}
				}
			} catch (Exception e1) {
				System.out.println("Invalid parameters at definition of order!");
			}
		}
	}
}
