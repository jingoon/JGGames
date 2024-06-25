package object;

import main.GamePanel;

public class OBJ_Boots extends SuperObject{
	
	public OBJ_Boots(GamePanel gp) {
		
		super(gp);
		name = "Boots";
		image = gp.utill.setupImage("/objects/boots");

	}
}
