import java.util.*;

public abstract class Person extends Identification {

	protected static int count = 0;
	protected int id;
	protected long password;
	protected String name;
	protected float money;
	protected Location location;

	protected Market nearestMarket(final ArrayList<Market> markets) {

		double minDistance = Double.MAX_VALUE;
		double newDistance;
		Market market = null;

		for (int i = 0; i < markets.size(); i++) {
			newDistance = this.getLocation().calculateDistance(markets.get(i).getLocation());
			if (newDistance < minDistance) {
				minDistance = newDistance;
				market = markets.get(i);
			}
		}
		if (market != null) {
			return market;
		}
		throw new RuntimeException("Error: Nearest market could not be found");
	}

	public Person(long password, String name, float x, float y) {
		
		this.location = new Location(x, y);
		count++;
		this.id = count;
		this.password = password;
		this.name = name;
		this.money = 0F;
	}

	public Person(int id, long password, String name, float x, float y, float money) {
		
		this.location = new Location(x, y);
		count = Math.max(id, count+1);
		this.id = id;
		this.password = password;
		this.name = name;
		this.money = money;
	}

	public abstract void transactionHistory();

	@Override
	public int getId() {

		return this.id;
	}

	public long getPassword() {

		return this.password;
	}

	@Override
	public String getName() {

		return this.name;
	}

	public float getMoney() {

		return this.money;
	}

	public Location getLocation() {

		return this.location;
	}

	public void showInfo() {

		Location location = this.getLocation();
		System.out.println("Username: " + this.getName());
		System.out.println("Balance: " + this.getMoney());
		System.out.println("Location (X, Y): " + location.getX() + ", " + location.getY());
	}

	public void setPassword(long password) {

		this.password = password;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setX(float x) {

		this.location.setX(x);
	}

	public void setY(float y) {

		this.location.setY(y);
	}

}