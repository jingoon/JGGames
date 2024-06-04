package object;

import main.GamePanel;

public class OBJ_Door extends SupperObject{
	
	GamePanel gp;

	public OBJ_Door(GamePanel gp) {

		this.gp = gp;
		name = "Door";
		collision = true;
		setup(name.toLowerCase(), gp);

	}
}
