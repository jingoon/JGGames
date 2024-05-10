package main;

import entity.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		//entity와 겹치는 타일이 좌상 우상 좌하 우하 에 있는지 체크
		
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
