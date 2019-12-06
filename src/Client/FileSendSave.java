package Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.StringTokenizer;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.lang.Object;

import org.apache.commons.io.FileUtils;

public class FileSendSave {

   String id ;
   Statement stmt = null; //SQL문을 데이터베이스로 전송하는데 사용
   ResultSet rs = null; //결과
   String url = "jdbc:mysql://127.0.0.1/SoftTalk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; 
   String sql = null;
   String sql2 = null;
   Properties info = null; //파일의 값을 가져오기위해
   Connection cnn = null; //db연결을 위해
   String imageAdress = null;
   String ext = null;

   FileInputStream fis;
   // Connection con=null;
   PreparedStatement pst=null;
   //   ResultSet rs=null;


   public int imageUpload(String id, String imageAdress) {
      int result = 0;//초기 결과값 0으로 설정 \
      int max = 0;
      this.id = id;//파일이름 불러오기
      this.imageAdress = imageAdress;//파일주소 불러오기
      StringTokenizer imageExt = new StringTokenizer(imageAdress, ".");//파일 확장자 불러오기
      imageExt.nextToken();//확장자 명을 가진 변수를 imageExt에 넣기
      this.ext = imageExt.nextToken();//ext라는 문자열 변수에 imageExt값 복사해 넣기
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); // 알아서 들어간다..conn로
         info = new Properties();
         info.setProperty("user", "root"); 
         info.setProperty("password", "wjs1018013");  
         cnn = DriverManager.getConnection(url, info); // 연결할 정보를 가지고있는 드라이버매니저를 던진다
         stmt = cnn.createStatement(); //db접속
         
         String sql = "select max(imageid) from fileList";   
            pst = cnn.prepareStatement(sql);  
            rs = pst.executeQuery();
            while(rs.next()) {
               max = rs.getInt(1);
            }
            
            max = max + 1 ;

         File image= new File(imageAdress);
         //        pst=cnn.prepareStatement("insert into chatting_image values (?,?,?,?)");
         //            pst.setInt(1, 0);
         //           pst.setInt(2, i1);
         //           pst.setInt(3, i2);
         //           pst.setString(4, ext);//확장자
         pst=cnn.prepareStatement("insert into fileList values (?,?,?,?,?)");
         pst.setInt(1, max);
         pst.setString(2, id);
         pst.setString(3, ext);//확장자
         fis=new FileInputStream(image);
         pst.setBinaryStream(4, fis,(int)image.length());
         pst.setString(5,imageAdress);
         pst.executeUpdate();
         //select id from user_info where id=(select max(id) from user_info);
         sql = "select imageId from fileList where imageId=(select max(imageId) from fileList)";
         //chatting_image의 imageId 가 제일 높은거
         rs = stmt.executeQuery(sql); //select문을 사용하기위해 executeQuery
         if(rs.next()==true) {
            return rs.getInt(1) ; // 성공
         }


      } catch (Exception ee) { //익셉션처리
         System.out.println("문제있음");
         ee.printStackTrace();
      }
      return result;
   }
   public static boolean blobToFile(Object item, String file_path) throws Exception{
      try {
         Blob blob = (Blob)item; //item은 데이터베이스 조회 결과이다. 여기서는 Object로 표시하였다.
         byte [] content = blob.getBytes( 1, (int) blob.length() );
         FileUtils.writeByteArrayToFile(new File(file_path), content);
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
      return true;
   }
   void imageDownload(int imageID) {
      int result = 0;
      this.id = id;
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); // 알아서 들어간다..conn로
         info = new Properties();
         info.setProperty("user", "root"); //root 계정으로
         info.setProperty("password", "wjs1018013");  //pw는 0000
         cnn = DriverManager.getConnection(url, info); // 연결할 정보를 가지고있는 드라이버매니저를 던진다
         stmt = cnn.createStatement(); //db접속
         // DownLoading the same Image
         
         
         pst=cnn.prepareStatement("select * from fileList where imageId='"+imageID+"'");
         rs=pst.executeQuery();
         rs.next();
         Blob b=rs.getBlob(4);
         
         //byte barr[]=new byte[(int)b.length()];//an array is created but contains no data
         //barr=b.getBytes(1,(int)b.length());
         String ext = rs.getString(3);   

         String filename = JOptionPane.showInputDialog("파일 이름을 입력해주세요.");
         String path = "/Users/jun/eclipse-workspace/Chatting/fileList/"+filename+"."+ext;
         blobToFile(b,path);
         JOptionPane.showMessageDialog(null, "저장 완료!", "알림", JOptionPane.INFORMATION_MESSAGE);
         //FileOutputStream fout=new FileOutputStream(path);
         //fout.write(barr);

         //fout.close();
//         return path;
      }
      catch(Exception e)
      {
//         return null;
      }

   }

}