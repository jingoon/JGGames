package main;


public class Utill {
	
	public static boolean linerSearch(int code, int[] key) {
		
		boolean codeInKeyList = false;
		
		for (int value : key) {
			if(value == code) {
				codeInKeyList = true;
				break;
			}
		};
		
		return codeInKeyList;
	}
	
	public static boolean moveKeyPress(KeyHandler keyH) {
		
		if(keyH.upPress || keyH.downPress || keyH.rigthPress || keyH.leftPress) {
			return true;
		}
		
		return false;
	}
	
}
