import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import java.sql.*;
import java.util.Random;

public class Panel extends JPanel implements KeyListener, ActionListener {


    private static int[] snakexlength = new int[1920];
    private static int[] snakeylength = new int[1080];

    static ButtonGroup bg = new ButtonGroup();


    private static boolean left = false;
    private static boolean right = false;
    private static boolean up = false;
    private static boolean down = false;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "admin";


    private static ImageIcon upmouth = new ImageIcon("upmouth.png");
    private static ImageIcon downmouth = new ImageIcon("downmouth.png");
    private static ImageIcon leftmouth = new ImageIcon("leftmouth.png");
    private static ImageIcon rightmouth = new ImageIcon("rightmouth.png");
    private ImageIcon snakeimage = new ImageIcon("1.png");
    private ImageIcon[] bodyimages = new ImageIcon[7];

    private  Image enemy;

    private Image[] backimageicons= new Image[10];
    private static String retname;
    private static int retscore;
    private static String text;
    private static String dtext;
    private static DisplayTime  dtime;
    private static Thread t1;
    private static int threadstart=0;
    private static int var=1;
    private int bodypos = 0;
    private static int moves = 0;
    private static int lengthofsnake = 2;
    private static Timer timer;

    private static int delay = 50;
    private static int bestscore = 0;
    private static int score = 0;
    private static int gameover = 0;

