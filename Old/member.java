
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class member extends JFrame {
    private GameInterface mainInterface; // อ้างอิงไปที่ GameInterface
    private SoundTrack soundTrack;

    public member (GameInterface mainInterface){
        this.mainInterface = mainInterface; // รับอ้างอิงจาก GameInterface

        setSize(1600,900);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // ไม่ให้ปิดหน้าต่างโดยตรง
        getContentPane().setBackground(Color.BLACK);

        soundTrack = new SoundTrack(); 

        JLabel imgLabel1 = new JLabel();
        JLabel imgLabel2 = new JLabel();
        JLabel imgLabel3 = new JLabel();
        JLabel imgLabel4 = new JLabel();

        imgLabel1.setIcon(new ImageIcon("Materials/mem1.jpeg"));
        imgLabel1.setBounds(50, 80, 400, 450);

        imgLabel2.setIcon(new ImageIcon("Materials/mem2.jpeg"));
        imgLabel2.setBounds(300, 80, 400, 450);

        imgLabel3.setIcon(new ImageIcon("Materials/mem3.jpeg"));
        imgLabel3.setBounds(750, 80, 400, 450);

        imgLabel4.setIcon(new ImageIcon("Materials/mem4.jpeg"));
        imgLabel4.setBounds(1200, 80, 400, 450);

        add(imgLabel1);
        add(imgLabel2);
        add(imgLabel3);
        add(imgLabel4);

        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();
        JLabel label4 = new JLabel();

        label1.setText("<html> ชื่อ : นายชวกร บัญดิษฐตา <br> รหัสนิสิต : 66011212084 </html>");
        label1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
        label1.setForeground(Color.WHITE);
        label1.setBounds(50, 550, 300, 200);

        label2.setText("<html> ชื่อ : นายศิริวัฒน์ จันทมนตรี <br> รหัสนิสิต : 66011212136 </html>");
        label2.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
        label2.setForeground(Color.WHITE);
        label2.setBounds(400, 550, 300, 200);

        label3.setText("<html> ชื่อ : นายณฐกฤต นาสุวรรณ <br> รหัสนิสิต : 66011212165 </html>");
        label3.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
        label3.setForeground(Color.WHITE);
        label3.setBounds(850, 550, 300, 200);

        label4.setText("<html> ชื่อ : นายสิทธิชัย ศรีนัคเรศ <br> รหัสนิสิต : 66011212225 </html>");
        label4.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
        label4.setForeground(Color.WHITE);
        label4.setBounds(1250, 550, 300, 200);

        add(label1);
        add(label2);
        add(label3);
        add(label4);

        // สร้างปุ่ม Home Menu และวางไว้ที่มุมซ้ายบน
        JButton homeButton = new JButton("Home Menu");
        homeButton.setFont(new Font("Serif", Font.BOLD, 18));  
        homeButton.setBounds(20, 20, 150, 40);      // จัดตำแหน่งของปุ่มให้อยู่มุมซ้ายบน
        homeButton.setForeground(Color.WHITE);
        homeButton.setBackground(Color.DARK_GRAY);
        homeButton.setFocusPainted(false);
        homeButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // เอฟเฟกต์เปลี่ยนสีเมื่อ hover ปุ่ม
        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeButton.setBackground(Color.GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeButton.setBackground(Color.DARK_GRAY);
            }
        });
         homeButton.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            mainInterface.setVisible(true); // กลับไปที่ GameInterface
            dispose(); // ปิดหน้าต่าง member เมื่อคลิกปุ่ม
         });
         add(homeButton); 

         setVisible(true);
    }
}