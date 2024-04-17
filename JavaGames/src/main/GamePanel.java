package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	// 화면 세팅
	final int orihinalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	// 기준 타일 크기
	final int tileSize = orihinalTileSize * scale ; // 48x48 tile
	
	// 스크린 크기
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	// keyPress
	KeyHandler keyH = new KeyHandler();
	
	Thread gameThread;
	
	// Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 5;	// 한번에 움직일 픽셀
	
	// 화면 갱신
	int FPS = 60;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		this.setName("name");
		
		// keyboard press
		this.addKeyListener(keyH);
		this.setFocusable(true); // with this, this GamePanel can be "focused" to receive key input
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();	// call run()
		
	}
/*	
	@Override
	public void run() {
		
		double aTime = System.nanoTime();
		double bTime;
		double cTime;
		double oneFrame = 1000000000/FPS; // 0.0166666666... seconds
		double sleepTime;
		
		// game loof;	
		while(gameThread != null) {
			
			// position update
			update();
			// draw 화면 정보 
			repaint();
			
			try {
				// 1 loof = 1 fps = (update, repain) Time + sleepTime  
				bTime = System.nanoTime();
				
				sleepTime = oneFrame - (bTime - aTime);
				
				if(sleepTime<=0) {
					sleepTime =0;
				}
				
				Thread.sleep((long)sleepTime/1000000);	// System.nanoTime() -> System.currentTimeMillis() 변환

				System.out.println("sleepTime = oneFrame - (bTime - aTime): "+sleepTime +" = "+ oneFrame + - (bTime - aTime));
				System.out.println("FPS = "+ 1000000000/sleepTime+(bTime - aTime));
				
				aTime = System.nanoTime();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
*/
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
			
			delta += (currentTime - lastTime) / drawInterver; // 경과시간을 drawInterver로 나눈후 누적. 최종 1이상 될때 update하기 위함
			timer += (currentTime - lastTime); // 경과시간을 누적
			lastTime = currentTime;
			
			
			if(delta >= 1) {
				update();
				repaint();
				delta --;
				drawCount++;
			}
			
			if(timer >= 1000000000) {	// 1초 이상이 되었을 때 update횟수를 체크 
				System.out.println("FPS: "+drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
	}
	
	
	// 1 update : 위치 업데이트
	public void update() {
		// 상하좌우 이동
		if(keyH.upPress) {
			playerY -= playerSpeed; // 좌상단 좌표 (0,0)
		}
		if(keyH.downPress) {
			playerY += playerSpeed;
		}
		if(keyH.leftPress) {
			playerX -= playerSpeed;
		}
		if(keyH.rigthPress) {
			playerX += playerSpeed;
		}
		
		// 창 벗어나지 않기
		if(playerX <0) {
			playerX =0;
		}else if(playerX > screenWidth - tileSize){
			playerX = screenWidth - tileSize;
		}
		if(playerY <0) {
			playerY =0;
		}else if(playerY > screenHeight - tileSize){
			playerY = screenHeight - tileSize;
		}
		
		
	}
	
	// 2 draw : 화면 정보 갱신 repaint()
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		g2.dispose();
				
	}
}
