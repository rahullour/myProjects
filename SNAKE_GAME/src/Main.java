import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] arg)
    {
        JFrame obj=new JFrame();
        Panel objin=new Panel();
        obj.setResizable(false);
        obj.setTitle("SNAKE GAME ");
        obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        obj.setUndecorated(false);

        obj.setBackground(Color.BLACK);
         obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(objin);
        obj.setVisible(true);

    }

}
