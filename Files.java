import java.util.*;
import java.io.*;

public class Files {

    public static void copyFile(final String source, final String destination) throws Exception {

		try {
			File sourceFile = new File(source);
			File destinationFile = new File(destination);
			Scanner reader = new Scanner(sourceFile);
			FileWriter writer = new FileWriter(destinationFile);
			String buffer;
			while (reader.hasNextLine()) {
				buffer = reader.nextLine();
				writer.write(buffer + "\n");
			}
			reader.close();
			writer.close();
		}
		catch (java.lang.Exception e) {
			throw e;
		}
	}
    
    public static void insertToFile(final String fileName, Person loggedUser, Item item) throws Exception {

		File tempFile = new File("Data/Temporary.txt");
		tempFile.createNewFile();
		try {
			File realFile = new File(fileName);
			Scanner reader = new Scanner(realFile);
			FileWriter writer = new FileWriter(tempFile);
			String buffer;
			int fields;
			if (loggedUser != null) {
				fields = 6;
			}
			else { // item != null
				fields = 10;
			}
			buffer = reader.nextLine();
			buffer = Encryption.decrypt(buffer);
			int objects = Integer.parseInt(buffer);
			writer.write(Encryption.encrypt(String.valueOf(objects+1)) + "\n");
			for (int i = 0; i < objects; i++) {
				for (int j = 0; j < fields; j++) {
					buffer = reader.nextLine();
					writer.write(buffer + "\n");
				}
			}
			if (loggedUser != null) {
				writer.write(Encryption.encrypt(String.valueOf(loggedUser.getId())) + "\n");
				writer.write(Encryption.encrypt(String.valueOf(loggedUser.getPassword())) + "\n");
				writer.write(Encryption.encrypt(loggedUser.getName()) + "\n");
				writer.write(Encryption.encrypt(String.valueOf(loggedUser.getLocation().getX())) + "\n");
				writer.write(Encryption.encrypt(String.valueOf(loggedUser.getLocation().getY())) + "\n");
				writer.write(Encryption.encrypt(String.valueOf(loggedUser.getMoney())) + "\n");
			}
			else { // item != null
				writer.write(Encryption.encrypt(String.valueOf(item.getId())) + "\n");
				writer.write(Encryption.encrypt(item.getName()) + "\n");
				writer.write(Encryption.encrypt(item.getType()) + "\n");
				writer.write(Encryption.encrypt(item.getDate()) + "\n");
				writer.write(Encryption.encrypt(String.valueOf(item.getNetCost())) + "\n");
				writer.write(Encryption.encrypt(String.valueOf(item.getFinalCost())) + "\n");
				if (item.getMarket() != null) {
					writer.write(Encryption.encrypt(String.valueOf(item.getMarket().getId())) + "\n");
				}
				else {
					writer.write(Encryption.encrypt(String.valueOf(0)) + "\n");
				}
				if (item.getSeller() != null) {
					writer.write(Encryption.encrypt(String.valueOf(item.getSeller().getId())) + "\n");
				}
				else {
					writer.write(Encryption.encrypt(String.valueOf(0)) + "\n");
				}
				if (item.getBuyer() != null) {
					writer.write(Encryption.encrypt(String.valueOf(item.getBuyer().getId())) + "\n");
				}
				else {
					writer.write(Encryption.encrypt(String.valueOf(0)) + "\n");
				}
				if (item.getType().equals("Chattel")) {
					writer.write(Encryption.encrypt(String.valueOf(((Chattel)item).getDeliveryCost())) + "\n");
				}
				else { // item.getType().equals("Estate")
					writer.write(Encryption.encrypt(String.valueOf(((Estate)item).getPropertyTax())) + "\n");
				}
			}
			reader.close();
			writer.close();
			copyFile("Data/Temporary.txt", fileName);
		}
		catch (java.lang.Exception e) {
			tempFile.delete();
			throw e;
		}
		tempFile.delete();
	}

