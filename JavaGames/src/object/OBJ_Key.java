package object;

import main.GamePanel;

public class OBJ_Key extends SuperObject{
	
	public OBJ_Key(GamePanel gp) {
		
		super(gp);
		name = "Key";
		image = gp.utill.setupImage("/objects/key");

	}
}
