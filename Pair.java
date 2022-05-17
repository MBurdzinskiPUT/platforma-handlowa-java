public class Pair<T, U> {

	public T first;
	public U second;

	public Pair (T first, U second) {

        this.first = first;
        this.second = second;
	}

	public Pair (Pair<T, U> pair) {
		this.first = pair.first;
		this.second = pair.second;
	}
	
}