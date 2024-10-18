import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PlayFrame extends JDialog{
    private JTextField IPField;
    private JTextField usernameField;
    private JButton button;
    private SoundTrack soundTrack;

    public PlayFrame(JFrame main){
        super(main, "", true); 
        setSize(400, 300);
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
        button.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            if (usernameField.getText().length() == 0) { // ใช้ length() ตรวจสอบความยาว
                JOptionPane.showMessageDialog(this, "Please enter a username.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //comming soon//
                System.out.println("Username: " + usernameField.getText());
                dispose(); // ปิดหน้าต่าง
            }
        });

        IPField.setText(IPAddress.getLocalIPAddress()); // แสดง IP Address ที่ได้รับ
        IPField.setEditable(false);

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

// รับ IP Address ของเครื่อง
class IPAddress {
    public static String getLocalIPAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress(); // คืนค่า IP Address
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknown IP"; // ถ้าเกิดข้อผิดพลาด
        }
    }
}