	public static void updateInFile(final String fileName, Person loggedUser, Item item) throws Exception {

		File tempFile = new File("Data/Temporary.txt");
		tempFile.createNewFile();
		try {
			File realFile = new File(fileName);
			Scanner reader = new Scanner(realFile);
			FileWriter writer = new FileWriter(tempFile);
			String buffer;
			int fields;
			int id;
			if (loggedUser != null) {
				fields = 6;
			}
			else { // item != null
				fields = 10;
			}
			if (loggedUser != null) {
				id = loggedUser.getId();
			}
			else { // item != null
				id = item.getId();
			}
			buffer = reader.nextLine();
			buffer = Encryption.decrypt(buffer);
			int objects = Integer.parseInt(buffer);
			writer.write(Encryption.encrypt(String.valueOf(objects)) + "\n");
			for (int i = 0; i < objects; i++) {
				for (int j = 0; j < fields; j++) {
					buffer = reader.nextLine();
					buffer = Encryption.decrypt(buffer);
					if (j == 0 && Integer.parseInt(buffer) == id) { // Found
						if (loggedUser != null) {
							writer.write(Encryption.encrypt(String.valueOf(loggedUser.getId())) + "\n");
							writer.write(Encryption.encrypt(String.valueOf(loggedUser.getPassword())) + "\n");
							writer.write(Encryption.encrypt(loggedUser.getName()) + "\n");
							writer.write(Encryption.encrypt(String.valueOf(loggedUser.getLocation().getX())) + "\n");
							writer.write(Encryption.encrypt(String.valueOf(loggedUser.getLocation().getY())) + "\n");
							writer.write(Encryption.encrypt(String.valueOf(loggedUser.getMoney())) + "\n");
						}
						else { // item != null
							writer.write(Encryption.encrypt(String.valueOf(item.getId())) + "\n");
							writer.write(Encryption.encrypt(item.getName()) + "\n");
							writer.write(Encryption.encrypt(item.getType()) + "\n");
							writer.write(Encryption.encrypt(item.getDate()) + "\n");
							writer.write(Encryption.encrypt(String.valueOf(item.getNetCost())) + "\n");
							writer.write(Encryption.encrypt(String.valueOf(item.getFinalCost())) + "\n");
							if (item.getMarket() != null) {
								writer.write(Encryption.encrypt(String.valueOf(item.getMarket().getId())) + "\n");
							}
							else {
								writer.write(Encryption.encrypt(String.valueOf(0)) + "\n");
							}
							if (item.getSeller() != null) {
								writer.write(Encryption.encrypt(String.valueOf(item.getSeller().getId())) + "\n");
							}
							else {
								writer.write(Encryption.encrypt(String.valueOf(0)) + "\n");
							}
							if (item.getBuyer() != null) {
								writer.write(Encryption.encrypt(String.valueOf(item.getBuyer().getId())) + "\n");
							}
							else {
								writer.write(Encryption.encrypt(String.valueOf(0)) + "\n");
							}
							if (item.getType().equals("Chattel")) {
								writer.write(Encryption.encrypt(String.valueOf(((Chattel)item).getDeliveryCost())) + "\n");
							}
							else { // item.getType().equals("Estate")
								writer.write(Encryption.encrypt(String.valueOf(((Estate)item).getPropertyTax())) + "\n");
							}
						}
						for (int k = 0; k < fields-1; k++) { // Skip
							buffer = reader.nextLine();
						}
						break;
					}
					else {
						writer.write(Encryption.encrypt(buffer) + "\n");
					}
				}
			}
			reader.close();
			writer.close();
			copyFile("Data/Temporary.txt", fileName);
		}
		catch (java.lang.Exception e) {
			tempFile.delete();
			throw e;
		}
		tempFile.delete();
	}

	public static void removeFromFile(final String fileName, Person loggedUser, int id) throws Exception {

		File tempFile = new File("Data/Temporary.txt");
		tempFile.createNewFile();
		try {
			File realFile = new File(fileName);
			Scanner reader = new Scanner(realFile);
			FileWriter writer = new FileWriter(tempFile);
			String buffer;
			int fields;
			if (loggedUser != null) {
				fields = 6;
			}
			else { // item != null
				fields = 10;
			}
			buffer = reader.nextLine();
			buffer = Encryption.decrypt(buffer);
			int objects = Integer.parseInt(buffer);
			writer.write(Encryption.encrypt(String.valueOf(objects-1)) + "\n");
			for (int i = 0; i < objects; i++) {
				for (int j = 0; j < fields; j++) {
					buffer = reader.nextLine();
					buffer = Encryption.decrypt(buffer);
					if (j == 0 && Integer.parseInt(buffer) == id) { // Skip
						for (int k = 0; k < fields-1; k++) {
							buffer = reader.nextLine();
						}
						break;
					}
					else {
						writer.write(Encryption.encrypt(buffer) + "\n");
					}
				}
			}
			reader.close();
			writer.close();
			copyFile("Data/Temporary.txt", fileName);
		}
		catch (java.lang.Exception e) {
			tempFile.delete();
			throw e;
		}
		tempFile.delete();
	}
}