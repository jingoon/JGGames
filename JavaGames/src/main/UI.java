package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	
	public GamePanel gp;
	Font godic_40 = new Font("맑은 고딕", Font.PLAIN, 40);
	Font godic_80B = new Font("맑은 고딕", Font.BOLD, 80);
	public BufferedImage keyImage;
	public String message = "";
	public Boolean messageOn = false;
	public int messageTimer = 0;
	public boolean gameFinished = false;
	private double gameTime = 0;
	private DecimalFormat dFormat = new DecimalFormat("#00.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
		
		//Utill.printFontStyle();	// 시스템 폰트 List
				
	}
	
	public void showMassage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		
		if(gameFinished) {
			
			String text;
			int x, y, length;
			
			// GAME FINISHED
			text = "GAME FINISHED !!";
			g2.setFont(godic_40);
			g2.setColor(Color.white);
			length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - length/2;
			y = gp.screenHeight/2 - gp.tileSize*3;		
			g2.drawString(text, x,y);

			// success time
			text = "Your Time is : "+ dFormat.format(gameTime)+"!";
			length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - length/2;
			y = gp.screenHeight/2 + gp.tileSize*4;
			g2.drawString(text, x,y);

			// Congratulation!
			g2.setFont(godic_80B);
			g2.setColor(Color.yellow);
			text = "Congratulation!";
			length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - length/2;
			y = gp.screenHeight/2 + gp.tileSize*2;
			g2.drawString(text, x,y);
			
			
			gp.gameThread =null;
		}else {
			
			// have key
			g2.setFont(godic_40);
			g2.setColor(Color.white);
			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, null);
			g2.drawString(" = "+gp.player.hasKey, gp.tileSize+(gp.tileSize/2), gp.tileSize+(gp.tileSize/3));
			
			// game time
			gameTime += (double)1/gp.FPS;
			g2.drawString("Time: "+dFormat.format(gameTime), gp.screenWidth - gp.tileSize*5, gp.tileSize*2);
			
			// pickup message
			if(messageOn) {
				messageTimer++;
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.setColor(Color.PINK);
				g2.drawString(message, gp.tileSize/2, gp.tileSize*5);			
				
				if(messageTimer > gp.FPS * 2) {	//2초
					messageTimer = 0;
					messageOn = false;
				}
			}
		}
		
		
	}
	
}
