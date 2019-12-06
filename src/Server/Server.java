package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;	
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame implements ActionListener{
	
	// Server Frame
	private JPanel contentPane;
	private JTextArea textArea = new JTextArea();
	private JButton button_start = new JButton("서버시작");
	private JButton button_stop = new JButton("서버중지");

	// Network Source
	private ServerSocket serverSocket;
	private Socket socket;
	private int port = 7777;
	private Vector vector_user = new Vector();
	private Vector vector_room = new Vector();
	
	// ETC Source
	private StringTokenizer stringTokenizer;
	private final JLabel lblNewLabel_1 = new JLabel("");
	

	
public void Fileout(String id,String context) throws IOException {
		
        try{
        	FileWriter fw=new FileWriter("msg.txt",true); 
        	//msg 텍스트 문서를 파일로 만들고 true->전 대화내용에 이어서 쓴다.
        	BufferedWriter bw=new BufferedWriter(fw); // 버퍼로 줄단위로 읽어온다.
   
        	bw.write(id+" : ");
        	bw.write(context); // 내용을 파일에 쓴다. 	
        	bw.newLine(); // 줄바꿈
        	bw.close(); // 다 썼으면 닫는다.       	
        	}catch(IOException e){
        		System.err.println(e);
        		System.exit(1);
        	}
    }		
public void insert_DB(String Idname,String context) { 

         Connection conn = null; // 데이터 연결하기 위함
         Statement stmt  = null; // 데이터를 읽어오기 위함
         try {
          Class.forName("com.mysql.cj.jdbc.Driver"); //jdbc 드라이버를 로드한다.
          conn = (Connection) DriverManager.getConnection( //정적 메소드인 getConnection 메소드 호출
           "jdbc:mysql://localhost:3306/javachat?useSSL=false", "root", "201483043"); // 데이터베이스에 필요한  URL,ID,PW
         stmt = (Statement) conn.createStatement();
         stmt.executeUpdate("insert into chat(name,context,time) "
          		+ " values('" +Idname +"', '" + context + "',now())"); //쿼리문 실행
         System.out.println("성공");
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
	
	
	
	private void server_start(){
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			serverSocket = new ServerSocket(port); //서버 소켓 생성자
		} catch (IOException e) { //이미 사용중일 경우 오류
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "이미 사용중인 포트", "알림", JOptionPane.ERROR_MESSAGE);
		}
		if(serverSocket != null){ // socket ok!
			connection();
		}
			
	}
	public class Socket_thread implements Runnable{ 
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				try { //wait client
					textArea.append("사용자 접속 대기중\n"); 
					socket = serverSocket.accept(); // 서버 소켓으로 연결 요청에 응답, 클라이언트 소켓 생성
					UserInfo userInfo = new UserInfo(socket); // 연결된 소켓으로 유저정보 생성
					userInfo.start();
				} catch (IOException e) {
					textArea.append("서버가 중지 되었습니다\n"); // 연결이 되지 않았을 경우
					break;
				}
			}
		}
	}
	
	private void connection(){
		Thread thread = new Thread(new Socket_thread()); // 소켓스레드 생성
		thread.start();
	
	}
	public Server(){
		setTitle("서버");
		init();	//GUI
		start();//ACTION
	}
	private void start(){
		button_start.setText("서버시작");
		button_start.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		button_start.addActionListener(this);
		button_stop.setText("서버중지");
		button_stop.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
		button_stop.addActionListener(this);
	}
	private void init(){ // Server GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 448);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 150, 485, 257);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		

		button_start.setBounds(249, 27, 258, 37);
		contentPane.add(button_start);
		

		button_stop.setBounds(249, 86, 258, 37);
		contentPane.add(button_stop);
		lblNewLabel_1.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/logo.png"));
		lblNewLabel_1.setBounds(22, 27, 200, 103);
		
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/Main1.png"));
		lblNewLabel.setBounds(0, 0, 540, 420);
		contentPane.add(lblNewLabel);
		
		this.setVisible(true);
		this.setResizable(false);
		
	}
	public static void main(String[] args) {
	
		new Server();
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button_start){ //서버실행 눌렀을 경우
			System.out.println("start server");
			server_start();
			button_start.setEnabled(true);
			button_stop.setEnabled(true);
		} else if(e.getSource() == button_stop){
			button_start.setEnabled(true);
			button_stop.setEnabled(true);
			try {
				serverSocket.close();
				vector_user.removeAllElements();
				vector_room.removeAllElements();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("stop server");
		}
	}
	private class UserInfo extends Thread{
		private InputStream inputStream;
		private OutputStream outputStream;
		private DataOutputStream dataOutputStream;
		private DataInputStream dataInputStream;
		private Socket socket_user;
		private String Nickname = "";
		private  String image_path = null;
		private String CurrentRoom = null;
		private boolean RoomCheck = true; // 기본적으로 만들 수 있는 상태
		public UserInfo(Socket socket){
			this.socket_user = socket;
			userNetwork();
		}
		public String getNicname(){ //닉네임 얻어오기
			return Nickname;
		}
		public void userNetwork(){
			try {
				inputStream = socket_user.getInputStream();
				dataInputStream = new DataInputStream(inputStream);
				outputStream = socket_user.getOutputStream();
				dataOutputStream = new DataOutputStream(outputStream);	
				Nickname = dataInputStream.readUTF();
				textArea.append(Nickname + " : 사용자 접속\n");
				
				//when a new user is connected
				
				BroadCast("NewUser?" + Nickname);
				for(int i = 0; i< vector_user.size(); i++){ // server alert at existing user and send the new user's id
					UserInfo userInfo = (UserInfo) vector_user.elementAt(i);
					this.send_Message("OldUser?" +  userInfo.getNicname());
				}
				vector_user.add(this);
				BroadCast("user_list_update? ");
				SetOldRoom();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Stream설정 에러", "알림", JOptionPane.ERROR_MESSAGE);
			}
		}
		private void BroadCast(String str){
			for(int i = 0; i< vector_user.size(); i++){ // server alert at existing user and send the new user's id
				UserInfo userInfo = (UserInfo) vector_user.elementAt(i);
				userInfo.send_Message(str);
			}
		}
		private void SetOldRoom(){
			for(int i = 0; i< vector_room.size(); i++){
				RoomInfo roomInfo = (RoomInfo) vector_room.elementAt(i);
				this.send_Message("OldRoom/" + roomInfo.getRoomName());
			}
			this.send_Message("room_list_update? ");
		}
		private void ExitRoom(UserInfo userInfo){
			for(int i = 0; i< vector_room.size(); i++){
				RoomInfo roomInfo = (RoomInfo) vector_room.elementAt(i);
				if(roomInfo.getRoomName().equals(CurrentRoom)){
					int size = roomInfo.Remove_User(userInfo);
					if(size ==0){
						BroadCast("ExitRoom?"+CurrentRoom);
						vector_room.remove(i);
						BroadCast("room_list_update? ");
					}
					else{
						roomInfo.BroadCast_Room("Chatting?알림?******    "+Nickname+"님이 퇴장하셨습니다     *****" + "?" );
					}
					CurrentRoom = null;
					break;
				}
			}
			
		}

		public void run() {
			// TODO Auto-generated method stub
			super.run();
			while(true){
				try {
					
					String msg = dataInputStream.readUTF();
					textArea.append(Nickname + " : " + msg + "\n");
					InMessage(msg);
					
					Fileout(Nickname,msg);
					insert_DB(Nickname,msg); // db연결 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					textArea.append(Nickname + " : 사용자 접속 끊어짐\n");
					ExitRoom(this);
					try {
						dataInputStream.close();
						dataOutputStream.close();
						socket_user.close();
						vector_user.remove(this);
						BroadCast("UserOut/"+Nickname);
						BroadCast("user_list_update/ ");
					} catch (IOException e1) {
						
					}
					
					
					break;
				}
				
			}
		}
		private void InMessage(String str){ //handle the message from client
			stringTokenizer = new StringTokenizer(str, "?");
			String protocol = stringTokenizer.nextToken();
			String message = stringTokenizer.nextToken();
			System.out.println("protocol : " + protocol);
			if(protocol.equals("Note")){
				//protocol = Note
				//message = user
				String note = stringTokenizer.nextToken();
				System.out.println("받는 사람 : " + message);
				System.out.println("보낼 내용 : " + note);
				for(int i = 0; i< vector_user.size(); i++){
					UserInfo userInfo = (UserInfo) vector_user.elementAt(i);
					if(userInfo.Nickname.equals(message)){
						userInfo.send_Message("Note?"+ Nickname+"?" + note);
					}
				}
			}
			else if(protocol.equals("CreateRoom")){
				for(int i = 0; i< vector_room.size(); i++){
					RoomInfo roomInfo = (RoomInfo) vector_room.elementAt(i);
					if(roomInfo.getRoomName().equals(message)){ //방이 이미  존재
						send_Message("CreateRoomFail?ok");
						RoomCheck = false;
						break;
					}
				}
				if(RoomCheck){
					RoomInfo roomInfo_new_room = new RoomInfo(message, this);
					vector_room.add(roomInfo_new_room);
					CurrentRoom = message;
					send_Message("CreateRoom?" + message);
					send_Message("Chatting?알림?******    "+Nickname+"님이 입장하셨습니다     *****");
					BroadCast("NewRoom?"+message);
				}
				else{
					RoomCheck = true;	
				}
				
			}
			else if(protocol.equals("Chatting")){
				String msg = stringTokenizer.nextToken();
				for(int i =0; i <vector_room.size(); i++){
					RoomInfo roomInfo = (RoomInfo) vector_room.elementAt(i);
					if(roomInfo.getRoomName().equals(message)){ // 해당 방을 찾으면
						roomInfo.BroadCast_Room("Chatting?"+Nickname+"?"+msg);
					}
				}
			}
			else if(protocol.equals("JoinRoom")){
				for(int i =0; i <vector_room.size(); i++){
					RoomInfo roomInfo = (RoomInfo) vector_room.elementAt(i);
					if(roomInfo.getRoomName().equals(message)){
						CurrentRoom = message;
						//새로운 사용자를 알린다
						roomInfo.Add_User(this);
						roomInfo.BroadCast_Room("Chatting?알림?******    "+Nickname+"님이 입장하셨습니다     *****");
						send_Message("JoinRoom?" + message);
					}
				}
			}
			else if(protocol.equals("ExitRoom")){
				ExitRoom(this);
			}
		}
		private void send_Message(String message){
			try {
				dataOutputStream.writeUTF(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				textArea.append("메세지 전송 실패");
			}
		}
		
	}
	
	private class RoomInfo{

		private String Room_name;
		private Vector vector_room_user = new Vector();
		
		RoomInfo(String str, UserInfo userInfo){
			this.Room_name = str;
			this.vector_room_user.add(userInfo);
		}
		public String getRoomName(){
			return Room_name;
		}
		public Vector getRoom_user(){
			return vector_room_user;
		}
		private void Add_User(UserInfo userInfo){
			this.vector_room_user.add(userInfo);
		}
		public int Remove_User(UserInfo userInfo){
			this.vector_room_user.remove(userInfo);
			return vector_room_user.size();

		}
		public void BroadCast_Room(String str){ //현재 방의 모든 사람에게 알린다.
			for(int i = 0; i<vector_room_user.size(); i++){
				UserInfo userInfo = (UserInfo) vector_room_user.elementAt(i);
				userInfo.send_Message(str);
			}
		}
		
		
	}
}


