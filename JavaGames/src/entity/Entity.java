package entity;

import java.awt.image.BufferedImage;

public class Entity {
	
	public int worldX, worldY;	// 생략은 default: 같은패키지 접근
	public int speed;	// 이동거리
	
	public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2; // img
	public String direction; // 상하좌우
	
	public int spriterCount = 0;	// img 변환 카운팅변수
	public int imageNumber = 1; 	// img 변환 종류변수

	// move image 변환 속도 
	// imageChangeSpeed가 60이면 초당 1번 변환(FPS=60 일때)
	// imageChangeSpeed * 초당변환횟수 = FPS
	public int imageChangeSpeed;	
								
}
