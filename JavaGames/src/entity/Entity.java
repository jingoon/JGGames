package entity;

import java.awt.image.BufferedImage;

public class Entity {
	
	public int x, y;	// 생략은 default: 같은패키지 접근
	public int speed;	// 이동거리
	public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2; // img
	public String direction; // 상하좌우
	public int spriterCount = 0;
	public int imageNumber = 1; 
	
}
