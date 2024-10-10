import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Help extends JDialog {
    public Help(JFrame main) {
        // ขนาดหน้าต่าง และการตั้งค่าเบื้องต้น
        super(main, "Help", true);
        setSize(720, 540);
        setLayout(null); 
        setLocationRelativeTo(main); 
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ImageIcon helpIcon = new ImageIcon(System.getProperty("user.dir")
                    +File.separator + "Materials/Question-mark.png");
        setIconImage(helpIcon.getImage());

        // เพิ่ม panel ที่มีเนื้อหาวิธีเล่น
        Helps panel = new Helps();
        panel.setBounds(0, 0, 720, 540); // จัดขนาดและตำแหน่งของ panel ให้เต็มหน้าต่าง
        add(panel); 
    }
}

class Helps extends JPanel {
    public Helps() {
        setBackground(Color.BLACK);
        setLayout(null); // ใช้ null layout เพื่อจัดตำแหน่งเอง

        // สร้าง JLabel สำหรับหัวข้อ "How to Play"
        JLabel titleLabel = new JLabel("How to Play");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // จัดให้อยู่กลางแนวนอน

        // คำนวณตำแหน่งของหัวข้อ "How to Play"
        int panelWidth = 720;
        int titleWidth = 600; // กำหนดความกว้างของหัวข้อ
        int titleHeight = 60;
        int titleX = (panelWidth - titleWidth) / 2; // คำนวณให้อยู่กลางแนวนอน
        int titleY = 30; // ตั้งให้อยู่ด้านบน

        titleLabel.setBounds(titleX, titleY, titleWidth, titleHeight); // กำหนดตำแหน่งของหัวข้อ
        add(titleLabel); 

        // สร้าง JTextArea สำหรับแสดงข้อความวิธีเล่นเกม Pong
        JTextArea helpText = new JTextArea(
            "\n\n1. เกมนี้รองรับผู้เล่น 4 คน\n" +
            "2. ใช้แป้นพิมพ์เพื่อควบคุม Paddle ของคุณ: \n" +
            "   - ผู้เล่นบังคับโดยใช้: ปุ่ม W และ S \n" +
            "   - หรือ ปุ่มลูกศรขึ้นและลงก็ได้\n" +
            "3. เป้าหมายของคุณคือการเด้งลูกบอลและป้องกันไม่ให้ลูกบอลออกนอกสนาม \n" +
            "ฝั่งของคุณ \n" +
            "4. ถ้าลูกบอลเข้าฝั่งคุณ ผู้เล่นคนอื่นจะได้คะแนน \n" +
            "5. เล่นจนกว่าจะมีผู้ชนะที่กำหนดตามคะแนน"
        );
        helpText.setFont(new Font("Tahoma", Font.PLAIN, 18)); 
        helpText.setForeground(Color.WHITE);
        helpText.setBackground(Color.BLACK);
        helpText.setEditable(false); // ป้องกันการแก้ไขข้อความ
        helpText.setOpaque(false); // ทำให้โปร่งแสงเข้ากับพื้นหลัง

        // คำนวณตำแหน่งของ JTextArea
        int textWidth = 600; // กำหนดความกว้างของข้อความ
        int textHeight = 300; // กำหนดความสูงของข้อความ
        int textX = (panelWidth - textWidth) / 2; // คำนวณให้อยู่กลางแนวนอน
        int textY = titleY + titleHeight + 20; // วางให้ห่างจากหัวข้อ 20 พิกเซล

        helpText.setBounds(textX, textY, textWidth, textHeight); // กำหนดตำแหน่งของ JTextArea
        add(helpText); // เพิ่ม JTextArea เข้าไปใน JPanel

        // สร้างปุ่มปิด
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Serif", Font.PLAIN, 18));
        closeButton.setBounds(300, 400, 120, 30); // กำหนดตำแหน่ง X, Y ของปุ่ม
        closeButton.addActionListener(e -> 
            ((JDialog) SwingUtilities.getWindowAncestor(closeButton)).dispose()); // ปิด dialog เมื่อคลิก
        add(closeButton); // เพิ่มปุ่มลงใน JPanel
    }
}
