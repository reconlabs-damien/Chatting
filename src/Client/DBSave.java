package Client;

//관리자모드의 회원정보 텍스트에 저장


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.swing.JFrame;
/*
 * DB에 저장되어 있는 chat_info테이블의 데이터를 txt파일로 저장해주는 클래스
 * (txt 저장 버튼 클릭시 작동)
 */

public class DBSave extends JFrame{
	DBSave() {
		Connection conn = null;
		Statement stmt = null;
		PrintWriter writer = null;
		String url = 
		"jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, "root", "wjs1018013");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM TB_MEMBER");
			writer = new PrintWriter("chatting.txt");
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String ph = rs.getString("ph");
				String addr = rs.getString("addr");
				String gender = rs.getString("gender");




				writer.printf("아이디 : %s %n이름 : %s %n전화번호 : %s %n" + "주소 : %s%n성별 : %s %n "+
						"---------------------------------------------------------------------%n%n", id, name, ph, addr, gender);
			}

		}
		catch(ClassNotFoundException cnfe) {
			System.out.println("해당클래스를 찾을 수 없습니다!!");

		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException ioe)
		{
			System.out.println("출력 불가");
		}

		finally {
			try {
				writer.close();
			}
			catch(Exception e) { }
			try {
				stmt.close();
			}
			catch(Exception ignored) {
			}
			try {
				conn.close();
			}
			catch(Exception ignored) {
			}
		}
	}
}