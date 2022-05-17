public class Chattel extends Item implements Taxed {

	private float deliveryCost;

	public Chattel(String name, float netCost, float deliveryCost) {
		
		super(name, "Chattel", netCost);
		this.deliveryCost = deliveryCost;
	}

	public Chattel(int id, String name, String date, float netCost, float finalCost, Market market, Salesman seller, Client buyer, float deliveryCost) {
		
		super(id, name, "Chattel", date, netCost, finalCost, market, seller, buyer);
		this.deliveryCost = deliveryCost;
	}

	@Override
	public float calculateTax() {

		return (this.netCost) * (1 + (Taxed.salesTax));
	}

	@Override
	public float totalCost(double distance) {

		return (this.calculateTax()) + (float)Math.sqrt((this.deliveryCost) * distance);
	}

	public float getDeliveryCost() {

		return this.deliveryCost;
	}

	@Override
	public void showInfo(boolean sold) {

		super.showInfo(sold);
		if (!sold) {
			System.out.println("Delivery Cost: " + this.getDeliveryCost() + "*");
		}
	}

}