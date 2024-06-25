package chatapplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import static chatapplication.Sender.dout;
import static chatapplication.Sender.f1;
import static chatapplication.Sender.formatLabel;
import static chatapplication.Sender.vertical;

/**
 *
 * @author divya
 */
public class Reciever implements ActionListener
{
    JTextField text;
    static JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static JFrame f1 = new JFrame();
    
    Reciever()
    {
        f1.setLayout(null);
        
        JPanel p1=new JPanel();
        p1.setBackground(new Color(40,100,40));
        p1.setBounds(0,0,400,80);
        p1.setLayout(null);
        f1.add(p1);
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource(""));
        Image i2=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel backIcon=new JLabel(i3);
        backIcon.setBounds(5,20,25,25);
        p1.add(backIcon);
        
        backIcon.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent ae){
               System.exit(0);
           } 
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("ImageIcon/black.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        
        ImageIcon i14=new ImageIcon(ClassLoader.getSystemResource(""));
        Image i15=i14.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i16=new ImageIcon(i15);
        JLabel more=new JLabel(i16);
        more.setBounds(360,10,10,25);
        p1.add(more);
        
        JLabel name=new JLabel("Shivani");
        name.setBounds(120,10,100,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SARIF",Font.BOLD,20));
        p1.add(name);
        
        JLabel status=new JLabel("Online");
        status.setBounds(120,35,100,20);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SARIF",Font.PLAIN,15));
        p1.add(status);
        
        a1= new JPanel();
        a1.setBounds(5,85,390,600);
        f1.add(a1);
        
        text=new JTextField();
        text.setBounds(5,690,300,40);
        text.setFont(new Font("SAN_SARIF",Font.PLAIN,15));
        f1.add(text);
        
        JButton send=new JButton("SEND");
        send.setBounds(310,690,80,40);
        send.setBackground(new Color(10,100,100));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SARIF",Font.PLAIN,15));
        f1.add(send);
        
        f1.setSize(400,800);
        f1.setUndecorated(true);
        f1.setLocation(800,10);
        f1.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
        String out=text.getText();
        a1.setLayout(new BorderLayout());
        
        JPanel p2=formatLabel(out);
        
        JPanel right =new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        
        a1.add(vertical,BorderLayout.PAGE_START);
        
        dout.writeUTF(out);
        
        text.setText("");
        f1.repaint();
        f1.validate();
        f1.invalidate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out)
    {
        JPanel p= new JPanel(); 
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        
        JLabel output=new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,15));
        output.setBackground(new Color(80,100,100));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        
        p.add(output);
        
        JLabel time= new JLabel();
        DateTimeFormatter f= DateTimeFormatter.ofPattern("HH:mm:ss");
        time.setText(LocalTime.now().format(f));
        p.add(time);
        
        return p;
    }
    public static void main (String []dj)
    {
        new Reciever();
        try
        {
            Socket s=new Socket("127.0.0.1",6001);
            
                
                DataInputStream din=new DataInputStream(s.getInputStream());
                dout= new DataOutputStream(s.getOutputStream());
                
                while(true)
                {
                    a1.setLayout(new BorderLayout());
                    String msg = din.readUTF();
                    JPanel p= formatLabel(msg);
                    
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(p,BorderLayout.LINE_START);
                    vertical.add(left);
                    f1.validate();
                }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
