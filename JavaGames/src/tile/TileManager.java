package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile [] tile;	// 바닥, 물, 벽, 나무 등등 타일 종류
	public int mapTileNumber[][];	// 맵타일 배열, 메모장 읽기 
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		tile = new Tile[10];	// 타일번호
		mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];	// 지도타일 배열
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	// mapTileNumber[][] 세팅
	public void loadMap(String mapPath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(mapPath);
			Reader r = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(r);
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				String number[] = line.split(" ");
				
				while(col < gp.maxWorldCol) {
					int num = Integer.parseInt(number[col]);
					mapTileNumber[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					row++;
					col=0;
				}
			}
			
			br.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
		
		
	}
	
	public void getTileImage() {

		setup(0, "grass", "풀", false);
		setup(1, "wall", "벽", true);
		setup(2, "water", "물", true);
		setup(3, "earth", "땅", false);
		setup(4, "tree", "나무", true);
		setup(5, "sand", "모래", false);
		
		
//		try {
//		
//			tile[0] = new Tile();	// 풀
//			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
//			
//			tile[0].image = gp.utill.scaleImage(tile[0].image, gp.tileSize, gp.tileSize);
//			
//			tile[1] = new Tile();	// 벽
//			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
//			tile[1].collision = true;	// 못지나감.
//			
//			tile[2] = new Tile();	// 물
//			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
//			tile[2].collision = true;	// 못지나감.
//			
//			tile[3] = new Tile();	// 땅
//			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
//			
//			tile[4] = new Tile();	// 나무
//			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
//			tile[4].collision = true;	// 못지나감.
//			
//			tile[5] = new Tile();	// 모래
//			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	public void setup(int index, String pathName, String kName, boolean collision) {

		try {
			tile[index] = new Tile();
			tile[index].name = kName; 
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+pathName+".png"));
			tile[index].image = gp.utill.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision; 
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void draw(Graphics2D g2) {
		
		// 그리기
//		g2.drawImage(tile[0].image, gp.tileSize*0, 0, gp.tileSize, gp.tileSize, null);
//		g2.drawImage(tile[1].image, gp.tileSize*1, 0, gp.tileSize, gp.tileSize, null);
//		g2.drawImage(tile[2].image, gp.tileSize*2, 0, gp.tileSize, gp.tileSize, null);
		
		// 자동 그리기 
		//mapTileNumber[][]
		int worldCol = 0;
		int worldRow = 0;
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNumber[worldCol][worldRow];
			int worldX = gp.tileSize * worldCol ; // tileSize 단위로 움직임
			int worldY = gp.tileSize * worldRow ;

			// 스타팅포인트와 스크링중앙 이라는 알수 있는 케릭터 좌표를 이용하여 월드맵 좌표를 찾는다. 
			int screenX = worldX - gp.player.worldX + gp.player.screenX;	 
			int screenY = worldY - gp.player.worldY + gp.player.screenY;	
			 
			
			// 50X50 전체 그리기
			//g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			
			// 16x12 (카메라영역 그리기)
			int screenLeft 	= gp.player.worldX - gp.player.screenX - (gp.tileSize *1);
			int screenRight = gp.player.worldX + gp.player.screenX + (gp.tileSize *1);
			int screenTop 	= gp.player.worldY - gp.player.screenY - (gp.tileSize *1);
			int screenBottom= gp.player.worldY + gp.player.screenY + (gp.tileSize *1);
			
			if(worldX > screenLeft && worldX < screenRight 
					&& worldY < screenBottom && worldY > screenTop) {
				
//				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
					
			}
			
			worldCol++;
			if(gp.maxWorldCol == worldCol) {
				worldCol=0;
				worldRow++;
			}
			
		}
		
		
	}

}
