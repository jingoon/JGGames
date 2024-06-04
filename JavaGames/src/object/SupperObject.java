package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class SupperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	public void update(int x, int y) {
		worldX = x;
		worldY = y;
	}
	
	public void setup(String pathName, GamePanel gp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/"+name+".png"));
			image = gp.utill.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void drow(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;	 
		int screenY = worldY - gp.player.worldY + gp.player.screenY;	
		 
		// 16x12 (카메라영역 그리기)
		int screenLeft 	= gp.player.worldX - gp.player.screenX - (gp.tileSize *1);
		int screenRight = gp.player.worldX + gp.player.screenX + (gp.tileSize *1);
		int screenTop 	= gp.player.worldY - gp.player.screenY - (gp.tileSize *1);
		int screenBottom= gp.player.worldY + gp.player.screenY + (gp.tileSize *1);
		
		if(worldX > screenLeft && worldX < screenRight 
				&& worldY < screenBottom && worldY > screenTop) {
			
			g2.drawImage(image, screenX, screenY, null);
				
		}
	}

}
