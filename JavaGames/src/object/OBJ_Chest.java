package object;

import main.GamePanel;

public class OBJ_Chest extends SuperObject{

	public OBJ_Chest(GamePanel gp) {

		super(gp);
		name = "Chest";
		image = gp.utill.setupImage("/objects/chest");
		
	}
}
