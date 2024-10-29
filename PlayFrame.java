import javax.swing.*;
import java.awt.*;

public class PlayFrame extends JDialog{
    private JTextField IPField;
    private JTextField usernameField;
    private JButton button;
    private SoundTrack soundTrack;

    public PlayFrame(JFrame main){
        super(main, "", true); 
        setSize(400, 275);
        setLocationRelativeTo(main);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        soundTrack = new SoundTrack(); 
        
        JLabel IP = new JLabel("IP:");
        IP.setForeground(Color.WHITE);
        IPField = new JTextField();

        JLabel user = new JLabel("UserName:");
        user.setForeground(Color.WHITE);
        usernameField = new JTextField();
        
        button = new JButton("OK");
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
        
        button.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            if (usernameField.getText().length() == 0) { // ใช้ length() ตรวจสอบความยาว
                JOptionPane.showMessageDialog(this, "Please enter a username.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //comming soon//
                dispose(); // ปิดหน้าต่าง
            }
        });

        IP.setBounds(30, 50, 100, 30);
        IPField.setBounds(150, 50, 200, 30);
        user.setBounds(30, 100, 100, 30);
        usernameField.setBounds(150, 100, 200, 30);
        button.setBounds(150, 150, 200, 30);

        add(IP);
        add(IPField);
        add(user);
        add(usernameField);
        add(button);
   }
}