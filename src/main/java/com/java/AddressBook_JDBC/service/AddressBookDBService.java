package com.java.AddressBook_JDBC.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.java.AddressBook_JDBC.Contact;


public class AddressBookDBService {

	private static AddressBookDBService addressBookDBService;

	public AddressBookDBService() {
	}

	public static AddressBookDBService getInstance() {
		if (addressBookDBService == null)
			addressBookDBService = new AddressBookDBService();
		return addressBookDBService;
	}
	public List<Contact> readData() {
		List<Contact> contactList = new ArrayList<Contact>();
		String sql = "SELECT * FROM addressbook;";
		try(Connection connection = this.getConnection()){
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			contactList = this.getDataUsingResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	public List<Contact> getDataUsingResultSet(ResultSet resultSet) {
		List<Contact> contactList = new ArrayList<Contact>();
		String sql = "SELECT * FROM addressbook;";
		try {
			//ResultSet resultSet = statement.executeQuery(sql);
			System.out.println("Id" + "\t" + "FirstName"+"\t" +"LastName"+"\t"+"Address"+ "\t\t" + "city" + "\t\t" +"state"+"\t\t"+"zip"+"\t"+"phone_number"+"\t"+"email");
			while(resultSet.next()) {
				int id=resultSet.getInt("Id");
				String firstName = resultSet.getString("firstname");
				String lastName = resultSet.getString("lastname");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				String zip = resultSet.getString("zip");
				String phoneNo = resultSet.getString("phone_number");
				String email = resultSet.getString("email");
				System.out.println(id+"\t"+firstName+"\t\t"+lastName+"\t\t"+address+"\t"+city+"\t"+state+"\t"+zip+"\t"+phoneNo+"\t"+email);
				Contact contact = new Contact(firstName, lastName, address, city, state, zip, phoneNo, email);
				contactList.add(contact);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressBook?useSSL=false";
		String userName = "root";
		String password = "mysql";
		Connection connection;
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection established: "+connection);
		return connection;
	}
}