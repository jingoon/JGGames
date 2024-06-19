package skill;

import entity.Entity;

public class Blink extends SuperSkill{

	public Blink(Entity player) {
		super(player);
		limit = 60;
	}	
	
	public void start() {
		if(delay>=limit) {
			player.randomRegen();
			delay = 0;		
		}
	}
}
