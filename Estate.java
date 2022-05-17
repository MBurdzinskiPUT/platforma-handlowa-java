public class Estate extends Item implements Taxed {

	private float propertyTax;

	public Estate(String name, float netCost, float propertyTax) {
		
		super(name, "Estate", netCost);
		this.propertyTax = propertyTax;
	}

	public Estate(int id, String name, String date, float netCost, float finalCost, Market market, Salesman seller, Client buyer, float propertyTax) {
		
		super(id, name, "Estate", date, netCost, finalCost, market, seller, buyer);
		this.propertyTax = propertyTax;
	}

	@Override
	public float calculateTax() {

		return (this.netCost) * (1 + (Taxed.salesTax) + (this.propertyTax));
	}

	@Override
	public float totalCost(double distance) {

		return (this.calculateTax());
	}

	public float getPropertyTax() {

		return this.propertyTax;
	}

	@Override
	public void showInfo(boolean sold) {

		super.showInfo(sold);
		if (!sold) {
			System.out.println("Property Tax: " + this.getPropertyTax() * 100 + "%");
		}
	}

}