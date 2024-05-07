package entity;

import java.awt.Graphics2D;
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
		screenY = gp.screenHeight/2 - (gp.tileSize/2);	//화면 세로 가운데 
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.worldWidth/2;	//world map 가로 가운데
		worldY = gp.worldHeight/2;	//world map 세로 가운데 
		
		speed = 4;
		direction = "up";
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
		// 상하좌우 이동
		if(keyH.upPress) {
			worldY -= speed; // 좌상단 좌표 (0,0)
			direction = "up";			
		}
		if(keyH.downPress) {
			worldY += speed;
			direction = "down";			
		}
		if(keyH.leftPress) {
			worldX -= speed;
			direction = "left";
		}
		if(keyH.rigthPress) {
			worldX += speed;
			direction = "right";
		}
		
		
		// img 변수
		if(Utill.moveKeyPress(keyH)) {
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
