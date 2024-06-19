package entity;

import main.GamePanel;

public class NPC_OldMan extends Entity{
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		speed = 1;
		direction = "down";
		moveChangeSpeed = 12;
		getImage();
		setDialogue();
		
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
	
	public void setDialogue() {
		dialogues[0] = " 영원한 섬에 도착을 환영하네!";
		dialogues[1] = " 일단 섬을 한번 둘러볼 기회를 주도록 하지";
		dialogues[2] = " 이름따윈 상관없어 어차피 너는 앞으로 쭈욱 계속 \n같은상태를 유지 할 거야";
		dialogues[3] = " 사람들을 찾고 싶은가? 텔레포트 [F1]는 영원한 \n섬 에서만 사용할 수 있는 특별한 능력이지!";
		dialogues[4] = " 오늘처럼 맑은 날에 사람들에게 말을 걸면 특별한 \n능력을 얻을 수 있을꺼야!";
		dialogues[5] = " [ESC]키로 일시정지 시킬 수 있네";
		
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
	
	public void speak() {
		// 아이템 소유시 케릭터별 대화 추가 가능.
		super.speak();
	}
	

}
