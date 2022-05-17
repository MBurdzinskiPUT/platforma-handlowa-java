import java.util.*;

public class Client extends Person {

	private ArrayList<Item> itemsPurchased = new ArrayList<Item>();

	private void canAfford(String name, Market market) {

		float cost = this.checkPrice(name, market).first;
		if (this.getMoney() < cost) {
			throw new RuntimeException("Error: You cannot afford to purchase this item");
		}
	}

	public Client(long password, String name, float x, float y) {

		super(password, name, x, y);
	}

	public Client(int id, long password, String name, float x, float y, float money) {

		super(id, password, name, x, y, money);
	}

	public Pair<Float, Item> purchaseFromMarket(String name, Market market) {

		this.canAfford(name, market);
		Pair<Float, Item> pair = market.bestOffer(name, this.getLocation());

		market.removeItem(pair.second);
		pair.second.getSeller().clearOffer(pair.second);
		pair.second.getSeller().finalizeOffer(pair.second);
		pair.second.setFinalCost(pair.first);
		pair.second.setBuyer(this);
		pair.second.setDate(new Date().toString());
		this.money -= pair.first;
		this.itemsPurchased.add(pair.second);
		return pair;
	}

	public Pair<Float, Item> purchaseFromNearest(String name, final ArrayList<Market> markets) {

		return this.purchaseFromMarket(name, this.nearestMarket(markets));
	}

	public Pair<Float, Item> checkPrice(String name, Market market) {

		if (!market.isAvailable(name)) {
			throw new RuntimeException("Error: No offers for item with this name could be found on this market");
		}
		return market.bestOffer(name, this.getLocation());
	}

	public Pair<Float, Item> checkNearest(String name, final ArrayList<Market> markets) {

		return this.checkPrice(name, this.nearestMarket(markets));
	}

	public Market findCheapest(String name, final ArrayList<Market> markets) {

		float minCost = Float.MAX_VALUE;
		float newCost;
		Market market = null;

		for (int i = 0; i < markets.size(); i++) {
			if (markets.get(i).isAvailable(name)) {
				newCost = markets.get(i).bestOffer(name, this.getLocation()).first;
				if (newCost < minCost) {
					minCost = newCost;
					market = markets.get(i);
				}
			}
		}
		if (market != null) {
			return market;
		}
		throw new RuntimeException("Error: No offers for item with this name could be found on any market");
	}

	public boolean moneyDeposit(float amount) {

		if (amount < 0F) {
			return false;
		}
		else {
			this.money += amount;
			return true;
		}
	}

	@Override
	public void transactionHistory() {

		if (itemsPurchased.isEmpty()) {
			System.out.println("No transactions were performed on this account");
		}
		else {
			for (int i = 0; i < itemsPurchased.size(); i++) {
				itemsPurchased.get(i).showInfo(true);
			}
		}
	}

	public void insertPurchased(Item item) {

		this.itemsPurchased.add(item);
	}

}