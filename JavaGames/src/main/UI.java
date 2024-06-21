package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.StrokeBorder;

import entity.Entity;
import object.OBJ_Key;
import object.SuperObject;

public class UI {
	
	public GamePanel gp;
	Graphics2D g2;
	Font onglip;
	BufferedImage pcImage;
	List<BufferedImage> images;
	public int imageX = 0;
	public int imageY = 0;
	public String message = "";
	public Boolean messageOn = false;
	public int messageTimer = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int titleMenuIndex = 0;
	public String[] menuList;
	public int subTitleState = 0; // 타이틀 화면,
	public final int MENUSCREEN = 0;
	public final int CHARECTERSCREEN = 1;
	public final int PLAYSCREEN = 2;
	public final int OPTIONSCREEN = 3;
	public final int EXITSCREEN = 4;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		//Utill.printFontStyle();	// 시스템 폰트 List
		onglip = gp.utill.getFont("/font/온글잎 류뚱체.ttf");
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(onglip);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);	// 글꼴 외각선 부드럽게
		g2.setColor(Color.white);
		
		if(gp.gameState == gp.TITLESTATE) {
			drawTitleState();
		}else if(gp.gameState == gp.PLAYSTATE) {
			drawPlayState();
		}else if(gp.gameState == gp.PAUSESTATE) {
			drawPauseState();
		}else if(gp.gameState == gp.DIALOGUESTATE) {
			drawDialogueScreen();
		}
		
	}
	// 타이틀 화면 UI
	private void drawTitleState() {
		
		if(subTitleState == MENUSCREEN) {
			drawMenu();
		}else if(subTitleState == CHARECTERSCREEN) {
			
		}else if (subTitleState == OPTIONSCREEN) {
			
		}

		
	}
	// 메뉴 목록
	private void drawMenu() {
		int x, y;
		String menu = "";
		
		menu = "JG 게임";
		g2.setFont(g2.getFont().deriveFont(100F));
		x = gp.utill.getXforCenteredText(menu, g2);
		y = gp.tileSize * 3;
		// 제목 그림자
		g2.setColor(Color.gray);
		g2.drawString(menu, x+3, y+3);
		// 제목 
		g2.setColor(Color.white);
		g2.drawString(menu, x, y);
		
		menuList = new String[4];
		menuList[0] = "NEW GAME";
		menuList[1] = "PLAY";
		menuList[2] = "OPTION";
		menuList[3] = "EXIT";
		y += gp.tileSize * 2;
		int lineSpace = (int) (gp.tileSize * 1.5);
		int menuIndex =0;
		if(titleMenuIndex >= menuList.length) {
			titleMenuIndex = 0;
		}else if(titleMenuIndex < 0) {
			titleMenuIndex = menuList.length -1;
		}
		for(String menuT : menuList) {
			g2.setFont(g2.getFont().deriveFont(70F));
			x = gp.utill.getXforCenteredText(menuT, g2);
			y += lineSpace;
			g2.setColor(Color.white);
			if(menuIndex == titleMenuIndex) {
				// 메뉴 textColor
				g2.setColor(Color.yellow);
			}
			g2.drawString(menuT, x, y);
			menuIndex++;
		}
		// 케릭터 이미지
		g2.drawImage(pcImage, imageX, imageY, gp.tileSize*2, gp.tileSize*2, null);
	}
	// 플레이 UI
	private void drawPlayState() {
		
	}
	// 일시정지 UI
	private void drawPauseState() {
		String text = "PAUSED";
		g2.setFont(g2.getFont().deriveFont(100F));
		int x = gp.utill.getXforCenteredText(text, g2);
		int y = gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	// 대화 UI
	private void drawDialogueScreen() {
		
		// window
		int x, y, width, height, round;
		x = gp.tileSize*2;
		y = gp.tileSize/2;
		width = gp.screenWidth - gp.tileSize*4;
		height = gp.screenHeight/2 - gp.tileSize*2;
		round = 35;
		
		drawSubWindow(x,y,width,height,round);
		
		// 대화 Text
		Font d = g2.getFont().deriveFont(Font.PLAIN,30f);
		g2.setFont(d);
		x += gp.tileSize;
		y += gp.tileSize;
		String[] text = currentDialogue.split("\n");

		for(String line : text) {
			g2.drawString(line, x, y);
			y += gp.tileSize*0.75;				// 줄간격
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
	// 타이틀 화면 랜덤위치 이미지
	public void titleImageR() {
		imageX = gp.utill.getRandomPoint(gp.screenWidth - gp.tileSize);
		imageY = gp.utill.getRandomPoint(gp.screenHeight - gp.tileSize);
		
		int randomNum = gp.utill.getRandomPoint(100)+1;
		randomNum %= images.size();
		
		pcImage = images.get(randomNum);
	}
	
	public void setUiImage() {
		images = new ArrayList<>();
		images.add(gp.player.down1);
		images.add(gp.player.down2);
		images.add(gp.player.left1);
		images.add(gp.player.left2);
		images.add(gp.player.right1);
		images.add(gp.player.right2);
		images.add(gp.player.up1);
		images.add(gp.player.up2);
		
		for(Entity npc : gp.npc) {
			if(npc == null) {
				continue;
			}
			images.add(npc.down1);
			images.add(npc.down2);
			images.add(npc.left1);
			images.add(npc.left2);
			images.add(npc.right1);
			images.add(npc.right2);
			images.add(npc.up1);
			images.add(npc.up2);
		}
		
		for(SuperObject obj : gp.obj) {
			if(obj == null) {
				continue;
			}
			images.add(obj.image);
		}
		
	}
	
}
