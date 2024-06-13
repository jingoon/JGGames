package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import entity.Entity;
import entity.Player;
import object.SupperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	// 화면 세팅
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	// 기준 타일 크기
	public final int tileSize = originalTileSize * scale ; // 48x48 tile
	
	// 스크린 크기
	public final int maxScreenCol = 16; // tile 갯수
	public final int maxScreenRow = 12; 
	public final int screenWidth = tileSize * maxScreenCol; // tile 갯수 * tile 사이즈
	public final int screenHeight = tileSize * maxScreenRow;
	
	// WORLD SET
	public final int maxWorldCol = 50;	// tile 갯수
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;	// tile 갯수 * tile 사이즈
	public final int worldHeight = tileSize * maxWorldRow;
	
	// GAME MODE
	public int gameState;
	public final int PAUSESTATE = 2;
	public final int PLAYSTATE = 1;
		
	// 화면 갱신
	int FPS = 60;
	
	// SYSTEM
	public Utill utill = new Utill(this);										// Utility Tool
	public TileManager tileM = new TileManager(this);						// TileManager
	public KeyHandler keyH = new KeyHandler(this);								// keyPress
	public Sound music = new Sound();										// sound(BGM, ..)
	public Sound soundEffect = new Sound();									// sound(effectSound, ..)
	public CollisionChecker cChecker = new CollisionChecker(this);			// CollisionChecker
	public AssetSetter aSetter = new AssetSetter(this);						// object setUp
	public UI ui = new UI(this);											// User InterFace(Screen Text,pick up Text, ..)
	public Thread gameThread;														// Thread

	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);							// player
	public SupperObject obj[] = new SupperObject[10];						// Objects, 갯수
	public Entity npc[] = new Entity[10];									// NPC, 갯수
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		this.setName("name");
		
		// keyboard press
		this.addKeyListener(keyH);
		this.setFocusable(true); // with this, this GamePanel can be "focused" to receive key input
		
		music.setFile(0); 				// BGM
		
	}
	
	public void setupGame() {
		playMusic();					// BGM start
		aSetter.setObject(); 			// objects setting
		aSetter.setNpc(); 				// NPC setting
		gameState = PLAYSTATE;			// gameMode
	}
	
	public void playMusic() {
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		soundEffect.setFile(i); 	
		soundEffect.play();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();	// call run()
		
	}

	@Override
	public void run() {
		double drawInterver = 1000000000/FPS; // nanoTime일때 : 초(s)/FPS
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterver; 	// 경과시간을 drawInterver로 나눈후 누적. 최종 1이상 될때 update하기 위함
			timer += (currentTime - lastTime); 					// 경과시간을 누적
			lastTime = currentTime;
			
			
			if(delta >= 1) {
				update();
				repaint();
				delta --;
				drawCount++;
			}
			
			if(timer >= 1000000000) {	// 1초 이상이 되었을 때 update횟수를 체크 
				//System.out.println("FPS: "+drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
	}
	
	
	// 1 update : 위치 업데이트
	public void update() {
		
		switch(gameState) {
			case PLAYSTATE:
				// 플레이어
				player.update();
				// NPC
				for(int i = 0; i < npc.length; i++ ) {
					if(npc[i]== null) {
						continue;
					}
					npc[i].update();
				}
	
				break;
			case PAUSESTATE:
				
				break;
		}
		
		
	}
	
	// 2 draw : 화면 정보 갱신 repaint()
	public void paintComponent(Graphics g) {
		// 그리기, 늦게 그릴수록 상위에 겹친다.
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// 디버그
		long drawStart = System.nanoTime();		
		
		// 타일 드로우
		tileM.draw(g2);		
		
		// 오브젝트(아이템, 문, 상자 등) 드로우
		for(int i = 0; i < obj.length; i++ ) {
			if(obj[i]== null) {
				continue;
			}
			obj[i].draw(g2, this);
		}
		
		// 엔피씨 드로우
		for(int i = 0; i < npc.length; i++ ) {
			if(npc[i]== null) {
				continue;
			}
			npc[i].draw(g2);
		}
		
		// 케릭터 드로우
		player.draw(g2);
		
		// 화면 UI, 텍스트
		ui.draw(g2);
		
		// 디버그
		if(keyH.drawTimePress) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(ui.godic_40);
			g2.setColor(Color.white);
			g2.drawString("Draw Time :"+passed , 10, 400);
		}
		// test BGM on/off
		if(!keyH.bgmonoffPress) {
			playMusic();
		}else {
			stopMusic();
		}

		g2.dispose();
				
	}
}
