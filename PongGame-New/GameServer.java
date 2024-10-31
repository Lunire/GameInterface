
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameServer extends JFrame {
    // Attribute GameServer
    private int max_clients = 4;
    private int count_clients = 0;
    private int player1_y = 410;
    private int player2_y = 410;
    private int player3_y = 410;
    private int player4_y = 410;

    // Attribute GamePanel
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
    private int paddle_y;
    private int paddle_height = 150;
    private int paddle_width = 20;
    private int paddle_speedY = 10;

    // Object from Inner Class
    private GamePanel gamePanel = new GamePanel();
    private Paddle[] paddles = new Paddle[4];
    private Ball ball = new Ball();

    // Object from JAVA Socket
    private Socket[] clients = new Socket[max_clients];

    // GameServer Constructor || class GameServer
    public GameServer() {
        setTitle("Pong Server");
        setSize(1600, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(gamePanel);

        setVisible(true);
    }

    // Main Method || class GameServer
    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.startServer();
    }

    // startServer Method || class GameServer
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server Started. Waiting for clients..");

            // ใช้ Thread จาก class ClientAcceptor เพื่อรับเครื่องอื่นให้เข่้า server
            Thread acceptThread = new Thread(new ClientAcceptor(serverSocket));
            acceptThread.start();

            // ถ้าจำนวนคนมากกว่าหรือเท่ากับเกมจะเริ่ม
            while (gameRunning) { 
                if (count_clients >= 1) {
                    updateGame();
                }
                
                broadcastGameState();
                gamePanel.repaint();
                Thread.sleep(10);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // updateGame Method || class GameServer
    public void updateGame() {
        // ทำให้ ball ขยับ และตรวจสสอบคะแนน
        ball.ballMove();
        checkWinningCondition();
    }

    // checkWinningCondition Method || class GameServer
    public void checkWinningCondition() {
        // ถ้า score มากกว่าหรือเท่ากับ 10, จะทำให้ Thread ตาย
        if (score1 >= winningScore) {
            gameRunning = false;
        } 
        else if (score2 >= winningScore) {
            gameRunning = false;
        }
    }

    // broadcastGameState Method || class GameServer
    public void broadcastGameState() {
        // ส่งข้อมูลจาก server ให้แต่ละ client ด้วย String
        // synchronized (clients) {
            String gameState = player1_y + "," + player2_y + "," + player3_y + "," + player4_y + "," 
            + ball_x + "," + ball_y + "," + score1 + "," + score2;
            
            for (int i = 0; i < count_clients; i++) {
                try {
                    PrintWriter out = new PrintWriter(clients[i].getOutputStream(), true);
                    out.println(gameState);
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        // }
    }

    // Inner class ClientAcceptor
    class ClientAcceptor implements Runnable {
        ServerSocket serverSocket;

        // ClientAcceptor Constructor || Inner class ClientAcceptor
        public ClientAcceptor(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        // run Method Override from Runnable || Inner class ClientAcceptor
        @Override
        public void run() {
            // ถ้า gameRunning == false, Thread นี้ตาย และไม่สามารถรับเครื่องอื่นเข้ามาได้
            while (gameRunning) { 
                try {
                    Socket client = serverSocket.accept();
                    System.out.println("Client Connected : " + client.getInetAddress());

                    // synchronized (clients) {
                        if (count_clients < max_clients) {
                            clients[count_clients++] = client;

                            // ใช้ Thread จาก Inner class ClientHandler เพื่อจัดการการส่งข้อมูลให้ client
                            Thread clientHandlerThread = new Thread(new ClientHandler(client));
                            clientHandlerThread.start();
                        } 
                        else {
                            client.close();
                        }
                    // }
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Inner class ClientHandler
    class ClientHandler implements Runnable {
        Socket client;

        // ClientHandler Constructor || Inner class ClientHandler
        public ClientHandler(Socket client) {
            this.client = client;
        }

        // run Method Override from Runnable || Inner class ClientHandler
        @Override
        public void run() {
            // ทำการรับข้อมูลด้วย BufferedReader และปรับเปลี่ยน server ตามข้อมูลที่ได้รับ
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String input;

                while (gameRunning) {
                    while ((input = in.readLine()) != null) {
                        // synchronized (GameServer.this) {
                            // player_y เอาไว้แยกว่า player คนไหนที่คุม paddle
                            if (client == clients[0]) {
                                if (input.equals("UP") && player1_y > 160) {
                                    player1_y -= paddle_speedY;
                                }

                                if (input.equals("DOWN") && player1_y < 680) {
                                    player1_y += paddle_speedY;
                                }
                            }
                            else if (client == clients[1]) {
                                if (input.equals("UP") && player2_y > 160) {
                                    player2_y -= paddle_speedY;
                                }

                                if (input.equals("DOWN") && player2_y < 680) {
                                    player2_y += paddle_speedY;
                                }
                            }
                            else if (client == clients[2]) {
                                if (input.equals("UP") && player3_y > 160) {
                                    player3_y -= paddle_speedY;
                                }

                                if (input.equals("DOWN") && player3_y < 680) {
                                    player3_y += paddle_speedY;
                                }
                            }
                            else if (client == clients[3]) {
                                if (input.equals("UP") && player4_y > 160) {
                                    player4_y -= paddle_speedY;
                                }

                                if (input.equals("DOWN") && player4_y < 680) {
                                    player4_y += paddle_speedY;
                                }
                            }
                        }
                    }
                }
            // } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Inner class GamePanel
    class GamePanel extends JPanel {
        // GamePanel Constructor || Inner class GamePanel
        public GamePanel() {
            setBackground(Color.black);
        }

        // paintComponent Method Override from JPanel || Inner class GamePanel
        @Override
        protected void paintComponent(Graphics g) {
            // ล้าง panel ก่อนที่จะวาด
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