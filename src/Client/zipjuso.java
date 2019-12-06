package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import Client.Client;

import javax.swing.JButton;

public class zipjuso extends JFrame implements MouseListener,ActionListener{
	 
	private JPanel contentPane;
    private JTable table;
    private JComboBox comboBox;
    private JComboBox comboBox_1;
    private JComboBox comboBox_2;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JTextField tf_addr1 = new JTextField();
	private JTextField tf_addr3 = new JTextField();
    ArrayList<zipDAO> addressList = new ArrayList<>();
    ArrayList<zipDAO> sidoList = new ArrayList<>();
    ArrayList<zipDAO> gugunList = new ArrayList<>();
    ArrayList<zipDAO> dongList = new ArrayList<>();
    JButton ok = new JButton("선택완료");
    
    public zipjuso(){
    	
    	init();
    	start();
    	new zipControl();
    	new zipTable();
    	

    }
    public void start(){
    	ok.addActionListener(this);
    }
  
    @Override
    public void mouseClicked(MouseEvent e){
    	int row=table.getSelectedRow();
    	int col=table.getSelectedColumn();
    	String zipcode=(String)table.getValueAt(row, 0);
		String juso=(String)table.getValueAt(row, 1)+" "+(String)table.getValueAt(row, 2)+" "+(String)table.getValueAt(row, 3)
		+" "+(String)table.getValueAt(row, 4)+" "+(String)table.getValueAt(row, 5)+" "+(String)table.getValueAt(row, 6);
		
		Member.tf_addr1.setText(zipcode);  //static
	    Member.tf_addr2.setText(juso);
		
    }
   
    
     public void init() {

         

         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         setBounds(100, 100, 660, 552);

         contentPane = new JPanel();
         contentPane.setBackground(Color.WHITE);
         contentPane.setForeground(Color.WHITE);

         contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

         setContentPane(contentPane);

         contentPane.setLayout(null);

         

         panel = new JPanel();
         panel.setBackground(Color.WHITE);

         panel.setBorder(new TitledBorder(null, "우편번호 검색", TitledBorder.LEADING, TitledBorder.TOP, null, null));

         panel.setBounds(22, 58, 594, 70);

         contentPane.add(panel);

         panel.setLayout(null);

         

         scrollPane = new JScrollPane();

         scrollPane.setBounds(28, 147, 588, 356);

         contentPane.add(scrollPane);

         

         table = new JTable();

         table.setModel(new DefaultTableModel(

                 new Object[][] { {" ", " ", " ", " ", " ", " ", " ", " "},},

                 new String[] {"우편번호", "시.도", "구.군", "동", "리", "시작번지", "마지막번지","일련번호" }) 
         {

                 boolean[] columnEditables = new boolean[] {

                        false, false, false, false, false, false, false, false

                 };

                 public boolean isCellEditable(int row, int column) {

                        return columnEditables[column];

                 }

         });

         

         scrollPane.setViewportView(table);
         
         table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//단일선택
         table.addMouseListener(this);
         
 
         

         //첫번째 combobox 생성

         comboBox = new JComboBox();   
         comboBox.setFont(new Font("돋움체", Font.PLAIN, 12));

         comboBox.setBounds(98, 42, 121, 20);

         panel.add(comboBox);

         comboBox.addItem("시.도 선택");

         

         displaySido();

         //시.도 콤보박스=============================================

         comboBox.addItemListener(new ItemListener() {

                 public void itemStateChanged(ItemEvent e) {

         if(e.getStateChange()==ItemEvent.SELECTED)

                 selectSido(comboBox.getSelectedItem().toString());

                        

                 }

         });

         comboBox.setToolTipText("");

         JLabel label = new JLabel("시.도");
         label.setFont(new Font("돋움체", Font.PLAIN, 12));

         label.setBounds(108, 16, 100, 20);

         panel.add(label);

         label.setHorizontalAlignment(SwingConstants.CENTER);

         

         //구.군 ComboBox=============================================

         comboBox_1 = new JComboBox();
         comboBox_1.setFont(new Font("돋움체", Font.PLAIN, 12));

         comboBox_1.setBounds(223, 42, 118, 20);

         panel.add(comboBox_1);

         

         JLabel label_1 = new JLabel("구.군");
         label_1.setFont(new Font("돋움체", Font.PLAIN, 12));

         label_1.setBounds(231, 16, 100, 20);

         panel.add(label_1);

         label_1.setHorizontalAlignment(SwingConstants.CENTER);

         

         comboBox_1.addItemListener(new ItemListener() {

                 public void itemStateChanged(ItemEvent e) {

                        if(e.getStateChange()==ItemEvent.SELECTED)

                                selectGugun(comboBox.getSelectedItem().toString() ,comboBox_1.getSelectedItem().toString());

                 }

         });

         

         //동 ComboBox=============================================

         comboBox_2 = new JComboBox();
         comboBox_2.setFont(new Font("돋움체", Font.PLAIN, 12));

         comboBox_2.setBounds(343, 42, 118, 20);

         panel.add(comboBox_2);

         

         JLabel label_2 = new JLabel("동");
         label_2.setFont(new Font("돋움체", Font.PLAIN, 12));

         label_2.setBounds(350, 16, 100, 20);

         panel.add(label_2);

         label_2.setHorizontalAlignment(SwingConstants.CENTER);
         ok.setFont(new Font("휴먼모음T", Font.PLAIN, 13));
         
         
         ok.setBounds(473, 42, 108, 20);
         panel.add(ok);
         
         JLabel lblNewLabel = new JLabel("우편번호 찾기");
         lblNewLabel.setFont(new Font("한컴 소망 B", Font.PLAIN, 18));
         lblNewLabel.setBounds(270, 20, 146, 28);
         contentPane.add(lblNewLabel);

         comboBox_2.addItemListener(new ItemListener() {

                 public void itemStateChanged(ItemEvent e) {

                        if(e.getStateChange()==ItemEvent.SELECTED)

                        

                        //table에 집어넣기 실행=====================================

                        selectDong(comboBox.getSelectedItem().toString(), comboBox_1.getSelectedItem().toString(), comboBox_2.getSelectedItem().toString());

                 }              

         });
         
        this.setVisible(true); //true면 화면에 보이게
 		this.setResizable(false);
     }

