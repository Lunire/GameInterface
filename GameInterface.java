import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class GameInterface extends JFrame {
    private JButton playButton;
    private JButton helpButton;
    private JButton quitButton;
    private JButton settingsButton;
    private JButton memberButton;
    private JLabel label;
    private SoundTrack soundTrack;
    private Background background;
    public boolean isMusicPlaying;

    public GameInterface() {

        setTitle("Meteor Strike"); 
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //----->เพื่อให้มีการกดตกลงก่อนแล้วค่อยปิดเฟรม
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        ImageIcon meteorIcon = new ImageIcon(System.getProperty("user.dir")
                    +File.separator + "Materials/MeteorIcon.png");
        setIconImage(meteorIcon.getImage());

        // สร้างพื้นหลังเคลื่อนไหว
        background = new Background();
        setContentPane(background);  // ตั้งค่า background เป็น content pane

        soundTrack = new SoundTrack(); //เป็นคลาสที่รับเสียงจากเครื่องผู้ใช้มา ไฟล์.wav
        soundTrack.soundBackground("Materials/to_the_shining_sky-241513.wav"); //---->เล่นเพลงพื้นหลัง

        // สร้างคอมโพเนนต์ต่าง ๆ
        label = new JLabel("Meteor Strike!", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 44));
        label.setForeground(Color.WHITE);

        //เมธอดบรรทัดที่ 113 เป็นเมธอดไว้สำหรับสร้างปุ่ม
        playButton = createButton("Play");
        helpButton = createButton("How to Play");
        memberButton = createButton("Member");
        settingsButton = createButton("Settings");
        quitButton = createButton("Quit");

        //Event ของปุ่มต่างๆ
        playButton.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            PlayFrame play = new PlayFrame(this);
            play.setVisible(true);
        });

        helpButton.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            Help help = new Help(this);
            help.setVisible(true);
        });

        quitButton.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            Exit(); //---->เมธอดบรรทัดที่ 121
        });

        settingsButton.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            SettingsOption settings = new SettingsOption(this);
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

        //Event ที่จะรอรับการกดกากบาตบนหน้าจอเฟรมหลักแล้วส่งไปที่เมธอดบรรทัดที่ 121
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
        int confirmed = JOptionPane.showConfirmDialog(this, //------->เป็นคลาสใน Java Swing ที่ใช้สำหรับแสดงกล่องข้อความ (dialog) เพื่อให้ผู้ใช้สามารถรับข้อมูลหรือแสดงข้อความต่างๆ ได้อย่างง่ายดาย
                "Are you sure you want to quit?",
                "Quit Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        GameInterface main = new GameInterface();
        main.setVisible(true);
    }
}
  
// Class สำหรับสร้างพื้นหลังเคลื่อนไหว
class Background extends JPanel {
    // ประกาศตัวแปรเอาไว้ก่อน
    int number = 10; // ตัวแปรที่จะเอาไว้กำหนดจำนวนอุกกาบาต
    int size = 50; // ขนาดของอุกกาบาต
    Image[] meteors; // รูปของอุกกาบาต
    Image[] bomb; // รูปของระเบิด
    Random rand = new Random(); // object random จะได้ไม่ต้อง new ทุกครั้ง
    int[] x; // ตำแหน่ง x ของอุกกาบาต
    int[] y; // ตำแหน่ง y ของอุกกาบาต
    int[] dx; // การเคลื่อนที่ของอุกกาบาตในแนว x มี 1 ถึง 3 และ -1 ถึง -3
    int[] dy; // การเคลื่อนที่ของอุกกาบาตในแนว y มี 1 ถึง 3 และ -1 ถึง -3
    int[] direction; // ทิศทางการเคลื่อนที่ของอุกกาบาต มี 0 แนวนอน, 1 แนวตั้ง, 2 แนวทะแยง
    boolean[] meteoAlive; // ใช้ตรวจสอบว่าอุกกาบาตยังอยู่
    boolean[] bombAlive; // ใช้ตรวจสอบว่าระเบิดถูกใช้งาน
    MyThread[] threads; // สร้าง thread จาก class MyThread เพื่อทำให้อุกกาบาตดูเหมื่อนเคลื่อนที่
    Timer[] timers; // สร้าง timer เพื่อกำหนดระยะเวลาที่ระเบิดจะหายไป

