import java.util.*;
import java.io.*;

public class Global {

	public static void viewOptions(final Context activeContext) {

		switch (activeContext) {

			case MAIN:
				System.out.println("Main Menu");
				System.out.println("1 - Register a new salesman account");
				System.out.println("2 - Register a new client account");
				System.out.println("3 - Login to an existing salesman account");
				System.out.println("4 - Login to an existing client account");
				System.out.println("5 - Exit the application");
			break;

			case MENU_SM:
				System.out.println("Salesman Account Menu");
				System.out.println("1 - Create a new sale offer");
				System.out.println("2 - Show your active sale offers");
				System.out.println("3 - Retract an active sale offer");
				System.out.println("4 - Show account information");
				System.out.println("5 - Change account information");
				System.out.println("6 - Show account transaction history");
				System.out.println("7 - Withdraw money from your account");
				System.out.println("8 - Logout and return to the main menu");
			break;

			case MENU_CL:
				System.out.println("Client Account Menu");
				System.out.println("1 - Purchase an item from a market");
				System.out.println("2 - Check price of an item on a market");
				System.out.println("3 - Find the cheapest offer for an item");
				System.out.println("4 - Show account information");
				System.out.println("5 - Change account information");
				System.out.println("6 - Show account transaction history");
				System.out.println("7 - Deposit money on your account");
				System.out.println("8 - Logout and return to the main menu");
			break;

			default:
				System.out.println("Error: Program is in an invalid state");
			break;
		}
	}

	public static boolean processOptions(Wrapper<Context> activeContext, Wrapper<Person> loggedUser, Scanner scanner) {

		System.out.print("Choose one of the available options: ");
		String in;
		in = scanner.nextLine();
		try {
			switch (activeContext.argValue) {

				case MAIN:
					switch (Integer.parseInt(in)) {

						case 1:
							activeContext.argValue = Context.REG_SM;
						return true;
						case 2:
							activeContext.argValue = Context.REG_CL;
						return true;
						case 3:
							activeContext.argValue = Context.LOG_SM;
						return true;
						case 4:
							activeContext.argValue = Context.LOG_CL;
						return true;
						case 5:
							activeContext.argValue = Context.EXIT;
						return false;
						default:
							System.out.println("Error: Please choose an existing option");
						return false;
					}

				case MENU_SM:
					switch (Integer.parseInt(in)) {

						case 1:
							activeContext.argValue = Context.SELL;
						return true;
						case 2:
							((Salesman)loggedUser.argValue).activeOffers();
						return false;
						case 3:
							activeContext.argValue = Context.RETRACT;
						return true;
						case 4:
							((Salesman)loggedUser.argValue).showInfo();
						return false;
						case 5:
							activeContext.argValue = Context.CHNG_SM;
						return true;
						case 6:
							((Salesman)loggedUser.argValue).transactionHistory();
						return false;
						case 7:
							activeContext.argValue = Context.MON_SM;
						return true;
						case 8:
							loggedUser.argValue = null;
							activeContext.argValue = Context.MAIN;
						return false;
						default:
							System.out.println("Error: Please choose an existing option");
						return false;
					}

				case MENU_CL:
					switch (Integer.parseInt(in)) {

						case 1:
							activeContext.argValue = Context.BUY;
						return true;
						case 2:
							activeContext.argValue = Context.CHECK;
						return true;
						case 3:
							activeContext.argValue = Context.FIND;
						return true;
						case 4:
							((Client)loggedUser.argValue).showInfo();
						return false;
						case 5:
							activeContext.argValue = Context.CHNG_CL;
						return true;
						case 6:
							((Client)loggedUser.argValue).transactionHistory();
						return false;
						case 7:
							activeContext.argValue = Context.MON_CL;
						return true;
						case 8:
							loggedUser.argValue = null;
							activeContext.argValue = Context.MAIN;
						return false;
						default:
							System.out.println("Error: Please choose an existing option");
						return false;
					}

				default:
					System.out.println("Error: Program is in an invalid state");
				return false;
			}
		}
		catch (java.lang.Exception e) {
			System.out.println("Error: Please provide a valid integer value");
			return false;
		}
	}

