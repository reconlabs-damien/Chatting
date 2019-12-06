package Client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import Client.king_frame;

public class kingg extends JFrame implements ActionListener{
	private JTextField tf_kingid;
	private JPasswordField pw_king;
	private JButton btn_kingok = new JButton("로그인");
	private ArrayList<String> id_king = new ArrayList<String>();
	private ArrayList<String> pw_kingg = new ArrayList<String>();
	private String id ="noname";
	private String pw ="noname";
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	
	public kingg(){
		setTitle("관리자 모드");
	init();
	start();
	}
	
	public void start() {
		btn_kingok.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		btn_kingok.addActionListener(this);
	
	}
	public void init() {
		setBounds(100, 100, 285, 226);
		getContentPane().setBackground(Color.white);
		getContentPane().setLayout(null);
		
		btn_kingok.setBounds(191, 125, 85, 71);
		getContentPane().add(btn_kingok);
		
		tf_kingid = new JTextField();
		tf_kingid.setHorizontalAlignment(SwingConstants.LEFT);
		tf_kingid.setBounds(55, 125, 124, 32);
		getContentPane().add(tf_kingid);
		tf_kingid.setColumns(10);
		
		pw_king = new JPasswordField();
		pw_king.setBounds(55, 164, 124, 32);
		getContentPane().add(pw_king);
		
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/logo2.png"));
		lblNewLabel_3.setBounds(23, 42, 253, 71);
		getContentPane().add(lblNewLabel_3);
		
		lblNewLabel = new JLabel("ID :");
		lblNewLabel.setBounds(23, 133, 35, 16);
		getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("PW :");
		lblNewLabel_1.setBounds(23, 172, 35, 16);
		getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/Main2.jpg"));
		lblNewLabel_2.setBounds(0, 0, 285, 204);
		getContentPane().add(lblNewLabel_2);
		
		this.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new kingg();
		

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn_kingok) {
			
			   char[] pass = pw_king.getPassword();      // 사용자가 패스워드 필드에 적은 값을 가져와 pass라는 char타입의 배열에 담는다.
		       String passString = new String(pass);      // pass 배열안의 값들을 String 타입의 passString 변수에 담는다.
		               
		          Connection conn = null;
		          Statement stmt = null;
		          String url = "jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		               try {
		                  Class.forName("com.mysql.cj.jdbc.Driver");
		                  conn = DriverManager.getConnection(url, "root", "wjs1018013");
		                  stmt = conn.createStatement();
		                  
		                  ResultSet rs = stmt.executeQuery("select id, pw from admink;");   //쿼리문을 실행 하여 select 문으로 id,pw 를 ResultSet클래스의 객체인 rs 로 결과를 받았다.
		                  
		                  while(rs.next()) {      // true이면 while문을  반복하고, false이면  빠져나온다. rs.next()= 다음 커서로 이동
		                     id_king.add(rs.getString("id"));      // rs 안에서 id를 string 타입으로 id_info 리스트 안에 저장
		                     pw_kingg.add(rs.getString("pw"));   // rs안에서 password를 string 타입으로 pw_info 리스트 안에 저장
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
		                     stmt.close();
		                  }
		                  catch (Exception ignored) {}
		                  try {
		                     conn.close();
		                  }
		                  catch (Exception ignored) {}
		               }
		               
		               for (int i=0; i < id_king.size(); i++) {      // id_info 사이즈만큼 반복
		                  if (id_king.get(i).equals(tf_kingid.getText()))      // id_info 리스트 안의 값들과 사용자가 textField에 입력한 값을  비교
		                     id = id_king.get(i);                     // true가 나오면   값을 변수 id 안에 넣는다.
		               }                                
		               
		               for (int j=0; j < pw_kingg.size(); j++) {      // pw_info사이즈만큼 반복
		                  if (pw_kingg.get(j).equals(passString))      // pw_info 리스트 안의 값들과  passString 값을  비교
		                     pw = pw_kingg.get(j);         // true가 나오면  그 값을 변수 pw 안에 넣는다.
		               }                                
		               if(tf_kingid.getText().length() == 0){
			                  JOptionPane.showMessageDialog(null, "ID를 입력해주세요", "알림", JOptionPane.ERROR_MESSAGE);
			                  tf_kingid.requestFocus();
			               }
			               else if(passString.length() == 0){
			                  JOptionPane.showMessageDialog(null, "Password를 입력해주세요", "알림", JOptionPane.ERROR_MESSAGE);
			                  pw_king.requestFocus();
			               }
			               else if(id == "noname") {         // 변수 id에 아무런 값이 담기지 않았다면(id에 noname 이 아직도 담겨있다면) 오류발생!
			                  JOptionPane.showMessageDialog(null, "존재하지 않는 아이디입니다.", "알림", JOptionPane.ERROR_MESSAGE);
			                  tf_kingid.requestFocus();
			               }
			               else if(pw == "noname") {      // 변수 password에 아무런 값이 담기지 않았다면(pw에 noname 이 아직도 담겨있다면) 오류발생!
			                  JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "알림", JOptionPane.ERROR_MESSAGE);
			                  pw_king.requestFocus();
			               }
			               else {
			            	   new king_frame(); 
			            	   this.setVisible(false);
			               }
			            	
	}
}
}