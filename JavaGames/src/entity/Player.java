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
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		x = 100;
		y = 100;
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
			y -= speed; // 좌상단 좌표 (0,0)
			direction = "up";			
		}
		if(keyH.downPress) {
			y += speed;
			direction = "down";			
		}
		if(keyH.leftPress) {
			x -= speed;
			direction = "left";
		}
		if(keyH.rigthPress) {
			x += speed;
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
		
		// 창 벗어나지 않기
//		if(x <0) {
//			x =0;
//		}else if(x > gp.screenWidth - gp.tileSize){
//			x = gp.screenWidth - gp.tileSize;
//		}
//		if(y <0) {
//			y =0;
//		}else if(y > gp.screenHeight - gp.tileSize){
//			y = gp.screenHeight - gp.tileSize;
//		}
		
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
		
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
		
		
	}
	
}
