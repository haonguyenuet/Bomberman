package uet.oop.bomberman.sounds;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.io.*;

public class SoundEffect {
	private static AudioInputStream sound;
	private static File file;
	private static Clip clip;

	public static void loopNTimes( String path , int n ) {
		try {
			file = new File(path);
			sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
			clip.loop(n);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void play( String path) {
		try {
			file = new File(path);
			sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
			clip.start();
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void loop( String path ) {
		try {
			file = new File(path);
			sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue((float) 0.2);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}
}
