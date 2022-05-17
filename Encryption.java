public class Encryption {

    public static String encrypt(String str) {

		String temp = new String();
		for (int i = 0; i < str.length(); i++) {
			temp += (char)((str.charAt(i) ^ 0x51) + 50);
		}
		return temp;
	}

	public static String decrypt(String str) {

		String temp = new String();
		for (int i = 0; i < str.length(); i++) {
			temp += (char)((str.charAt(i) - 50) ^ 0x51);
		}
		return temp;
	}

	public static long rshash (String str) {

		long prime[] = {378551, 63689};
		long hash = 0;
		long mask = 0x7FFFFFFFFFFFFFFFL;
		for (int i = 0; i < str.length(); i++) {
			hash *= prime[1];
			hash += str.charAt(i);
			prime[1] *= prime[0];
		}
		return hash & mask;
	}
}
