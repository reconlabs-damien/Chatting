package Client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;


public class emoticon extends JFrame {
   private JPanel a = new JPanel();
      emoticon(){
      Emoticon_init();
      
      
   }
   void Emoticon_init() {
      JFrame b = new JFrame("이모티콘박스");
      b.setTitle("EMOTICON");
      b.setBounds(100, 100, 348, 362);
      a.setBackground(Color.WHITE);
      a.setBorder(new EmptyBorder(5, 5, 5, 5));
      a.setLayout(null);
      b.getContentPane().add(a);
      JButton Image1 = new JButton("");
      Image1.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/in_love.png"));
      Image1.setHorizontalAlignment(SwingConstants.CENTER);
      Image1.setBounds(12, 6, 100, 100);
      a.add(Image1);
            
      JButton Image2 = new JButton("");
      Image2.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/angry.png"));
      Image2.setHorizontalAlignment(SwingConstants.CENTER);
      Image2.setBounds(124, 6, 100, 100);
      a.add(Image2);
      
      JButton Image3 = new JButton("");
      Image3.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/bored.png"));
      Image3.setHorizontalAlignment(SwingConstants.CENTER);
      Image3.setBounds(236, 6, 100, 100);
      a.add(Image3);
      
      JButton Image4 = new JButton("");
      Image4.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/confused.png"));
      Image4.setHorizontalAlignment(SwingConstants.CENTER);
      Image4.setBounds(12, 118, 100, 100);
      a.add(Image4);
      
      JButton Image5 = new JButton("");
      Image5.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/crying.png"));
      Image5.setHorizontalAlignment(SwingConstants.CENTER);
      Image5.setBounds(124, 118, 100, 100);
      a.add(Image5);
      
      JButton Image6 = new JButton("");
      Image6.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/embarrassed.png"));
      Image6.setHorizontalAlignment(SwingConstants.CENTER);
      Image6.setBounds(236, 118, 100, 100);
      a.add(Image6);
      
      JButton Image7 = new JButton("");
      Image7.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/happy.png"));
      Image7.setHorizontalAlignment(SwingConstants.CENTER);
      Image7.setBounds(12, 230, 100, 100);
      a.add(Image7);
      
      JButton Image8 = new JButton("");
      Image8.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/ill.png"));
      Image8.setHorizontalAlignment(SwingConstants.CENTER);
      Image8.setBounds(124, 230, 100, 100);
      a.add(Image8);
      
      JButton Image9 = new JButton("");
      Image9.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/surprised.png"));
      Image9.setHorizontalAlignment(SwingConstants.CENTER);
      Image9.setBounds(236, 230, 100, 100);
      a.add(Image9);
      
      Image1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting?"+Client.my_room+"?"+"/Users/jun/eclipse-workspace/Chatting/img/in_love.png");
            }  
         }); 
      Image2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting?"+Client.my_room+"?"+"/Users/jun/eclipse-workspace/Chatting/img/angry.png");
            }  
         });
      Image3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting?"+Client.my_room+"?"+"/Users/jun/eclipse-workspace/Chatting/img/bored.png");
               
            }
         });
      Image4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting?"+Client.my_room+"?"+"/Users/jun/eclipse-workspace/Chatting/img/confused.png");
            } 
         });
      Image5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting?"+Client.my_room+"?"+"/Users/jun/eclipse-workspace/Chatting/img/crying.png");
            }
         });
      Image6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting?"+Client.my_room+"?"+"/Users/jun/eclipse-workspace/Chatting/img/embarrassed.png");
            }
         });
      Image7.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting?"+Client.my_room+"?"+"/Users/jun/eclipse-workspace/Chatting/img/happy.png");
            }
         });
      Image8.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting?"+Client.my_room+"?"+"/Users/jun/eclipse-workspace/Chatting/img/ill.png");
            }
         });
      Image9.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e) {
             Client.send_message("Chatting?"+Client.my_room+"?"+"/Users/jun/eclipse-workspace/Chatting/img/surprised.png");
          }
       });
      
      b.setVisible(true);
      
   }
   
public static void main(String[] args) {
      
      new emoticon();

   }
}