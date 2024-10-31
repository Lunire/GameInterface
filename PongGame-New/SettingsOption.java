import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsOption extends JDialog{
    private SoundTrack soundTrack;
    private boolean isMusicPlaying;
    private JSlider volumeSlider;
    private JComboBox<String> musicSelector;
    private GameInterface main; // อ้างอิงไปที่ GameInterface
    private final String[][] musicOptions = { //-------> รายชื่อเพลง
        {"To The Shining Sky", "Materials/music1.wav"},
        {"Candy Vocal", "Materials/music2.wav"},
        {"In To The Fire", "Materials/music3.wav"}
    };

    public SettingsOption(GameInterface main) {
        super(main, "Settings Option", true); //สร้างปุ่มตกลง
        setSize(400, 300);
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

        //สร้างตัวปรับระดับเสียง
        JLabel volumeLabel = new JLabel("Volume");
        volumeLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        volumeLabel.setForeground(Color.WHITE);
        int volume = main.getVolume(); // รับค่าระดับเสียงจาก GameInterface
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, volume); //ซึ่งเป็นส่วนประกอบ GUI ใน Java Swing ใช้สำหรับให้ผู้ใช้ปรับค่าต่างๆ โดยการเลื่อนแถบ (slider)
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setBackground(Color.black);
        volumeSlider.addChangeListener(e -> {
            int value = volumeSlider.getValue();
            soundTrack.setVolume(value); //รับค่าเสียงจาก soundTrack
            main.setVolume(value); // อัปเดตสถานะเสียงใน GameInterface
        });

        //สร้างกล่องเลือกเสียง
        JLabel musicLabel = new JLabel("Music");
        musicLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        musicLabel.setForeground(Color.WHITE);
        // สร้าง JComboBox สำหรับเลือกเพลง
        String[] musicNames = {musicOptions[0][0], musicOptions[1][0], musicOptions[2][0]};
        musicSelector = new JComboBox<>(musicNames);
        musicSelector.setBackground(Color.BLACK);
        musicSelector.setForeground(Color.WHITE);

        // ตั้งค่า JComboBox ให้ตรงกับเพลงที่ถูกบันทึกใน GameInterface
        musicSelector.setSelectedItem(main.getCurrentMusic());

        musicSelector.addActionListener(e -> {
            // หยุดเพลงก่อนหน้านี้
            if (isMusicPlaying) {
                soundTrack.stopBackground(); // ฟังก์ชันหยุดเพลง
            }

            String selectedMusic = (String) musicSelector.getSelectedItem();
            String musicFile = ""; // ตัวแปรสำหรับเก็บไฟล์เพลง
            
            // หาชื่อไฟล์เพลงจากชื่อเพลงที่เลือก
            for (String[] option : musicOptions) {
                if (option[0].equals(selectedMusic)) {
                    musicFile = option[1];
                    break;
                }
            }

            // เล่นเพลงใหม่
            soundTrack.soundBackground(musicFile);
            soundTrack.setVolume(volumeSlider.getValue()); //ตั้งค่าระดับเสียงของเพลงพื้นหลัง โดยเรียกใช้เมธอด setVolume ของออบเจ็กต์ soundTrack ด้วยค่าที่ได้จาก volumeSlider
            isMusicPlaying = true; //เปลี่ยนให้เป็นเล่น
            main.setCurrentMusic(selectedMusic); //อัปเดตชื่อเพลงที่กำลังเล่นใน GameInterface
        });

        // สร้างปุ่มปิด
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Serif", Font.PLAIN, 14));
        closeButton.setBounds(135, 210, 120, 30); // กำหนดตำแหน่ง X, Y ของปุ่ม
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.DARK_GRAY);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // เอฟเฟกต์เปลี่ยนสีเมื่อ hover ปุ่ม
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(Color.GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(Color.DARK_GRAY);
            }
        });
        closeButton.addActionListener(e -> 
            ((JDialog) SwingUtilities.getWindowAncestor(closeButton)).dispose()); // ปิด dialog เมื่อคลิก
        
        setLayout(null);
        volumeLabel.setBounds(25, 50, 300, 50);
        volumeSlider.setBounds(70, 53, 300, 50);
        musicLabel.setBounds(25, 125, 300, 50);
        musicSelector.setBounds(80, 135, 275, 30);

        add(volumeLabel);
        add(volumeSlider);
        add(musicLabel);
        add(musicSelector);
        add(closeButton);
    }
}