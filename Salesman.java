import java.util.*;

public class Salesman extends Person {

	private ArrayList<Item> itemsForSale = new ArrayList<Item>();
	private ArrayList<Item> itemsSold = new ArrayList<Item>();


	public Salesman(long password, String name, float x, float y) {

		super(password, name, x, y);
	}

	public Salesman(int id, long password, String name, float x, float y, float money) {

		super(id, password, name, x, y, money);
	}

	public void offerOnMarket(Item item, Market market) {

		market.insertItem(item);
		item.setMarket(market);
		item.setSeller(this);
		this.insertForSale(item);
	}

	public void offerOnNearest(Item item, final ArrayList<Market> markets) {

		this.offerOnMarket(item, this.nearestMarket(markets));
	}

	public String retractOffer(int id) {

		Item item = Utility.findById(id, this.itemsForSale);
		Market market = item.getMarket();
		if (market == null) {
			throw new RuntimeException("Error: This item does not have a market associated with it");
		}
		this.clearOffer(item);
		market.removeItem(item);
		item.setMarket(null);
		item.setSeller(null);
		return market.getName();
	}

	public void activeOffers() {

		if (itemsForSale.isEmpty()) {
			System.out.println("There are no active offers to show");
		}
		else {
			for (int i = 0; i < itemsForSale.size(); i++) {
				itemsForSale.get(i).showInfo(false);
			}
		}
	}

	public boolean moneyWithdraw(float amount) {

		if (amount > (this.money) || amount < 0F) {
			return false;
		}
		else {
			this.money -= amount;
			return true;
		}
	}

	@Override
	public void transactionHistory() {

		if (itemsSold.isEmpty()) {
			System.out.println("No transactions were performed on this account");
		}
		else {
			for (int i = 0; i < itemsSold.size(); i++) {
				itemsSold.get(i).showInfo(true);
			}
		}
	}

	public boolean noOffers() {

		return this.itemsForSale.isEmpty();
	}

	public boolean clearOffer(Item item) {

		return Utility.removeByValue(item, this.itemsForSale);
	}

	public void insertForSale(Item item) {

		this.itemsForSale.add(item);
	}

	public void insertSold(Item item) {

		this.itemsSold.add(item);
	}

	public void finalizeOffer(Item item) {

		this.insertSold(item);
		this.money += item.getNetCost();
	}

}