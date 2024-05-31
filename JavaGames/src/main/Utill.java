package main;

import java.awt.GraphicsEnvironment;

public class Utill {
	
	// 방향키를 눌렀을때 상태
	public static boolean moveKeyPress(KeyHandler keyH) {
		
		if(keyH.upPress || keyH.downPress || keyH.rigthPress || keyH.leftPress) {
			return true;
		}
		
		return false;
	}
	
	// 시스템에 설치된 폰트 목록 출력
	public static void printFontStyle() {
		String[] fontFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		int i = 0;
        // 시스템에 설치된 폰트 목록 출력
        for (String font : fontFamilies) {
        	i++;
            System.out.print(font+", ");
            if(i%10==0) {
            	System.out.println();
            }
        }
	}
	
}
