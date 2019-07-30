package svc;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

import static db.JdbcUtil.*;

public class BoardDetailService {
    // 글 내용 보기 요청을 처리하는 비즈니스 로직 - getArticle() 메서드 정의
    public BoardBean getArticle(int board_num) throws Exception {
        // Connection 객체 가져오기 - getConnection()
        Connection con = getConnection();
        
        // BoardDAO 객체 가져오기 - getInstance()
        BoardDAO boardDAO = BoardDAO.getInstance();
        
        // Connection 객체 -> BoardDAO 에 전달 - setConnection()
        boardDAO.setConnection(con);
        
        // BoardDAO 객체의 selectArticle() 메서드를 호출하여 글 상세 내용(BoardBean 객체) 리턴받기
        BoardBean article = boardDAO.selectArticle(board_num);
        
        
        // 조회수 증가
        int updateCount = boardDAO.updateReadcount(board_num);
        
        if(updateCount > 0) {
            commit(con);
        } else {
            rollback(con);
        }
        
        // Connection 객체 반환
        close(con);
        
        // BoardBean 객체 리턴
        return article;
    }
    
}
















