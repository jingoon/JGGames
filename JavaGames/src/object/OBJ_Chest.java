package object;

import main.GamePanel;

public class OBJ_Chest extends SupperObject{
	
	GamePanel gp;

	public OBJ_Chest(GamePanel gp) {

		this.gp = gp;
		name = "Chest";
		setup(name.toLowerCase(), gp);
		
	}
}
