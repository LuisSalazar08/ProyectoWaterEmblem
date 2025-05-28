package Main;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusicManager {
    private Clip clip;
    public void playLevelMusic(int nivel) {
        stop();
        String file = String.format("/music/%02d.wav", nivel + 1);
        try (InputStream is = getClass().getResourceAsStream(file)) {
            if (is == null) {
                System.err.println("[MusicManager] No se encontró: " + file);
                return;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }
}