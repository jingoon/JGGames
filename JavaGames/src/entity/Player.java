package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import object.SuperObject;
import skill.Blink;
import skill.SuperSkill;

public class Player extends Entity{

	KeyHandler keyH;					// 키보드 액션
	
	public final int screenX, screenY;	// 스크린에서 케릭터 위치
	public SuperSkill blink;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);		//화면 가로 가운데
		screenY = gp.screenHeight/2 - (gp.tileSize/2);		//화면 세로 가운데 
				
		// 충돌영역 크기와 위치 생성
		solidArea = new Rectangle();
		solidArea.x = 8; 	// player 기준
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
		// object 충돌감지 후 초기화 변수
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
		setHp();
		
		//test
		blink = new Blink(this);
				
	}
	
	public void setHp() {
		hp = 6;
	}
	
	public void setDefaultValues() {
		//스타팅 포인트(worldMap)
		worldX = gp.worldWidth/2 - gp.tileSize*2;		
		worldY = gp.worldHeight/2 - gp.tileSize*4;	
		//randomRegen();	
		speed = 4;
		direction = "down";
		moveChangeSpeed = 12;
		
		// 플레이어 status
		maxHp = 6;
		hp = maxHp;

	}
	
	public void getPlayerImage() {
		up1 = gp.utill.setupImage("/player/boy_up_1");
		up2 = gp.utill.setupImage("/player/boy_up_2");
		down1 = gp.utill.setupImage("/player/boy_down_1");
		down2 = gp.utill.setupImage("/player/boy_down_2");
		right1 = gp.utill.setupImage("/player/boy_right_1");
		right2 = gp.utill.setupImage("/player/boy_right_2");
		left1 = gp.utill.setupImage("/player/boy_left_1");
		left2 = gp.utill.setupImage("/player/boy_left_2");
				
	}
	
	public void update() {
		blink.delay++;
		if(keyH.blinkPress) {
			blink.start();
		}
		
		// movekey를 눌러야지만 움직임. 
		if(gp.utill.moveKeyPress(keyH)) {
		
			// keyPress 효과(행동)
			if(keyH.upPress) {	
				direction = "up";			
			}else if(keyH.downPress) {
				direction = "down";			
			}else if(keyH.leftPress) {
				direction = "left";
			}else if(keyH.rigthPress) {
				direction = "right";
			}
			
			// CHECK TILE COLLISION 타일 충돌감지
			collisionOn = false; 		// 초기화용
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION object 충돌감지
			int objectIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objectIndex);
			
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc, true);
			interactNPC(npcIndex);
			
			// IF COLLISION IS FALSE. PLAYER CAN MOVE
			moveToCollision();
		
		}
				
	}
	
	// object collision effect 오브젝트 충돌 효과 
	public void pickUpObject(int i) {
		
		if(i != 999) {

		}
	}
	// npc와의 충돌 효과
	public void interactNPC(int i) {
		if(i != 999) {
			gp.npc[i].stopNPC();
			if(gp.keyH.nextPress) {
				gp.gameState = gp.DIALOGUESTATE;
				gp.npc[i].speak();
			}
		}
		//gp.keyH.nextPress = false;
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		
		switch (direction) {
		case "up":
			if(imageNumber==1) {
				image = up1;
			}else {
				image = up2;
			}
			break;
		case "down":
			if(imageNumber==1) {
				image = down1;
			}else {
				image = down2;
			}
			break;
		case "right":
			if(imageNumber==1) {
				image = right1;
			}else {
				image = right2;
			}
			break;
		case "left":
			if(imageNumber==1) {
				image = left1;
			}else {
				image = left2;
			}
			break;
		}
		
		g2.drawImage(image, screenX, screenY, null);
		
	}
	
	// pickUpObject() 호출 시 해당 아이템이 사라지고 새로운 아이템 생성
	private void createItem(int index, SuperObject item) {
		int x = gp.obj[index].worldX;
		int y = gp.obj[index].worldY;
		gp.obj[index] = null;
		gp.obj[index] = item;
		gp.obj[index].update(x+gp.tileSize, y+gp.tileSize);
	}
	
}