     public class zipDAO {

         private String seq;
         private String zipcode;
         private String sido;
         private String gugun;
         private String dong;
         private String ri;
         private String bldg;
         private String bungi;

         public String getSeq() {

                return seq;

         }

         public void setSeq(String seq) {

                this.seq = seq;

         }

         public String getZipcode() {

                return zipcode;

         }

         public void setZipcode(String zipcode) {

                this.zipcode = zipcode;

         }

         public String getSido() {

                return sido;

         }

         public void setSido(String sido) {

                this.sido = sido;

         }

         public String getGugun() {

                return gugun;

         }

         public void setGugun(String gugun) {

                this.gugun = gugun;

         }

         public String getDong() {

                return dong;

         }

         public void setDong(String dong) {

                this.dong = dong;

         }

         public String getRi() {

                return ri;

         }

         public void setRi(String ri) {

                this.ri = ri;

         }

         public String getSt_bldg() {

                return bldg;

         }

         public void setSt_bldg(String bldg) {

                this.bldg=bldg;

         }

         public String getEd_bungi() {

                return bungi;

         }

         public void setEd_bungi(String bungi) {

                this.bungi=bungi;

         }

         

         

 }
     public class zipTable extends AbstractTableModel {

         //컬럼의 이름

         String[] columNames = {"우편번호","시.도","구.군","동","리","시작번지","마지막번지","일련번호"};

         //데이터

         Object[][] data = {{" ", " "," "," "," "," "," "," "}};

         
         public zipTable(){

            
         }

  

