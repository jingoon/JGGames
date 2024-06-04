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
		tile = new Tile[50];	// 타일번호
		mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];	// 지도타일 배열
		getTileImage();
		loadMap("/maps/worldV2.txt");
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
		// -> 사용안하는 타일
		setup(0, "grass00", "xx", true);
		setup(1, "grass00", "xx", true);
		setup(2, "grass00", "xx", true);
		setup(3, "grass00", "xx", true);
		setup(4, "grass00", "xx", true);
		setup(5, "grass00", "xx", true);
		setup(6, "grass00", "xx", true);
		setup(7, "grass00", "xx", true);
		setup(8, "grass00", "xx", true);
		setup(9, "grass00", "xx", true);
		// <- 사용안함
		
			
		setup(10, "grass00", "풀", false);
		setup(11, "grass01", "풀", false);
		setup(12, "water00", "물", true);  
		setup(13, "water01", "물", true);  
		setup(14, "water02", "물", true);  
		setup(15, "water03", "물", true);  
		setup(16, "water04", "물", true);  
		setup(17, "water05", "물", true);  
		setup(18, "water06", "물", true);  
		setup(19, "water07", "물", true);  
		setup(20, "water08", "물", true);  
		setup(21, "water09", "물", true);  
		setup(22, "water10", "물", true);  
		setup(23, "water11", "물", true);  
		setup(24, "water12", "물", true);  
		setup(25, "water13", "물", true);  
		setup(26, "road00", "길", false);
		setup(27, "road01", "길", false);
		setup(28, "road02", "길", false);
		setup(29, "road03", "길", false);
		setup(30, "road04", "길", false);
		setup(31, "road05", "길", false);
		setup(32, "road06", "길", false);
		setup(33, "road07", "길", false);
		setup(34, "road08", "길", false);
		setup(35, "road09", "길", false);
		setup(36, "road10", "길", false);
		setup(37, "road11", "길", false);
		setup(38, "road12", "길", false);
		setup(39, "earth", "땅", false);
		setup(40, "wall", "벽", true);
		setup(41, "tree", "나무", true);
		
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
