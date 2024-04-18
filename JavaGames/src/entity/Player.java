package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

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

	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/down_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/right_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/left_2.png"));
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
		
		// 창 벗어나지 않기
		if(x <0) {
			x =0;
		}else if(x > gp.screenWidth - gp.tileSize){
			x = gp.screenWidth - gp.tileSize;
		}
		if(y <0) {
			y =0;
		}else if(y > gp.screenHeight - gp.tileSize){
			y = gp.screenHeight - gp.tileSize;
		}
		
		// img 변수
		spriterCount++;
		if(spriterCount>12) {
			if(imageNumber == 1) {
				imageNumber = 2;
			}else if(imageNumber == 2) {
				imageNumber = 1;
			}
			spriterCount = 0;
		}
		
		
	}
	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
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
