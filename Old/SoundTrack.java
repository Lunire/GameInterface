import javax.sound.sampled.*;
import java.io.File;

public class SoundTrack{
    private static Clip backgroundClip;

        //เมธอดรับเสียงต่างๆ ความยาวอยู่ที่ 1 sec
        public static void sound(String soundFileName) {
    
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
                Thread.sleep(1000); //---->กำหนดให้ความยาว 1 sec
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        //เมธอดเล่นเสียงเพลงพื้นหลัง
        public static void soundBackground(String backgroundSong) {
    
            try{
                AudioInputStream stream;
                File initialFile = new File(System.getProperty("user.dir")
                        +File.separator + backgroundSong);
                stream = AudioSystem.getAudioInputStream(initialFile);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(stream);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); //----->กำหนดให้เป็นloopเพื่อเล่นเพลงพื้นหลัง
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

        //เมธอดสำหรับตรวจสอบว่าเพลงพื้นหลังเล่นอยู่หรือปล่าว
        public boolean isMusicBackgroundPlaying(){
            return backgroundClip != null && backgroundClip.isRunning();
        }

		public void setVolume(int volume) {
			if (backgroundClip != null) {
                FloatControl control = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
                float min = control.getMinimum();
                float max = control.getMaximum();
                float scale = (volume / 100f) * 0.70f + 0.30f;
                float dB = min + (max - min) * scale;
                control.setValue(dB);
            }
		}
}