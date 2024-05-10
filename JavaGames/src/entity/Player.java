package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.Utill;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX, screenY;	// 스크린에서 케릭터 위치
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);		//화면 가로 가운데
		screenY = gp.screenHeight/2 - (gp.tileSize/2);		//화면 세로 가운데 
				
		// 충돌영역 크기와 위치 생성
		solidArea = new Rectangle();
		solidArea.x = 8; 	// screen 기준
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
				
	}
	
	public void setDefaultValues() {
		//스타팅 포인트(worldMap)
		worldX = gp.worldWidth/2 - gp.tileSize*2;		
		worldY = gp.worldHeight/2 - gp.tileSize*4;	
				
		speed = 4;
		direction = "down";
		imageChangeSpeed = 12;

	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		// movekey를 눌러야지만 움직임. 
		if(Utill.moveKeyPress(keyH)) {
		
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
			
			
			// 충돌감지
			collisionOn = false; //안써도 될듯한데 TEST
			gp.cChecker.checkTile(this);
			
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
			if(spriterCount>=imageChangeSpeed) {
				if(imageNumber == 1) {
					imageNumber = 2;
				}else if(imageNumber == 2) {
					imageNumber = 1;
				}
				spriterCount = 0;
			}
		
		}
				
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
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
		
	}
	
}
