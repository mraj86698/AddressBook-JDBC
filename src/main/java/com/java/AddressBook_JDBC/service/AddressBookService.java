package com.java.AddressBook_JDBC.service;
import java.util.List;

import com.java.AddressBook_JDBC.Contact;

public class AddressBookService {
	AddressBookDBService addressBookDBService = AddressBookDBService.getInstance();

	public List<Contact> readData() {
		return addressBookDBService.readData();
	}

}
