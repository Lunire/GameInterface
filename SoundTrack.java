import javax.sound.sampled.*;
import java.io.File;

public class SoundTrack{
    private static Clip backgroundClip;
    
        public static void soundClick(String soundFileName) {
    
            try{
                AudioInputStream stream;
                AudioFormat format;
                DataLine.Info info;
                Clip clip;
                File initialFile = new File(System.getProperty("user.dir")
                        +File.separator + soundFileName);
                stream = AudioSystem.getAudioInputStream(initialFile);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);
                clip.start();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    
        public static void soundBackground(String backgroundSong) {
    
            try{
                AudioInputStream stream;
                File initialFile = new File(System.getProperty("user.dir")
                        +File.separator + backgroundSong);
                stream = AudioSystem.getAudioInputStream(initialFile);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(stream);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundClip.start();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        //เมธอดเอาไว้หยุดเพลงพื้นหลัง
        public void stopBackground() {
            if (backgroundClip != null && backgroundClip.isRunning()) {
                backgroundClip.stop();
                backgroundClip.close();
            }
        }
}