package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	
	// 이동키
	final int UP[] = {KeyEvent.VK_W, KeyEvent.VK_UP};
	final int DOWN[] = {KeyEvent.VK_S, KeyEvent.VK_DOWN };
	final int LEFT[] = {KeyEvent.VK_A, KeyEvent.VK_LEFT};
	final int RIGHT[] = {KeyEvent.VK_D, KeyEvent.VK_RIGHT};
	public boolean upPress, downPress, leftPress, rigthPress;
	
	// 특수키
	final int DRAWTIME[] = {KeyEvent.VK_T};
	final int PAUSED = KeyEvent.VK_ESCAPE;
	
	boolean drawTimePress = false; // check draw time
	
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
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
		
		drawTimePress = keySwitch(code, DRAWTIME, drawTimePress );
		
		changeMode(code);
		
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
	
	// 키 스위치
	private boolean keySwitch(int code, int[] key, boolean stste) {
		
		for (int value : key) {
			if(value == code) {
				if(stste) {
					stste = false;
				}else{
					stste = true;
				}
				break;
			}
		};
		return stste;
	}
	
	// 일시정지 or 플레이
	public void changeMode(int code) {

		if(code == PAUSED) {
			if(gp.gameState == gp.PLAYSTATE) {
				gp.gameState = gp.PAUSESTATE;
				gp.stopMusic();
			}else if(gp.gameState == gp.PAUSESTATE){
				gp.gameState = gp.PLAYSTATE;
				gp.playMusic(0);
			}
		}
	}

}
