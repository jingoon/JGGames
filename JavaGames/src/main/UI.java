package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.border.StrokeBorder;

import object.OBJ_Key;

public class UI {
	
	public GamePanel gp;
	Graphics2D g2;
	Font godic_40 = new Font("맑은 고딕", Font.PLAIN, 40);		// 프레임 마다 만들기때문에 필드에서 한번 만들고 계속 사용.
	Font godic_80B = new Font("맑은 고딕", Font.BOLD, 80);
	public String message = "";
	public Boolean messageOn = false;
	public int messageTimer = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	
	public UI(GamePanel gp) {
		this.gp = gp;
		//Utill.printFontStyle();	// 시스템 폰트 List
				
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(godic_40);
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.PLAYSTATE) {
			drawPlayState();
			
		}else if(gp.gameState == gp.PAUSESTATE) {
			drawPauseState();
			
		}else if(gp.gameState == gp.DIALOGUESTATE) {
			drawDialogueScreen();
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
	// 대화 UI
	public void drawDialogueScreen() {
		
		// window
		int x, y, width, height, round;
		x = gp.tileSize*2;
		y = gp.tileSize/2;
		width = gp.screenWidth - gp.tileSize*4;
		height = gp.screenHeight/2 - gp.tileSize*2;
		round = 35;
		
		drawSubWindow(x,y,width,height,round);
		
		// 대화 Text
		Font d = g2.getFont().deriveFont(Font.PLAIN,20f);
		g2.setFont(d);
		x += gp.tileSize;
		y += gp.tileSize;
		String[] text = currentDialogue.split("\n");

		for(String line : text) {
			g2.drawString(line, x, y);
			y += gp.tileSize/2;				// 줄간격
		}
		
		
	}
	
	// 보조 창 그리기
	private void drawSubWindow(int x, int y, int width, int height, int round) {
		// 둥근 사각형
		g2.setColor(new Color(0, 0, 0, 200));	// black & alpha
		g2.fillRoundRect(x, y, width, height, round, round);
		// 테두리
		g2.setColor(new Color(255, 255, 255));	// white
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, round-10, round-10);
	}
	

	
}
