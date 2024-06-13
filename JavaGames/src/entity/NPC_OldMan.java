package entity;

import main.GamePanel;

public class NPC_OldMan extends Entity{
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		speed = 1;
		direction = "down";
		moveChangeSpeed = 12;
		getImage();
		
	}
	
	public void getImage() {
		up1 = setupImage("/npc/oldman_up_1");
		up2 = setupImage("/npc/oldman_up_2");
		down1 = setupImage("/npc/oldman_down_1");
		down2 = setupImage("/npc/oldman_down_2");
		right1 = setupImage("/npc/oldman_right_1");
		right2 = setupImage("/npc/oldman_right_2");
		left1 = setupImage("/npc/oldman_left_1");
		left2 = setupImage("/npc/oldman_left_2");
	}
	
	public void setAction() {
		actionSpeed++;
		if(actionSpeed == 120) {
			int randomNum = gp.utill.getRandomPoint(100);
			randomNum = (randomNum+1) % 4;
			// 방향전환 & 이동
			if(randomNum == 0) {	
				direction = "up";			
			}else if(randomNum == 1) {
				direction = "down";			
			}else if(randomNum == 2) {
				direction = "left";
			}else if(randomNum == 3) {
				direction = "right";
			}
			actionSpeed =0;
		}
	}
	
}
