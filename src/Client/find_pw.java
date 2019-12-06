package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.FileDialog;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class find_pw extends JFrame implements ActionListener,ItemListener{
	private JTextField tf_name; // 입력된 이름
	private JTextField tf_id;
	private JButton btn_idok = new JButton("확인"); // 확인
	private JButton btn_idcancel = new JButton("취소"); // 취소
	private String name ="noname"; 
	private String pwd;
	private final JLabel lblNewLabel = new JLabel("");
	
	
	public find_pw() {
		setTitle("FInd PW");
		init();
		start();
	}
	public void start(){
		btn_idok.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		btn_idok.addActionListener(this);
		btn_idcancel.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		btn_idcancel.addActionListener(this);
	}
	
	public void init() {
		
		setBounds(100, 100, 211, 167);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("I D : ");
		lblNewLabel_1.setBounds(29, 27, 38, 15);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("이 름 : ");
		lblNewLabel_2.setBounds(29, 64, 38, 15);
		getContentPane().add(lblNewLabel_2);
		
		tf_id = new JTextField();
		tf_id.setFont(new Font("돋움체", Font.PLAIN, 12));
		tf_id.setBounds(75, 24, 116, 21);
		getContentPane().add(tf_id);
		tf_id.setColumns(10);
		
		tf_name = new JTextField();
		tf_name.setFont(new Font("돋움체", Font.PLAIN, 12));
		tf_name.setBounds(75, 61, 116, 21);
		getContentPane().add(tf_name);
		tf_name.setColumns(10);
		
		
		btn_idok.setBounds(16, 91, 75, 36);
		getContentPane().add(btn_idok);
		
		
		btn_idcancel.setBounds(103, 91, 75, 36);
		getContentPane().add(btn_idcancel);
		lblNewLabel.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/Main4.jpg"));
		lblNewLabel.setBounds(0, 0, 211, 145);
		
		getContentPane().add(lblNewLabel);
		
		this.setVisible(true);
		this.setResizable(false);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new find_pw();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == btn_idok){	
			
			System.out.println("비밀번호 찾기");
			
			Connection conn = null; // sql과 연결을 위해 필요
            Statement stmt = null; // 테이블의 데이터를 읽어오기 위해서 필요

            try {
               String name=tf_name.getText().trim();
               String id=tf_id.getText().trim();
               Class.forName("com.mysql.cj.jdbc.Driver");
               String url = 
            		   "jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
               conn = DriverManager.getConnection(url, "root", "wjs1018013");
               stmt = conn.createStatement(); //Statement 객체를 만든다.     
               ResultSet rs = stmt.executeQuery("select pw from tb_member where name='"+name+"' and id='"+id+"'");   
               // 파라미터로 넘겨준 select문을 데이터베이스로 보내서 실행하고 결과로 Resultset 객체 리턴
               while(rs.next()) {
            	   pwd = rs.getString("pw");
            	   
               }
               if(pwd != null)
               {
            	   JOptionPane.showMessageDialog(null, "비밀번호는 " + pwd + " 입니다.", "알림", JOptionPane.ERROR_MESSAGE);
               }
               else if(pwd == null)
               {
            	   JOptionPane.showMessageDialog(null, "일치하는 아이디가 없습니다.", "알림", JOptionPane.ERROR_MESSAGE);
               }
               else if(tf_name.getText().length()==0){
            	   JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "알림", JOptionPane.ERROR_MESSAGE);
            	   tf_name.requestFocus();
               }
               else if(tf_id.getText().length()==0){
            	   JOptionPane.showMessageDialog(null, "아이디를 입력해주세요", "알림", JOptionPane.ERROR_MESSAGE);
            	   tf_id.requestFocus();
               }
               
            }
            catch (ClassNotFoundException cnfe) {
               System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
            }
            catch (SQLException e1) {
               e1.printStackTrace();
            }
            finally {
           	 try {
           		 stmt.close(); // 데이터 읽어오기를 끊는다.
           	 }
           	 catch (Exception ignored) {}
           	 	try { 
           	 		conn.close(); //연결을 끊는다.
           	 	}
           	 catch (Exception ignored) {}
            }
            }

               
			
		else if(e.getSource() == btn_idcancel){
			dispose();
		}

	}
}
