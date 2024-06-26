package main;

import entity.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	// collision Object
	public int checkObject(Entity entity, boolean playerTf) {
		int index = 999;
		// entity(player)와 object의 위치가 교차하는지 체크
		// Rectangle.intersects()를 사용하기 위해 좌표를 사용하고, solidAreaDefault좌표로 초기화함.
		entity.solidArea.x += entity.worldX;
		entity.solidArea.y += entity.worldY;
		
		for(int i=0; i < gp.obj.length; i++) {
			if(gp.obj[i] == null) {
				continue;
			}
			gp.obj[i].solidArea.x += gp.obj[i].worldX;
			gp.obj[i].solidArea.y += gp.obj[i].worldY;

			// 방향에 따라 미리 감지하여 낑기지 안도록 한다.
			switch(entity.direction) {
			case "up":
				entity.solidArea.y -= entity.speed;
				break;
			case "down":
				entity.solidArea.y += entity.speed;
				break;
			case "left":
				entity.solidArea.x -= entity.speed;
				break;
			case "right":
				entity.solidArea.x += entity.speed;
				break;
			}
			
			if(gp.obj[i].solidArea.intersects(entity.solidArea)) {
				if(gp.obj[i].collision) {		// object가 겹칠수 없다면.(문과 바닥차이)
					entity.collisionOn = true;	// 플레이어의 충돌설정 
				}
				if(playerTf) {
					index = i;		// 플레이어와 충돌한 object index 반환				
				}
			}
			// object solidArea position 초기화
			gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
			gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
		}
		// entity(player) solidArea position 초기화
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		
		return index;
	}
	
	// collision NPC & PLAYER & MONSTER
	public int checkEntity(Entity entity, Entity[] taget, boolean playerTf) {
		int index = 999;
		
		entity.solidArea.x += entity.worldX;
		entity.solidArea.y += entity.worldY;
		
		index = checkCollision(entity, taget, index);

		// entity(player, NPC) solidArea position 초기화
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		
		return index;
	}
	
	public int checkEntity(Entity entity, Entity taget, boolean playerTf) {
		Entity[] oneEntity = new Entity[1]; // ex) 플레이어
		oneEntity[0] = taget;
		return checkEntity(entity, oneEntity, playerTf);
	}
	
	// collision checkCollision
	public int checkCollision(Entity entity, Entity[] taget, int index) {
		
		
		for(int i=0; i < taget.length; i++) {
			if(taget[i] == null) {
				continue;
			}
			taget[i].solidArea.x += taget[i].worldX;
			taget[i].solidArea.y += taget[i].worldY;
			
			// 방향에 따라 미리 감지하여 낑기지 안도록 한다.
			switch(entity.direction) {
			case "up":
				entity.solidArea.y -= entity.speed;
				break;
			case "down":
				entity.solidArea.y += entity.speed;
				break;
			case "left":
				entity.solidArea.x -= entity.speed;
				break;
			case "right":
				entity.solidArea.x += entity.speed;
				break;
			}
			
			if(taget[i].solidArea.intersects(entity.solidArea)) {
				entity.collisionOn = true;
				index = i;	// return  npc index
				
			}
			// object solidArea position 초기화
			taget[i].solidArea.x = taget[i].solidAreaDefaultX;
			taget[i].solidArea.y = taget[i].solidAreaDefaultY;
			
		}
		
		return index;
		
	}
	
	// collision Tiles
	public void checkTile(Entity entity) {
		
		// entity(player)와 겹치는 타일이 좌상 우상 좌하 우하 에 있는지 체크
		
		int entityLeftX = entity.worldX  + entity.solidArea.x;
		int entityRightX = entityLeftX + entity.solidArea.width;
		int entityTopY = entity.worldY + entity.solidArea.y;
		int entityBottomY = entityTopY + entity.solidArea.height;
		
		int entityLeftCol = entityLeftX / gp.tileSize;		// 좌벽
		int entityRightCol = entityRightX / gp.tileSize;	// 우벽
		int entityTopRow = entityTopY / gp.tileSize;		// 상벽
		int entityBottomRow = entityBottomY / gp.tileSize;	// 하벽
		
		int mapTileNum1, mapTileNum2;
		String direction = entity.direction;
		
		// tileCheck type 2
		int left2 = (entityLeftX - entity.speed) / gp.tileSize;		// 좌좌
		int rigth2 = (entityRightX + entity.speed) / gp.tileSize;	// 우우
		int top2 = (entityTopY - entity.speed) / gp.tileSize;		// 상상
		int bottom2 = (entityBottomY + entity.speed) / gp.tileSize;	// 하하
		
		int colNum1 = entityLeftCol;
		int colNum2 = entityRightCol;
		int rowNum1 = entityTopRow;
		int rowNum2 = entityBottomRow;
		if(direction.equals("up")) {			//좌상, 우상
			rowNum1 = top2; 				//상
			rowNum2 = top2;					//상
		}else if(direction.equals("down")) {	// 좌하, 우하
			rowNum1 = bottom2; 				//하
			rowNum2 = bottom2;				//하
		}else if(direction.equals("left")) {	// 좌상, 좌하
			colNum1 = left2;				//좌
			colNum2 = left2;				//좌
		}else if(direction.equals("right")) {	// 우상, 우하
			colNum1 = rigth2;				//우
			colNum2 = rigth2;				//우
		}
		mapTileNum1 = gp.tileM.mapTileNumber[colNum1][rowNum1];	
	    mapTileNum2 = gp.tileM.mapTileNumber[colNum2][rowNum2];
	    if(gp.tileM.tile[mapTileNum1].collision || gp.tileM.tile[mapTileNum2].collision) {
	    	entity.collisionOn = true;
	    }
		
		
		// tileCheck type 1
//		switch(direction) {
//			case("up"):		// 좌상, 우상
//				entityTopRow = (entityTopY - entity.speed) / gp.tileSize;
//				mapTileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];	
//			    mapTileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];	
//			    if(gp.tileM.tile[mapTileNum1].collision || gp.tileM.tile[mapTileNum2].collision) {
//			    	entity.collisionOn = true;
//			    }
//				break;
//			case("down"):	// 좌하, 우하
//				entityBottomRow = (entityBottomY + entity.speed) / gp.tileSize;
//				mapTileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];	
//			    mapTileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
//			    if(gp.tileM.tile[mapTileNum1].collision || gp.tileM.tile[mapTileNum2].collision) {
//			    	entity.collisionOn = true;
//			    }
//				break;
//			case("left"):	// 좌상, 좌하
//				entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
//				mapTileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];	
//			    mapTileNum2 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
//			    if(gp.tileM.tile[mapTileNum1].collision || gp.tileM.tile[mapTileNum2].collision) {
//			    	entity.collisionOn = true;
//			    }
//				break;
//			case("right"):	// 우상, 우하 
//				entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
//				mapTileNum1 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];	
//			    mapTileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
//			    if(gp.tileM.tile[mapTileNum1].collision || gp.tileM.tile[mapTileNum2].collision) {
//			    	entity.collisionOn = true;
//			    }
//				break;
//		}
		
	}
	
	

}
