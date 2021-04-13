package validator;

import model.Clienti;

public class ValidatorClient {
	/**
	 * 
	 * @param client = the client object
	 * @return false if the name is incorrect and else true
	 */
	public boolean validatorClientNume(Clienti client) throws Exception {
		if (client.getNume().isEmpty() == true || !client.getNume().matches("^[a-zA-Z\\s]+")) {
			throw new Exception("Invalid client name!");
		}
		return true;
	}
	/**
	 * 
	 * @param client = the client object
	 * @return false if the adreess is incorrect and else true
	 */
	public boolean validatorClientAdresa(Clienti client) throws Exception {
		if (client.getAdresa().isEmpty() == true || !client.getAdresa().matches("^[A-Za-z0-9-\\s]+")) {
				throw new Exception("Invalid client adress!");
			}
		return true;
	}

}
