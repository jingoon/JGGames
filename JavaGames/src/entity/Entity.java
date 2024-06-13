package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public abstract class Entity {
	
	public GamePanel gp;
	public String name;
	
	public int worldX, worldY;	// worldMap에서 객체 좌상단 좌표.
	public int speed;		// 이동거리 : 높을 수록 멀리감
	public int actionSpeed;	// 행동속도, update 속도 
	
	public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2; // img
	public String direction; // 상하좌우
	
	// 케릭터 움직임 이미지
	public int spriterCount = 0;	// img 변환 카운팅변수
	public int imageNumber = 1; 	// img 변환 종류변수
	// move image 변환 속도 
	// moveChangeSpeed가 60이면 초당 1번 변환(FPS=60 일때)
	// moveChangeSpeed * 초당변환횟수 = FPS
	public int moveChangeSpeed;	
	// 행동 딜레이
	public int delay = 0;
	
	// 충돌세팅
	public boolean collisionOn = false;
	public Rectangle solidArea;	
	public int solidAreaDefaultX, solidAreaDefaultY; 
	
	public Entity(GamePanel gp) {
		this.gp = gp;
		direction = "down";
		solidArea = new Rectangle(gp.tileSize, gp.tileSize);
	}
	
	// 이미지 셋업
	public BufferedImage setupImage(String pathName) {
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(pathName+".png"));
			image = gp.utill.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	// 기본행동
	public void setAction() {}; 
	
	// 프레임당 체크할 것들.
	public void update() {
		
		// 기본행동 설정
		setAction();
		
		// 타일 충돌감지
		collisionOn = false; 		//	초기화용
		gp.cChecker.checkTile(this);
		// object 충돌감지
		gp.cChecker.checkObject(this, false);
		// 플레이어와 충돌 설정
		gp.cChecker.checkEntity(this, gp.player, false); // player index is 0
		
		// 충돌하면 움직이지 않는다.
		moveToCollision();
	
	}
	
	// 충돌과 이동, 이미지 변화
	protected void moveToCollision() {
		if(!collisionOn) {
			// 이동
			switch(direction) {
				case("up"):  	worldY -= speed;	break;
				case("down"): 	worldY += speed;	break;
				case("left"): 	worldX -= speed;	break;
				case("right"): 	worldX += speed;	break;
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
	
	// 랜덤 위치 
	public void randomRegen() {
		int x = gp.utill.getRandomPoint(gp.worldWidth - gp.tileSize);
		int y = gp.utill.getRandomPoint(gp.worldHeight - gp.tileSize);
		boolean tileCollision = false;
		x /= gp.tileSize;
		y /= gp.tileSize;
		int tileN = gp.tileM.mapTileNumber[x][y];
		tileCollision = gp.tileM.tile[tileN].collision;
		if(tileCollision) {
			randomRegen();
		}else {
			worldX = x *gp.tileSize;
			worldY = y *gp.tileSize;
		}
	}

	// 그리기
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
		int screenX = worldX - gp.player.worldX + gp.player.screenX;	 
		int screenY = worldY - gp.player.worldY + gp.player.screenY;	
		
		// 16x12 (카메라영역 그리기)
		int screenLeft 	= gp.player.worldX - gp.player.screenX - (gp.tileSize *1);
		int screenRight = gp.player.worldX + gp.player.screenX + (gp.tileSize *1);
		int screenTop 	= gp.player.worldY - gp.player.screenY - (gp.tileSize *1);
		int screenBottom= gp.player.worldY + gp.player.screenY + (gp.tileSize *1);
		
		if(worldX > screenLeft && worldX < screenRight 
				&& worldY < screenBottom && worldY > screenTop) {
			
			g2.drawImage(image, screenX, screenY, null);
				
		}
		
	}

}
