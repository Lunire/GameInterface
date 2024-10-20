import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsOption extends JDialog {
    private JButton musicButton;
    private SoundTrack soundTrack;
    private boolean isMusicPlaying;

    public SettingsOption(JFrame main) {
        super(main, "Settings Option", true);
        setSize(400, 300);
        setLocationRelativeTo(main);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ImageIcon settingsIcon = new ImageIcon(System.getProperty("user.dir")
                + File.separator + "Materials/SettingsIcon.png");
        setIconImage(settingsIcon.getImage());

        soundTrack = new SoundTrack();
        isMusicPlaying = true; // ----->กำหนดสถานะเพลง

        musicButton = new JButton("Stop Music");
        musicButton.setFont(new Font("Serif", Font.PLAIN, 14));
        musicButton.setPreferredSize(new Dimension(300, 50));
        musicButton.setForeground(Color.WHITE);
        musicButton.setBackground(Color.DARK_GRAY);
        musicButton.setFocusPainted(false);
        musicButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // เอฟเฟกต์เปลี่ยนสีเมื่อ hover ปุ่ม
        musicButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                musicButton.setBackground(Color.GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                musicButton.setBackground(Color.DARK_GRAY);
            }
        });
        musicButton.addActionListener(e -> {
            if (isMusicPlaying) {
                soundTrack.stopBackground(); // ----->หยุดเพลงพื้นหลัง
                soundTrack.sound("Materials/mouse-click-sound-233951.wav");
                isMusicPlaying = false; // ------>เปลี่ยนสถานะเพลง
                musicButton.setText("Play Music"); // ----->เปลี่ยนข้อความบนปุ่ม
            } else {
                soundTrack.soundBackground("Materials/to_the_shining_sky-241513.wav");
                soundTrack.sound("Materials/mouse-click-sound-233951.wav");
                isMusicPlaying = true; // ------>เปลี่ยนสถานะเพลง
                musicButton.setText("Stop Music"); // ------->เปลี่ยนข้อความบนปุ่ม
            }
        });

        setLayout(null);
        musicButton.setBounds(50, 100, 300, 50);
        add(musicButton);
    }
}