    // constructor ที่ต้องมีการ input number ก่อน ถึงจะเรียกใช้งานได้
    public Background() {
        // กำหนดตัวแปรอาเรย์หลังจากมี number
        meteors = new Image[number];
        bomb = new Image[number];
        x = new int[number];
        y = new int[number];
        dx = new int[number];
        dy = new int[number];
        direction = new int[number];
        meteoAlive = new boolean[number];
        bombAlive = new boolean[number];
        threads = new MyThread[number];
        timers = new Timer[number];

        // สร้าง panel เพื่อให้สามารถวาดรูปบน panel
        setSize(getWidth(), getHeight());
        setLocation(0, 0);

        // ใส่ข้อมูลลงใน image และตัวแปรต่างๆ เพื่อเตรียมวาดรูป
        for (int i = 0; i < number; i++) {
            // สร้างอุกกาบาต แล้วก็สุ่มรูปอุกกาบาต
            meteors[i] = Toolkit.getDefaultToolkit().createImage(
                    System.getProperty("user.dir") + File.separator + "Materials/Meteor/" + (rand.nextInt(10) + 1) + ".png");

            // สร้างระเบิด เพื่อรอใช้งาน
            bomb[i] = Toolkit.getDefaultToolkit().createImage(
                    System.getProperty("user.dir") + File.separator + "Materials/Meteor/bomb.gif");

            // กำหนดค่าต่างๆ เพื่อเตรียมใช้งาน
            x[i] = rand.nextInt(700);
            y[i] = rand.nextInt(700);
            dx[i] = rand.nextInt(3) + 1;
            dy[i] = dx[i];
            direction[i] = rand.nextInt(3);
            meteoAlive[i] = true;
            bombAlive[i] = false;

            threads[i] = new MyThread(this);
            threads[i].start();
        }

        // ถ้าคลิกที่อุกกาบาต 2 ครั้ง จะทำให้อุกกาบาตหายไป
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    for (int i = 0; i < number; i++) {
                        // ตรวจว่าอุกกาบาตนั้นมีอยู่หรือไม่ และเรียกใช้ method
                        // ที่สามารถตรวจสอบขนาดสี่เหลี่ยมของอุกกาบาต
                        if (meteors[i] != null && getRectMeteor(x[i], y[i]).contains(e.getPoint())) {
                            dx[i] = 0;
                            dy[i] = 0;
                            meteoAlive[i] = false;
                            bombAlive[i] = true;

                            explosionMeteos(); // method บรรทัดที่ 127 : เพื่อวาดรูประเบิด
                        }
                    }
                }
            }
        });
    }

    // method ทีีใช้ timer ที่จะทำให้รูป gif ทำงานและหายไป
    public void explosionMeteos() {
        for (int i = 0; i < number; i++) {
            if (bombAlive[i]) {
                timers[i] = new Timer();
                // เรียกใช้ TimerTask แบบ inner class
                timers[i].schedule(new TimerTask() {

                    @Override
                    public void run() {
                        for (int i = 0; i < number; i++) {
                            bombAlive[i] = false;
                            repaint();
                        }
                    }

                }, 1000);
            }
        }
    }

    // method paint ใช้วาดรูป
    @Override
    protected void paintComponent(Graphics g) {
        // วาดรูปพื้นหลังสีดำ
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        // วาดรูปอุกกาบาต และระเบิด
        for (int i = 0; i < number; i++) {
            // วาดรูปอุกกาบาต เมื่อระเบิดยังเป็น false
            if (meteoAlive[i] && !bombAlive[i]) {
                g.drawImage(meteors[i], x[i], y[i], x[i] + size, y[i] + size, 0, 0, 300, 300, this);
            }

            // วาดรูประเบิด เมื่อระเบิดเป็น true
            if (!meteoAlive[i] && bombAlive[i]) {
                g.drawImage(bomb[i], x[i], y[i], x[i] + size, y[i] + size, 0, 0, 80, 80, this); 
            }
        }

        movingMeteor(); // method บรรทัดที่ 171 : เพื่อให้อุกกาบาตเคลื่อนที่ตามแนว
    }

    // method ที่จะควบคุมลักษณะการเคลื่อนที่ของอุกกาบาต
    public void movingMeteor() {
        for (int i = 0; i < number; i++) {
            // เคลื่อนที่แนวนอน
            if (direction[i] == 0) {
                x[i] += dx[i];
            }
            // เคลื่อนที่แนวตั้ง
            else if (direction[i] == 1) {
                y[i] += dy[i];
            }
            // เคลื่อนที่แนวเฉียง
            else if (direction[i] == 2) {
                x[i] += dx[i];
                y[i] += dy[i];
            }

            checkWallHit(); // method บรรทัดที่ 193 : เพื่อตรวจสอบเมื่ออุกกาบาตชนเข้ากับขอบของ frame
            checkMeteosHit(); // method บรรทัด 212 : เพื่อตรวจสอบเมื่ออุกกาบาต 2 ลูกมาชนกัน
        }
    }

    // method ตรวจสอบการชนกับขอบของ frame
    public void checkWallHit() {
        for (int i = 0; i < number; i++) {
            // เมื่อขอบชนแถวๆ ผั่งซ้าย
            if (x[i] <= 0 || y[i] <= 0) {
                dx[i] = rand.nextInt(3) + 1;
                dy[i] = dx[i];
                direction[i] = rand.nextInt(3);
            }

            // เมื่อขอบชนแถวๆ ผั่งขวา
            if (x[i] >= getWidth() - size || y[i] >= getHeight() - size) {
                dx[i] = rand.nextInt(3) - 3;
                dy[i] = dx[i];
                direction[i] = rand.nextInt(3);
            }
        }
    }

    // method ตรวจสอบเมื่ออุกกาบาต 2 ลูกมาชนกัน
    public void checkMeteosHit() {
        for (int i = 0; i < number; i++) {
            for (int j = i + 1; j < number; j++) {
                // เรียกใช้ method getRectMeteor ทั้งสองลูก
                // เทื่อตรวจสอบว่ามีการซ้อนทับกันของตำแหน่งหรือไม่
                if (getRectMeteor(x[i], y[i]).intersects(getRectMeteor(x[j], y[j]))) {
                    // จะทำงานก็ต่อเมื่ออุกกาบาตทั้ง 2 ลูกยังไม่ระเบิด
                    if (meteoAlive[i] && meteoAlive[j]) {
                        // ถ้าการเคลื่อนที่ของอุกกาบาตทั้งสองเป็นลบ ทำการสุ่มการเคลื่อนที่
                        if (dx[i] < 0 || dy[i] < 0 || dx[j] > 0 || dy[j] > 0) {
                            dx[i] = rand.nextInt(3) - 3;
                            dy[i] = dx[i];
                            dx[j] = rand.nextInt(3) + 1;
                            dy[j] = dx[j];
                        }

                        // ถ้าการเคลื่อนที่ของอุกกาบาตทั้งสองเป็นบวก ทำการสุ่มการเคลื่อนที่
                        if (dx[i] > 0 || dy[i] > 0 || dx[j] < 0 || dy[j] < 0) {
                            dx[i] = rand.nextInt(3) - 3;
                            dy[i] = dx[i];
                            dx[j] = rand.nextInt(3) + 1;
                            dy[j] = dx[j];
                        }

                        // สุ่มลักษณะการเคลื่อนที่ใหม่
                        direction[i] = rand.nextInt(3);
                        direction[j] = rand.nextInt(3);
                    }
                }
            }
        }
    }

    // method ที่กำหนดลักษณะสี่เหลี่ยมของอุกกาบาต
    public Rectangle getRectMeteor(int x, int y) {
        return new Rectangle(x, y, size, size);
    }
}

// class thread เอาไว้ทำให้อุกกาบาตดูเหมือนเคลื่อนที่
class MyThread extends Thread {
    // เรียกใช้ class MyPaint เพื่อที่จะวาดลง panel เรื่อยๆ
    Background paint;

    public MyThread(Background paint) {
        this.paint = paint;
    }

    @Override
    public void run() {
        while (true) {
            paint.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}