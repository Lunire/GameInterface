
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameClient extends JFrame {
    // Attribute PongClient
    private int player1_y = 410;
    private int player2_y = 410;
    private int player3_y = 410;
    private int player4_y = 410;
    int port;
    String IP;

    // Attribute PongPanel
    private int winningScore = 10;
    private boolean gameRunning = true;

    // Attribute Ball
    private int ball_x = 785;
    private int ball_y = 490;
    private int ball_size = 30;
    private int ball_speedX = 5;
    private int ball_speedY = 5;
    private int score1 = 0;
    private int score2 = 0;

    // Attribute Paddle
    private int paddle_x;
    private int paddle_y;
    private int paddle_height = 150;
    private int paddle_width = 20;

    // Object from Inner Class
    GamePanel gamePanel = new GamePanel();
    Paddle[] paddles = new Paddle[4];
    Ball ball = new Ball();

    // Object from JAVA Socket
    Socket socket;
    PrintWriter out;
    BufferedReader in;

    // GameClient Constructor || class GameClient
    public GameClient(String IP, int port) {
        setTitle("Pong Client");
        setSize(1600, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.IP = IP;
        this.port = port;

        gamePanel.setFocusable(true);
        add(gamePanel);

        setVisible(true);
    }

    // main Method || class GameClient
    // public static void main(String[] args) {
    //     GameClient gameClient = new GameClient("192.168.1.7");
    //     gameClient.startClient();
    // }

    // startClient Method || class GameClient
    public void startClient() {
        // เชื่อมต่อกับ server ด้วย IP และ Port ของ server
        try {
            socket = new Socket(IP, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // ใช้ Thread จาก class GameStateReceiver เพื่อรับข้อมูลที่ได้มาจาก server
            Thread receiveThread = new Thread(new GameStateReceiver());
            receiveThread.start();

            gamePanel.addKeyListener(new KeyAdapter() {

                @Override
                public void keyPressed(KeyEvent e) {
                    // ใช้ Thread จาก class KeyPressHandler เพื่อส่งข้อมูลของแต่ละ client ไปให้ server
                    Thread keyPressThread = new Thread(new KeyPressHandler(e));
                    keyPressThread.start();
                }
            });
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // checkWinningCondition Method || class GameClient
    public void checkWinningCondition() {
        // ถ้า score มากกว่าหรือเท่ากับ 10, จะทำให้ Thread ตาย
        if (score1 >= winningScore) {
            gameRunning = false;
            // แสดงหน้าว่าทีมที่ 1 ชนะ
            JOptionPane.showMessageDialog(this, "Team 1 wins!");
        } 
        else if (score2 >= winningScore) {
            gameRunning = false;
            // แสดงหน้าว่าทีมที่ 2 ชนะ
            JOptionPane.showMessageDialog(this, "Team 2 wins!");
        }
    }

    // Inner class GameStateReceiver
    class GameStateReceiver implements Runnable {

        // run Method Overrid from Runnable || Inner class GameStateReceiver
        @Override
        public void run() {
            // ถ้า gameRunning == false, Thread นี้ตาย และไม่สามารถรับข้อมูล gameState จาก server ได้
            while (gameRunning) {
                String gameState;

                try {
                    while ((gameState = in.readLine()) != null) {
                        // แยกข้อมูลจาก gameState ให้เป็น array ด้วย split
                        String[] tokens = gameState.split(",");

                        player1_y = Integer.parseInt(tokens[0]);
                        player2_y = Integer.parseInt(tokens[1]);
                        player3_y = Integer.parseInt(tokens[2]);
                        player4_y = Integer.parseInt(tokens[3]);
                        ball_x = Integer.parseInt(tokens[4]);
                        ball_y = Integer.parseInt(tokens[5]);
                        score1 = Integer.parseInt(tokens[6]);
                        score2 = Integer.parseInt(tokens[7]);

                        gamePanel.repaint();
                        checkWinningCondition();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } 
        }
    }

    // Inner class KeyPressHandler
    class KeyPressHandler implements Runnable {
        private KeyEvent e;

        // KeyPressHandler Constructor || Inner class KeyPressHandler
        public KeyPressHandler(KeyEvent e) {
            this.e = e;
        }

        // run Method Override from Runnable || Inner class KeyPressHandler
        @Override
        public void run() {
            try {
                // ถ้ากดปุ่มลูกศร จะใส่ข้อมูล String ลงใน PrintWriter เพื่อเตรียมส่งให้ server
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    out.println("UP");
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    out.println("DOWN");
                }
            } 
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Inner class PongPanel
    class GamePanel extends JPanel {
        public GamePanel() {
            setBackground(Color.black);
        }

        // paintComponent Method Override from JPanel || Inner class GamePanel
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // สร้างกรอบสนาม
            g.setColor(Color.white);
            g.fillRect(20, 150, 10, 680);
            g.fillRect(20, 830, 1540, 10);
            g.fillRect(20, 150, 1540, 10);
            g.fillRect(1550, 150, 10, 680);

            // สร้างเส้นแบ่งกึ่งกลาง
            g.setColor(Color.white);
            Graphics2D graphics2d = (Graphics2D) g;
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0);
            graphics2d.setStroke(dashed);
            graphics2d.drawLine(798, 160, 798, 830);

            // สร้างเส้นกำหนดตำแหน่งของ paddle
            g.setColor(Color.white);
            g.fillRect(120, 150, 1, 680);
            g.fillRect(320, 150, 1, 680);
            g.fillRect(1270, 150, 1, 680);
            g.fillRect(1460, 150, 1, 680);

            // สร้างกล่องแสดงข้อมูล (แค่กล่องเปล่าๆ)
            g.setColor(Color.WHITE);
            g.fillRect(700, 20, 200, 110);
            g.fillRect(20, 20, 300, 110);
            g.fillRect(1260, 20, 300, 110);
            g.fillRect(350, 20, 100, 110);
            g.fillRect(1130, 20, 100, 110);

            // สร้างคะแนนที่เป็นเป้าหมายในการจบเกม
            g.setColor(Color.black);
            g.setFont(new Font("Tahoma", Font.BOLD, 40));
            g.drawString("GOAL", 742, 70);
            g.drawString("" + winningScore, 772, 110);

            // วาดคะแนนของแต่ละทีม
            g.setColor(Color.black);
            g.setFont(new Font("Tahoma", Font.BOLD, 40));
            g.drawString(String.valueOf(score1), 387, 88);
            g.drawString(String.valueOf(score2), 1170, 88);

            // สร้างตัวอักษร Team, Player
            g.setColor(Color.black);
            g.setFont(new Font("Tahoma", Font.BOLD, 20));
            g.drawString("Team1", 130, 40);
            g.drawString("Team2", 1380, 40);
            g.drawString("Player 1", 40, 75);
            g.drawString("Player 2", 1280, 75);
            g.drawString("Player 3", 40, 105);
            g.drawString("Player 4", 1280, 105);

            // สร้างแถบสีให้ player (อยู่ที่กล่องข้อมูล)
            g.setColor(Color.red);      // สีแดง player1
            g.fillRect(150, 58, 150, 20);
            g.setColor(Color.orange);   // สีส้ม player2
            g.fillRect(1390, 58, 150, 20);
            g.setColor(Color.blue);     // สีน้ำเงิน player3
            g.fillRect(150, 88, 150, 20);
            g.setColor(Color.green);    // สีเขียว player4
            g.fillRect(1390, 88, 150, 20);

            // สร้าง paddle
            paddles[0] = new Paddle(110, player1_y, paddle_width, paddle_height);
            paddles[1] = new Paddle(1450, player2_y, paddle_width, paddle_height);
            paddles[2] = new Paddle(310, player3_y, paddle_width, paddle_height);
            paddles[3] = new Paddle(1260, player4_y, paddle_width, paddle_height);

            for (int i = 0; i < paddles.length; i++) {
                if (i == 0) {g.setColor(Color.red);}
                if (i == 1) {g.setColor(Color.orange);}
                if (i == 2) {g.setColor(Color.blue);}
                if (i == 3) {g.setColor(Color.green);}
    
                paddles[i].draw(g);
            }

            // สร้าง ball
            ball.draw(g);
        }
    }

    // Inner class Ball
    class Ball {
        // draw Method || Inner class Ball
        public void draw(Graphics g) {
            g.setColor(Color.white);
            g.fillOval(ball_x, ball_y, ball_size, ball_size);
        }

        // ballMove Method || Inner class Ball
        public void ballMove() {
            // เคลื่อนที่แนวทะแยง (แนวเฉียง)
            ball_x = ball_x + ball_speedX;
            ball_y = ball_y + ball_speedY;

            // ถ้า ball ชนกับขอบ จะทำการเปลี่ยนทิศทาง
            if (ball_y < 160 || ball_y > 800) {
                if (ball_y < 160) {ball_y = 160;}
                else if (ball_y > 800) {ball_y = 800;}
                ball_speedY = randomNumber();
            }

            // ถ้า ball ชนกับ paddle จะเปลี่ยนทิศทาง
            for (int i = 0; i < paddles.length; i++) {
                if (ball.getBounds().intersects(paddles[i].getBounds())) {
                    ball_speedX = -ball_speedX;
                    ball_speedY = randomNumber();
                    // break;
                }
            }

            scoring();
        }

        // scoring Method || Inner class Ball
        public void scoring() {
            // ถ้าบอลเข้าฝั่งทีมที่ 1, ทีมที่ 2 จะได้คะแนน
            if (ball_x <= 20) {
                score2++;
                resetBall();
            }
            // ถ้าบอลเข้าฝั่งทีมที่ 2, ทีมที่ 1 จะได้คะแนน
            else if (ball_x >= 1550) {
                score1++;
                resetBall();
            }
        }

        // resetBall Method || Inner class Ball
        public void resetBall() {
            // นำ ball กลับมาที่ตรงกลาง และสุ่มความเร็ว
            ball_x = 785;
            ball_y = 490;
            ball_speedX = randomNumber();
            ball_speedY = randomNumber();
        }

        // randomNumber Method || Inner class Ball
        public int randomNumber() {
            // สุ่มตัวเลขและคินค่าตัวเลข 1 ถึง 5 และ -1 ถึง -5โดยที่ไม่ใช่เลข 0
            Random random = new Random();
            int number = 0;
            boolean isNegative = random.nextBoolean();

            if (isNegative) {
                while (number == 0) {
                    number = random.nextInt(6) - 10;
                }
            }
            else {
                while (number == 0) {
                    number = random.nextInt(6) + 5;
                }
            }

            return number;
        }

        // getBounds Method || Inner class Ball
        public Rectangle getBounds() {
            // คืนค่าขอบเขตของ ball
            return new Rectangle(ball_x, ball_y, ball_size, ball_size);
        }
    }

    // Inner class Paddle
    class Paddle {
        // Attribute Paddle
        private int x, y, width, height;

        // Paddle Constructor || Inner class Paddle
        public Paddle(int x, int y, int width, int height) {
            // ใช้แค่กำหนดว่าจะสร้าง paddle ที่ตรงไหนบ้าง
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        // draw Method || Inner class Paddle
        public void draw(Graphics g) {
            g.fillRect(x, y, width, height);
        }

        // getBounds Method || Inner class Paddle
        public Rectangle getBounds() {
            // คืนค่าขอบเขตของ paddle
            return new Rectangle(x, y, width, height);
        }
    }
}