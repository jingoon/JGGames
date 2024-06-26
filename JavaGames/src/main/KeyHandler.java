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
	final int BLINK = KeyEvent.VK_F1;			// blink 
	final int DRAWTIME[] = {KeyEvent.VK_T};		// 그리기 시간 화면표시(성능)
	final int CANCEL = KeyEvent.VK_ESCAPE;		// ESC, 취소 (일시정지모드or플레이모드전환)	
	final int BGMONOFF = KeyEvent.VK_M;			// 배경음악 켬/끔
	final int NEXT[] = {KeyEvent.VK_SPACE, KeyEvent.VK_ENTER};			// 선택, 대화 
	final int F2 = KeyEvent.VK_F2;
	
	public boolean blinkPress = false;
	boolean drawTimePress = false; // check draw time
	boolean bgmonoffPress = true; // BGM on/off
	public boolean nextPress = false;
	public boolean enterPress = false;
	public boolean f2Press = false;
	
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
			if(gp.gameState == gp.TITLESTATE) {
				gp.ui.titleMenuIndex--;
				gp.ui.titleImageR();
			}
		}
		if(linerSearch(code, DOWN)) {
			downPress = true;
			if(gp.gameState == gp.TITLESTATE) {
				gp.ui.titleMenuIndex++;
				gp.ui.titleImageR();
			}
		}
		if(linerSearch(code, LEFT)) {
			leftPress = true;
		}
		if(linerSearch(code, RIGHT)) {
			rigthPress = true;
		}
		if(linerSearch(code, BLINK)) {
			blinkPress = true;
		}
		if(linerSearch(code, F2)) {
			f2Press = true;
		}
		if(linerSearch(code, NEXT)) {
			nextPress = true;
			if(gp.gameState == gp.TITLESTATE) {
				menuSelectEffect();
			}
		}
		
		drawTimePress = keySwitch(code, DRAWTIME, drawTimePress );
		bgmonoffPress = keySwitch(code, BGMONOFF, bgmonoffPress );
		
		changeMode(code);
		
		
		
		
	}

	private void menuSelectEffect() {
		if(gp.gameState == gp.TITLESTATE) {
			// 메뉴 선택
			if(gp.ui.subTitleState == gp.ui.MENUSCREEN) {
				gp.ui.subTitleState = gp.ui.titleMenuIndex+1;
				// 플레이
				if(gp.ui.subTitleState == gp.ui.PLAYSCREEN) {
					gp.gameState = gp.PLAYSTATE;
				}
				// 종료
				if(gp.ui.subTitleState == gp.ui.EXITSCREEN) {
					System.exit(0);
				}
			}else if(gp.ui.subTitleState == gp.ui.CHARACTERSCREEN) {
				// 케릭터 선택화면
				String message = gp.ui.menuList[gp.ui.titleMenuIndex];
				message += "를 선택하였습니다.";
				if(gp.ui.titleMenuIndex == 0) {	
					// 전사
					gp.gameState = gp.PLAYSTATE;
				}else if(gp.ui.titleMenuIndex == 1) {
					// 궁수
					gp.gameState = gp.PLAYSTATE;
				}else if(gp.ui.titleMenuIndex == 2) {
					// 마법사
					gp.gameState = gp.PLAYSTATE;
				}else if(gp.ui.titleMenuIndex == 3) {
					// back
					gp.ui.titleMenuIndex = 0;
					gp.ui.subTitleState = gp.ui.MENUSCREEN;
				}
				System.out.println(message);
			}else if(gp.ui.subTitleState == gp.ui.OPTIONSCREEN) {
				//옵션 화면, only back
				gp.ui.titleMenuIndex = 0;
				gp.ui.subTitleState = gp.ui.MENUSCREEN;
			}
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
		if(linerSearch(code, BLINK)) {
			blinkPress = false;
		}
		if(linerSearch(code, NEXT)) {
			nextPress = false;
		}
		if(linerSearch(code, F2)) {
			f2Press = false;
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
	public boolean linerSearch(int code, int key) {
		int keys[] = new int[1];
		keys[0] = key;
		return linerSearch(code, keys);
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
	
	// 키 스위치
	private boolean keySwitch(int code, int key, boolean stste) {
	
		int keys[] = new int[1];
		keys[0] = key;
		return keySwitch(code, keys, stste);
	}
	
	// 일시정지 or 플레이
	private void changeMode(int code) {

		if(code == CANCEL) {
			if(gp.gameState == gp.PLAYSTATE) {
				gp.gameState = gp.PAUSESTATE;
			}else if(gp.gameState == gp.PAUSESTATE){
				gp.gameState = gp.PLAYSTATE;
			}else if (gp.gameState == gp.DIALOGUESTATE) {
				gp.gameState = gp.PLAYSTATE;
			}
		}
		if(linerSearch(code, NEXT)) {
			if (gp.gameState == gp.DIALOGUESTATE) {
				gp.gameState = gp.PLAYSTATE;
			}
		}
	}

}
