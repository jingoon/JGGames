package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	final int UP[] = {KeyEvent.VK_W, KeyEvent.VK_UP};
	final int DOWN[] = {KeyEvent.VK_S, KeyEvent.VK_DOWN };
	final int LEFT[] = {KeyEvent.VK_A, KeyEvent.VK_LEFT};
	final int RIGHT[] = {KeyEvent.VK_D, KeyEvent.VK_RIGHT};
	
	public boolean upPress, downPress, leftPress, rigthPress;

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		upPress = Utill.linerSearch(code, UP);
		downPress = Utill.linerSearch(code, DOWN);
		leftPress = Utill.linerSearch(code, LEFT);
		rigthPress = Utill.linerSearch(code, RIGHT);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(Utill.linerSearch(code, UP)) {
			upPress = false;
		}
		if(Utill.linerSearch(code, DOWN)) {
			downPress = false;
		}
		if(Utill.linerSearch(code, LEFT)) {
			leftPress = false;
		}
		if(Utill.linerSearch(code, RIGHT)) {
			rigthPress = false;
		}
		
	}
	
	

}
