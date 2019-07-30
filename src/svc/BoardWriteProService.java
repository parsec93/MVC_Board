package svc;

import java.sql.Connection;

import dao.BoardDAO;
import db.JdbcUtil;
import vo.BoardBean;

// 글 등록 요청을 처리하는 비즈니스 로직을 구현하는 Service 클래스
// => Action 클래스로부터 전달받은 요청을 DAO 객체에 전달하여 처리 결과를 리턴받아 commit/rollback 결정 역할
public class BoardWriteProService {
    
    public boolean registArticle(BoardBean boardBean) throws Exception {
//        System.out.println("BoardWriteProService!");
        
        boolean isWriteSuccess = false; // 글쓰기 성공/실패 여부 저장 변수
        
        // Connection, BoardDAO 인스턴스 가져오기
        // => Service 클래스에서 Connection 객체를 가져오는 이유는
        //    DB 작업 수행 결과에 따라 commit or rollback 을 Service 클래스에서 결정하기 위함
        Connection con = JdbcUtil.getConnection();
        BoardDAO dao = BoardDAO.getInstance();
        dao.setConnection(con); // DAO 객체에 Connection 객체 전달
        
        // DAO 객체의 insertArticle() 메서드를 호출하여 BoardBean 객체 전달 => 결과값 정수 리턴받기
        int insertCount = dao.insertArticle(boardBean);
        
        // insertCount 가 1 이면 글 등록 성공이므로 commit() 실행 및 isWriteSuccess 를 true 로 설정
        // 아니면 rollback() 실행
        if(insertCount == 1) {
            con.commit();
            isWriteSuccess = true;
        } else {
            con.rollback();
        }
        
        // Connection 객체 반환
        JdbcUtil.close(con);
        
        return isWriteSuccess;
    }
}















