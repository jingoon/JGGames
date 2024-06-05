package main;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public class Utill {
	GamePanel gp;
	
	public Utill(GamePanel gp) {
		this.gp = gp;
	}
	
	// 방향키를 눌렀을때 상태
	public boolean moveKeyPress(KeyHandler keyH) {
		
		if(keyH.upPress || keyH.downPress || keyH.rigthPress || keyH.leftPress) {
			return true;
		}
		
		return false;
	}
	
	// 시스템에 설치된 폰트 목록 출력
	public void printFontStyle() {
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
	
	// 이미지 렌더링
	public BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(originalImage, 0, 0, width, height, null);
		g2.dispose();
		return scaledImage;
	}
	
	// 
	public int getXforCenteredText(String txet, Graphics2D g2) {
		
		// 글자길이
		int lenth = (int)g2.getFontMetrics().getStringBounds(txet, g2).getWidth();
		// 화면 중앙 에서 글자길이의 반만큼 좌측, 즉 글자의 x축 중앙과 화면의 x축 중앙을 일치시킴
		int x = gp.screenWidth/2 - lenth/2; 
		return x;
	}
	
}
