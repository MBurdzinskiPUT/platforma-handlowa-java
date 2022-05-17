import java.util.*;

public class Validation {

    public static void validateUsername(final String name, final Context activeContext, ArrayList<Salesman> salesmen, ArrayList<Client> clients) {

		Person user = null;
		if (name.isBlank()) {
			throw new RuntimeException("Error: The username must contain visible characters");
		}
		if (activeContext == Context.REG_SM || activeContext == Context.CHNG_SM) {
			try {
				user = Utility.findByName(name, salesmen);
			}
			catch (java.lang.Exception e) {
				// Not Found = Ok
			}
			if (user != null) {
				throw new RuntimeException("Error: A salesman account with this name already exists");
			}
		}
		else { // activeContext == Context.REG_CL || activeContext == Context.CHNG_CL 
			try {
				user = Utility.findByName(name, clients);
			}
			catch (java.lang.Exception e) {
				// Not Found = Ok
			}
			if (user != null) {
				throw new RuntimeException("Error: A client account with this name already exists");
			}
		}
	}

	public static void validatePassword(final String password) {

		if (password.length() < 8) {
			throw new RuntimeException("Error: The password must contain at least 8 characters");
		}
		if (password.isBlank()) {
			throw new RuntimeException("Error: The password must contain visible characters");
		}
	}

	public static void validateX(final String sx) {

		float x;
		try {
			x = Float.parseFloat(sx);
		}
		catch (java.lang.Exception e) {
			throw new RuntimeException("Error: The X coordinate must be a floating point number");
		}
		if (x < -180F || x > 180F) {
			throw new RuntimeException("Error: The X coordinate must be between -180 and 180");
		}
	}

	public static void validateY(final String sy) {

		float y;
		try {
			y = Float.parseFloat(sy);
		}
		catch (java.lang.Exception e) {
			throw new RuntimeException("Error: The Y coordinate must be a floating point number");
		}
		if (y < -90F || y > 90F) {
			throw new RuntimeException("Error: The Y coordinate must be between -90 and 90");
		}
	}

	public static void validateName(final String name) {

		if (name.isBlank()) {
			throw new RuntimeException("Error: The name of the item must contain visible characters");
		}
	}

	public static void validateFloat(final String sf) {

		float f;
		try {
			f = Float.parseFloat(sf);
		}
		catch (java.lang.Exception e) {
			throw new RuntimeException("Error: This value must be a floating point number");
		}
		if (f < 0F) {
			throw new RuntimeException("Error: This value must not be a negative number");
		}
	}

	public static void validateInt(final String si) {

		int i;
		try {
			i = Integer.parseInt(si);
		}
		catch (java.lang.Exception e) {
			throw new RuntimeException("Error: This value must be an integer");
		}
		if (i < 0) {
			throw new RuntimeException("Error: This value must not be a negative number");
		}
	}
	
}
