package object;

import main.GamePanel;

public class OBJ_Boots extends SuperObject{
	
	GamePanel gp;

	public OBJ_Boots(GamePanel gp) {
		
		this.gp = gp;
		name = "Boots";
		setup(name.toLowerCase(), gp);

	}
}
