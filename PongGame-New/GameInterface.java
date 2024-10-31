import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class GameInterface extends JFrame{
    private JButton playButton;
    private JButton helpButton;
    private JButton quitButton;
    private JButton settingsButton;
    private JButton memberButton;
    private JLabel label;
    private SoundTrack soundTrack;
    public boolean isMusicPlaying;
    private int volume = 100; // ค่าเริ่มต้นเสียงเพลง
    private String currentMusic;

    public GameInterface() {

        setTitle("PingPong Strike"); 
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //----->เพื่อให้มีการกดตกลงก่อนแล้วค่อยปิดเฟรม
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        ImageIcon meteorIcon = new ImageIcon(System.getProperty("user.dir")
                    +File.separator + "Materials/MeteorIcon.png");
        setIconImage(meteorIcon.getImage());

        soundTrack = new SoundTrack(); //เป็นคลาสที่รับเสียงจากเครื่องผู้ใช้มา ไฟล์.wav
        soundTrack.soundBackground("Materials/music1.wav"); //---->เล่นเพลงพื้นหลัง

        // สร้างคอมโพเนนต์ต่าง ๆ
        label = new JLabel("PingPong Strike!", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 44));
        label.setForeground(Color.WHITE);

        //เมธอดบรรทัดที่ 117 เป็นเมธอดไว้สำหรับสร้างปุ่ม
        playButton = createButton("Play");
        helpButton = createButton("How to Play");
        memberButton = createButton("Member");
        settingsButton = createButton("Settings");
        quitButton = createButton("Quit");

        //Event ของปุ่มต่างๆ
        playButton.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            PlayFrame play = new PlayFrame(this); // ส่ง GameInterface ไปที่ Play
            play.setVisible(true);
        });

        helpButton.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            Help help = new Help(this); // ส่ง GameInterface ไปที่ Help
            help.setVisible(true);
        });

        quitButton.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            Exit(); //---->เมธอดบรรทัดที่ 140
        });

        settingsButton.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            SettingsOption settings = new SettingsOption(this); // ส่ง GameInterface ไปที่ Settings
            settings.setVisible(true);
        });

        memberButton.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            new member(this); // ส่ง GameInterface ไปที่ Member
            this.setVisible(false); // ซ่อน MainClass    
        });

        // ใช้ GridBagLayout สำหรับการจัดวางคอมโพเนนต์
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(100, 10, 10, 10); // ตั้งระยะห่างระหว่างคอมโพเนนต์

        // ตั้งค่าให้ text อยู่ด้านบนสุดและอยู่ตรงกลางเฟรม
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(40, 10, 10, 10);
        add(label, gbc);

        // ตั้งค่าให้ button อยู่ตรงกลาง
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // ใช้ GridLayout เพื่อจัดปุ่มในแนวตั้ง
        panel.setBackground(new Color(0, 0, 0, 0));  // ตั้งค่าสีเป็นโปร่งใส เพื่อไม่ให้บังพื้นหลัง
        panel.add(playButton);
        panel.add(helpButton);
        panel.add(memberButton);
        panel.add(settingsButton);
        panel.add(quitButton);

        // ตั้งค่าให้ panel ปุ่มอยู่ตรงกลาง
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        add(panel, gbc);

        //Event ที่จะรอรับการกดกากบาตบนหน้าจอเฟรมหลักแล้วส่งไปที่เมธอดบรรทัดที่ 140
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Exit();
            }
        });
    }

    //เมธอดสร้างปุ่มต่างๆ
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.PLAIN, 14)); //---->กำหนดฟอนต์
        button.setPreferredSize(new Dimension(300, 50)); //---->เซตขนาดปุ่ม
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // เอฟเฟกต์เปลี่ยนสีเมื่อ hover ปุ่ม
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.DARK_GRAY);
            }
        });
        return button;
    }

    //เมธอดคำสั่งออกแล้วให้แสดงหน้า popup ถามความแน่ใจว่าจะออกหรือปล่าว
    private void Exit() {
        String message = "<html><body style='text-align: center; color: black;'>"
                         + "</h2><p>Are you sure you want to quit?</p></body></html>";

        int confirmed = JOptionPane.showConfirmDialog(this, //------->เป็นคลาสใน Java Swing ที่ใช้สำหรับแสดงกล่องข้อความ (dialog) เพื่อให้ผู้ใช้สามารถรับข้อมูลหรือแสดงข้อความต่างๆ ได้อย่างง่ายดาย
                message,
                "Quit Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    //ใช้สำหรับคืนค่าระดับเสียงปัจจุบัน (volume) ของเพลงพื้นหลัง
    public int getVolume() {
        return volume;
    }
    
    //ใช้เพื่อกำหนดระดับเสียงใหม่ให้กับเพลงพื้นหลัง
    public void setVolume(int volume) {
        this.volume = volume;
        soundTrack.setVolume(volume);
    }

    //ใช้สำหรับคืนค่าชื่อเพลงที่กำลังเล่นอยู่ในขณะนี้
    public String getCurrentMusic() {
        return currentMusic;
    }

    //ใช้เพื่อกำหนดชื่อเพลงใหม่ที่จะเล่น
    public void setCurrentMusic(String music) {
        this.currentMusic = music;
    }

    public static void main(String[] args) {
        GameInterface main = new GameInterface();
        main.setVisible(true);
    }
}