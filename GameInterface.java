import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class GameInterface extends JFrame {
    private JButton playButton;
    private JButton helpButton;
    private JButton quitButton;
    private JButton settingsButton;
    private JButton memberButton;
    private JLabel label;
    private SoundTrack soundTrack;

    public GameInterface() {

        setTitle("Meteor Strike"); 
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //----->เพื่อให้มีการกดตกลงก่อนแล้วค่อยปิดเฟรม
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        ImageIcon meteorIcon = new ImageIcon(System.getProperty("user.dir")
                    +File.separator + "Materials/MeteorIcon.png");
        setIconImage(meteorIcon.getImage());

        soundTrack = new SoundTrack(); //เป็นคลาสที่รับเสียงจากเครื่องผู้ใช้มา ไฟล์.wav
        soundTrack.soundBackground("Materials/to_the_shining_sky-241513.wav");

        // สร้างคอมโพเนนต์ต่าง ๆ
        label = new JLabel("Meteor Strike!", SwingConstants.CENTER); //--->ตรงนี้อาจเปลี่ยนใส่เป็นรูปภาพแทน
        label.setFont(new Font("Serif", Font.BOLD, 24));
        label.setForeground(Color.WHITE);

        //เมธอดบรรทัดที่ 83
        playButton = createButton("Play");
        helpButton = createButton("How to Play");
        memberButton = createButton("Member");
        settingsButton = createButton("Settings");
        quitButton = createButton("Quit");

        //Event ของปุ่มต่างๆ
        playButton.addActionListener(e -> {
            soundTrack.soundClick("Materials/mouse-click-sound-233951.wav");
            PlayFrame play = new PlayFrame(this);
            play.setVisible(true);
        });

        helpButton.addActionListener(e -> {
            soundTrack.soundClick("Materials/mouse-click-sound-233951.wav");
            helpOption(); //---->เมธอดบรรทัดที่ 107
        });

        quitButton.addActionListener(e -> {
            soundTrack.soundClick("Materials/mouse-click-sound-233951.wav");
            Exit(); //---->เมธอดบรรทัดที่ 96
        });

        settingsButton.addActionListener(e -> {
            soundTrack.soundClick("Materials/mouse-click-sound-233951.wav");
            SettingsOption settings = new SettingsOption(this);
            settings.setVisible(true);
        });

        memberButton.addActionListener(e -> {
            soundTrack.soundClick("Materials/mouse-click-sound-233951.wav");
            //Coming Soon//
        });

        // ใช้ GridBagLayout สำหรับการจัดวางคอมโพเนนต์
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // ตั้งระยะห่างระหว่างคอมโพเนนต์

        // ตั้งค่าให้ text อยู่ด้านบนสุดและอยู่ตรงกลางเฟรม
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(label, gbc);

        // ตั้งค่าให้ button อยู่ตรงกลาง
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // ใช้ GridLayout เพื่อจัดปุ่มในแนวตั้ง
        panel.setBackground(Color.BLACK);
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

        //Event ที่จะรอรับการกดกากบาตบนหน้าจอเฟรมหลักแล้วส่งไปที่เมธอดบรรทัดที่ 96
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
        button.setPreferredSize(new Dimension(300, 50)); //---->เซตขนาดปุ่ม **ถ้าใช้setSize มันจะไม่ออก**
        return button;
    }

    //เมธอดคำสั่งออกแล้วให้แสดงหน้า popup ถามความแน่ใจว่าจะออกหรือปล่าว
    private void Exit() {
        int confirmed = JOptionPane.showConfirmDialog(this, //------->เป็นคลาสใน Java Swing ที่ใช้สำหรับแสดงกล่องข้อความ (dialog) เพื่อให้ผู้ใช้สามารถรับข้อมูลหรือแสดงข้อความต่างๆ ได้อย่างง่ายดาย
                "Are you sure you want to quit?",
                "Quit Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }

    //เมธอดหน้าต่างให้คำช่วยเหลือ
    private void helpOption(){
        JDialog helpDialog = new JDialog(this, "How To Play", true); // ใส่ true เพื่อกำหนดว่าต้องปิดหน้านี้ก่อนถึงจะทำอย่างอื่นได้
        helpDialog.setSize(400, 300);
        helpDialog.setLocationRelativeTo(this);
        helpDialog.setLayout(new BorderLayout());
        helpDialog.setResizable(false);
        helpDialog.setBackground(Color.BLACK);

        ImageIcon helpIcon = new ImageIcon(System.getProperty("user.dir")
                    +File.separator + "Materials/Question-mark.png");
        helpDialog.setIconImage(helpIcon.getImage());

        JTextArea text = new JTextArea("                  How To Play for the Meteor Strike Game:\n\n" + 
                "       1. Start the game by clicking the Play button.\n" +
                "       2. For more information, click the Help button again.\n" +
                "       3. Click Quit to exit the game.");
        text.setEditable(false); 
        text.setFont(new Font("Serif", Font.PLAIN, 14));   
        text.setForeground(Color.WHITE);
        text.setBackground(helpDialog.getBackground());
        
        helpDialog.add(text, BorderLayout.CENTER);
        helpDialog.setVisible(true);
    }

    public static void main(String[] args) {
        GameInterface GUI = new GameInterface();
        GUI.setVisible(true);
    }
}
     