import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsOption extends JDialog{
    private JButton musicButton;
    private SoundTrack soundTrack;
    private boolean isMusicPlaying;

    public SettingsOption(JFrame main){ 
    super(main, "Settings Option", true); 
    setSize(400, 300);
    setLocationRelativeTo(main);
    setResizable(false);

    ImageIcon settingsIcon = new ImageIcon(System.getProperty("user.dir")
                +File.separator + "Materials/Question-mark.png");
    setIconImage(settingsIcon.getImage());

    soundTrack = new SoundTrack();
    isMusicPlaying = true;

    musicButton = new JButton("Stop Music");
    musicButton.setFont(new Font("Serif", Font.PLAIN, 14)); //---->กำหนดฟอนต์
    musicButton.setPreferredSize(new Dimension(300, 50));
    musicButton.addActionListener(e -> {
        if(isMusicPlaying) {
            soundTrack.stopBackground(); //----->หยุดเพลงพื้นหลัง
            soundTrack.soundClick("Materials/mouse-click-sound-233951.wav");
            isMusicPlaying = false; //------>เปลี่ยนสถานะเพลง
            musicButton.setText("Play Music"); //----->เปลี่ยนข้อความบนปุ่ม
        }
        else {
            soundTrack.soundBackground("Materials/to_the_shining_sky-241513.wav");    
            soundTrack.soundClick("Materials/mouse-click-sound-233951.wav");
            isMusicPlaying = true; //------>เปลี่ยนสถานะเพลง
            musicButton.setText("Stop Music"); //------->เปลี่ยนข้อความบนปุ่ม
        }
    });

    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(10, 10, 10, 10); // ตั้งระยะห่างระหว่างคอมโพเนนต์
    
    add(musicButton, gbc);
    }
}       