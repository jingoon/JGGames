package object;

import main.GamePanel;

public class OBJ_Key extends SupperObject{
	
	GamePanel gp;

	public OBJ_Key(GamePanel gp) {
		
		this.gp = gp;
		name = "Key";
		setup(name.toLowerCase(), gp);

	}
}
