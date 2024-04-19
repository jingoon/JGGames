package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	Tile [] tile;	// 바닥, 물, 벽, 나무 등등 타일 종류
	int mapTileNumber[][];	// 맵타일 배열, 메모장 읽기 
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		tile = new Tile[10];	// 타일 종류 가짓수
		mapTileNumber = new int[gp.maxScreenCol][gp.maxScreenRow];
		getTileImage();
		loadMap("/maps/testMap.txt");
	}
	
	// mapTileNumber[][] 세팅
	public void loadMap(String mapPath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(mapPath);
			Reader r = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(r);
			int col = 0;
			int row = 0;
			while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();
				String number[] = line.split(" ");
				
				while(col < gp.maxScreenCol) {
					int num = Integer.parseInt(number[col]);
					mapTileNumber[col][row] = num;
					col++;
				}
				if(col == gp.maxScreenCol) {
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
		
		try {
		
			tile[0] = new Tile();	// 풀
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			tile[1] = new Tile();	// 벽
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[2] = new Tile();	// 물
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
		
		
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
		int col = 0;
		int row = 0;
		while(gp.maxScreenCol > col && gp.maxScreenRow > row) {
			
			int tileNum = mapTileNumber[col][row];
			
			g2.drawImage(tile[tileNum].image, gp.tileSize*col, gp.tileSize*row, gp.tileSize, gp.tileSize, null);
			col++;
			if(gp.maxScreenCol == col) {
				col=0;
				row++;
			}
			
		}
		
		
	}

}