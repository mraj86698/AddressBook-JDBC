package com.java.AddressBook_JDBC;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.java.AddressBook_JDBC.service.AddressBookCSVService;
import com.java.AddressBook_JDBC.service.AddressBookJSONService;

public class AddressBook {

	Scanner sc = new Scanner(System.in);

	AddressBookJSONService addressBookJsonService;

	List<Contact> addressList = new LinkedList<Contact>();
	/**
	 * Map to store multiple address books
	 */
	HashMap<String, List<Contact>> addressBookMap = new HashMap<String, List<Contact>>();
	/**
	 * Dictionary of city and state
	 */
	HashMap<Contact, String> personCityMap = new HashMap<Contact, String>();
	HashMap<Contact, String> personStateMap = new HashMap<Contact, String>();

	private String addressListName;

	void init() {
		addressBookJsonService = new AddressBookJSONService();
		addressBookMap = addressBookJsonService.getAddressBookMap();
	}

	void setAddressListName(String listName) {
		addressListName = listName;

	}

	/**
	 * @param contactIsAdded
	 * @param contactObj
	 */
	void addToDictionary(boolean contactIsAdded, Contact contactObj) {
		if (contactIsAdded == true) {
			personCityMap.put(contactObj, contactObj.getCity());
			personStateMap.put(contactObj, contactObj.getState());
		}
	}

	/**
	 * Map to store multiple address books to satisfy condition of unique name
	 *
	 * @param contactObj
	 * @return
	 */
	public boolean addContact(Contact contactObj) {
		Contact contact;
		boolean isPresent = addressList.stream().anyMatch(obj -> obj.equals(contactObj));
		if (isPresent == false) {
			addressList.add(contactObj);
			new AddressBookJSONService().writeContactToAddressBook(contactObj, addressListName);
			System.out.println("Contact added");
			return true;
		} else {
			System.out.println("Contact already present. Duplication not allowed");
			return false;
		}
	}

	/**
	 * Edit Contact Details Enter First Name and LastName of person to Edit Details
	 *
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public boolean editDetails(String firstName, String lastName) {
		Contact editObj;
		boolean contactFound = false;
		for (int i = 0; i < addressList.size(); i++) {
			editObj = addressList.get(i);
			if ((editObj.getFirstName().equals(firstName)) && (editObj.getLastName().equals(lastName))) {
				System.out.println("Enter new Address:");
				editObj.setAddress(sc.nextLine());
				System.out.println("Enter new City");
				editObj.setCity(sc.nextLine());
				System.out.println("Enter new State");
				editObj.setState(sc.nextLine());
				System.out.println("Enter new Zip");
				editObj.setZip(sc.nextLine());
				System.out.println("Enter new Phone no");
				editObj.setPhoneNo(sc.nextLine());
				System.out.println("Enter new Email");
				editObj.setEmail(sc.nextLine());
				contactFound = true;
				break;
			}
		}
		return contactFound;
	}

	/**
	 * Remove Contact from given Address Book Enter FirstName and LastName of person
	 * to delete data
	 *
	 * @param firstName
	 * @param lastName
	 * @return
	 */

	public boolean removeDetails(String firstName, String lastName) {
		Contact removeObj;
		boolean contactFound = false;
		for (int i = 0; i < addressList.size(); i++) {
			removeObj = addressList.get(i);
			if ((removeObj.getFirstName().equals(firstName)) && (removeObj.getLastName().equals(lastName))) {
				addressList.remove(i);
				contactFound = true;
				break;
			}
		}
		return contactFound;
	}

	/**
	 * Add an AddressBook to map
	 *
	 * @param listName
	 */
	public void addAddressList(String listName) {
		List<Contact> newAddressList = new LinkedList<Contact>();
		addressBookMap.put(listName, newAddressList);
		boolean isAddressBookAdded = new AddressBookCSVService().addAddressBook(listName);
		if (isAddressBookAdded)
			System.out.println("Address book added");
		else
			System.out.println("Address book not added. Might already be present");
		addressListName = listName;
	}

	/**
	 * Search person in a city or state across multiple address book
	 *
	 * @param searchPerson
	 * @param searchChoice
	 * @param cityOrState
	 */

	void searchPersonAcrossCityState(String searchPerson, int searchChoice, String cityOrState) {
		for (Map.Entry<String, List<Contact>> entry : addressBookMap.entrySet()) {
			List<Contact> list = entry.getValue();
			if (searchChoice == 1)
				list.stream().filter(
						obj -> ((obj.getCity().equals(cityOrState)) && (obj.getFirstName().equals(searchPerson))))
						.forEach(System.out::println);
			else if (searchChoice == 2)
				list.stream().filter(
						obj -> ((obj.getState().equals(cityOrState)) && (obj.getFirstName().equals(searchPerson))))
						.forEach(System.out::println);
		}
	}

	/**
	 * Ability to View Person by City or State
	 *
	 * @param cityOrState
	 * @param searchChoice
	 */

	void viewPersonsByCityState(String cityOrState, int searchChoice) {
		for (Map.Entry<String, List<Contact>> entry : addressBookMap.entrySet()) {
			List<Contact> list = entry.getValue();
			if (searchChoice == 1)
				list.stream().filter(obj -> obj.getCity().equals(cityOrState)).forEach(System.out::println);
			else if (searchChoice == 2)
				list.stream().filter(obj -> obj.getState().equals(cityOrState)).forEach(System.out::println);
		}
	}

	/**
	 * Ability to get number of contact persons (count by City or State)
	 *
	 * @param cityOrState
	 * @param searchChoice
	 * @return
	 */

	long getCountByCityState(String cityOrState, int searchChoice) {
		long count = 0;
		for (Map.Entry<String, List<Contact>> entry : addressBookMap.entrySet()) {
			List<Contact> list = entry.getValue();
			if (searchChoice == 1)
				count += list.stream().filter(obj -> obj.getCity().equals(cityOrState)).count();
			else if (searchChoice == 2)
				count += list.stream().filter(obj -> obj.getState().equals(cityOrState)).count();
		}
		return count;
	}

	/**
	 * To Sort AddressBook Details by name Use Collection Library for Sorting
	 *
	 * @param sortList
	 * @return
	 */
	List<Contact> sortAddressBookByName(List<Contact> sortList) {
		FlexibleSort flexibleSort = new FlexibleSort(FlexibleSort.Order.NAME);
		Collections.sort(sortList, flexibleSort);
		return sortList;
	}

	/**
	 * Ability to sort the entries in the address book by City,State, or Zip
	 *
	 * @param sortChoice
	 * @param sortList
	 * @return Sorted Address Book List By Choice (UC12)
	 */
	List<Contact> sortAddressBookByChoice(int sortChoice, List<Contact> sortList) {
		FlexibleSort flexibleSort = null;
		switch (sortChoice) {
		case 1:
			flexibleSort = new FlexibleSort(FlexibleSort.Order.CITY);
			break;
		case 2:
			flexibleSort = new FlexibleSort(FlexibleSort.Order.STATE);
			break;
		case 3:
			flexibleSort = new FlexibleSort(FlexibleSort.Order.ZIP);
			break;
		default:
			System.out.println("Invalid Choice");
		}
		Collections.sort(sortList, flexibleSort);
		return sortList;
	}
	public void show(){
		System.out.println(addressList.size());
		if (addressList.size() == 0) {
			System.out.println("No Record Found !!!!!!");
		} else {
			for (int i = 0; i < addressList.size(); i++) {
				Contact contact = addressList.get(i);
				System.out.println(addressList.get(i));
				System.out.println("");
			}
		}
	}

}