         public zipTable(Object[][] data) {

                this.data = data;

         }

  

         @Override

         public int getColumnCount() {

                // TODO Auto-generated method stub

                return columNames.length;

         }

  

         @Override

         public int getRowCount() {

                // TODO Auto-generated method stub

                return data.length;           //2차 배열의 길이

         }

  

         @Override

         public Object getValueAt(int arg0, int arg1) {

                // TODO Auto-generated method stub

                return data[arg0][arg1];

         }

  

         @Override

         public String getColumnName(int arg0) {

                // TODO Auto-generated method stub

                return columNames[arg0];

         }


 }
     public class zipControl {

         Connection conn;
         Statement stmt;
         ResultSet rs;


         // 데이터베이스 연결

         public void connection() {

                  try {
       
                	  Class.forName("com.mysql.cj.jdbc.Driver");
                      conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "wjs1018013");

                  } catch (ClassNotFoundException e) {

                  } catch (SQLException e) {

                  }

         }

         

         // 데이터베이스 연결종료

         public void disconnection() {

                  try {

                           if(stmt != null) stmt.close();

                           if(rs != null) rs.close();

                           if(conn != null) conn.close();

                  } catch (SQLException e) {

                  }

         }

         

         // 시도데이터=============================================

         public ArrayList<zipDAO> searchSido() {

                 

                  try {

                          
                           stmt = conn.createStatement();   
                           
                           ResultSet rs = stmt.executeQuery("select distinct(sido) from zipcode");   

                           while(rs.next()){

                                   zipDAO zipcode = new zipDAO();

                                   zipcode.setSido(rs.getString("SIDO"));

                                   sidoList.add(zipcode);

                           }

                  } catch (SQLException e) {

                  }

 

                  return sidoList;

                  

         }

         

         // 구군데이터=============================================

         public ArrayList<zipDAO> searchGugun(String sido) {

                  try {

                           stmt =conn.createStatement();   
                           ResultSet rs = stmt.executeQuery("select distinct(gugun) from zipcode where sido = \'" + sido + "\' ");   

                           while(rs.next()){

                                   zipDAO zipcode = new zipDAO();

                                   zipcode.setGugun(rs.getString("GUGUN"));

                                   gugunList.add(zipcode);

                           }

                  } catch (SQLException e) {

                  }

                                   

                  return gugunList;          

         }

         

         // 동데이터=============================================

         public ArrayList<zipDAO> searchDong(String sido, String gugun) {

                  try {

                           stmt = conn.createStatement();   
                           ResultSet rs = stmt.executeQuery("select distinct(dong) from zipcode where sido = \'" + sido + "\'  and gugun = \'" + gugun + "\'");   
                        		   
                        		   
                           while(rs.next()){

                                   zipDAO zipcode = new zipDAO();

                                   zipcode.setDong(rs.getString("DONG"));

                                   dongList.add(zipcode);

                           }

                  } catch (SQLException e) {

                  }

                  

                  

                  return dongList;           

         }

         

         // 전부주소 데이터 =============================================

