package object;

import main.GamePanel;

/**
 * @apiNote 
 * image (heart_blank),
 *  image2 (heart_half),
 *  image3 (heart_full)
 */
public class OBJ_Heart extends SuperObject{

	public OBJ_Heart(GamePanel gp) {
			
		super(gp);
		name = "Heart";
		image = gp.utill.setupImage("/objects/heart_blank");
		image2 = gp.utill.setupImage("/objects/heart_half");
		image3 = gp.utill.setupImage("/objects/heart_full");
	
	}
}
