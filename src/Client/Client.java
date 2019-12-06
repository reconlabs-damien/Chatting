package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.omg.CORBA.Environment;	

import javax.swing.event.ChangeListener;
import javax.swing.border.TitledBorder;

public class Client extends JFrame implements ActionListener, KeyListener,ItemListener,MouseListener{

	// Login Frame
	private JFrame Login_GUI = new JFrame();
	private JPanel Login_panel;
	private JTextField textField_ip; // id받는텍스트 필드
	private JTextField textField_port; // port 받는 텍스트 필드
	private JTextField textField_id; // id 받는 텍스트 필드
	private static JPasswordField pw_pw;
	private JButton button_sign= new JButton(" 회 원 가 입 "); 
	private JButton button_login =new JButton("로그인");
	private JButton btn_king = new JButton("관리자 권한");

	// Main Frame
	private JFrame frm = new JFrame();
	private JPanel contentPane;
	private JTextField textField_message;
	private JButton btn_emo = new JButton("이모티콘");
	private JButton button_send_note = new JButton("쪽지");
	private JButton button_join_room = new JButton("입장");
	private JButton button_create_room = new JButton("방+-");
	private JButton button_send_message = new JButton("전송");
	private JButton button_clear=new JButton("퇴장");
	private JButton Save_File = new JButton("파일 받기");
	public JButton file_Send = new JButton("");
	private JList list_user = new JList();
	private JList list_roomname = new JList();
	private JTextPane textPane_chat = new JTextPane();
	private JLabel pro_img = new JLabel("");
	private JButton btn_change = new JButton("수정");
	public static ImageIcon emoticon = new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/emoticon.png");
	public static ImageIcon mainImage = new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/main.jpg");
	public static ImageIcon logoImage = new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/logo.png");
	private String profile;
	private String path = "";


	// Network Source
	private Socket socket = null;
	private String ip = "127.0.0.1";
	private int port = 7777;
	private String id ="noname";
	private String pw ="noname";


	private InputStream inputStream;
	private OutputStream outputStream;
	private static DataOutputStream dataOutputStream;
	private DataInputStream dataInputStream;

	//etc valuable
	private Vector vector_user_list = new Vector();
	private Vector vector_room_list = new Vector();   
	private StringTokenizer stringTokenizer;
	static String my_room = null; //현재 나의 방

	private Image img = null;
	private ArrayList<String> id_info = new ArrayList<String>();
	private ArrayList<String> pw_info = new ArrayList<String>();
	private ArrayList<String> img_info = new ArrayList<String>();
	private ArrayList<String> idd_info = new ArrayList<String>();
	private JTextField now;

	//color
	private JColorChooser colorChooser=new JColorChooser();
	private StyleContext context = new StyleContext();

	private String img_id="";
	private String image="";
	private ImageIcon originalIcon;
	private InputStream profileimg = null; 

	//calendar

	Calendar c1 = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	String strToday = sdf.format(c1.getTime());


	public String url = "jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private final JLabel pro_id = new JLabel("");
	private final JLabel pro_name = new JLabel("");
	private final JLabel pro_phone = new JLabel("");
	private final JTextArea pro_add = new JTextArea("");
	private final JLabel pro_gen = new JLabel("");
	Container contentPane1;
	private final JPanel panel = new JPanel();
	JButton emo_btn = new JButton();

	public Client(){

		// ImageIcon icon = new ImageIcon("C:\\javaimg\\design\\6.jpg");
		setTitle("CHATTING ROOM");

		Login_init(); //Login GUI
		Main_init();  //Main GUI
		start();     //ACTION

	}

	private void start(){ //ACTION
		try {
			UIManager
			.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// TODO: handle exception
		}
		contentPane.addMouseListener(this);
		btn_change.setBounds(86, 427, 87, 23);
		panel.setBounds(483, 48, 247, 512);
		panel.setBackground(Color.WHITE);
		panel.add(btn_change);

		JLabel lblNewLabel_7 = new JLabel("아이디 : ");
		lblNewLabel_7.setBounds(6, 17, 57, 15);
		panel.add(lblNewLabel_7);
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_8 = new JLabel("이름 : ");
		lblNewLabel_8.setBounds(6, 52, 57, 15);
		panel.add(lblNewLabel_8);
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_9 = new JLabel("연락처 : ");
		lblNewLabel_9.setBounds(6, 86, 57, 15);
		panel.add(lblNewLabel_9);
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_10 = new JLabel("주소 : ");
		lblNewLabel_10.setBounds(6, 123, 57, 15);

		panel.add(lblNewLabel_10);
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_11 = new JLabel("성별 : ");
		lblNewLabel_11.setBounds(6, 197, 57, 15);
		panel.add(lblNewLabel_11);
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel_12 = new JLabel("사진");
		lblNewLabel_12.setBounds(92, 234, 57, 23);
		panel.add(lblNewLabel_12);
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		btn_change.addActionListener(this);
		button_login.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		button_login.addActionListener(this);
		button_send_note.setBounds(367, 452, 90, 23);
		button_send_note.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		button_send_note.addActionListener(this);
		button_join_room.setBounds(367, 478, 90, 23);
		button_join_room.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		button_join_room.addActionListener(this);
		button_create_room.setBounds(367, 502, 90, 23);
		button_create_room.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		button_create_room.addActionListener(this);
		button_send_message.setBounds(229, 570, 70, 30);
		button_send_message.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		button_send_message.addActionListener(this);
		textField_message.addKeyListener(this);
		button_sign.addActionListener(this);
		button_clear.addActionListener(this);
		file_Send.addActionListener(this);
		Save_File.addActionListener(this);
	}


