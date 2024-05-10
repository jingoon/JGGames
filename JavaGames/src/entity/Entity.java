package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	
	public int worldX, worldY;	// worldMap에서 객체 좌상단 좌표.
	public int speed;	// 이동거리
	
	public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2; // img
	public String direction; // 상하좌우
	
	// 케릭터 움직임 이미지
	public int spriterCount = 0;	// img 변환 카운팅변수
	public int imageNumber = 1; 	// img 변환 종류변수
	// move image 변환 속도 
	// imageChangeSpeed가 60이면 초당 1번 변환(FPS=60 일때)
	// imageChangeSpeed * 초당변환횟수 = FPS
	public int imageChangeSpeed;	
	
	// 충돌세팅
	public boolean collisionOn = false;
	public Rectangle solidArea;					
}
