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
import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

public class UI {
	
	public GamePanel gp;
	Graphics2D g2;
	Font onglip;
	// 렌덤 이미지 
	BufferedImage allImage;
	List<BufferedImage> images;
	public int imageX = 0;
	public int imageY = 0;
	// 화면 메시지 출력
	public String message = "";
	public Boolean messageOn = false;
	public int messageTimer = 0;
	public boolean gameFinished = false;
	// 엔피씨 대화
	public String currentDialogue = "";
	// 타이틀 UI
	public int subTitleState = 0; 	// 타이틀 화면
	public int titleMenuIndex = 0;	// 메뉴이동
	public String[] menuList;		// 메뉴 
	// 서브 타이틀 UI 상수
	public final int MENUSCREEN = 0;		// 타이틀 UI
	public final int CHARACTERSCREEN = 1;	// 새게임
	public final int PLAYSCREEN = 2;		// 플레이
	public final int OPTIONSCREEN = 3;		// 옵션
	public final int EXITSCREEN = 4;		// 종료
	// 플레이어 status
	private BufferedImage heart_full, heart_half, heart_blank;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		//Utill.printFontStyle();	// 시스템 폰트 List
		onglip = gp.utill.getFont("/font/온글잎 류뚱체.ttf");
		setheart();			// 하트 이미지
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
		
		if(gp.gameState == gp.PLAYSTATE) {
			// 플레이 UI
			drawPlayerHp();								// 플레이어 HP
		}else if(gp.gameState == gp.TITLESTATE) {
			drawTitleScreen();							// 타이틀 화면 UI
		}else if(gp.gameState == gp.PAUSESTATE) {
			drawPlayerHp();								// 플레이어 HP
			drawPauseScreen();							// 일시정지 UI
		}else if(gp.gameState == gp.DIALOGUESTATE) {
			drawPlayerHp();								// 플레이어 HP
			drawDialogueScreen();						// 대화 UI
		}
		
	}
	// hp 하트 이미지 세팅
	private void setheart() {
		SuperObject heart = new OBJ_Heart(gp);
		heart_blank = heart.image;
		heart_half = heart.image2;
		heart_full = heart.image3;
	}
	// 플레이어 hp
	private void drawPlayerHp() {
		int hp = gp.player.hp;
		int maxHp = gp.player.maxHp;
		int fullHp = hp/2;
		int halfHp = hp%2;
		
		int x, y;
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		
		// 빈하트
		for(int i=0; i<maxHp/2;i++) {
			if(i<fullHp) {
				// 풀하트 
				g2.drawImage(heart_full, x + (i*gp.tileSize), y, null);
			}else if(i<fullHp+halfHp) {
				// 하프하트
				g2.drawImage(heart_half, x + (i*gp.tileSize), y, null);
			}else {
				// 빈하트
				g2.drawImage(heart_blank, x + (i*gp.tileSize), y, null);
			}
		}
		
	}
	// 타이틀 화면 UI
	private void drawTitleScreen() {
		
		if(subTitleState == MENUSCREEN) {
			drawMenu();
		}else if(subTitleState == CHARACTERSCREEN) {
			drawClass();
		}else if (subTitleState == OPTIONSCREEN) {
			drawOption();
		}
		
	}
	// 옵션 목록
	private void drawOption() {
		int x, y;
		String menu = "";
		
		menu = "옵션";
		g2.setFont(g2.getFont().deriveFont(70F));
		x = gp.utill.getXforCenteredText(menu, g2);
		y = gp.tileSize * 2;
		// 제목 그림자
		g2.setColor(Color.gray);
		g2.drawString(menu, x+3, y+3);
		// 제목 
		g2.setColor(Color.white);
		g2.drawString(menu, x, y);
		
		menuList = new String[7];
		menuList[0] = "[ESC] : 일시정지, 취소";
		menuList[1] = "[SPACE] : 선택, 대화, 다음";
		menuList[2] = "[m] : 배경음악 on/off";
		menuList[3] = "[w] : 위로";
		menuList[4] = "[s] : 아래로";
		menuList[5] = "[F1] : 텔레포트";
		menuList[6] = "뒤로가기";
		y += gp.tileSize * 1;
		int lineSpace = (int) (gp.tileSize * 1.2);
		int menuIndex = 0;
		if(titleMenuIndex >= menuList.length) {
			titleMenuIndex = 0;
		}else if(titleMenuIndex < 0) {
			titleMenuIndex = menuList.length -1;
		}
		for(String menuT : menuList) {
			g2.setFont(g2.getFont().deriveFont(50F));
			x = gp.tileSize * 2;
			y += lineSpace;
			g2.setColor(Color.white);
			if(menuIndex == menuList.length -1) {
				// 마지막 메뉴 위치
				x =gp.screenWidth - (gp.tileSize * 4);
				g2.setColor(Color.yellow);
			}
			g2.drawString(menuT, x, y);
			menuIndex++;
		}
		// 랜덤 이미지
		g2.drawImage(allImage, imageX, imageY, null);
	}
	// 케릭터 목록
	private void drawClass() {
		int x, y;
		String menu = "";
		
		menu = "직업을 선택하세요!";
		g2.setFont(g2.getFont().deriveFont(70F));
		x = gp.utill.getXforCenteredText(menu, g2);
		y = gp.tileSize * 3;
		// 제목 그림자
		g2.setColor(Color.gray);
		g2.drawString(menu, x+3, y+3);
		// 제목 
		g2.setColor(Color.white);
		g2.drawString(menu, x, y);
		
		menuList = new String[4];
		menuList[0] = "전사";
		menuList[1] = "궁수";
		menuList[2] = "마법사";
		menuList[3] = "뒤로가기";
		y += gp.tileSize * 2;
		int lineSpace = (int) (gp.tileSize * 1.2);
		int menuIndex =0;
		if(titleMenuIndex >= menuList.length) {
			titleMenuIndex = 0;
		}else if(titleMenuIndex < 0) {
			titleMenuIndex = menuList.length -1;
		}
		for(String menuT : menuList) {
			g2.setFont(g2.getFont().deriveFont(50F));
			x = gp.utill.getXforCenteredText(menuT, g2);
			y += lineSpace;
			if(menuIndex==3) {
				y += lineSpace;
			}
			g2.setColor(Color.white);
			if(menuIndex == titleMenuIndex) {
				// 메뉴 textColor
				g2.setColor(Color.yellow);
			}
			g2.drawString(menuT, x, y);
			menuIndex++;
		}
		// 랜덤 이미지
		g2.drawImage(allImage, imageX, imageY, gp.tileSize*2, gp.tileSize*2, null);
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
		menuList[0] = "새게임";
		menuList[1] = "플레이";
		menuList[2] = "옵션";
		menuList[3] = "종료";
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
		// 랜덤 이미지
		g2.drawImage(allImage, imageX, imageY, gp.tileSize*2, gp.tileSize*2, null);
	}
	// 일시정지 UI
	private void drawPauseScreen() {
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
		
		allImage = images.get(randomNum);
	}
	// all 이미지 세팅, 타이틀 화면에 사용.
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
