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
		
		if(linerSearch(code, UP)) {
			upPress = true;
		}
		if(linerSearch(code, DOWN)) {
			downPress = true;
		}
		if(linerSearch(code, LEFT)) {
			leftPress = true;
		}
		if(linerSearch(code, RIGHT)) {
			rigthPress = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(linerSearch(code, UP)) {
			upPress = false;
		}
		if(linerSearch(code, DOWN)) {
			downPress = false;
		}
		if(linerSearch(code, LEFT)) {
			leftPress = false;
		}
		if(linerSearch(code, RIGHT)) {
			rigthPress = false;
		}
		
	}
	
	// 키 할당
	public boolean linerSearch(int code, int[] key) {
		
		boolean codeInKeyList = false;
		
		for (int value : key) {
			if(value == code) {
				codeInKeyList = true;
				break;
			}
		};
		
		return codeInKeyList;
	}
	

}
