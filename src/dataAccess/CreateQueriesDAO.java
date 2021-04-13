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

public class CreateQueriesDAO<T> extends ImplementQueriesDAO<T>{

	protected static final Logger LOGGER = Logger.getLogger(CreateQueriesDAO.class.getName());
	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public CreateQueriesDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	/**
	 * Creates a connection with the DB and executes the
	 * query and then clls a function to create the object.
	 * The object will be returned.
	 * @param id = the id to be found in DB
	 * @return instance of Class T
	 */
	public T findById(int id) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = createSelectQuery("id");
		try {
			conn = ConnectionFactory.getConnection();
			statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeQuery();
			return createObjects(result).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error: the element can't be find!");
		}
		ConnectionFactory.close(result);
		ConnectionFactory.close(statement);
		ConnectionFactory.close(conn);
		return null;
	}
	/**
	 * Creates a connection with the DB and executes the
	 * query and then clls a function to create the object.
	 * The object will be returned.
	 * @return the ArrayList of Class T
	 */
	public ArrayList<T> findAll() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		String q = createSelectQuery();
		try {
			conn = ConnectionFactory.getConnection();
			statement = conn.prepareStatement(q);
			result = statement.executeQuery();
			return createObjects(result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: the element can't be find!");
		}
		ConnectionFactory.close(result);
		ConnectionFactory.close(statement);
		ConnectionFactory.close(conn);
		return null;
	}
	/**
	 * Creates a connection with the DB and executes the
	 * query and then clls a function to create the object.
	 * The object will be returned.
	 * @param field = the field name from DB table
	 * @param name = the name of the object to be found in DB
	 * @return instance of Class T
	 */
	public T findByName(String field, String name) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = createSelectQuery(field);
		try {
			conn = ConnectionFactory.getConnection();
			statement = conn.prepareStatement(query);
			statement.setString(1, name);
			result = statement.executeQuery();
			return createObjects(result).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error: the element can't be found!");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: the element can't be found!");
		} catch (Exception e) {
			System.out.println("Error: the element can't be found!");
		}
		ConnectionFactory.close(result);
		ConnectionFactory.close(statement);
		ConnectionFactory.close(conn);
		return null;
	}
	/**
	 * delete an T object from DB
	 * @param condition = condition for delete
	 * @param name = the name of object which will be deleted
	 */
	public void delete(String condition, String name) {
		Connection conn = null;
		String q = createDeleteQuery(condition, name);
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement statement = conn.prepareStatement(q);
			//ResultSet result = statement.executeQuery();
			statement.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Error: The element haven't been deleted!");
		}
		ConnectionFactory.close(conn);
	}
	/**
	 *  inserts an T object into the DB
	 * @param cond = a string which contains the values which will be inserted
	 */
	public void insert(String cond) {
		Connection conn = null;
		String q = createInsertQuery(cond);
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement statement = conn.prepareStatement(q);
			//ResultSet result = statement.executeQuery();
			statement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: The element haven't been inserted");
		}
		ConnectionFactory.close(conn);
	}
	/**
	 * updates an T Object of the DB
	 * @param seted = a string which contains the new value to insert
	 * @param cond = a string which contains name object to be updated
	 */
	public void update(String seted, String cond) {
		Connection conn = null;
		String q = createUpdateQuery(seted, cond);
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement statement = conn.prepareStatement(q);
			//ResultSet result = statement.executeQuery();
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: The element haven't been updated");
		}
		ConnectionFactory.close(conn);
	}
	/**
	 * updates a Client object in the DB
	 * @param seted = a string which contains the new value to insert
	 * @param cond = a string which contains name object to be updated
	 */
	public void updateClient(String seted, String cond) {
		Connection conn = null;
		String q = createUpdateQueryClient(seted, cond);
		try {
			conn = ConnectionFactory.getConnection();
			PreparedStatement statement = conn.prepareStatement(q);
			//ResultSet result = statement.executeQuery();
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: The element haven't been updated");
		}
		ConnectionFactory.close(conn);
	}
	/**
	 *  Creates objects using the reflecion techniques
	 * @param result = the result obtained by executing a querry
	 * @return ArrayList of objects of type class of the instance which calls this function
	 */
	private ArrayList<T> createObjects(ResultSet result) {
		ArrayList<T> lista = new ArrayList<T>();
		try {
			while (result.next()) {
				T inst = type.newInstance();
				for (Field f : type.getDeclaredFields()) {
					Object obj = result.getObject(f.getName());
					PropertyDescriptor descriptor = new PropertyDescriptor(f.getName(), type);
					Method m = descriptor.getWriteMethod();
					m.invoke(inst, obj);
				}
				lista.add(inst);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			System.out.println("ERROR: Create Objects!");
		} catch (SecurityException | SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR: Create Objects!");
		} catch (IntrospectionException | InstantiationException e) {
			e.printStackTrace();
			System.out.println("ERROR: Create Objects!");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.out.println("ERROR: Create Objects!");
		}

		return lista;
	}
}
