public class Location {

	private static final int diameter = 12742;
	private float x;
	private float y;

	private double toRadians(double degrees) {

		return degrees * Math.PI / 180;
	}

	public Location(float x, float y) {

		this.x = x;
		this.y = y;
	}

	public double calculateDistance(Location location) {

		double[] radX = {toRadians(this.getX()), toRadians(location.getX())};
		double[] radY = {toRadians(this.getY()), toRadians(location.getY())};

		double haversine = Math.pow(Math.sin((radY[1] - radY[0]) / 2), 2) + Math.cos(radY[0]) * Math.cos(radY[1]) * Math.pow(Math.sin((radX[1] - radX[0]) / 2), 2);
		return diameter * Math.asin(Math.sqrt(haversine));
	}

	public float getX() {

		return this.x;
	}

	public float getY() {

		return this.y;
	}

	public void setX(float x) {

		this.x = x;
	}

	public void setY(float y) {

		this.y = y;
	}

}