         public ArrayList<zipDAO> searchAddress(String sido, String gugun, String dong) {


                  try {

                           stmt = conn.createStatement();
                           
                           ResultSet rs = stmt.executeQuery("select * from zipcode where sido = \'" + sido + "\'  and gugun = \'" + gugun + "\' and dong = \'" + dong +"\'");   
                        		   

                           while(rs.next()){

                                   zipDAO zipcode = new zipDAO();

                                   zipcode.setZipcode(rs.getString("zipcode"));

                                   zipcode.setSido(rs.getString("sido"));

                                   zipcode.setGugun(rs.getString("gugun"));

                                   zipcode.setDong(rs.getString("dong"));

                                   zipcode.setRi(rs.getString("ri"));

                                   zipcode.setSt_bldg(rs.getString("bldg"));

                                   zipcode.setEd_bungi(rs.getString("bungi"));
                                   
                                   zipcode.setSeq(rs.getString("seq"));

                                   addressList.add(zipcode);

                           }

                  } catch (SQLException e) {

                  }

                  return addressList;                

         }

}

     public static void main(String[] args) {     

    	new zipjuso();
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                   try {

                           zipjuso frame = new zipjuso();

                           frame.setVisible(true);

                   } catch (Exception e) {

                           e.printStackTrace();

                   }

            }

    });
        }

     
    


     /**

      * Create the frame.

      */

    

     //프로그램 시작시 시.도 보여주기====================================================================

     public void displaySido(){

            //선언

            zipControl controller = new zipControl();

            //DB연결

            controller.connection();              

            //

            ArrayList<zipDAO> sidoList = controller.searchSido();

            for(int i = 0 ; i < sidoList.size() ; i++){

                    zipDAO zipcode = sidoList.get(i);

                    comboBox.addItem(zipcode.getSido());

            }              

            //DB연결 해제

            controller.disconnection();

     }

     //sido 선택(gugun 출력)====================================================================

     public void selectSido(String sido){

            System.out.println(sido);

            zipControl controller = new zipControl();

            //DB연결

            controller.connection();              

            //

            ArrayList<zipDAO> gugunList = controller.searchGugun(sido);

            comboBox_1.removeAllItems();

            comboBox_2.removeAllItems();

            comboBox_1.addItem("구.군 선택");

            for(int i = 0 ; i < gugunList.size() ; i++){

                    zipDAO zipcode = gugunList.get(i);

                    comboBox_1.insertItemAt(zipcode.getGugun(), i);

            }

            table.setModel(new zipTable());

            //DB연결 해제

            controller.disconnection();

     }       

     //gugun 선택(dong 출력)====================================================================

     public void selectGugun(String sido, String gugun){

            System.out.println(gugun);

            zipControl controller = new zipControl();

            //DB연결

            controller.connection();              

            //

            ArrayList<zipDAO> dongList = controller.searchDong(sido, gugun);

            comboBox_2.removeAllItems();

            comboBox_2.addItem("동 선택");

            for(int i = 0 ; i < dongList.size() ; i++){

                    zipDAO zipcode = dongList.get(i);

                    comboBox_2.insertItemAt(zipcode.getDong(),i);

            }

            table.setModel(new zipTable());

            //DB연결 해제

            controller.disconnection();                  

     }

     

     //마지막 Dong 선택(테이블에 출력)====================================================================

     public void selectDong(String sido, String gugun, String dong){

            

            zipControl controller = new zipControl();

            //DB연결

            controller.connection();              

            //

            ArrayList<zipDAO> addressList = controller.searchAddress(sido, gugun, dong);

            

            Object[][] arrAdd = new Object[addressList.size()][8];

            

            for(int i = 0 ; i < addressList.size() ; i++){

                    zipDAO zipcode = addressList.get(i);

                    //출력!

                    System.out.println(zipcode.getZipcode()+ " " +zipcode.getSido()+ " " +zipcode.getGugun()+ " " +zipcode.getDong() + " " + zipcode.getRi() + " " + zipcode.getSt_bldg() + " " + zipcode.getEd_bungi() + zipcode.getSeq());                       
                    
                    //테이블에 넣기!


                    arrAdd[i][0] = zipcode.getZipcode();

                    arrAdd[i][1] = zipcode.getSido();

                    arrAdd[i][2] = zipcode.getGugun();

                    arrAdd[i][3] = zipcode.getDong();

                    arrAdd[i][4] = zipcode.getRi();

                    arrAdd[i][5] = zipcode.getSt_bldg();

                    arrAdd[i][6] = zipcode.getEd_bungi();

                    arrAdd[i][7] = zipcode.getSeq();

                    

                    table.setModel(new zipTable(arrAdd));

            }

            //DB연결 해제

            controller.disconnection();

            

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


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==ok){
	         dispose();
		}
		
	}


 
}



