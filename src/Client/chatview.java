package Client;


import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class chatview extends JFrame{
    
    // DB에서 스윙 화면으로 테이블 값 가져오기(select) , 저장하기(insert), 수정하기(update), 삭제하기(delete)
        private static final long serialVersionUID = 1L;
        private JFrame frame = new JFrame();
        private JPanel panel_1;

        
        private JTable table;    
        private JScrollPane scrollPane;        // 테이블 스크롤바 자동으로 생성되게 하기

        private String driver = "com.mysql.cj.jdbc.Driver";        
        private String url = 
        "jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";        // @호스트 IP : 포트 : SID
        private String colNames[] = {"사용자","내용","시간"};  // 테이블 컬럼 값들
        private DefaultTableModel model = new DefaultTableModel(colNames, 0); //  테이블 데이터 모델 객체 생성
                
        private Connection con = null;
        private PreparedStatement pstmt = null;
        private ResultSet rs = null;   // 리턴받아 사용할 객체 생성 ( select에서 보여줄 때 필요 )

        public chatview() {
        	frame.setTitle("사용자 채팅 기록");
        	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	frame.setBounds(270, 120, 760, 442);
        	panel_1 =new JPanel();
        	panel_1.setBackground(Color.white);
            
        	setBackground(new Color(255, 250, 240));
        	frame.setContentPane(panel_1);
    		panel_1.setLayout(null); // 레이아웃 배치관리자 삭제
            table = new JTable(model);  // 테이블에 모델객체 삽입
            table.addMouseListener(new JTableMouseListener());        // 테이블에 마우스리스너 연결
            scrollPane = new JScrollPane(table);            // 테이블에 스크롤 생기게 하기
            scrollPane.setLocation(5, 119);
            scrollPane.setSize(749, 256);
            
            panel_1.add(scrollPane);         
            
            JLabel lblNewLabel = new JLabel("사용자 채팅 기록");
            lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lblNewLabel.setBackground(new Color(255, 255, 255));
            lblNewLabel.setFont(new Font("휴먼모음T", Font.PLAIN, 28));
            lblNewLabel.setForeground(Color.BLACK);
            lblNewLabel.setBounds(270, 44, 229, 40);
            panel_1.add(lblNewLabel);
            
            JLabel lblNewLabel_l = new JLabel("");
    		lblNewLabel_l.setBounds(0, 0, 749, 416);
    		panel_1.add(lblNewLabel_l);
    		
            frame.setVisible(true); //윈도우창에 보이기 지정
        	frame.setResizable(false); //윈도우창 크기 고정
            select();

        }

        private class JTableMouseListener implements MouseListener{    // 마우스로 눌려진값확인하기
            public void mouseClicked(java.awt.event.MouseEvent e) {    // 선택된 위치의 값을 출력
                
                JTable jtable = (JTable)e.getSource();
                int row = jtable.getSelectedRow();                // 선택된 테이블의 행값
                int col = jtable.getSelectedColumn();         // 선택된 테이블의 열값
            
                System.out.println(model.getValueAt(row, col));   // 선택된 위치의 값을 얻어내서 출력
                    
            }
            public void mouseEntered(java.awt.event.MouseEvent e) {
            }
            public void mouseExited(java.awt.event.MouseEvent e) {    
            }
            public void mousePressed(java.awt.event.MouseEvent e) {
            }
            public void mouseReleased(java.awt.event.MouseEvent e) {
            }
        }
                
        private void select(){        // 테이블에 보이기 위해 검색
            
            String query = "select * from tb_member";     
            try{
                Class.forName(driver);
                con = DriverManager.getConnection(url, "root", "wjs1018013");
                pstmt = con.prepareStatement(query);
                rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
                
                while(rs.next()){            // 각각 값을 가져와서 테이블값들을 추가
                    model.addRow(new Object[]{rs.getString("name"),rs.getString("context"),rs.getString("time")});
                }
                JOptionPane.showMessageDialog(null, "정상적으로 채팅정보가 txt로 저장되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){
            	JOptionPane.showMessageDialog(null, "채팅내역이 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(e.getMessage());
            }finally{
                try {
                    rs.close(); 
                    pstmt.close(); 
                    con.close();   // 객체 생성한 반대 순으로 사용한 객체는 닫아준다.
                } catch (Exception e2) {}
            }
        }       
    public static void main(String[] args) {
        
   new chatview();
}
}