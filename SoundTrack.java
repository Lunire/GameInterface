import javax.sound.sampled.*;
import java.io.File;

public class SoundTrack{
    public static void playSound(String soundFileName) {

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
}