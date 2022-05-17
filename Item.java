public abstract class Item extends Identification {

	protected static int count = 0;
	protected int id;
	protected String name;
	protected String type;
	protected String date;
	protected float netCost;
	protected float finalCost;
	protected Market market;
	protected Salesman seller;
	protected Client buyer;

	public Item(String name, String type, float netCost) {

		count++;
		this.id = count;
		this.name = name;
		this.type = type;
		this.date = "0";
		this.netCost = netCost;
		this.finalCost = 0F;
		this.market = null;
		this.seller = null;
		this.buyer = null;
	}

	public Item(int id, String name, String type, String date, float netCost, float finalCost, Market market, Salesman seller, Client buyer) {

		count = Math.max(id, count+1);
		this.id = id;
		this.name = name;
		this.type = type;
		this.date = date;
		this.netCost = netCost;
		this.finalCost = finalCost;
		this.market = market;
		this.seller = seller;
		this.buyer = buyer;
	}

	public abstract float totalCost(double distance);

	@Override
	public int getId() {

		return this.id;
	}

	@Override
	public String getName() {

		return this.name;
	}

	public String getType() {

		return this.type;
	}

	public String getDate() {

		return this.date;
	}

	public float getNetCost() {

		return this.netCost;
	}

	public float getFinalCost() {

		return this.finalCost;
	}

	public Market getMarket() {

		return this.market;
	}

	public Salesman getSeller() {

		return this.seller;
	}

	public Client getBuyer() {

		return this.buyer;
	}

	public void showInfo(boolean sold) {

		System.out.println("-".repeat(6) + " Item " + this.getId() + " (" + this.getType() + ") " + "-".repeat(6));
		System.out.println("Name: " + this.getName());
		System.out.println("Market: " + this.getMarket().getName());
		System.out.println("Net Cost: " + this.getNetCost());
		if (sold) {
			System.out.println("Final Cost: " + this.getFinalCost());
			System.out.println("Seller: " + this.getSeller().getName());
			System.out.println("Buyer: " + this.getBuyer().getName());
			System.out.println("Transaction Date: " + this.getDate());
		}
	}

	public void setDate(String date) {

		this.date = date.charAt(date.length() - 1) == '\n' ? date.substring(0, date.length() - 1) : date;
	}

	public void setFinalCost(float finalCost) {

		this.finalCost = finalCost;
	}

	public void setMarket(Market market) {

		this.market = market;
	}

	public void setSeller(Salesman seller) {

		this.seller = seller;
	}

	public void setBuyer(Client buyer) {

		this.buyer = buyer;
	}

}