package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Utill;
import object.OBJ_Boots;
import object.SupperObject;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	public int hasKey = 0;
	
	public final int screenX, screenY;	// 스크린에서 케릭터 위치
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
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
				
	}
	
	public void setDefaultValues() {
		//스타팅 포인트(worldMap)
		worldX = gp.worldWidth/2 - gp.tileSize*2;		
		worldY = gp.worldHeight/2 - gp.tileSize*4;	
				
		speed = 4;
		direction = "down";
		moveChangeSpeed = 12;

	}
	
	public void getPlayerImage() {
		up1 = setup("boy_up_1");
		up2 = setup("boy_up_2");
		down1 = setup("boy_down_1");
		down2 = setup("boy_down_2");
		right1 = setup("boy_right_1");
		right2 = setup("boy_right_2");
		left1 = setup("boy_left_1");
		left2 = setup("boy_left_2");
				
	}
	
	public BufferedImage setup(String pathName) {
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/"+pathName+".png"));
			image = gp.utill.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	public void update() {
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
			collisionOn = false; 		//안쓰면 낑김.
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION object 충돌감지
			int objectIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objectIndex);
			
			// IF COLLISION IS FALSE. PLAYER CAN MOVE
			if(!collisionOn) {
				// 이동
				switch(direction) {
					case("up"):  	worldY -= speed;	break;
					case("down"): 	worldY += speed;	break;
					case("left"): 	worldX -= speed;	break;
					case("right"): 	worldX += speed;	break;
				}
				
			}
			
			// 객체 keyPress IMG변화
			spriterCount++;
			if(spriterCount>=moveChangeSpeed) {
				if(imageNumber == 1) {
					imageNumber = 2;
				}else if(imageNumber == 2) {
					imageNumber = 1;
				}
				spriterCount = 0;
			}
		
		}
				
	}
	
	// object collision effect 오브젝트 충돌 효과 
	public void pickUpObject(int i) {
		
		if(i != 999) {
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "Key":
				hasKey++;
				gp.playSE(gp.soundEffect.soundIndexList.get("coin"));
				gp.obj[i] = null;
				gp.ui.showMassage("열쇠를 획득하였습니다.");
				break;
			case "Boots":
				gp.obj[i] = null;
				speed += 2;
				gp.playSE(2);
				gp.ui.showMassage("속도 업!");
				break;
			case "Door":
				if(hasKey>0) {
					gp.playSE(gp.soundEffect.soundIndexList.get("unlock"));
					gp.obj[i] = null;
					hasKey--;
				}else {
					gp.ui.showMassage("열쇠가 없습니다.");
					if(gp.ui.messageTimer>60 && gp.ui.messageTimer<120 ) {
						gp.ui.showMassage("열쇠를 찾으세요!");
					}
				}
				break;
			case "Chest":
				gp.playSE(4);					// 종료 사운드
				createItem(i, new OBJ_Boots(gp));
				gp.ui.gameFinished = true;		// 종료 UI
				gp.stopMusic(); 				// BGM 종료
				break;
				
			}
		}
	}
	
	// pickUpObject() 호출 시 해당 아이템이 사라지고 새로운 아이템 생성
	private void createItem(int index, SupperObject item) {
		int x = gp.obj[index].worldX;
		int y = gp.obj[index].worldY;
		gp.obj[index] = null;
		gp.obj[index] = item;
		gp.obj[index].update(x+gp.tileSize, y+gp.tileSize);
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
	
}
