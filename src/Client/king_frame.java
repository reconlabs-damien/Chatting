package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class king_frame extends JFrame implements ActionListener{

	private JButton btn_client = new JButton("회원 관리");
	private JButton btn_login = new JButton("로그인 관리");
	private JButton btn_save = new JButton("DB저장");
	private JButton btn_chat = new JButton("채팅 저장");
	private final JLabel lblNewLabel = new JLabel("");
	private final JLabel lblNewLabel_1 = new JLabel("");
	private final JLabel lblNewLabel_2 = new JLabel("ADMINISTER");
	public king_frame(){
		setTitle("관리자 모드");
		king_frame_init();
		start();
	}

	public void start(){
		btn_client.setBounds(32, 377, 253, 36);
		btn_client.addActionListener(this);
		btn_login.setBounds(32, 279, 253, 36);
		btn_login.setText("로그인 관리");
		btn_login.addActionListener(this);
		btn_save.setBounds(32, 327, 253, 36);
		btn_save.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		btn_save.setText("DB 저장");
		btn_save.addActionListener(this);

	}
	
	public void king_frame_init() {
		setBounds(100, 100, 320, 490);
		getContentPane().setBackground(Color.white);
		getContentPane().setLayout(null);
		btn_chat.setBounds(32, 222, 253, 36);

		btn_chat.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		getContentPane().add(btn_chat);
		lblNewLabel_1.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/admin.png"));
		lblNewLabel_1.setBounds(116, 92, 82, 82);

		getContentPane().add(lblNewLabel_1);
		lblNewLabel_2.setFont(new Font("Apple LiGothic", Font.BOLD, 30));
		lblNewLabel_2.setBounds(89, 174, 146, 36);

		getContentPane().add(lblNewLabel_2);

		btn_login.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		getContentPane().add(btn_login);


		btn_client.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
		getContentPane().add(btn_client);
		getContentPane().add(btn_save);
		lblNewLabel.setIcon(new ImageIcon("/Users/jun/eclipse-workspace/Chatting/img/Client.png"));
		lblNewLabel.setBounds(0, 0, 320, 468);

		getContentPane().add(lblNewLabel);

		this.setVisible(true); 


	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new king_frame();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn_login){
			new login_db();
		}

		else if(e.getSource() == btn_chat) 
		{
			new chatview();
		}
		else if(e.getSource() == btn_client){
			new member_db();
		}
		else if(e.getSource() == btn_save){
			new DBSave();
			JOptionPane.showMessageDialog(null, "정상적으로 DB데이터가 txt로 저장되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