	private void Main_init(){ //Main GUI
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 758, 668);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("전 체 접 속 자");
		lblNewLabel.setBounds(367, 110, 90, 15);
		lblNewLabel.setForeground(new Color(0,0,0));
		lblNewLabel.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		list_user.setBounds(367, 140, 90, 120);
		list_user.setBorder(new LineBorder(new Color(0, 0, 0)));
		list_user.setFont(new Font("돋움체", Font.PLAIN, 12));

		button_login.setFont(new Font("돋움체", Font.PLAIN, 12));
		button_sign.setFont(new Font("돋움체", Font.PLAIN, 12));
		contentPane.add(list_user);
		list_user.setListData(vector_user_list);
		contentPane.add(button_send_note);


		list_user.setBorder(new LineBorder(new Color(0, 0, 0)));
		list_user.setFont(new Font("돋움체", Font.PLAIN, 12));
		list_user.setListData(vector_user_list);
		contentPane.add(button_send_note);


		JLabel lblNewLabel_1 = new JLabel("채 팅 방 목 록");
		lblNewLabel_1.setBounds(367, 290, 90, 15);
		lblNewLabel_1.setForeground(new Color(0,0,0));
		lblNewLabel_1.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_1);
		list_roomname.setBounds(367, 320, 90, 120);
		list_roomname.setFont(new Font("돋움체", Font.PLAIN, 12));
		list_roomname.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		contentPane.add(list_roomname);
		list_roomname.setListData(vector_room_list);
		contentPane.add(button_join_room);
		contentPane.add(button_create_room);
		Save_File.setBounds(367, 526, 90, 29);
		contentPane.add(Save_File);
		Save_File.setVisible(false);
		
		list_roomname.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		list_roomname.setFont(new Font("돋움체", Font.PLAIN, 12));
		list_roomname.setListData(vector_room_list);
		contentPane.add(button_join_room);
		contentPane.add(button_create_room);
		emo_btn.setBounds(292, 570, 30, 30);
		emo_btn.setIcon(emoticon);
		emo_btn.setVisible(false);
		contentPane.add(emo_btn);
		emo_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new emoticon();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 55, 336, 508);
		contentPane.add(scrollPane);
		textPane_chat.setEditable(false);


		scrollPane.setViewportView(textPane_chat);
		textField_message = new JTextField();
		textField_message.setBounds(10, 570, 222, 30);
		contentPane.add(textField_message);
		textField_message.setColumns(10);
		textField_message.setEnabled(false);
		textField_message.setVisible(false);
		button_send_message.setVisible(false);
		contentPane.add(button_send_message);
		panel.setBorder(new TitledBorder(UIManager.getBorder
				("TitledBorder.border"), "사용자 정보", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		contentPane.add(panel);
		panel.setLayout(null);
		pro_img.setBounds(52, 267, 147, 150);
		pro_img.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.add(pro_img);


		pro_img.setIcon(null);
		btn_change.setText("수정");
		btn_change.setFont(new Font("휴먼모음T", Font.PLAIN, 13));


		JLabel lblNewLabel_6 = new JLabel("DATE");
		lblNewLabel_6.setBounds(370, 43, 87, 23);
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setForeground(Color.black);
		lblNewLabel_6.setFont(new Font("돋움체", Font.PLAIN, 12));
		contentPane.add(lblNewLabel_6);

		now = new JTextField();
		now.setBounds(367, 76, 90, 21);
		contentPane.add(now);
		now.setColumns(10);

		pro_id.setBounds(76, 17, 83, 15);
		panel.add(pro_id);

		pro_id.setFont(new Font("돋움체", Font.PLAIN, 12));
		pro_id.setForeground(Color.black);
		pro_name.setBounds(76, 52, 83, 15);
		panel.add(pro_name);

		pro_name.setFont(new Font("돋움체", Font.PLAIN, 12));
		pro_name.setForeground(Color.black);
		pro_phone.setBounds(76, 86, 83, 15);
		panel.add(pro_phone);

		pro_phone.setFont(new Font("돋움체", Font.PLAIN, 12));
		pro_phone.setForeground(Color.black);
		pro_gen.setBounds(75, 197, 84, 15);
		panel.add(pro_gen);

		pro_gen.setFont(new Font("돋움체", Font.PLAIN, 12));
		pro_gen.setForeground(Color.black);
		pro_add.setBounds(72, 121, 147, 46);
		pro_add.setLineWrap(true);
		pro_add.setEditable(false);
		panel.add(pro_add);

		pro_add.setFont(new Font("돋움체", Font.PLAIN, 12));
		pro_add.setForeground(Color.black);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/logo1.png"));
		lblNewLabel_2.setBounds(530, 576, 200, 50);
		contentPane.add(lblNewLabel_2);
		
		file_Send.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/file.png"));
		file_Send.setBounds(321, 571, 27, 29);
		contentPane.add(file_Send);
		file_Send.setVisible(false);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/Main.jpg"));
		lblNewLabel_3.setBounds(0, 0, 771, 646);
		contentPane.add(lblNewLabel_3);
		


		this.setVisible(false);
	}

	private void Login_init(){ //Login GUI
		Login_GUI.setTitle("LOGIN");
		Login_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login_GUI.setBounds(100, 100, 320, 490);
		ImageIcon icon = new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/Client.png");
		this.setIconImage(icon.getImage());
		this.setSize(330,490);
		Login_panel = new JPanel()
		{
			public void paintComponent(Graphics g) 
			{
				// Approach 1 : Dispaly image at at full size
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false); // 그림을표시하게설정, 투명하게조절
				super.paintComponents(g);
			}

		};
		Login_panel.setBackground(Color.WHITE);
		Login_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		Login_GUI.setContentPane(Login_panel);
		Login_panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setBounds(43, 200, 70, 15);
		Login_panel.add(lblNewLabel_1);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(43, 240, 70, 15);
		Login_panel.add(lblPassword);


		textField_id = new JTextField();
		textField_id.setBounds(125, 190, 171, 36);
		Login_panel.add(textField_id);
		textField_id.setColumns(10);

		pw_pw = new JPasswordField();
		pw_pw.setBounds(125, 230, 171, 36);
		Login_panel.add(pw_pw);
		pw_pw.setColumns(10);

		button_login.setBounds(43, 280, 253, 36);
		Login_panel.add(button_login);


		JButton button_sign = new JButton("회원가입");
		button_sign.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		button_sign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Member();
			}
		});

		button_sign.setBounds(43, 329, 253, 36);
		Login_panel.add(button_sign);


		JButton btn_king = new JButton("관리자 권한");
		btn_king.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		btn_king.setBounds(43, 374, 253, 36);
		Login_panel.add(btn_king);
		btn_king.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				new kingg();
			}
		});

		JButton btn_find = new JButton("비밀번호 찾기");
		btn_find.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		btn_find.setBounds(43, 414, 253, 36);
		Login_panel.add(btn_find);
		btn_find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new find_pw();
			}
		});

		Login_panel.add(btn_find);

		JLabel lblNewLabel_mi = new JLabel("");
		lblNewLabel_mi.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_mi.setIcon(new ImageIcon("C:\\javaimg\\design\\6.jpg"));
		lblNewLabel_mi.setBounds(12, 10, 317, 200);
		Login_panel.add(lblNewLabel_mi);

		JLabel lblNewLabel_M = new JLabel("");
		lblNewLabel_M.setBounds(0, 0, 341, 534);
		Login_panel.add(lblNewLabel_M);
		Login_GUI.setVisible(true);
	}

	//-----------------------------------------------------------
	private void view(String id) { //프로필 정보 뷰
		// TODO Auto-generated method stub
		Connection con = null;  //view 도 역시 list와 같이 데이터를 불러와야하기 때문에 ResultSet을 준비 한다.
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url, "root","wjs1018013");
			stmt = con.createStatement();       //sql문 전달.
			stmt2 = con.createStatement();

			rs = stmt.executeQuery(         // 해당 칼럼 선택.
					"select  id,name,ph,addr,gender"
					+ " from tb_member"
					+ " where id='" +  id  + "'");    // 조건은 로그인 한 id와 같은 id를 가진 데이타.

			rs2 = stmt2.executeQuery("select * from image where id = '" + id + "'"); 

			if(rs.next()) {
				String name = rs.getString("name");
				String ph=rs.getString("ph");
				String addr=rs.getString("addr");
				String gen=rs.getString("gender");

				pro_name.setText(name);
				pro_phone.setText(ph);
				pro_add.setText(addr);
				pro_gen.setText(gen);

				if(rs2.next()) {   
					String pro = rs2.getString("image_path");
					pro_img.setIcon(ResizeImage(pro,pro_img));
				}
			}
			else {
				throw new Exception("해당 id의 멤버가 없습니다.");  //
			}     
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			try {rs.close();} catch (Exception e) {}
			try {stmt.close();} catch (Exception e) {}
			try {con.close();} catch (Exception e) {}
		}  
	}

	public static String checkIDImage(String id) {//쉽게말해 아이디를 입력 그 아이디에 해당하는 프로필사진을 출력
		id = id;
		String str=null;

		Connection con = null;  //view 도 역시 list와 같이 데이터를 불러와야하기 때문에 ResultSet을 준비 한다.
		Statement stmt = null;
		ResultSet rs = null;
		String url = "jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // 알아서 들어간다..conn으로
			con=DriverManager.getConnection(url, "root","wjs1018013"); 
			stmt = con.createStatement(); //db접속

			String sql = "select * from image where id='" + id + "'";
			rs = stmt.executeQuery(sql); // 입력id와 테이블에 저장된 id와 비교
			//sql = "select * from (select * from joindb where id='" + id + "')";
			while (rs.next() == true) {       // 다음값의   
				str =rs.getString(2);       // 같으면 로그인 성공
			}      
		} catch (Exception ee) { //익셉션처리
			System.out.println("문제있음");
			ee.printStackTrace();
		}
		return str; 
	}

	/*public ImageIcon ResizeImage(String ImagePath)  //이미지 사이즈조절
      {  //import java.awt.Image;
         ImageIcon MyImage = new ImageIcon(ImagePath);
         Image img = MyImage.getImage(); //이미지얻기
         Image newImg = img.getScaledInstance(pro_img.getWidth(), pro_img.getHeight(), Image.SCALE_SMOOTH);
         //사진의 사이즈를 라벨에 맞게
         ImageIcon image = new ImageIcon(newImg);
         return image; //이미지반환
      } */
	public static ImageIcon ResizeImage(String ImagePath, JLabel a)//지정된 라벨에 크기에 맞게 사진을 넣을때
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(a.getWidth(), a.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	public static ImageIcon ResizeImage(String ImagePath,int a,int b)//크기를 직접 입력해 줄때
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(a, b, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}



	private void network(){
		try {
			socket = new Socket(ip, port);
			if(socket != null){ //socket ok!!
				connection();
				view(id);
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void connection(){
		try {
			inputStream = socket.getInputStream();
			dataInputStream = new DataInputStream(inputStream);
			outputStream = socket.getOutputStream();
			dataOutputStream = new DataOutputStream(outputStream);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
		this.setVisible(true);
		this.Login_GUI.setVisible(false);
		send_message(id); //first connect -> send id
		vector_user_list.add(id); //add my id in user_list
		Thread thread = new Thread(new Socket_thread());
		thread.start();

	}
	public class Socket_thread implements Runnable{
		public void run() {
			// TODO Auto-generated method stub          
			while(true){
				try {

					InMessage(dataInputStream.readUTF());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					try{
						outputStream.close();
						inputStream.close();
						dataInputStream.close();
						dataOutputStream.close();
						socket.close();

						JOptionPane.showMessageDialog(null, "서버와 접속 끊어짐", "알림", JOptionPane.ERROR_MESSAGE);
					}catch(IOException e1){}
					break;

				}
			}
		}
	}

	private void InMessage(String str){ //all message from server


		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		int amPm = calendar.get(Calendar.AM_PM);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		int sec = calendar.get(Calendar.SECOND);
		String sAmPm = amPm == Calendar.AM ? "오전" : "오후";

		stringTokenizer = new StringTokenizer(str, "?");
		String protocol = stringTokenizer.nextToken();
		String message = stringTokenizer.nextToken();
		System.out.println("프로토콜 : " + protocol);
		System.out.println("내용 : " + message);
		if(protocol.equals("NewUser")){
			vector_user_list.add(message);

		}
		else if(protocol.equals("OldUser")){
			vector_user_list.add(message);

		}
		else if(protocol.equals("Note")){
			String note = stringTokenizer.nextToken();
			System.out.println(message + " 사용자에게 온 쪽지 " + note);
			JOptionPane.showMessageDialog(null, note, message + "님으로 부터 온 쪽지", JOptionPane.CLOSED_OPTION); //basic support dialog
		}
		else if(protocol.equals("user_list_update")){
			list_user.setListData(vector_user_list);
		}
		else if(protocol.equals("CreateRoom")){
			my_room = message;
			JOptionPane.showMessageDialog(null, "채팅방에 입장했습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			button_send_message.setEnabled(true);
			//button_send_file.setEnabled(true);
			//btn_era.setEnabled(true);
			//fontcolor.setEnabled(true);
			button_clear.setEnabled(true);      
			textField_message.setEnabled(true);
			textField_message.setVisible(true);
			button_send_message.setVisible(true);
			button_login.setEnabled(true);
			emo_btn.setEnabled(true);
			emo_btn.setVisible(true);
			Save_File.setVisible(true);
			Save_File.setEnabled(true);
			file_Send.setVisible(true);
			file_Send.setEnabled(true);

		}
		else if(protocol.equals("CreateRoomFail")){
			JOptionPane.showMessageDialog(null, "방 만들기 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
		else if(protocol.equals("NewRoom")){
			vector_room_list.add(message);
			list_roomname.setListData(vector_room_list);
		}
		else if(protocol.equals("OldRoom")){
			vector_room_list.add(message);
		}
		else if(protocol.equals("room_list_update")){
			list_roomname.setListData(vector_room_list);
		}
		else if(protocol.equals("JoinRoom")){
			my_room = message;
			JOptionPane.showMessageDialog(null, "채팅방에 입장했습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			button_send_message.setEnabled(true);
			button_send_message.setVisible(true);
			//button_send_file.setEnabled(true);
			//btn_era.setEnabled(true);
			button_clear.setEnabled(true);
			emo_btn.setEnabled(true);
			emo_btn.setVisible(true);
			//fontcolor.setEnabled(true);
			textField_message.setEnabled(true);
			textField_message.setVisible(true);
			button_create_room.setEnabled(true);
			file_Send.setVisible(true);
			file_Send.setEnabled(true);
			Save_File.setVisible(true);
			Save_File.setEnabled(true);
		}
		else if(protocol.equals("ExitRoom")){
			vector_room_list.remove(message);
			button_send_message.setEnabled(false);
			button_send_message.setVisible(false);
			//button_send_file.setEnabled(true);
			//btn_era.setEnabled(true);
			button_clear.setEnabled(false);
			emo_btn.setEnabled(false);
			emo_btn.setVisible(false);
			//fontcolor.setEnabled(true);
			textField_message.setEnabled(false);
			textField_message.setVisible(false);
			button_create_room.setEnabled(false);
			file_Send.setVisible(false);
			file_Send.setEnabled(false);
			Save_File.setVisible(false);
			Save_File.setEnabled(false);
		}
		else if(protocol.equals("Chatting")){


			String msg =  stringTokenizer.nextToken();
			StyledDocument doc = textPane_chat.getStyledDocument();
			SimpleAttributeSet smi=new SimpleAttributeSet();

			Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
			Style s =doc.addStyle("magenta", def);
			StyleConstants.setForeground(s, Color.magenta); //다른 사용자 색 
			s =doc.addStyle("blue", def);
			StyleConstants.setForeground(s, Color.blue); //자신의 채팅 색 
			s =doc.addStyle("black", def);
			StyleConstants.setForeground(s, Color.black); //알림 컬러 
			s =doc.addStyle("right", def);
			StyleConstants.setAlignment(s, StyleConstants.ALIGN_RIGHT);
			s =doc.addStyle("left", def);
			StyleConstants.setAlignment(s, StyleConstants.ALIGN_LEFT);
			s =doc.addStyle("center", def);
			StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);

			System.out.println(message);
			System.out.println(id);
			try {
				String Str=null;


				if(message.equals(id)) {
					doc.insertString(doc.getLength()," ("+sAmPm+""+hour+":"+min+") ", doc.getStyle("blue"));
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));


					if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/in_love.png")||msg.equals("1")){          


						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/in_love.png"));      // smi변수에 이모티콘을 저장한다.
						doc.insertString(doc.getLength(),message,smi);// 이미지가 출력된다.
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));

						// 그 후 엔터가 입력된다.
					} 


					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/angry.png")||msg.equals("2")){     

						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/angry.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));


					}
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/bored.png")||msg.equals("3")){     


						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/bored.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));


					}
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/confused.png")||msg.equals("4")){     


						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/confused.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));

					} 
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/crying.png")||msg.equals("5")){      


						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/crying.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));
					} 
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/embarrassed.png")||msg.equals("6")){    

						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/embarrassed.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));

					} 
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/happy.png")||msg.equals("7")){      

						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/happy.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));

					} 
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/ill.png")||msg.equals("8")){      


						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/ill.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));

					} 
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/surprised.png")||msg.equals("9")){    

						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/surprised.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));

					} 

					else{
						doc.insertString(doc.getLength(),msg, doc.getStyle("blue"));
					}

					StyleConstants.setForeground(smi, Color.black);
					doc.insertString(doc.getLength()," : " + message, doc.getStyle("blue"));
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));
					Style profile = doc.addStyle("프로필사진1", null);
					StyleConstants.setIcon(profile, ResizeImage(checkIDImage(id),40,40));
					doc.insertString(doc.getLength(),"ignored text", profile);
					doc.insertString(doc.getLength(),"\n", null);
					textPane_chat.setCaretPosition(doc.getLength());

				}


				else if(message.equals("알림")) {
					doc.insertString(doc.getLength(), message+" : "+msg+"\n", doc.getStyle("black"));
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("center"));
					textPane_chat.setCaretPosition(doc.getLength());

				}


				else {

					Style profile = doc.addStyle("프로필사진2", null);
					StyleConstants.setIcon(profile, ResizeImage(checkIDImage(message),40,40));
					doc.insertString(doc.getLength(),"ignored text", profile);
					StyleConstants.setForeground(smi, Color.black);
					doc.insertString(doc.getLength(),message+" : ", doc.getStyle("magenta"));
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));


					if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/in_love.png")||msg.equals("1")){          


						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/in_love.png"));      // smi변수에 이모티콘을 저장한다.
						doc.insertString(doc.getLength(),message,smi);               // 이미지가 출력된다.
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));

						// 그 후 엔터가 입력된다.
					} 


					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/angry.png")||msg.equals("2")){     

						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/angry.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));


					}
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/bored.png")||msg.equals("3")){     


						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/bored.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));


					}
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/confused.png")||msg.equals("4")){     


						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/confused.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));

					} 
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/crying.png")||msg.equals("5")){      


						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/crying.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));

					} 
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/embarrassed.png")||msg.equals("6")){    

						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/embarrassed.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));

					} 
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/happy.png")||msg.equals("7")){      

						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/happy.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));

					} 
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/ill.png")||msg.equals("8")){      


						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/ill.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));

					} 
					else if(msg.equals("/Users/jun/eclipse-workspace/Chatting/img/surprised.png")||msg.equals("9")){    

						StyleConstants.setIcon(smi,new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/surprised.png"));
						doc.insertString(doc.getLength(),message,smi);
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));

					} 
					else{
						doc.insertString(doc.getLength(),msg, doc.getStyle("magenta"));
						doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));
					}

					doc.insertString(doc.getLength()," ("+sAmPm+""+hour+":"+min+") ", doc.getStyle("magenta"));
					doc.insertString(doc.getLength(),"\n", null);
					textPane_chat.setCaretPosition(doc.getLength());
				}


			}


			catch (Exception e) {
				System.out.println("문제발생");
			}
		}




		else if(protocol.equals("UserOut")){
			vector_user_list.remove(message);
		}
	}






	private String getLocalServerIp()
	{
		try
		{
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
			{
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
				{
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress())
					{
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		}
		catch (SocketException ex) {}
		return null;
	}
	static void send_message(String message){ //button
		try {
			dataOutputStream.writeUTF(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Client();
	}

	public void actionPerformed(ActionEvent e) {


		if(e.getSource() == btn_change) {

			FileDialog dlg=new FileDialog(new JFrame(),"파일 열기",FileDialog.LOAD);
			dlg.setVisible(true);

			if(dlg.getFile()==null)
			{
				return;
			}
			else{

				ImageIcon originalIcon=new ImageIcon(dlg.getDirectory()+dlg.getFile());
				Image originalImage=originalIcon.getImage();
				Image resizeImage=originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				ImageIcon resizeIcon=new ImageIcon(resizeImage);

				profile=dlg.getDirectory()+dlg.getFile();
				pro_img.setIcon(resizeIcon);

				Connection conn=null;
				java.sql.PreparedStatement pstmt=null;
				FileInputStream fis=null;

				String sql="update image set img=?,image_path=? where id=?";
				String url = "jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
				try{
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn = DriverManager.getConnection(url, "root", "wjs1018013");

					fis=new FileInputStream(profile);

					pstmt=conn.prepareStatement(sql);
					pstmt.setBinaryStream(1, fis);
					pstmt.setString(2, profile);
					pstmt.setString(3, id);
					pstmt.executeUpdate();

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
		}


		else if(e.getSource() == button_login) {

			char[] pass = pw_pw.getPassword();      // 사용자가 패스워드 필드에 적은 값을 가져와 pass라는 char타입의 배열에 담는다.
			String passString = new String(pass);      // pass 배열안의 값들을 String 타입의 passString 변수에 담는다.

			Connection conn = null;
			Statement stmt = null;

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "wjs1018013");             
				stmt = conn.createStatement();                     
				ResultSet  rs = stmt.executeQuery(         // 해당 칼럼 선택.
						"select id,pw from tb_member");    // 조건은 로그인 한 id와 같은 id를 가진 데이타.   //쿼리문을 실행 하여 select 문으로 id,pw 를 ResultSet클래스의 객체인 rs 로 결과를 받았다.

				while(rs.next()) {      // true이면 while문을  반복하고, false이면  빠져나온다. rs.next()= 다음 커서로 이동
					id_info.add(rs.getString("id"));      // rs 안에서 id를 string 타입으로 id_info 리스트 안에 저장
					pw_info.add(rs.getString("pw"));

					// rs안에서 password를 string 타입으로 pw_info 리스트 안에 저장
				}

				for (int i=0; i < id_info.size(); i++) {      // id_info 사이즈만큼 반복
					if (id_info.get(i).equals(textField_id.getText()))      // id_info 리스트 안의 값들과 사용자가 textField에 입력한 값을  비교
						id = id_info.get(i);                     // true가 나오면   값을 변수 id 안에 넣는다.
				}                                

				for (int j=0; j < pw_info.size(); j++) {      // pw_info사이즈만큼 반복
					if (pw_info.get(j).equals(passString))      // pw_info 리스트 안의 값들과  passString 값을  비교
						pw = pw_info.get(j);         // true가 나오면  그 값을 변수 pw 안에 넣는다.
				}

				String id=textField_id.getText();

				ResultSet re=stmt.executeQuery("select id,image_path from image where id='"+id+"';");

				while(re.next()) {
					String path=re.getString("image_path");  
					originalIcon=new ImageIcon(path); // 파일의 위치를 얻어온다
					Image originalImage=originalIcon.getImage(); // 해당 위치의 파일을 이미지로 얻어온다
					Image resizeImage= originalImage.getScaledInstance(100,90,Image.SCALE_SMOOTH); //파일의 크기를 조정한다.
					ImageIcon resizeIcon=new ImageIcon(resizeImage); // 객체 생성후      
					pro_img.setIcon(resizeIcon);
				}

				for (int i=0; i < idd_info.size(); i++) {      // id_info 사이즈만큼 반복
					if (idd_info.get(i).equals(textField_id.getText()))      // id_info 리스트 안의 값들과 사용자가 textField에 입력한 값을  비교
						img_id = idd_info.get(i);                     // true가 나오면   값을 변수 id 안에 넣는다.
				}                                

				for (int j=0; j < img_info.size(); j++) {      // pw_info사이즈만큼 반복
					if (img_info.get(j).equals(passString))      // pw_info 리스트 안의 값들과  passString 값을  비교
						image = img_info.get(j);         // true가 나오면  그 값을 변수 pw 안에 넣는다.
				}                                


				for(int a=0;a<id_info.size();a++){
					for(int b=0;b<idd_info.size();b++){
						if(id_info.get(a).equals(idd_info.get(b))){
							pro_img.setIcon(new ImageIcon(img_info.get(b)));
						}
					}
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


			if(textField_id.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "ID를 입력해주세요", "알림", JOptionPane.ERROR_MESSAGE);
				textField_id.requestFocus();
			}

			else if(passString.length() == 0){
				JOptionPane.showMessageDialog(null, "Password를 입력해주세요", "알림", JOptionPane.ERROR_MESSAGE);
				pw_pw.requestFocus();
			}
			else if(id == "noname") {         // 변수 id에 아무런 값이 담기지 않았다면(id에 noname 이 아직도 담겨있다면) 오류발생!
				JOptionPane.showMessageDialog(null, "존재하지 않는 아이디입니다.", "알림", JOptionPane.ERROR_MESSAGE);
				textField_id.requestFocus();
			}
			else if(pw == "noname") {      // 변수 password에 아무런 값이 담기지 않았다면(pw에 noname 이 아직도 담겨있다면) 오류발생!
				JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "알림", JOptionPane.ERROR_MESSAGE);
				pw_pw.requestFocus();
			}
			else {
				id = textField_id.getText().trim();
				pw = passString.trim();

				network();

				pro_id.setText(id+" 님");
				now.setText(strToday);

				conn = null;
				stmt = null;

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn = DriverManager.getConnection(url, "root", "wjs1018013");                      
					stmt = conn.createStatement();
					String sql = "insert into iptime (ip,id,time) values('"+ip+"','" +id+ "',now())";
					stmt.executeUpdate(sql);
				}
				catch (ClassNotFoundException cnfe) {
					System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
				}
				catch (SQLException se) {
					System.out.println(se.getMessage());
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
			}



		}

		else if(e.getSource()==button_sign) {
			System.out.println("회원가입");
			new Member();
		}

		else if(e.getSource()==button_clear){
			textPane_chat.setText("");
		}


		else if(e.getSource() == button_send_note)
		{
			System.out.println("send_note");
			String user = (String) list_user.getSelectedValue();

			String note = JOptionPane.showInputDialog("보낼메세지"); //basic support dialog
			if(note != null){
				send_message("Note?" + user + "?" + note); //ex) Note/User2/hi
			}
			System.out.println("받는사람 : " + user + " | 보낼 내용 : " + note);
		}
		else if(e.getSource() == button_join_room)
		{
			String JoinRoom = (String) list_roomname.getSelectedValue();
			button_create_room.setText("퇴장");


			if(my_room!= null){
				if(my_room.equals(JoinRoom)){
					JOptionPane.showMessageDialog(null, "현재 채팅방입니다.", "알림", JOptionPane.ERROR_MESSAGE);
					return;
				}
				send_message("JoinRoom?" + JoinRoom +"?" );
				textPane_chat.setText("");
			}


			send_message("JoinRoom?" + JoinRoom +"?" );
			System.out.println("join_room");

		}
		else if(e.getSource() == button_create_room){
			String roomname = null;
			if(my_room == null){
				roomname = JOptionPane.showInputDialog("방 이름");
			}
			if(roomname != null){
				send_message("CreateRoom?"+roomname);
				button_create_room.setText("퇴장");
			}else{
				send_message("ExitRoom?"+my_room + "?");
				button_send_message.setEnabled(true);
				//   button_send_file.setEnabled(false);
				button_clear.setEnabled(true);
				emo_btn.setEnabled(true);
				my_room = null;
				button_create_room.setText("방+-");
				textPane_chat.setText("");
				JOptionPane.showMessageDialog(null, "채팅방에서 퇴장했습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			System.out.println("create_room");
		}
		
		else if(e.getSource() == button_send_message){
			System.out.println("send_message");
			if(my_room == null){
				JOptionPane.showMessageDialog(null, "채팅방에 참여해주세요", "알림", JOptionPane.ERROR_MESSAGE);
			}
			else{
				send_message("Chatting?"+my_room+"?" + textField_message.getText().trim() + "?");
				textField_message.setText("");

				textField_message.requestFocus();
			}
		}
		
		else if(e.getSource()== file_Send) {
	         String a;
	         FileSendSave b = new FileSendSave() ;
	         FileDialog fdlg = new FileDialog(new JFrame(),"file open",FileDialog.LOAD);
	         int Max=0;
	         String Path=null;
	         fdlg.setVisible(true);         

	         if(fdlg.getFile()==null){      
	            return;
	         }         
	         else{
	            ImageIcon originalIcon=new ImageIcon(fdlg.getDirectory()+fdlg.getFile());
	            Image originalImage = originalIcon.getImage();
	            Image resizeImage = originalImage.getScaledInstance(123, 151, Image.SCALE_SMOOTH);
	            ImageIcon resizeIcon = new ImageIcon(resizeImage);
	            a=fdlg.getDirectory()+fdlg.getFile();
	         }
	         System.out.println(id);
	         System.out.println(a);
	         Max = b.imageUpload(id,a);
	         send_message("Image?"+my_room+"?"+a+"?"+Max);
	         //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	      }
	      else if(e.getSource()== Save_File) {
	         FileSendSave b = new FileSendSave() ;
	         String imageid = JOptionPane.showInputDialog("파일 번호를 입력해주세요.");
	         int id = Integer.parseInt(imageid);
	         b.imageDownload(id);
	         System.out.println("파일저장 버튼 클릭");
	      }

	}
	class MyMouseListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount()==2){

				int r=(int)(Math.random()*256);
				int g=(int)(Math.random()*256);
				int b=(int)(Math.random()*256);
				contentPane.setBackground(new Color(r,g,b));
			}
		}
	}
	public void keyPressed(KeyEvent arg0) { // 눌렀을 때
		// TODO Auto-generated method stub

	}
	public void keyReleased(KeyEvent e) { //눌렀다 땠을 때
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 10){ //enter
			send_message("Chatting?"+my_room+"?" + textField_message.getText().trim() + "?" );
			textField_message.setText("");
			textField_message.requestFocus();
		}
	}
	public void keyTyped(KeyEvent arg0) { //타이핑
		// TODO Auto-generated method stub

	}


	public void stateChanged(ChangeEvent e) {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub


	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}    
}
