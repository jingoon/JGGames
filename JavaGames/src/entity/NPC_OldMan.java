package entity;

import main.GamePanel;

public class NPC_OldMan extends Entity{
	
	int move;
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		setDefaultValues();
		getImage();
		
	}
	
	public void setDefaultValues() {
		//스타팅 포인트(worldMap)
		worldX = gp.worldWidth/2 - gp.tileSize*3;		
		worldY = gp.worldHeight/2 - gp.tileSize*4;
		//worldX = 0;		
		//worldY = 0;
		
		//randomRegen();
				
		speed = 2;
		direction = "down";
		moveChangeSpeed = 30;

	}
	
	public void getImage() {
		up1 = setup("/npc/oldman_up_1");
		up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_down_1");
		down2 = setup("/npc/oldman_down_2");
		right1 = setup("/npc/oldman_right_1");
		right2 = setup("/npc/oldman_right_2");
		left1 = setup("/npc/oldman_left_1");
		left2 = setup("/npc/oldman_left_2");
	}
	
	public void update() {
		// random으로 방향 정함.
		move++;
		if(move == 2) {
			int randomNum = gp.utill.getRandomPoint(100);
			randomNum = (randomNum+1) % 50;
			move =0;
			// keyPress 효과(행동)
			if(randomNum == 0) {	
				direction = "up";			
			}else if(randomNum == 1) {
				direction = "down";			
			}else if(randomNum == 2) {
				direction = "left";
			}else if(randomNum == 3) {
				direction = "right";
			}
			
			
			// CHECK TILE COLLISION 타일 충돌감지
			collisionOn = false; 		//	초기화용
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION object 충돌감지
			int objectIndex = gp.cChecker.checkObject(this, false);
			// 플레이어나 타 엔피씨와 충돌 설정
			int playerIndex = gp.cChecker.checkNPC(this, false);
			
			// IF COLLISION IS FALSE. NPC CAN MOVE
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
		
				
	}
	
}
