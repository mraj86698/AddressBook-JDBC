package com.java.AddressBook_JDBC;
import java.util.List;
import java.util.Scanner;

import com.java.AddressBook_JDBC.service.AddressBookService;

/**
 * Hello world!
 *
 */
public class AddressBookMain
{
    public static void main( String[] args )
    {
        System.out.println( "Welcome to the Address Book Using JDBC!" );
        Scanner sc = new Scanner(System.in);

		AddressBook addressObj = new AddressBook();
		AddressBookService service=new AddressBookService();
		int choice = 0;
		/**
		 * If no address book is present, it asks to add at least one address book
		 * Then Enter the Name of Address Book to add
		 */
		System.out.println("Welcome to address book program");
		addressObj.init();
		while (choice != 12) {


			System.out.println("Enter a choice: \n 1)Add a new AddressBook\n 2)Add a New Contact \n 3)Edit a contact \n 4)Delete Contact \n 5)View current Address Book Contacts"+ " \n 6)Search person in a city or state across the multiple Address Books \n 7)View persons by city or state \n "+ "8)Get count of contact persons by city or state \n 9)Sort entries by name in current address book\n 10)Sort entries in current address book by city, state or zip \n 11)View all contacts from all address books \n 12)Exit");
			choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1: {

					System.out.println("Please add an address book :");
					System.out.println("Enter the name of address book to add:");
					String listName = sc.nextLine();
					addressObj.addAddressList(listName);
				break;
			}
			case 2: {

				System.out.println("Enter the name of the address book you want to access");
				String listName = sc.nextLine();
				if (addressObj.addressBookMap.containsKey(listName)) {
					addressObj.addressList = addressObj.addressBookMap.get(listName);
					addressObj.setAddressListName(listName);
				}

				else {
					System.out.println("Address list with name" + listName + " not present. Please add it first.");
				}

				System.out.println("Add Person Details:");
				System.out.println("First Name:");
				String firstName = sc.nextLine();
				System.out.println("Last Name:");
				String lastName = sc.nextLine();
				System.out.println("Address:");
				String address = sc.nextLine();
				System.out.println("City:");
				String city = sc.nextLine();
				System.out.println("State:");
				String state = sc.nextLine();
				System.out.println("Zip:");
				String zip = sc.nextLine();
				System.out.println("Phone no:");
				String phoneNo = sc.nextLine();
				System.out.println("Email");
				String email = sc.nextLine();
				// Input
				Contact contactObj = new Contact(firstName, lastName, address, city, state, zip, phoneNo,email);
				boolean contactIsAdded = addressObj.addContact(contactObj);
				addressObj.addToDictionary(contactIsAdded,contactObj);

				break;
			}
			case 3: {
				System.out.println(
						"Enter first name and  Enter last name of person to edit details:");
				String firstName = sc.nextLine();
				String lastName = sc.nextLine();
				boolean contactFound = addressObj.editDetails(firstName, lastName);
				if (contactFound == true)
					System.out.println("Details successfully edit");
				else
					System.out.println("Contact not found");
				break;
			}
			case 4: {
				System.out.println(
						"Enter first name and  enter last name of person to delete data");
				String firstName = sc.nextLine();
				String lastName = sc.nextLine();
				boolean contactFound = addressObj.removeDetails(firstName, lastName);
				if (contactFound == true)
					System.out.println("Details successfully deleted");
				else
					System.out.println("Contact not found");
				break;
			}

			case 5: {
					service.readData();


				break;
			}
			case 6: {
				System.out.println("Enter first name of person to search");
				String searchPerson = sc.nextLine();
				System.out.println("Enter the name of city or state");
				String cityOrState = sc.nextLine();
				System.out.println("Enter 1 if you entered name of a city \nEnter 2 if you entered name of a state");
				int searchChoice = Integer.parseInt(sc.nextLine());
				addressObj.searchPersonAcrossCityState(searchPerson,searchChoice, cityOrState);
			}
			case 7: {
				System.out.println("Enter the name of city or state");
				String cityOrState = sc.nextLine();
				System.out.println("Enter 1 if you entered name of a city \nEnter 2 if you entered name of a state");
				int searchChoice = Integer.parseInt(sc.nextLine());
				addressObj.viewPersonsByCityState(cityOrState,searchChoice);
				break;
			}
			case 8: {
				System.out.println("Enter the name of city or state");
				String cityOrState = sc.nextLine();
				System.out.println("Enter 1 if you entered name of a city \nEnter 2 if you entered name of a state");
				int searchChoice = Integer.parseInt(sc.nextLine());
				System.out.println("Total persons in "+cityOrState+" = "+addressObj.getCountByCityState(cityOrState,searchChoice));
				break;
			}
			case 9: {
				List<Contact> sortedEntriesList = addressObj.sortAddressBookByName(addressObj.addressList);
				System.out.println("Entries sorted in current address book. Sorted Address Book Entries:");
				System.out.println(sortedEntriesList);
				break;
			}
			case 10: {
				System.out.println("Enter 1 to sort by city \nEnter 2 to sort by state \nEnter 3 to sort by zipcode");
				int sortChoice = Integer.parseInt(sc.nextLine());
				List<Contact> sortedEntriesList = addressObj.sortAddressBookByChoice(sortChoice,
						addressObj.addressList);
				System.out.println(sortedEntriesList);
				break;
			}
			case 11: {
				addressObj.addressBookJsonService.print();
				break;
			}
			case 12: {
				System.out.println("Thank you for using the application");
			}
			}
		}
	}
}
