import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsOption extends JDialog {
    private JButton musicButton;
    private SoundTrack soundTrack;
    private boolean isMusicPlaying;
    private GameInterface main; // อ้างอิงไปที่ GameInterface

    public SettingsOption(GameInterface main) {
        super(main, "Settings Option", true);
        setSize(400, 400);
        setLocationRelativeTo(main);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ImageIcon settingsIcon = new ImageIcon(System.getProperty("user.dir")
                + File.separator + "Materials/SettingsIcon.png");
        setIconImage(settingsIcon.getImage());

        soundTrack = new SoundTrack();
        this.main = main; // รับอ้างอิงจาก GameInterface

        //ตรวจสอบสถานะเพลง
        isMusicPlaying = soundTrack.isMusicBackgroundPlaying();
        main.isMusicPlaying = isMusicPlaying;
        musicButton = new JButton(isMusicPlaying ? "Stop Music" : "Play Music");

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
        main.isMusicPlaying = isMusicPlaying; // Update สถานะต่างๆบนเฟรมหลัก

        setLayout(null);
        musicButton.setBounds(50, 100, 300, 50);
        add(musicButton);
    }
}