package Client;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Client.zipSearch;


public class Member extends JFrame implements ActionListener,ItemListener, KeyListener{

	public static String image_path;
	private JFrame frame = new JFrame();
	private JPanel contentPanee = null;
	private MsgeBox msgbox = new MsgeBox();
	private String gender;
	private JTextField textField_idd = new JTextField();
	private JTextField textField_name = new JTextField();
	static JTextField tf_addr1 = new JTextField();
	static JTextField tf_addr2 = new JTextField();
	private JTextField textField_ph2 = new JTextField();
	private JTextField textField_ph3 = new JTextField();
	private JTextArea textField_info = new JTextArea();
	private JButton btn_find = new JButton("우편번호 찾기");
	private JButton btn_re = new JButton("중복확인");
	private JButton btn_ok = new JButton("확인");
	private JButton btn_cancel = new JButton("취소");
	private JRadioButton ck_man = new JRadioButton("남자");
	private JRadioButton ck_woman = new JRadioButton("여자");
	private JComboBox Combo_ph = new JComboBox();
	private JPasswordField textField_pw;
	private JPasswordField textField_pwr;
	private String id ="noname"; //회원가입 변수 저장
	ArrayList<String> id_confrim = new ArrayList<String>(); //id의 정보를 담기위한 문자열 배열리스트 생성
	private ImageIcon originalIcon;
	public JLabel check_pwd = new JLabel("사용불가");
	public JLabel check_pwd1 = new JLabel("사용가능");
	private JButton uploadbutton = new JButton("사진");
	JLabel imge = new JLabel();
	private final JLabel fakeImage = new JLabel("");
	private final JLabel lblNewLabel_6 = new JLabel("");

