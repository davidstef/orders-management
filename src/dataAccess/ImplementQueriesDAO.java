package dataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import connection.ConnectionFactory;

public class ImplementQueriesDAO<T> {

	protected static final Logger LOGGER = Logger.getLogger(CreateQueriesDAO.class.getName());
	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public ImplementQueriesDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected String createSelectQuery(String condition) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		//sql.append(" * ");
		//sql.append("FROM ");
		sql.append(type.getSimpleName());
		sql.append(" WHERE " + condition + " =?");
		System.out.println(sql.toString());
		return sql.toString();
	}

	protected String createSelectQuery() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		//sql.append(" * ");
		//sql.append(" FROM ");
		sql.append(type.getSimpleName());
		return sql.toString();
	}
	/**
	 * Creates a query for deleting from DB
	 * @param cond = the field name from DB table
	 * @param name = the name of the object to be deleted
	 * @return a string with the created query
	 */
	protected String createDeleteQuery(String cond, String name) {
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("DELETE FROM ");
			//sql.append("FROM ");
			sql.append(type.getSimpleName());
			sql.append(" WHERE " + cond + "='" + name + "';");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(sql.toString());
		return sql.toString();
	}
	/**
	 * Creates a query for updating into the DB
	 * @param seted = a string with the new values to be updated
	 * @param condition = a string with name of the object to be updated
	 * @return a string with the created query
	 */
	protected String createUpdateQuery(String seted, String condition) {
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("UPDATE ");
			sql.append(type.getSimpleName());
			sql.append(" SET cantitate=" + seted);
			sql.append(" WHERE numeProdus='" + condition + "';");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(sql.toString());
		return sql.toString();
	}
	/**
	 * Creates a query for updating the client into the DB
	 * @param seted = a string with the new values to be updated
	 * @param condition =  a string with name of the client to be updated
	 * @return a string with the created query
	 */
	protected String createUpdateQueryClient(String seted, String condition) {
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("UPDATE ");
			sql.append(type.getSimpleName());
			sql.append(" SET adresa='" + seted + "'");
			sql.append(" WHERE nume ='" + condition + "';");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(sql.toString());
		return sql.toString();
	}
	/**
	 * Creates a query for insertion into the DB
	 * @param condition = a string with the values to be inserted
	 * @return a string with the created query
	 */
	protected String createInsertQuery(String condition) {
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("INSERT INTO ");
			//sql.append(" INTO ");
			sql.append(type.getSimpleName());
			sql.append(" VALUE (" + condition + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

}
