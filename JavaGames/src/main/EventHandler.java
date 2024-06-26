package main;

import java.awt.Rectangle;

public class EventHandler {
	
	GamePanel gp;
	// 충돌영역, 이벤트 위치
	Rectangle eventRact;
	int eventRactDefaultX, eventRactDefaultY;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		setRactangle();
	}
	
	// 충돌영역 세팅
	private void setRactangle() {
		eventRact = new Rectangle();
		eventRact.width = 2;
		eventRact.height = 2;
		eventRact.x = (gp.tileSize/2 - eventRact.width/2); 	// 타일 중앙
		eventRact.y = (gp.tileSize/2 - eventRact.height/2);
		eventRactDefaultX = eventRact.x;
		eventRactDefaultY = eventRact.y;
	}
	
	public void checkEvent(){
		 boolean damageTile = hit(27, 16, "right");
		 if(damageTile) {
			 tileDamage(gp.DIALOGUESTATE);
		 }
		
	}
	
	private boolean hit(int col, int row, String reqDirection) {
		
		boolean hit = false;
		gp.player.solidArea.x += gp.player.worldX;
		gp.player.solidArea.y += gp.player.worldY;
		eventRact.x += col * gp.tileSize;
		eventRact.y += row * gp.tileSize;
		
		if(eventRact.intersects(gp.player.solidArea)) {
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("all") ) {
				hit = true;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRact.x = eventRactDefaultX;
		eventRact.y = eventRactDefaultY;
		
		return hit;
	}
	
	private void tileDamage(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "가시덩굴에 렸습니다.";
		gp.player.hp --;
	}

}
