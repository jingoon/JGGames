package main;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	public Clip clip;
	public List<URL> soundURL = new LinkedList<>();	//map으로 하는게 더 효과적이지 않을까?
	public Map<String, Integer> soundIndexList = new HashMap<>();
	
	public Sound() {
		soundURL.add(0, getClass().getResource("/sound/BlueBoyAdventure.wav"));
		soundURL.add(1, getClass().getResource("/sound/coin.wav"));
		soundURL.add(2, getClass().getResource("/sound/powerup.wav"));
		soundURL.add(3, getClass().getResource("/sound/unlock.wav"));
		soundURL.add(4, getClass().getResource("/sound/fanfare.wav"));
		
		soundIndexList.put("BGM", 0);
		soundIndexList.put("coin", 1);
		soundIndexList.put("powerup", 2);
		soundIndexList.put("unlock", 3);
		soundIndexList.put("fanfare", 4);
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL.get(i));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
}
