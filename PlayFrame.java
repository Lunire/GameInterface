import javax.swing.*;
import java.awt.*;

public class PlayFrame extends JDialog{
    private JTextField UIDField;
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

        soundTrack = new SoundTrack(); 
        
        JLabel UID = new JLabel("UID:");
        UID.setForeground(Color.WHITE);
        UIDField = new JTextField();

        JLabel user = new JLabel("UserName:");
        user.setForeground(Color.WHITE);
        usernameField = new JTextField();
        
        button = new JButton("OK");
        button.addActionListener(e -> {
            soundTrack.sound("Materials/mouse-click-sound-233951.wav");
            //Comming Soon//
        });

        UID.setBounds(30, 50, 100, 30);
        UIDField.setBounds(150, 50, 200, 30);
        user.setBounds(30, 100, 100, 30);
        usernameField.setBounds(150, 100, 200, 30);
        button.setBounds(150, 150, 100, 30);

        add(UID);
        add(UIDField);
        add(user);
        add(usernameField);
        add(button);
   }
}