	public void init() { //GUI

		setTitle("회 원 가 입");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 544, 433);
		contentPanee = new JPanel();
		contentPanee.setBackground(Color.WHITE);
		contentPanee.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanee);
		contentPanee.setLayout(null);

		JLabel lblNewLabel = new JLabel("회원가입");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("한컴 윤고딕 240", Font.PLAIN, 24));
		lblNewLabel.setBounds(202, 39, 154, 31);
		contentPanee.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("아이디");
		lblNewLabel_1.setBounds(40, 90, 57, 15);
		contentPanee.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("비밀번호");
		lblNewLabel_2.setBounds(40, 120, 57, 15);
		contentPanee.add(lblNewLabel_2);


		check_pwd.setForeground(Color.RED);
		check_pwd.setBounds(256, 120, 60, 15);
		check_pwd.setVisible(true);
		contentPanee.add(check_pwd);

		check_pwd1.setForeground(Color.BLUE);
		check_pwd1.setBounds(256, 120, 60, 15);
		check_pwd1.setVisible(false);
		contentPanee.add(check_pwd1);

		JLabel lblNewLabel_3 = new JLabel("비밀번호 확인");
		lblNewLabel_3.setBounds(40, 150, 82, 15);
		contentPanee.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("이름");
		lblNewLabel_4.setBounds(40, 180, 57, 15);
		contentPanee.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("주소");
		lblNewLabel_5.setBounds(40, 300, 57, 15);
		contentPanee.add(lblNewLabel_5);

		textField_idd.setBounds(128, 85, 116, 21);
		contentPanee.add(textField_idd);
		textField_idd.setColumns(10);


		textField_name.setBounds(128, 175, 116, 21);
		contentPanee.add(textField_name);
		textField_name.setColumns(10);


		tf_addr2.setBounds(128, 296, 250, 21);
		contentPanee.add(tf_addr2);
		tf_addr2.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("성별");
		lblNewLabel_7.setBounds(40, 210, 57, 15);
		contentPanee.add(lblNewLabel_7);


		btn_re.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		btn_re.setBounds(256, 87, 97, 23);
		contentPanee.add(btn_re);

		JLabel lblNewLabel_9 = new JLabel("전화번호");
		lblNewLabel_9.setBounds(40, 240, 57, 15);
		contentPanee.add(lblNewLabel_9);
		Combo_ph.setBackground(Color.WHITE);


		Combo_ph.setFont(new Font("돋움체", Font.PLAIN, 12));
		Combo_ph.setModel(new DefaultComboBoxModel(new String[] {"010", "011", "016", "017", "019"}));
		Combo_ph.setBounds(128, 238, 76, 21);
		contentPanee.add(Combo_ph);

		JLabel Labell = new JLabel("-");
		Labell.setHorizontalAlignment(SwingConstants.CENTER);
		Labell.setBounds(202, 240, 20, 15);
		contentPanee.add(Labell);


		textField_ph2.setBounds(218, 236, 72, 21);
		contentPanee.add(textField_ph2);
		textField_ph2.setColumns(10);

		JLabel label_1 = new JLabel("-");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(288, 240, 20, 15);
		contentPanee.add(label_1);


		textField_ph3.setBounds(310, 236, 72, 21);
		contentPanee.add(textField_ph3);
		textField_ph3.setColumns(10);
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btn_ok.setText("확인");


		btn_ok.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		btn_ok.setBounds(50, 346, 154, 41);
		contentPanee.add(btn_ok);
		btn_cancel.setText("취소");


		btn_cancel.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		btn_cancel.setBounds(256, 346, 154, 41);
		contentPanee.add(btn_cancel);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(80, 410, 4, 24);
		contentPanee.add(textArea);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(141, 440, 259, -68);
		contentPanee.add(textArea_1);

		uploadbutton.setFont(new Font("휴먼모음T", Font.PLAIN, 13));

		uploadbutton.setIcon(new ImageIcon(Member.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		uploadbutton.setBounds(395, 200, 108, 34);
		contentPanee.add(uploadbutton);


		imge = new JLabel("");
		imge.setBorder(new LineBorder(Color.LIGHT_GRAY));
		imge.setBackground(Color.LIGHT_GRAY);
		imge.setForeground(Color.GRAY);
		imge.setBounds(395, 74, 108, 112);
		contentPanee.add(imge);
		imge.setVisible(false);

		ButtonGroup gench = new ButtonGroup();
		gench.add(ck_man);
		gench.add(ck_woman);

		ck_man.setBackground(Color.WHITE);
		ck_man.setForeground(Color.BLACK);
		ck_man.setFont(new Font("돋움체", Font.PLAIN, 12));
		ck_man.setBounds(128, 205, 66, 23);
		contentPanee.add(ck_man);


		ck_woman.setBackground(Color.WHITE);
		ck_woman.setFont(new Font("돋움체", Font.PLAIN, 12));
		ck_woman.setBounds(206, 205, 69, 23);
		contentPanee.add(ck_woman);

		textField_pw = new JPasswordField();
		textField_pw.setBounds(128, 115, 116, 18);
		contentPanee.add(textField_pw);
		textField_pw.addKeyListener(new KeyAdapter(){
			@Override
			public void keyTyped(KeyEvent e) {// 비밀번호 확인
				// TODO Auto-generated method stubString regExp_symbol ="([a-z0-9_-].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[a-z0-9_-])";
				//정규표현식 컴파일
				String pwPattern = "([a-z0-9_-].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[a-z0-9_-])";
				Boolean tt = Pattern.matches(pwPattern, textField_pw.getText());
				if(textField_pw.getText().length()<=8 || tt == true) {
					//정규식에 만족하지 않거나 공백이들어갔을경우
					check_pwd.setVisible(true);
					check_pwd1.setVisible(false);
				}

				else {
					check_pwd.setVisible(false);
					check_pwd1.setVisible(true);

				}
			}
		});

		textField_pwr = new JPasswordField();
		textField_pwr.setBounds(128, 145, 116, 18);
		contentPanee.add(textField_pwr);

		btn_find.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		btn_find.setBounds(246, 267, 124, 23);
		contentPanee.add(btn_find);


		tf_addr1.setBounds(128, 267, 116, 23);
		contentPanee.add(tf_addr1);
		tf_addr1.setColumns(10);

		JLabel lblNewLabel_14 = new JLabel("우편번호");
		lblNewLabel_14.setBounds(40, 270, 57, 15);
		contentPanee.add(lblNewLabel_14);
		fakeImage.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/profile.jpeg"));
		fakeImage.setBounds(395, 74, 108, 114);

		contentPanee.add(fakeImage);
		lblNewLabel_6.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/Main3.jpg"));
		lblNewLabel_6.setBounds(0, 0, 544, 411);
		fakeImage.setVisible(true);

		contentPanee.add(lblNewLabel_6);


		this.setVisible(true); //true면 화면에 보이게
		this.setResizable(false);
	}
	
	private void start() {

		uploadbutton.addActionListener(this);      
		btn_re.addActionListener(this);
		btn_find.addActionListener(this);
		btn_ok.addActionListener(this);
		btn_cancel.addActionListener(this);
		ck_man.addItemListener(this);
		ck_woman.addItemListener(this);


	}

	public Member() {
		init(); //GUI 불러오기
		start(); // AcitonListner 실행
	}

	public static void main(String[] args) {
		new Member(); // 객체생성
	}
	public ImageIcon ResizeImage(String ImagePath)  //이미지 사이즈조절
	{  //import java.awt.Image;
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage(); //이미지얻기
		Image newImg = img.getScaledInstance(imge.getWidth(), imge.getHeight(), Image.SCALE_SMOOTH);
		//사진의 사이즈를 라벨에 맞게
		ImageIcon image = new ImageIcon(newImg);
		return image; //이미지반환
	}   



	@Override  //추상메소드
	public void actionPerformed(ActionEvent e){

		char[] pass1=textField_pw.getPassword(); // 비밀번호 창에 입력된 숫자를 저장한다
		char[] pass2=textField_pwr.getPassword(); // 비밀번호 일치 창에 입력된 숫자를 저장한다.
		String pws = new String(pass1); // 문자열로 생성한다.
		String pwrs = new String(pass2);
		String ph_r = (String)Combo_ph.getSelectedItem(); // 콤보상자 값으로 불러오기

		if(e.getSource() == uploadbutton){   

			System.out.println("사진 등록");

			FileInputStream fis=null;
			Connection conn = null; // 데이터 연결하기 위함
			PreparedStatement pstmt  = null; // 데이터를 읽어오기 위함

			FileDialog file=new FileDialog(frame,"사진찾기",FileDialog.LOAD); // 파일을 선택할 수있는 창을 생성한다
			file.setSize(300,200); // 파일 선택창의 크기이다
			file.show(); // 창을 연다
			originalIcon=new ImageIcon(file.getDirectory()+file.getFile()); // 파일의 위치를 얻어온다

			Image originalImage=originalIcon.getImage(); // 해당 위치의 파일을 이미지로 얻어온다
			Image resizeImage= originalImage.getScaledInstance(100,90,Image.SCALE_SMOOTH); //파일의 크기를 조정한다.
			ImageIcon resizeIcon=new ImageIcon(resizeImage); // 객체 생성후      
			imge.setIcon(resizeIcon);//레이블에 icon으로 나타낸다.
			String id=textField_idd.getText().trim();
			String image_path=file.getDirectory()+file.getFile();

			String sql ="insert into image(id,image_path,img) values(?,?,?)";
			imge.setVisible(true);
			fakeImage.setVisible(false);

			try{

				Class.forName("com.mysql.cj.jdbc.Driver"); //jdbc 드라이버를 로드한다.
				conn = (Connection) DriverManager.getConnection( //정적 메소드인 getConnection 메소드 호출
						"jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "wjs1018013"); // 데이터베이스에 필요한  URL,ID,PW

				fis =new FileInputStream(image_path);

				pstmt=conn.prepareStatement(sql);

				pstmt.setBinaryStream(3,fis,(int)image_path.length());
				pstmt.setString(2, image_path);
				pstmt.setString(1, id);
				pstmt.executeUpdate();

				System.out.println("이미지 저장 성공");

				pstmt.close();
				conn.close();


			}
			catch(ClassNotFoundException cnfe){
				cnfe.printStackTrace();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			catch(FileNotFoundException fnfe){
				fnfe.printStackTrace();
			}
			finally{
				try{
					fis.close();
				}
				catch(Exception e2){
					e2.printStackTrace();

				}
			}

		}

		else if(e.getSource()==btn_re) { // 아이디 중복확인을 눌렀을때


			System.out.println("아이디중복확인"); // 버튼이 제대로 눌렸는지 확인
			Connection conn = null; // sql과 연결을 위해 필요
			Statement stmt = null; // 테이블의 데이터를 읽어오기 위해서 필요

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "wjs1018013");
				stmt = conn.createStatement(); //Statement 객체를 만든다.     
				ResultSet rs = stmt.executeQuery("select id from tb_member;");   
				// 파라미터로 넘겨준 select문을 데이터베이스로 보내서 실행하고 결과로 Resultset 객체 리턴
				while(rs.next()) {      //더 이상 데이터가 없을 때까지 반복해서 읽어온다.
					id_confrim.add(rs.getString("id"));      // rs 안에서 id를 string 타입으로 id_info 리스트 안에 저장
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

			for (int i=0; i < id_confrim.size(); i++) {      // 배열리스트에 들어간 id 크기만큼 반복
				if (id_confrim.get(i).equals(textField_idd.getText())){      // 배열리스트 안의 값들과 사용자가 아이디(textField_idd)에 적은 값  비교(equals)
					id = id_confrim.get(i);                     // 값이 일치하면  id_confrim 안의 그 값을 변수 id 안에 넣는다. (String id=no name)
				}                  
			}           
			if(id.equals("noname")) {         
				JOptionPane.showMessageDialog(null, "아이디 사용가능합니다", "알림", JOptionPane.INFORMATION_MESSAGE);
				btn_re.requestFocus();
			}
			else{
				JOptionPane.showMessageDialog(null, "아이디가 존재합니다. 다른 아이디를 입력하세요", "알림", JOptionPane.ERROR_MESSAGE);
				btn_re.requestFocus();
			}


		}
		else if(!(pws.equals(pwrs))){
			JOptionPane.showMessageDialog(null, "비밀번호 불일치","알림", JOptionPane.ERROR_MESSAGE);
			textField_pw.requestFocus();
		}
		else if(e.getSource()==btn_ok){
			System.out.println("등록");

			if(textField_idd.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "아이디를 입력하세요", "알림", 
						JOptionPane.ERROR_MESSAGE);
				textField_idd.requestFocus();
				System.out.println("아이디를 입력하세요");
			}

			else if(textField_name.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "이름를 입력하세요", "알림", JOptionPane.ERROR_MESSAGE);
				textField_name.requestFocus();
				System.out.println("이름를 입력하세요");
			}
			else if(textField_ph2.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "핸드폰번호를 입력하세요", "알림", JOptionPane.ERROR_MESSAGE);
				textField_ph2.requestFocus();
				System.out.println("핸드폰번호를 입력하세요");
			}
			else if(textField_ph3.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "핸드폰번호를 입력하세요", "알림", JOptionPane.ERROR_MESSAGE);
				textField_ph3.requestFocus();
				System.out.println("핸드폰번호를 입력하세요");
			}
			else if(tf_addr2.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "주소를 입력하세요", "알림", JOptionPane.ERROR_MESSAGE);
				tf_addr2.requestFocus();
				System.out.println("주소를 입력하세요");
			}




			else
			{
				frame.setVisible(false);


				if(ck_man.isSelected())
					gender="남자";
				else
					gender="여자";

				String id=textField_idd.getText().trim();
				String pw=textField_pw.getText().trim();
				String name=textField_name.getText().trim();
				String addr=tf_addr1.getText()+" "+tf_addr2.getText();
				String ph=ph_r+textField_ph2.getText().trim()
						+textField_ph3.getText().trim();
				String pwPattern = "([a-z0-9_-].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[a-z0-9_-])";
				Boolean tt = Pattern.matches(pwPattern, pw);
				if((textField_pw.getText().length()<=3) || tt != true){

					JOptionPane.showMessageDialog(null, "비밀번호는 4자리 이상, 특수문자 !@#$^등 을 입력해주세요", "알림", JOptionPane.ERROR_MESSAGE);


				}



				else if((textField_pw.getText().equals(textField_pwr.getText()) == true)){ //비밀번호 확인 
					//비밀번호 유효성 검사식1 : 숫자, 특수문자가 포함되어야 한다.
					String regExp_symbol ="([a-z0-9_-].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[a-z0-9_-])";
					//정규표현식 컴파일
					Pattern pattern_symbol=Pattern.compile(regExp_symbol);
					//문자 매칭
					Matcher matcher_symbol=pattern_symbol.matcher(pw);

					String regExp_alpha="([a-z].*[A-Z])|([A-Z].*[a-z])";
					Pattern pattern_alpha=Pattern.compile(regExp_alpha);
					Matcher matcher_alpha=pattern_alpha.matcher(pw);


					Connection conn = null; // 데이터 연결하기 위함
					Statement stmt  = null; // 데이터를 읽어오기 위함


					try {
						Class.forName("com.mysql.cj.jdbc.Driver"); //jdbc 드라이버를 로드한다.
						conn = (Connection) DriverManager.getConnection( //정적 메소드인 getConnection 메소드 호출
								"jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "wjs1018013"); // 데이터베이스에 필요한  URL,ID,PW
						stmt = (Statement) conn.createStatement();
						String sql
						="insert into tb_member(id,pw,name,ph,addr,gender) "
								+ "values('"+id+"','"+pw+"','"+name+"','"+ph+"','"+addr+"','"+gender+"')";

						stmt.executeUpdate(sql); //쿼리문 실행
						JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다");
						dispose();
						System.out.println("회원가입이 완료되었습니다");

						int n = stmt.executeUpdate(sql); 

						if(n>0){
							System.out.println("성공");

						}else{
							System.out.println("실패");
						}

					}
					catch(ClassNotFoundException cnfe) {
						System.out.println("해당클래스를 찾을수 없습니다."+cnfe.getMessage());
					}
					catch(SQLException se) {
						System.out.println(se.getMessage());
					}
					finally {
						try {
							stmt.close(); //Statement 닫는다.
						}
						catch(Exception ignored) {
						}
						try {
							conn.close(); //Connection 닫는다.

						}
						catch (Exception ignored ) {
						}

					}

				}
			}
		}
		else if(e.getSource()==btn_cancel) {
			System.out.println("취소");
			dispose();
		}
		else if(e.getSource()==btn_find){
			System.out.println("우편번호 찾기");
			zipSearch frame = new zipSearch(); // 우편번호검색창보임
			frame.setVisible(true);
		}


	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(ck_man.isSelected())
			System.out.println("남");
		if(ck_woman.isSelected())
			System.out.println("여");   
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
}