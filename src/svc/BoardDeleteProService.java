package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;

public class BoardDeleteProService {

    // 게시물 패스워드 확인
    public boolean isArticleWriter(int board_num, String board_pass) throws Exception {
        boolean isArticleWriter = false;
        
        // 객체 가져오기
        Connection con = getConnection();
        BoardDAO boardDAO = BoardDAO.getInstance();
        boardDAO.setConnection(con); // DAO 객체에 Connection 객체 전달
        
        // BoardDAO 객체의 isArticleBoardWriter() 메서드를 호출하여 패스워드 확인
        isArticleWriter = boardDAO.isArticleBoardWriter(board_num, board_pass);
        
        close(con);
        
        return isArticleWriter;
    }

    // 글 번호를 전달받아 게시물 삭제(본인 확인 완료된 상태)
    public boolean removeArticle(int board_num) throws Exception {
        boolean isRemoveSuccess = false; // 게시물 삭제 결과 저장할 변수
        
        Connection con = getConnection();
        BoardDAO boardDAO = BoardDAO.getInstance();
        boardDAO.setConnection(con);
        		
        // BoardDAO 객체의 deleteArticle() 메서드를 호출하여 글 번호(board_num)을 전달
        // => 계시물 삭제 결과를 정수형으로 리턴받아 성공 여부판별
        int deleteCount = boardDAO.deleteArticle(board_num);
        if(deleteCount == 1) {
        	commit(con);
        	isRemoveSuccess = true;
        }else {
        	rollback(con);
        }
        
        close(con);
        
        return isRemoveSuccess;
    }
    
    

}
