    private int[] enemyxpos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850, 875, 900, 925, 950, 975, 1000, 1025, 1050, 1075, 1100, 1125, 1150, 1175, 1200, 1225, 1250, 1275, 1300, 1325, 1350, 1375, 1400, 1425, 1450, 1475, 1500};
    private int[] enemyypos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775};
    private static int[] backimages={0,1,2,3,4,5,6,7,8,9};

    private static Random random = new Random();
    private int xpos = random.nextInt(enemyxpos.length);
    private int ypos = random.nextInt(enemyypos.length);
    private static int imageno=random.nextInt(backimages.length);


    public static void createAboutApp() {
        JOptionPane.showMessageDialog(null, "The player controls a dot, square, or object on a bordered plane. \n" +
                "As it moves forward, it leaves a trail behind, resembling a moving snake.\n " +
                "In some games, the end of the trail is in a fixed position, so the snake continually gets longer as it moves. \n" +
                "In another common scheme, the snake has a specific length, so there is a moving tail a fixed number of units away from the head.\n" +
                "The player loses when the snake runs into the screen border, a trail, other obstacle, or itself.", "ABOUT THIS APP", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void creatAboutMe() {
        JOptionPane.showMessageDialog(null, "I LOVE TO CODE , YOUR SUGGESTION ARE MOST WELCOME \n" +
                "FROM DEVELOPER SIDE--HAPPY GAMING :)-(: \n" +
                "Developer's Contact -- rahulbidawas@gmail.com ", "ABOUT THE DEVELOPER", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void newgame() {
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel("Enter Your Name: ");
        JTextField txt = new JTextField(20);
        panel.add(lbl);
        panel.add(txt);
        int selectedOption = JOptionPane.showOptionDialog(null, panel, "", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options , options[0]);
        if(JOptionPane.OK_OPTION==selectedOption)
        {
            text=txt.getText();
        }
        if(text==null || text.isBlank())
        {
            text=":) No-Name ):";
        }
        var=1;
        left=false;
        right=false;
        down=false;
        up=false;
        moves = 0;
        score = 0;
        lengthofsnake = 2;
        obj.repaint();
        gameover = 0;
        timer.start();
        right=true;
        timer.setDelay(50);
        dtime.stop(1);
        t1.interrupt();
        threadstart=1;
        int tempimgno=imageno;
        imageno=random.nextInt(backimages.length);
        while(tempimgno==imageno)
        {
            imageno=random.nextInt(backimages.length);
        }
    }

    public static void Easy() {
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel("Enter Your Name: ");
        JTextField txt = new JTextField(20);
        panel.add(lbl);
        panel.add(txt);
        int selectedOption = JOptionPane.showOptionDialog(null, panel, "", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options , options[0]);
        if(JOptionPane.OK_OPTION==selectedOption)
        {
            text=txt.getText();
        }
        if(text==null || text.isBlank())
        {
            text=":) No-Name ):";
        }
        var=1;
        left=false;
        right=false;
        down=false;
        up=false;
        moves = 0;
        score = 0;
        lengthofsnake = 2;
        obj.repaint();
        gameover = 0;
        timer.start();
        right=true;
        timer.setDelay(70);
        dtime.stop(1);
        t1.interrupt();
        threadstart=1;
        int tempimgno=imageno;
        imageno=random.nextInt(backimages.length);
        while(tempimgno==imageno)
        {
            imageno=random.nextInt(backimages.length);
        }
    }

    public static void Medium() {
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel("Enter Your Name: ");
        JTextField txt = new JTextField(20);
        panel.add(lbl);
        panel.add(txt);
        int selectedOption = JOptionPane.showOptionDialog(null, panel, "", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options , options[0]);
        if(JOptionPane.OK_OPTION==selectedOption)
        {
            text=txt.getText();
        }
        if(text==null || text.isBlank())
        {
            text=":) No-Name ):";
        }
        var=1;
        left=false;
        right=false;
        down=false;
        up=false;
        moves = 0;
        score = 0;
        lengthofsnake = 2;
        obj.repaint();
        gameover = 0;
        timer.start();
        right=true;
        timer.setDelay(50);
        dtime.stop(1);
        t1.interrupt();
        threadstart=1;
        int tempimgno=imageno;
        imageno=random.nextInt(backimages.length);
        while(tempimgno==imageno)
        {
            imageno=random.nextInt(backimages.length);
        }
    }

    public static void Hard() {
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel("Enter Your Name: ");
        JTextField txt = new JTextField(20);
        panel.add(lbl);
        panel.add(txt);
        int selectedOption = JOptionPane.showOptionDialog(null, panel, "", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options , options[0]);
        if(JOptionPane.OK_OPTION==selectedOption)
        {
            text=txt.getText();
        }
        if(text==null || text.isBlank())
        {
            text=":) No-Name ):";
        }
        var=1;
        left=false;
        right=false;
        down=false;
        up=false;
        moves = 0;
        score = 0;
        lengthofsnake = 2;
        obj.repaint();
        gameover = 0;
        timer.start();
        right=true;
        timer.setDelay(20);
        dtime.stop(1);
        t1.interrupt();
        threadstart=1;
        int tempimgno=imageno;
        imageno=random.nextInt(backimages.length);
        while(tempimgno==imageno)
        {
            imageno=random.nextInt(backimages.length);
        }
    }


    static JFrame obj ;
    static Panel objin ;
     public static void main(String[] arg) {

         obj = new JFrame();
         objin = new Panel();
         String[] options = {"OK"};
         JPanel panel = new JPanel();
         JLabel lbl = new JLabel("Enter Your Name: ");
         JTextField txt = new JTextField(20);
         panel.add(lbl);
         panel.add(txt);
         int selectedOption = JOptionPane.showOptionDialog(null, panel, "", JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options , options[0]);
         if(JOptionPane.OK_OPTION==selectedOption)
         {
             text=txt.getText();
         }
         if(text==null || text.isBlank())
         {
             text=":) No-Name ):";
         }
         Connection conn = null;
         Statement stmt = null;
         try {
             //STEP 3: Open a connection
             System.out.println("Connecting to database...");
             conn = DriverManager.getConnection(DB_URL, USER, PASS);

         }
         catch (Exception cd) {
             System.out.println("Connection Failed!");
         }
          try {
              //STEP 4: Execute a query
              System.out.println("Creating Statement...");
              stmt = conn.createStatement();
          }
          catch (SQLException cd) {
              System.out.println("Statement Creation Failed!");
          }
         try {

             String sql = "CREATE DATABASE snakegame";
             stmt.executeUpdate(sql);
             System.out.println("Database Created Successfully...");
         }
         catch (SQLException cd) {
             System.out.println("Database Creation Failed!");
         }
         try {

             String sql = "use snakegame;";
             stmt.executeUpdate(sql);
             System.out.println("Database In Use");
         }
         catch (SQLException cd) {
             System.out.println("Database Selection Failed!");
         }

         try {
             String sql = "CREATE TABLE playersdata(name varchar(20),playTime int(10),score int(10),startTime time,finishTime time,datePlayed date);";
             stmt.executeUpdate(sql);
             System.out.println("Table Created Successfully...");

         }
         catch (SQLException ct)
         {
             System.out.println("Table Creation Failed!");
         }
         try {
             String sql = "select name,score from playersdata where playtime IN(select min(playtime) from playersdata where score=(select max(score) from playersdata));";
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs=statement.executeQuery();
             rs.next();
             retname=rs.getString("name");
             retscore=rs.getInt("score");
             System.out.println("Data Selection Successfull!");
             bestscore=retscore;
             dtext=retname;
             conn.close();
         }
         catch (SQLException ct)
         {
             System.out.println("Data Selection Failed!");
         }


         System.out.println("Goodbye!");

         threadstart=1;
        JMenu menu1, menu2, menu3;
        JMenuItem menu11, menu21, menu22, menu23, menu31, menu32;
        JMenuBar mb;
        mb = new JMenuBar();
        menu1 = new JMenu("FILE");
        menu2 = new JMenu("SET LEVEL");
        menu3 = new JMenu("HELP");

        menu11 = new JMenuItem("NEW GAME");
        menu21 = new JMenuItem("EASY");
        menu22 = new JMenuItem("MEDIUM");
        menu23 = new JMenuItem("HARD");
        menu31 = new JMenuItem("ABOUT SNAKE GAME");
        menu32 = new JMenuItem("ABOUT-ME");
        menu1.add(menu11);
        menu2.add(menu21);
        menu2.add(menu22);
        menu2.add(menu23);
        menu3.add(menu31);
        menu3.add(menu32);

        mb.add(menu1);
        mb.add(menu2);
        mb.add(menu3);
        obj.setJMenuBar(mb);

        ActionListener menu11al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                newgame();

            }
        };


        ActionListener menu21al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // obj.setVisible(false);
                Easy();
            }
        };
        ActionListener menu22al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Medium();
            }
        };
        ActionListener menu23al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Hard();
            }
        };

        ActionListener menu31al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                createAboutApp();
            }
        };

        ActionListener menu32al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                creatAboutMe();
            }
        };

        menu11.addActionListener(menu11al);
        menu21.addActionListener(menu21al);
        menu22.addActionListener(menu22al);
        menu23.addActionListener(menu23al);
        menu31.addActionListener(menu31al);
        menu32.addActionListener(menu32al);


        obj.setResizable(false);
        obj.setTitle("SNAKE GAME ");
        obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //obj.setUndecorated(true);
        obj.setBackground(Color.BLACK);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         obj.add(objin);
        objin.setVisible(true);
        obj.setVisible(true);

         dtime=new DisplayTime();



     }
    Panel() {
        bodyimages[0] = new ImageIcon("1.png");
        bodyimages[1] = new ImageIcon("2.png");
        bodyimages[2] = new ImageIcon("3.png");
        bodyimages[3] = new ImageIcon("4.png");
        bodyimages[4] = new ImageIcon("5.png");
        bodyimages[5] = new ImageIcon("6.png");
        bodyimages[6] = new ImageIcon("7.png");
        try {

            backimageicons[0] = ImageIO.read(new File("C:\\Program Files\\SnakeGame\\back1.jpg"));
            backimageicons[1] = ImageIO.read(new File("C:\\Program Files\\SnakeGame\\back2.jpg"));
            backimageicons[2] = ImageIO.read(new File("C:\\Program Files\\SnakeGame\\back3.jpg"));
            backimageicons[3] = ImageIO.read(new File("C:\\Program Files\\SnakeGame\\back4.jpg"));
            backimageicons[4] = ImageIO.read(new File("C:\\Program Files\\SnakeGame\\back5.jpg"));
            backimageicons[5] = ImageIO.read(new File("C:\\Program Files\\SnakeGame\\back6.jpg"));
            backimageicons[6] = ImageIO.read(new File("C:\\Program Files\\SnakeGame\\back7.jpg"));
            backimageicons[7] = ImageIO.read(new File("C:\\Program Files\\SnakeGame\\back8.jpg"));
            backimageicons[8] = ImageIO.read(new File("C:\\Program Files\\SnakeGame\\back9.jpg"));
            backimageicons[9]=ImageIO.read(new File("C:\\Program Files\\SnakeGame\\back10.jpg"));
            enemy=ImageIO.read(new File("C:\\Program Files\\SnakeGame\\enemy.png"));
        }
        catch (Exception e)
        {
            System.out.println("Image File Not Found!");
        }
        addKeyListener(this);
        setFocusable(true);
        timer = new Timer(delay, this);
        timer.start();
    }


    public void paint(Graphics g) {


        SimpleAudioPlayer ap = new SimpleAudioPlayer();
        try {
            ap.playempty();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        if (moves == 0) {
            snakexlength[2] = 50;
            snakexlength[1] = 75;
            snakexlength[0] = 100;

            snakeylength[2] = 100;
            snakeylength[1] = 100;
            snakeylength[0] = 100;

        }



        g.setColor(Color.WHITE);
        g.drawRect(0, 0, 1920, 50);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1920, 50);

        // draw gamebox
        g.setColor(Color.WHITE);
        g.drawRect(0, 51, 1920, 1075);

        //draw background for gamebox
        g.setColor(Color.WHITE);
        g.fillRect(1, 51, 1920, 1075);


           g.drawImage(backimageicons[imageno], 0, 0, 1920, 1080, 0, 0, 1920, 1080, null);

            if(imageno==0 || imageno==2 ||imageno==3 ||imageno==4  || imageno==6 || imageno==8  )
            {
                g.setColor(Color.WHITE);
            }
            else
            {
                g.setColor(Color.BLACK);
            }

        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("(: SNAKE :)", 710, 30);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Score:   " + score, 1000, 30);

        //Best Score
        g.drawString("Best Score:   " + bestscore+"    By:  " + dtext, 1150, 30);

        g.setFont(new Font("arial", Font.ITALIC, 7));
        g.drawString("By-RL: ", 750, 40);
        //draw length of snake

        g.setFont(new Font("arial", Font.PLAIN, 14));

        //playtime
        g.drawString("PlayTime : "+dtime.gettime(),850,30);
        if (score > bestscore) {
            dtext=text;

        }
        //adding to playbox
        rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);

        for (int a = 0; a < lengthofsnake; a++) {
            if (a == 0 && right) {
                rightmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
            }
            if (a == 0 && left) {
                leftmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);

            }
            if (a == 0 && down) {
                downmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
            }
            if (a == 0 && up) {
                upmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
            }
            if (a != 0) {
                snakeimage.paintIcon(this, g, snakexlength[a], snakeylength[a]);
            }
        }
        if (enemyxpos[xpos] == snakexlength[0] && enemyypos[ypos] == snakeylength[0]) {
            try {

                ap.playeat();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            if (bodypos == 7) {
                bodypos = 0;
            }
            snakeimage = bodyimages[bodypos];
            bodypos++;


            score++;
            lengthofsnake++;

            xpos = random.nextInt(enemyxpos.length);
            ypos = random.nextInt(enemyypos.length);


        }
        g.drawImage(enemy,enemyxpos[xpos], enemyypos[ypos],30,25,null);

        for (int b = 1; b < lengthofsnake; b++) {
            if (snakexlength[b] == snakexlength[0] && snakeylength[b] == snakeylength[0]) {

                Connection conn = null;

                try {
                    //STEP 3: Open a connection
                    System.out.println("Connecting to database...");
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);

                }
                catch (Exception cd) {
                    System.out.println("Connection Failed!");
                }

                try {

                    String sql = "use snakegame;";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.executeQuery();
                    System.out.println("Database In Use");
                }
                catch (SQLException cd) {
                    System.out.println("Database Selection Failed!");
                }
                try {
                    java.util.Date date1=new java.util.Date();

                    java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
                    java.sql.Timestamp endTime=new java.sql.Timestamp(date1.getTime());

                    java.util.Date date2=new java.util.Date();
                    date2.setTime(date2.getTime() -dtime.gettime()*1000);
                    java.sql.Timestamp startTime=new java.sql.Timestamp(date2.getTime());
                    String sql = "INSERT INTO playersdata(name,playtime,score,startTime,finishTime,datePlayed)VALUES(?,?,?,?,?,?);";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1,text);
                    statement.setString(2,String.valueOf(dtime.gettime()));
                    statement.setString(3,String.valueOf(score));
                    statement.setString(4,String.valueOf(startTime));
                    statement.setString(5,String.valueOf(endTime));
                    statement.setString(6,String.valueOf(sqlDate));
                    statement.executeUpdate();
                    System.out.println("Data Insertion Successful");
                }
                catch (SQLException ct)
                {
                    System.out.println(ct);
                }
                try {
                    String sql = "select name,score from playersdata where playtime IN(select min(playtime) from playersdata where score=(select max(score) from playersdata));";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    ResultSet rs=statement.executeQuery();
                    rs.next();
                    retname=rs.getString("name");
                    retscore=rs.getInt("score");
                    System.out.println("Data Selection Successfull!");
                    bestscore=retscore;
                    dtext=retname;
                    conn.close();
                }
                catch (SQLException ct)
                {
                    System.out.println("Data Selection Failed!");
                }
                dtime.stop(1);
                t1.interrupt();
                gameover = 1;
                timer.stop();

                  if(var==1) {
                      try {

                          ap.playdie();
                          var=0;

                      } catch (UnsupportedAudioFileException e) {
                          e.printStackTrace();
                      } catch (IOException e) {
                          e.printStackTrace();
                      } catch (LineUnavailableException e) {
                          e.printStackTrace();
                      }
                  }
                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString(":( Game Over ):", 50, 30);
                g.setFont(new Font("arial", Font.PLAIN, 15));
                g.drawString(":) Press SPACEBAR to restart (:", 250, 30);
            }
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (right) {

            for (int r = lengthofsnake - 1; r >= 0; r--) {
                snakeylength[r + 1] = snakeylength[r];
            }
            for (int r = lengthofsnake; r >= 0; r--) {
                if (r == 0) {
                    snakexlength[r] = snakexlength[r] + 25;
                } else {
                    snakexlength[r] = snakexlength[r - 1];
                }
                if (snakexlength[r] > 1500) {
                    snakexlength[r] = 25;
                }
            }
            repaint();

        }
        if (left) {

            for (int r = lengthofsnake - 1; r >= 0; r--) {
                snakeylength[r + 1] = snakeylength[r];
            }
            for (int r = lengthofsnake; r >= 0; r--) {
                if (r == 0) {
                    snakexlength[r] = snakexlength[r] - 25;
                } else {
                    snakexlength[r] = snakexlength[r - 1];
                }
                if (snakexlength[r] < 25) {
                    snakexlength[r] = 1500;
                }
            }
            repaint();
        }
        if (up) {
            {
                for (int r = lengthofsnake - 1; r >= 0; r--) {
                    snakexlength[r + 1] = snakexlength[r];
                }
                for (int r = lengthofsnake; r >= 0; r--) {
                    if (r == 0) {
                        snakeylength[r] = snakeylength[r] - 25;
                    } else {
                        snakeylength[r] = snakeylength[r - 1];
                    }
                    if (snakeylength[r] < 75) {
                        snakeylength[r] = 775;
                    }
                }
                repaint();
            }
        }
        if (down) {
            {
                for (int r = lengthofsnake - 1; r >= 0; r--) {
                    snakexlength[r + 1] = snakexlength[r];
                }
                for (int r = lengthofsnake; r >= 0; r--) {
                    if (r == 0) {
                        snakeylength[r] = snakeylength[r] + 25;
                    } else {
                        snakeylength[r] = snakeylength[r - 1];
                    }
                    if (snakeylength[r] > 775) {
                        snakeylength[r] = 75;
                    }
                }
                repaint();
            }
        }
    }



    @Override
    public void keyTyped(KeyEvent e) {
    }
    int free=1;

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameover == 1) {
               var=1;
                left=false;
                right=false;
                down=false;
                up=false;
                moves = 0;
                score = 0;
                lengthofsnake = 2;
                repaint();
                gameover = 0;
                timer.start();
                right=true;
                threadstart=1;
                int tempimgno=imageno;
                imageno=random.nextInt(backimages.length);
                while(tempimgno==imageno)
                {
                    imageno=random.nextInt(backimages.length);
                }

            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (gameover == 1) {
                timer.stop();
            }
            if(threadstart==1)
            {
                try
                {     t1=new Thread(dtime);
                    t1.start();
                    threadstart=0;
                }
                catch (Exception ex)
                {
                    System.out.println("t1 start exception ");
                }
            }


            free=0;

                moves++;
                right = true;
                if (!left) {
                    right = true;
                } else {
                    right = false;
                    left = true;
                }

                up = false;
                down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (gameover == 1) {
                timer.stop();
            }
            if(threadstart==1)
            {
                try
                {     t1=new Thread(dtime);
                    t1.start();
                    threadstart=0;
                }
                catch (Exception ex)
                {
                    System.out.println("t1 start exception ");
                }
            }

            free=0;
                moves++;
                left = true;
                if (!right) {
                    left = true;
                } else {
                    left = false;
                    right = true;
                }

                up = false;
                down = false;


        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (gameover == 1) {
                timer.stop();
            }
            if(threadstart==1)
            {
                try
                {     t1=new Thread(dtime);
                    t1.start();
                    threadstart=0;
                }
                catch (Exception ex)
                {
                    System.out.println("t1 start exception ");
                }
            }
            free=0;
                moves++;
                up = true;
                if (!down) {
                    up = true;
                } else {
                    up = false;
                    down = true;
                }

                left = false;
                right = false;

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (gameover == 1) {
                timer.stop();
            }
            if(threadstart==1)
            {
                try
                {     t1=new Thread(dtime);
                    t1.start();
                    threadstart=0;
                }
                catch (Exception ex)
                {
                    System.out.println("t1 start exception ");
                }
            }

            free=0;

                moves++;
                down = true;
                if (!up) {
                    down = true;
                } else {
                    down = false;
                    up = true;
                }

                left = false;
                right = false;

            }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}







