package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

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
	
	// 폰트 파일 load
	public Font getFont(String path) {
		
		Font resultFont = null;
		try {
			InputStream is = getClass().getResourceAsStream(path);
			resultFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return resultFont;
		
	}
	
	// local 시스템에 설치된 폰트 목록 출력
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
	// 글자길이
	public int getTextLenth(String text, Graphics2D g2) {
		return (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	}
	// 텍스트 가운데 맞춤
	public int getXforCenteredText(String text, Graphics2D g2) {
		int lenth = getTextLenth(text, g2);
		// 화면 중앙 에서 글자길이의 반만큼 좌측, 즉 글자의 x축 중앙과 화면의 x축 중앙을 일치시킴
		int x = gp.screenWidth/2 - lenth/2; 
		return x;
	}
	
	// 지도 랜덤위치 x
	public int getRandomPoint(int maximum) {
		Random r = new Random();
		int x = r.nextInt(maximum);
		return x;
	}
}
