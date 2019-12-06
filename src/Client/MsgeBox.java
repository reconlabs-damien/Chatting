package Client;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MsgeBox { //메시지박스클래스 (아이콘과함께 뜨는 메시지박스)

   public void messageBox(Object obj , String message){
        JOptionPane.showMessageDialog( (Component)obj , message); //알림창을띄우는 함수
    }
}