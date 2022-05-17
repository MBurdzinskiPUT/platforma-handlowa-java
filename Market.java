import java.util.*;

public class Market extends Identification {

	private static int count = 0;
	private int id;
	private String name;
	private Location location;
	private ArrayList<Item> itemsForSale = new ArrayList<Item>();

	public Market(String name, float x, float y) {
		
		this.location = new Location(x, y);
		count++;
		this.id = count;
		this.name = name;
	}

	public Market(int id, String name, float x, float y) {
		
		this.location = new Location(x, y);
		count = Math.max(id, count+1);
		this.id = id;
		this.name = name;
	}

	public boolean isAvailable(String name) {

		try {
			Utility.findByName(name, this.itemsForSale);
			return true;
		}
		catch (java.lang.Exception e) {
			return false;
		}
	}

	public Pair<Float, Item> bestOffer(String name, Location location) {

		float minCost = Float.MAX_VALUE;
		float newCost;
		double distance = this.getLocation().calculateDistance(location);
		Item item = null;

		for (int i = 0; i < itemsForSale.size(); i++) {
			if (name.equals(itemsForSale.get(i).getName())) {
				newCost = itemsForSale.get(i).totalCost(distance);
				if (newCost < minCost) {
					minCost = newCost;
					item = itemsForSale.get(i);
				}
			}
		}
		return new Pair<Float, Item>(minCost, item);
	}

	@Override
	public int getId() {

		return this.id;
	}

	@Override
	public String getName() {

		return this.name;
	}

	public Location getLocation() {

		return this.location;
	}

	public void insertItem(Item item) {

		this.itemsForSale.add(item);
	}

	public boolean removeItem(Item item) {

		return Utility.removeByValue(item, this.itemsForSale);
	}

}