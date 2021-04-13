package validator;
import model.Produs;

public class ValidatorProdus {
	/**
	 * 
	 * @param produs = the product object
	 * @return false if the name of product is incorrect and else true
	 */
	public boolean validatorProdusNume(Produs produs) throws Exception {
		if(produs.getNumeProdus().isEmpty() == true || !produs.getNumeProdus().matches("^[A-Za-z\\s]+")) {
			throw new Exception("Invalid product name!");
		}
	return true;	
	}	
	
}
