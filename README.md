# Chatting Program!

#개발환경
OS : macOS Mojave 10.14.5
Tool : Eclipse IDE2019-06 (4.12.0)
       MySQL workbench Version 8.0.16
Java : version "1.8.0_231"
MySQL : Ver 8.0.16 for osx10.14 on x86_64 (Homebrew)

#기능
클라이언트 : 쪽지보내기, 채팅하기, 이모티콘보내기, 첨부파일보내기
관리자 : 방문기록 저장, 회원정보 수정, 회원정보DB 저장(txt), 채팅내용저장(txt) 
#설명
자바 언어를 사용한 채팅프로그램입니다.
Server.java 실행시킨 뒤 Client.java 실행하면됩니다.

사용하는 데이터베이스(oracle, mysql 등)에 맞게 코드 변경하시고 사용하면 정상적으로 작동됩니다.

#DB table 목록
1. tb_member(id, pw, name, ph, addr, gender) - 회원정보 테이블(데이터타입은 전부 varchar사용, 기본키 : id(필수))
2. iptime(ip, id, time) - 회원이 로그인한 ip와 시간을 알려주는 테이블(데이터타입 : ip(varchar), id(varchar), time(datetime))
3. chat(name, context, time) - 사용자의 이름과 채팅내용, 시간을 저장하는 테이블(데이터타입 : name(varchar), context(varchar), time(datetime))
4. zipcode(zipcode, sido, gugun, dong, ri, st_bunji, ed_bunji, seq) - 우편정보 저장하는 테이블(데이터타입 전부 varchar)
5. image(id, image_path, img) - 사용자의 사진정보를 저장하는 테이블(데이터타입 : id(varchar), image_path(varchar), img(blob))
6. admink(id, pw) - 관리자 회원정보를 저장하는 테이블(데이터타입 : id(varchar), pw(varchar))
7. fileList(imageId, Id, ext, PHOTO, imagePath) - 첨부파일 저장하는 테이블(데이터타입 : imageId(varchar), id(varchar), ext(varchar), photo(blob), imagePath(varchar))
