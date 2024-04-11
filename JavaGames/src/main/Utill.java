package main;

public class Utill {
	
	public static boolean linerSearch(int code, int[] key) {
		
		boolean returnBoolean = false;
		
		for (int value : key) {
			if(value == code) {
				returnBoolean = true;
				break;
			}
		};
		
		return returnBoolean;
	}

}
