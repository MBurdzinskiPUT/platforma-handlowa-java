import java.util.*;

public class Utility {

    public static <T> boolean removeByValue(final T item, ArrayList<T> vector) {

		for (int i = 0; i < vector.size(); i++) {
			if (vector.get(i) == item) {
				vector.remove(i);
				return true;
			}
		}
		return false;
	}

	public static <T extends Identification> T findByName(final String name, final ArrayList<T> vector) {

		for (int i = 0; i < vector.size(); i++) {
			if (name.equals(vector.get(i).getName())) {
				return vector.get(i);
			}
		}
		throw new RuntimeException("Error: Object with this name could not be found");
	}

	public static <T extends Identification> T findById(final int id, final ArrayList<T> vector) {
        
        for (int i = 0; i < vector.size(); i++) {
			if (vector.get(i).getId() == id) {
				return vector.get(i);
			}
		}
		throw new RuntimeException("Error: Object with this id could not be found");
    }
    
}