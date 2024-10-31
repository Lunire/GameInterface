import java.awt.*;
import javax.swing.*;

public class PlayFrame extends JDialog{
    private JTextField IPField;
    private JTextField portField;
    private JButton button;
    private SoundTrack soundTrack;

    public PlayFrame(JFrame main){
        super(main, "", true); //กำหนดเป็นtrueเพื่อให้สร้างได้เพียงเฟรมเดียว
        setSize(400, 275);
        setLocationRelativeTo(main);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        soundTrack = new SoundTrack(); 
        
        //สร้างกล่องข้อความรับIP
        JLabel IP = new JLabel("IP:");
        IP.setForeground(Color.WHITE);
        IPField = new JTextField();

        //สร้างกล่องข้อความรับPort
        JLabel port = new JLabel("Port:");
        port.setForeground(Color.WHITE);
        portField = new JTextField();

        //สร้างปุ่มตกลง
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
            if (portField.getText().length() == 0 || IPField.getText().length() == 0) { // ใช้ length() ตรวจสอบความยาว
                JOptionPane.showMessageDialog(this, "Please enter a IP or Port.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
              try {
                GameClient gameClient = new GameClient(IPField.getText(), Integer.parseInt(portField.getText()));
                gameClient.startClient();

                dispose(); // ปิดหน้าต่าง
                main.dispose(); //ปิดหน้าหลัก
            } catch (NumberFormatException ex) {
                System.err.println("Invalid port input: " + portField.getText());
                JOptionPane.showMessageDialog(this, "Please enter a valid Port number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
            }
        });

        IP.setBounds(30, 50, 100, 30);
        IPField.setBounds(150, 50, 200, 30);
        port.setBounds(30, 100, 100, 30);
        portField.setBounds(150, 100, 200, 30);
        button.setBounds(150, 150, 200, 30);

        add(IP);
        add(IPField);
        add(port);
        add(portField);
        add(button);
   }
}