package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	
	public GamePanel gp;
	Graphics2D g2;
	Font godic_40 = new Font("맑은 고딕", Font.PLAIN, 40);
	Font godic_80B = new Font("맑은 고딕", Font.BOLD, 80);
	//public BufferedImage keyImage;
	public String message = "";
	public Boolean messageOn = false;
	public int messageTimer = 0;
	public boolean gameFinished = false;
	private double gameTime = 0;
	private DecimalFormat dFormat = new DecimalFormat("#00.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		//Utill.printFontStyle();	// 시스템 폰트 List
				
	}
	
	public void showMassage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(godic_40);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.PLAYSTATE) {
			drawPlayState();
			
		}else if (gp.gameState == gp.PAUSESTATE) {
			drawPauseState();
			
		}
		
	}
	// 일시정지 UI
	public void drawPauseState() {
		String text = "PAUSED";
		g2.setFont(g2.getFont().deriveFont(80F));
		int x = gp.utill.getXforCenteredText(text, g2);
		int y = gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	// 플레이 UI
	public void drawPlayState() {
		
	}
	
}