	public static void inputData(Wrapper<Context> activeContext, Wrapper<Person> loggedUser, ArrayList<Market> markets, ArrayList<Salesman> salesmen, ArrayList<Client> clients, ArrayList<Item> items, Scanner scanner) {

		String[] in = new String[4];
		Person user;
		Item item;
		String fileName;
		Pair<Float, Item> pair;

		switch (activeContext.argValue) {

			case REG_SM:
			case REG_CL:
				while (true) {
					System.out.print("Choose the username for your account: ");
					in[0] = scanner.nextLine();
					try {
						Validation.validateUsername(in[0], activeContext.argValue, salesmen, clients);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				while (true) {
					System.out.print("Type in your password (at least 8 characters): ");
					in[1] = scanner.nextLine();
					try {
						Validation.validatePassword(in[1]);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				while (true) {
					System.out.print("Provide the X coordinate of your location (number between -180 and 180): ");
					in[2] = scanner.nextLine();
					try {
						Validation.validateX(in[2]);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				while (true) {
					System.out.print("Provide the Y coordinate of your location (number between -90 and 90): ");
					in[3] = scanner.nextLine();
					try {
						Validation.validateY(in[3]);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				if (activeContext.argValue == Context.REG_SM) {
					salesmen.add(new Salesman(Encryption.rshash(in[1]), in[0], Float.parseFloat(in[2]), Float.parseFloat(in[3])));
					loggedUser.argValue = salesmen.get(salesmen.size()-1);
					fileName = "Data/Salesmen.txt";
					activeContext.argValue = Context.MENU_SM;
				}
				else { // activeContext.argValue == Context.REG_CL
					clients.add(new Client(Encryption.rshash(in[1]), in[0], Float.parseFloat(in[2]), Float.parseFloat(in[3])));
					loggedUser.argValue = clients.get(clients.size()-1);
					fileName = "Data/Clients.txt";
					activeContext.argValue = Context.MENU_CL;
				}
				try {
					Files.insertToFile(fileName, loggedUser.argValue, null);
				}
				catch (java.lang.Exception e) {
					System.out.println("Error: Account data could not be saved to file");
				}
				System.out.println("Account registered successfully");
			break;

			case LOG_SM:
			case LOG_CL:
				System.out.print("Username: ");
				in[0] = scanner.nextLine();
				if (activeContext.argValue == Context.LOG_SM) {
					try {
						user = Utility.findByName(in[0], salesmen);
					}
					catch (java.lang.Exception e) {
						System.out.println("Error: Salesman account with this username was not found");
						activeContext.argValue = Context.MAIN;
						break;
					}
				}
				else { // activeContext.argValue == Context.LOG_CL
					try {
						user = Utility.findByName(in[0], clients);
					}
					catch (java.lang.Exception e) {
						System.out.println("Error: Client account with this username was not found");
						activeContext.argValue = Context.MAIN;
						break;
					}
				}
				System.out.print("Password: ");
				in[1] = scanner.nextLine();
				if (user.getPassword() != Encryption.rshash(in[1])) {
					System.out.println("Error: Passwords do not match");
					activeContext.argValue = Context.MAIN;
					break;
				}
				loggedUser.argValue = user;
				if (activeContext.argValue == Context.LOG_SM) {
					activeContext.argValue = Context.MENU_SM;
				}
				else { // activeContext.argValue == Context.LOG_CL
					activeContext.argValue = Context.MENU_CL;
				}
				System.out.println("Logged in successfully");
			break;

			case MON_SM:
				float money = ((Salesman)loggedUser.argValue).getMoney();
				if (money == 0F) {
					System.out.println("Error: There is no money to withdraw from the account");
					activeContext.argValue = Context.MENU_SM;
					break;
				}
				while (true) {
					System.out.print("Choose the amount of money to withdraw (" + money + " units available): ");
					in[0] = scanner.nextLine();
					try {
						if (((Salesman)loggedUser.argValue).moneyWithdraw(Float.parseFloat(in[0]))) {
							break;
						}
						else {
							throw new RuntimeException("");
						}
					}
					catch (java.lang.Exception e) {
						System.out.println("Error: The amount of money must be a number between 0 and " + money);
					}
				}
				fileName = "Data/Salesmen.txt";
				try {
					Files.updateInFile(fileName, loggedUser.argValue, null);
				}
				catch (java.lang.Exception e) {
					System.out.println("Error: Account data could not be saved to file");
				}
				System.out.println("Account balance decreased to " + ((Salesman)loggedUser.argValue).getMoney());
				activeContext.argValue = Context.MENU_SM;
			break;

			case MON_CL:
				while (true) {
					System.out.print("Choose the amount of money to deposit: ");
					in[0] = scanner.nextLine();
					try {
						if (((Client)loggedUser.argValue).moneyDeposit(Float.parseFloat(in[0]))) {
							break;
						}
						else {
							throw new RuntimeException("");
						}
					}
					catch (java.lang.Exception e) {
						System.out.println("Error: The amount of money must be a number greater than 0");
					}
				}
				fileName = "Data/Clients.txt";
				try {
					Files.updateInFile(fileName, loggedUser.argValue, null);
				}
				catch (java.lang.Exception e) {
					System.out.println("Error: Account data could not be saved to file");
				}
				System.out.println("Account balance increased to " + ((Client)loggedUser.argValue).getMoney());
				activeContext.argValue = Context.MENU_CL;
			break;

			case CHNG_SM:
			case CHNG_CL:
				System.out.println("Leaving a field blank will keep current account information");
				while (true) {
					System.out.print("Choose the new username for your account: ");
					in[0] = scanner.nextLine();
					if (in[0].length() == 0) {
						break;
					}
					try {
						Validation.validateUsername(in[0], activeContext.argValue, salesmen, clients);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				while (true) {
					System.out.print("Type in your new password (at least 8 characters): ");
					in[1] = scanner.nextLine();
					if (in[1].length() == 0) {
						break;
					}
					try {
						Validation.validatePassword(in[1]);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				while (true) {
					System.out.print("Provide the X coordinate of your new location (number between -180 and 180): ");
					in[2] = scanner.nextLine();
					if (in[2].length() == 0) {
						break;
					}
					try {
						Validation.validateX(in[2]);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				while (true) {
					System.out.print("Provide the Y coordinate of your new location (number between -90 and 90): ");
					in[3] = scanner.nextLine();
					if (in[3].length() == 0) {
						break;
					}
					try {
						Validation.validateY(in[3]);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				if (in[0].length() > 0) {
					loggedUser.argValue.setName(in[0]);
				}
				if (in[1].length() > 0) {
					loggedUser.argValue.setPassword(Encryption.rshash(in[1]));
				}
				if (in[2].length() > 0) {
					loggedUser.argValue.setX(Float.parseFloat(in[2]));
				}
				if (in[3].length() > 0) {
					loggedUser.argValue.setY(Float.parseFloat(in[3]));
				}
				if (activeContext.argValue == Context.CHNG_SM) {
					fileName = "Data/Salesmen.txt";
				}
				else { // activeContext.argValue == Context.CHNG_CL
					fileName = "Data/Clients.txt";
				}
				try {
					Files.updateInFile(fileName, loggedUser.argValue, null);
				}
				catch (java.lang.Exception e) {
					System.out.println("Error: Account data could not be saved to file");
				}
				System.out.println("Information updated successfully");
				if (activeContext.argValue == Context.CHNG_SM) {
					activeContext.argValue = Context.MENU_SM;
				}
				else { // activeContext.argValue == Context.CHNG_CL
					activeContext.argValue = Context.MENU_CL;
				}
			break;

			case SELL:
				while (true) {
					System.out.print("Enter the name of the item you want to sell: ");
					in[0] = scanner.nextLine();
					try {
						Validation.validateName(in[0]);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				while (true) {
					System.out.print("Choose the net price of the item: ");
					in[1] = scanner.nextLine();
					try {
						Validation.validateFloat(in[1]);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				while (true) {
					System.out.print("Is this item a chattel or an estate? Type in 'c' or 'e': ");
					in[2] = scanner.nextLine();
					if (in[2].equals("c")) {
						while (true) {
							System.out.print("Enter the delivery cost multiplier: ");
							in[3] = scanner.nextLine();
							try {
								Validation.validateFloat(in[3]);
								break;
							}
							catch (java.lang.Exception e) {
								System.out.println(e.getMessage());
							}
						}
						items.add(new Chattel(in[0], Float.parseFloat(in[1]), Float.parseFloat(in[3])));
						item = items.get(items.size()-1);
						break;
					}
					else if (in[2].equals("e")) {
						while (true) {
							System.out.print("Enter the property tax value (in percents): ");
							in[3] = scanner.nextLine();
							try {
								Validation.validateFloat(in[3]);
								break;
							}
							catch (java.lang.Exception e) {
								System.out.println(e.getMessage());
							}
						}
						items.add(new Estate(in[0], Float.parseFloat(in[1]), Float.parseFloat(in[3]) / 100));
						item = items.get(items.size()-1);
						break;
					}
					else {
						System.out.println("Error: Neither item category was chosen");
					}
				}
				while (true) {
					System.out.print("Provide the name of the market to sell the item or leave blank for nearest: ");
					in[2] = scanner.nextLine();
					if (in[2].length() == 0) {
						try {
							((Salesman)loggedUser.argValue).offerOnNearest(item, markets);
							break;
						}
						catch (java.lang.Exception e) {
							System.out.println(e.getMessage());
						}
					}
					else {
						try {
							((Salesman)loggedUser.argValue).offerOnMarket(item, Utility.findByName(in[2], markets));
							break;
						}
						catch (java.lang.Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
				fileName = "Data/Items.txt";
				try {
					Files.insertToFile(fileName, null, item);
				}
				catch (java.lang.Exception e) {
					System.out.println("Error: Item data could not be saved to file");
				}
				System.out.println("Item successfully offered on " + item.getMarket().getName());
				activeContext.argValue = Context.MENU_SM;
			break;

			case RETRACT:
				if (((Salesman)loggedUser.argValue).noOffers()) {
					System.out.println("Error: There are no active offers to retract");
					activeContext.argValue = Context.MENU_SM;
					break;
				}
				while (true) {
					System.out.print("Provide the ID of the item you wish to retract or leave blank to cancel: ");
					in[0] = scanner.nextLine();
					if (in[0].length() == 0) {
						System.out.println("Operation cancelled");
						break;
					}
					try {
						Validation.validateInt(in[0]);
						String name = ((Salesman)loggedUser.argValue).retractOffer(Integer.parseInt(in[0]));
						System.out.println("Item retracted successfully from " + name);
						break;
					}
					catch (java.lang.Exception e) {
						System.out.println(e.getMessage());
					}
				}
				if (in[0].length() > 0) {
					fileName = "Data/Items.txt";
					try {
						Files.removeFromFile(fileName, null, Integer.parseInt(in[0]));
					}
					catch (java.lang.Exception e) {
						System.out.println("Error: Item data could not be saved to file");
					}
				}
				activeContext.argValue = Context.MENU_SM;
			break;

			case BUY:
				System.out.print("Enter the name of the item you want to purchase: ");
				in[0] = scanner.nextLine();
				System.out.print("Enter the name of the market to purchase from or leave blank for nearest: ");
				in[1] = scanner.nextLine();
				try {
					if (in[1].length() == 0) {
						pair = new Pair<Float, Item>(((Client)loggedUser.argValue).purchaseFromNearest(in[0], markets));
					}
					else {
						pair = new Pair<Float, Item>(((Client)loggedUser.argValue).purchaseFromMarket(in[0], Utility.findByName(in[1], markets)));
					}
				}
				catch (java.lang.Exception e) {
					System.out.println(e.getMessage());
					activeContext.argValue = Context.MENU_CL;
					break;
				}
				try {
					fileName = "Data/Salesmen.txt";
					Files.updateInFile(fileName, pair.second.getSeller(), null);
					fileName = "Data/Clients.txt";
					Files.updateInFile(fileName, loggedUser.argValue, null);
				}
				catch (java.lang.Exception e) {
					System.out.println("Error: Account data could not be saved to file");
				}
				try {
					fileName = "Data/Items.txt";
					Files.updateInFile(fileName, null, pair.second);
				}
				catch (java.lang.Exception e) {
					System.out.println("Error: Item data could not be saved to file");
				}
				System.out.println("Successfully purchased " + in[0] + " for " + pair.first + " from " + pair.second.getMarket().getName());
				activeContext.argValue = Context.MENU_CL;
			break;

			case CHECK:
				System.out.print("Enter the name of the item you want to check price of: ");
				in[0] = scanner.nextLine();
				System.out.print("Enter the name of the market to search on or leave blank for nearest: ");
				in[1] = scanner.nextLine();
				try {
					if (in[1].length() == 0) {
						pair = new Pair<Float, Item>(((Client)loggedUser.argValue).checkNearest(in[0], markets));
					}
					else {
						pair = new Pair<Float, Item>(((Client)loggedUser.argValue).checkPrice(in[0], Utility.findByName(in[1], markets)));
					}
				}
				catch (java.lang.Exception e) {
					System.out.println(e.getMessage());
					activeContext.argValue = Context.MENU_CL;
					break;
				}
				System.out.println(in[0] + " costs " + pair.first + " on " + pair.second.getMarket().getName());
				activeContext.argValue = Context.MENU_CL;
			break;

			case FIND:
				System.out.print("Enter the name of the item to search for cheapest offers: ");
				in[0] = scanner.nextLine();
				try {
					pair = new Pair<Float, Item>(((Client)loggedUser.argValue).checkPrice(in[0], ((Client)loggedUser.argValue).findCheapest(in[0], markets)));
				}
				catch (java.lang.Exception e) {
					System.out.println(e.getMessage());
					activeContext.argValue = Context.MENU_CL;
					break;
				}
				System.out.println("Best offer for " + in[0] + " is " + pair.first + " on " + pair.second.getMarket().getName());
				activeContext.argValue = Context.MENU_CL;
			break;

			default:
				System.out.println("Error: Program is in an invalid state");
			break;
		}
	}

	public static void main(String[] args) {

		ArrayList<Market> markets = new ArrayList<Market>();
		ArrayList<Salesman> salesmen = new ArrayList<Salesman>();
		ArrayList<Client> clients = new ArrayList<Client>();
		ArrayList<Item> items = new ArrayList<Item>();

		String fileName;
		File inFile;
		Scanner reader;
		FileWriter writer;

		fileName = "Data/Marketplaces.txt";
		try {
			inFile = new File(fileName);
			if (!inFile.exists()) {
				throw new RuntimeException("");
			}
		}
		catch (java.lang.Exception e) {
			System.out.println("Fatal Error: The program was unable to open the database file: " + fileName);
			System.out.print("Enter any value to terminate the application: ");
			reader = new Scanner(System.in);
			reader.next();
			reader.close();
			return;
		}
		String[] in = new String[10];
		try {
			reader = new Scanner(inFile);
			in[0] = reader.nextLine();
			int n = Integer.parseInt(in[0]);
			if (n < 1) {
				reader.close();
				throw new RuntimeException("");
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < 4; j++) {
					in[j] = reader.nextLine();
				}
				markets.add(new Market(Integer.parseInt(in[0]), in[1], Float.parseFloat(in[2]), Float.parseFloat(in[3])));
			}
			reader.close();
		}
		catch (java.lang.Exception e) {
			System.out.println("Fatal Error: The database file is corrupted: " + fileName);
			System.out.print("Enter any value to terminate the application: ");
			reader = new Scanner(System.in);
			reader.next();
			reader.close();
			return;
		}
		
		fileName = "Data/Salesmen.txt";
		try {
			inFile = new File(fileName);
			if (!inFile.exists()) {
				throw new RuntimeException("");
			}
			reader = new Scanner(inFile);
			in[0] = reader.nextLine();
			in[0] = Encryption.decrypt(in[0]);
			try {
				int n = Integer.parseInt(in[0]);
				if (n < 0) {
					reader.close();
					throw new RuntimeException("");
				}
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < 6; j++) {
						in[j] = reader.nextLine();
						in[j] = Encryption.decrypt(in[j]);
					}
					salesmen.add(new Salesman(Integer.parseInt(in[0]), Long.parseLong(in[1]), in[2], Float.parseFloat(in[3]), Float.parseFloat(in[4]), Float.parseFloat(in[5])));
				}
				reader.close();
			}
			catch (java.lang.Exception e) {
				System.out.println("Error: The file is corrupted: " + fileName);
				System.out.println("Some information might have been lost");
			}
		}
		catch (java.lang.Exception e) {
			System.out.println("No existing salesman account data detected");
			try {
				inFile.createNewFile();
				writer = new FileWriter(inFile);
				writer.write(Encryption.encrypt(String.valueOf(0)) + "\n");
				writer.close();
			}
			catch (java.lang.Exception f) {
				System.out.println("Error: Opening " + fileName + " failed");
			}
		}

		fileName = "Data/Clients.txt";
		try {
			inFile = new File(fileName);
			if (!inFile.exists()) {
				throw new RuntimeException("");
			}
			reader = new Scanner(inFile);
			in[0] = reader.nextLine();
			in[0] = Encryption.decrypt(in[0]);
			try {
				int n = Integer.parseInt(in[0]);
				if (n < 0) {
					reader.close();
					throw new RuntimeException("");
				}
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < 6; j++) {
						in[j] = reader.nextLine();
						in[j] = Encryption.decrypt(in[j]);
					}
					clients.add(new Client(Integer.parseInt(in[0]), Long.parseLong(in[1]), in[2], Float.parseFloat(in[3]), Float.parseFloat(in[4]), Float.parseFloat(in[5])));
				}
				reader.close();
			}
			catch (java.lang.Exception e) {
				System.out.println("Error: The file is corrupted: " + fileName);
				System.out.println("Some information might have been lost");
			}
		}
		catch (java.lang.Exception e) {
			System.out.println("No existing client account data detected");
			try {
				inFile.createNewFile();
				writer = new FileWriter(inFile);
				writer.write(Encryption.encrypt(String.valueOf(0)) + "\n");
				writer.close();
			}
			catch (java.lang.Exception f) {
				System.out.println("Error: Opening " + fileName + " failed");
			}
		}

		fileName = "Data/Items.txt";
		try {
			inFile = new File(fileName);
			if (!inFile.exists()) {
				throw new RuntimeException("");
			}
			reader = new Scanner(inFile);
			in[0] = reader.nextLine();
			in[0] = Encryption.decrypt(in[0]);
			try {
				int n = Integer.parseInt(in[0]);
				if (n < 0) {
					reader.close();
					throw new RuntimeException("");
				}
				Market market;
				Salesman seller;
				Client buyer;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < 10; j++) {
						in[j] = reader.nextLine();
						in[j] = Encryption.decrypt(in[j]);
					}
					try {
						market = Utility.findById(Integer.parseInt(in[6]), markets);
					}
					catch (java.lang.Exception e) {
						market = null;
					}
					try {
						seller = Utility.findById(Integer.parseInt(in[7]), salesmen);
					}
					catch (java.lang.Exception e) {
						seller = null;
					}
					try {
						buyer = Utility.findById(Integer.parseInt(in[8]), clients);
					}
					catch (java.lang.Exception e) {
						buyer = null;
					}
					if (in[2].equals("Chattel")) {
						items.add(new Chattel(Integer.parseInt(in[0]), in[1], in[3], Float.parseFloat(in[4]), Float.parseFloat(in[5]), market, seller, buyer, Float.parseFloat(in[9])));
					}
					else { // in[2].equals("Estate")
						items.add(new Estate(Integer.parseInt(in[0]), in[1], in[3], Float.parseFloat(in[4]), Float.parseFloat(in[5]), market, seller, buyer, Float.parseFloat(in[9])));
					}
					if (buyer != null) { // Sold
						buyer.insertPurchased(items.get(items.size()-1));
						seller.insertSold(items.get(items.size()-1));
					}
					else { // Not Sold
						market.insertItem(items.get(items.size()-1));
						seller.insertForSale(items.get(items.size()-1));
					}
				}
				reader.close();
			}
			catch (java.lang.Exception e) {
				System.out.println("Error: The file is corrupted: " + fileName);
				System.out.println("Some information might have been lost");
			}
		}
		catch (java.lang.Exception e) {
			System.out.println("No existing item data detected");
			try {
				inFile.createNewFile();
				writer = new FileWriter(inFile);
				writer.write(Encryption.encrypt(String.valueOf(0)) + "\n");
				writer.close();
			}
			catch (java.lang.Exception f) {
				System.out.println("Error: Opening " + fileName + " failed");
			}
		}

		Context activeContext = Context.MAIN;
		boolean inputRequired = false;
		Person loggedUser = null;
		Wrapper<Context> wrappedContext = new Wrapper<Context>(activeContext);
		Wrapper<Person> wrappedUser = new Wrapper<Person>(loggedUser);
		Scanner scanner = new Scanner(System.in);

		while (activeContext != Context.EXIT) {
			System.out.println("=".repeat(50));
			if (inputRequired) {
				inputData(wrappedContext, wrappedUser, markets, salesmen, clients, items, scanner);
				activeContext = wrappedContext.argValue;
				loggedUser = wrappedUser.argValue;
				System.out.println("=".repeat(50));
			}
			viewOptions(activeContext);
			inputRequired = processOptions(wrappedContext, wrappedUser, scanner);
			activeContext = wrappedContext.argValue;
			loggedUser = wrappedUser.argValue;
		}
		scanner.close();

		markets.clear();
		salesmen.clear();
		clients.clear();
		items.clear();
	}
}