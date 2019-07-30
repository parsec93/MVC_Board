package dao;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.BoardBean;

public class BoardDAO {
    // -----------------------------------------------------
    // DAO 인스턴스 생성 관리를 위한 싱글톤 디자인 패턴
    private static BoardDAO instance;
    private BoardDAO() {}
    
    public static BoardDAO getInstance() {
        // 기존의 BoardDAO 인스턴스가 없을 경우에만 인스턴스를 새로 생성
        if(instance == null) {
            instance = new BoardDAO();
        }
        
        return instance;
    }
    // -----------------------------------------------------

    Connection con;
    
    // Service 클래스로부터 Connection 객체 전달받는 메서드
    public void setConnection(Connection con) {
        this.con = con;
    }
    
    
    // 글 등록 요청을 처리하는 insertArticle() 메서드
    public int insertArticle(BoardBean article) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int insertCount = 0; // 게시물 등록 성공 여부를 저장할 변수(성공 = 1, 실패 = 0)
        
        try {
            // 현재 게시물 번호 중 가장 큰 번호 조회
            String sql = "SELECT MAX(board_num) FROM board";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            int num = 1; // 게시물 번호 저장할 변수(기본값으로 1 지정)
            
            if(rs.next()) {
                // 기존 게시물이 존재할 경우(가장 큰 번호를 조회했을 경우)
                num = rs.getInt(1) + 1; // 가장 큰 번호 + 1 값(새로운 게시물 번호)을 저장
            } 
            
            // 게시물 등록 구문 작성(마지막 컬럼인 board_date 항목은 DB 현재 시각 정보 사용)
            sql = "INSERT INTO board VALUES (?,?,?,?,?,?,?,?,?,?,now())";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, num); // 게시물 번호(새로 계산한 번호 사용)
            // board_name, board_pass, board_subject, board_content, board_file - BoardBean 객체 값 사용
            pstmt.setString(2, article.getBoard_name());
            pstmt.setString(3, article.getBoard_pass());
            pstmt.setString(4, article.getBoard_subject());
            pstmt.setString(5, article.getBoard_content());
            pstmt.setString(6, article.getBoard_file());
            pstmt.setInt(7, num); // 참조 게시물 번호 = 새 글이므로 현재 게시물 번호로 설정
            pstmt.setInt(8, 0); // 들여쓰기 레벨 = 새 글이므로 0
            pstmt.setInt(9, 0); // 글 순서번호 = 새 글이므로 0
            pstmt.setInt(10, 0); // 조회수 = 새 글이므로 0
            
            insertCount = pstmt.executeUpdate(); // 글 등록 처리 결과를 int 형 값으로 리턴받음
            
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("insertArticle() 에러 - " + e.getMessage());
            
            // 만약, 외부로 예외를 던질 때 메세지를 직접 지정하고 싶을 경우 throw 키워드 사용하여
            // Exception 객체 생성 시 예외 메세지를 지정하면 된다! => throws 키워드로 예외 던지기 필요!
//            throw new Exception("insertArticle() 에러 - " + e.getMessage());
        } finally {
            // static import 문을 사용하여 JdbcUtil 클래스명 지정 필요없음
            close(rs);
            close(pstmt);
        }
        
        return insertCount;
    }

    // 전체 게시물 갯수를 조회하여 리턴
    public int selectListCount() {
        int listCount = 0; // 게시물 갯수를 저장하는 변수
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT COUNT(*) FROM board";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                listCount = rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.out.println("selectListCount() 에러 - " + e.getMessage());
        } finally {
            // static import 문을 사용하여 JdbcUtil 클래스명 지정 필요없음
            close(rs);
            close(pstmt);
        }
        
        return listCount;
    }

    // 게시물 목록 조회하여 리턴
    public ArrayList<BoardBean> selectArticleList(int page, int limit) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();

        int startRow = (page - 1) * 10; // 읽어올 목록의 첫 레코드 번호
        
        try {
            // SELECT 구문 : board 테이블 데이터 전체 조회 
            // => board_re_ref 기준 내림차순, board_re_seq 기준 오름차순
            // => 전체 갯수가 아닌 시작 레코드 번호 ~ limit 갯수 만큼 읽어오기
            String sql = "SELECT * FROM board ORDER BY board_re_ref DESC,board_re_seq ASC LIMIT ?,?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, limit);
            rs = pstmt.executeQuery();
            
            // ResultSet 객체 내의 모든 레코드를 각각 레코드별로 BoardBean 에 담아서 ArrayList 객체에 저장
            // => 패스워드 제외
            while(rs.next()) {
                BoardBean boardBean = new BoardBean();
                boardBean.setBoard_num(rs.getInt("board_num"));
                boardBean.setBoard_name(rs.getString("board_name"));
                boardBean.setBoard_subject(rs.getString("board_subject"));
                boardBean.setBoard_content(rs.getString("board_content"));
                boardBean.setBoard_file(rs.getString("board_file"));
                boardBean.setBoard_re_ref(rs.getInt("board_re_ref"));
                boardBean.setBoard_re_lev(rs.getInt("board_re_lev"));
                boardBean.setBoard_re_seq(rs.getInt("board_re_seq"));
                boardBean.setBoard_readcount(rs.getInt("board_readcount"));
                boardBean.setBoard_date(rs.getDate("board_date"));
                
                articleList.add(boardBean);
            }
            
        } catch (SQLException e) {
            System.out.println("selectArticleList() 에러 - " + e.getMessage());
        } finally {
            close(rs);
            close(pstmt);
        }
        
        
        return articleList;
    }
    
    // 글 내용 보기 - 게시물 상세 정보를 조회하여 리턴
    public BoardBean selectArticle(int board_num) { // 글 번호를 전달받아 조회 후, 결과를 BoardBean 으로 리턴
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BoardBean boardBean = null;
        
        try {
            // board_num 에 해당하는 게시물 조회 후, 결과값을 BoardBean 에 저장하여 리턴
            String sql = "SELECT * FROM board WHERE board_num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, board_num);
            rs = pstmt.executeQuery();
            
            if(rs.next()) { // 조회된 게시물이 있을 경우
                boardBean = new BoardBean();
                boardBean.setBoard_num(rs.getInt("board_num"));
                boardBean.setBoard_name(rs.getString("board_name"));
                boardBean.setBoard_subject(rs.getString("board_subject"));
                boardBean.setBoard_content(rs.getString("board_content"));
                boardBean.setBoard_file(rs.getString("board_file"));
                boardBean.setBoard_re_ref(rs.getInt("board_re_ref"));
                boardBean.setBoard_re_lev(rs.getInt("board_re_lev"));
                boardBean.setBoard_re_seq(rs.getInt("board_re_seq"));
                boardBean.setBoard_readcount(rs.getInt("board_readcount"));
                boardBean.setBoard_date(rs.getDate("board_date"));
                boardBean.setBoard_pass(rs.getString("board_pass"));
            }
        } catch (SQLException e) {
            System.out.println("selectArticle() 에러 - " + e.getMessage());
        } finally {
            close(rs);
            close(pstmt);
        }
        
        return boardBean;
        
    }

    // 게시물 패스워드 확인
    public boolean isArticleBoardWriter(int board_num, String board_pass) {
//        System.out.println("BoardDAO - isArticleBoardWriter()");
        // board_num 에 해당하는 게시물의 패스워드를 비교
        boolean isArticleWriter = false; 
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // 게시물 번호에 해당하는 패스워드가 존재하는지 검색
            String sql = "SELECT * FROM board WHERE board_num=? AND board_pass=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, board_num);
            pstmt.setString(2, board_pass);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                // 게시물 번호에 해당하는 패스워드가 일치하여 레코드가 검색될 경우
                isArticleWriter = true;
            }
            
        } catch (SQLException e) {
            System.out.println("isArticleBoardWriter() 에러 - " + e.getMessage());
        } finally {
            close(rs);
            close(pstmt);
        }
        
        
        return isArticleWriter;
    }

    // 글 수정
    public int isUpdateArticle(BoardBean article) {
        int updateCount = 0;
        
        PreparedStatement pstmt = null;
        
        try {
            // 글 번호에 해당하는 레코드에 대해 제목(subject), 내용(content) 수정 후 결과값 리턴
            String sql = "UPDATE board SET board_subject=?,board_content=? WHERE board_num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, article.getBoard_subject());
            pstmt.setString(2, article.getBoard_content());
            pstmt.setInt(3, article.getBoard_num());
            updateCount = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("isUpdateArticle() 에러 - " + e.getMessage());
        } finally {
            close(pstmt);
        }
        
        return updateCount;
    }

    // 답글 등록
    public int insertReplyArticle(BoardBean article) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int num = 1; // 답변글 번호
        int insertCount = 0; // 성공 여부의 리턴값을 저장할 변수
        
        // article 객체에서 참조글(board_re_ref), 들여쓰기레벨(board_re_lev), 순서번호(board_re_seq) 가져오기 
        int board_re_ref = article.getBoard_re_ref();
        int board_re_lev = article.getBoard_re_lev();
        int board_re_seq = article.getBoard_re_seq();
        
        try {
            // 현재 게시물에서 가장 큰 번호 조회
            String sql = "SELECT MAX(board_num) FROM board";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            // ResultSet 객체가 존재할 경우(조회 성공할 경우) => 현재 게시물 가장 큰번호 + 1 값을 num 에 저장
            // => 객체가 없을 경우 기본값 num = 1
            if(rs.next()) {
                num = rs.getInt(1) + 1;
            }
            
            // 동일한 참조글(board_re_ref)에 대한 기존 답글의 순서번호 값을 모두 1씩 증가시킴 
            // => 최신 글이 seq 번호가 가장 낮은 값(1)이어야 하므로 기존 답글들의 값을 전부 +1 시킴
            sql = "UPDATE board SET board_re_seq=board_re_seq+1 WHERE board_re_ref=? AND board_re_seq>?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, board_re_ref);
            pstmt.setInt(2, board_re_seq);
            int updateCount = pstmt.executeUpdate();
            
            if(updateCount > 0) {
                commit(con);
            }
            
            // 새 답글에 대한 순서 번호, 들여쓰기 레벨 1 증가시킴
            board_re_seq += 1;
            board_re_lev += 1;
            
            // 답변글 등록 => 파일을 제외한 나머지 등록
            sql = "INSERT INTO board VALUES (?,?,?,?,?,?,?,?,?,?,now())";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, num); // 게시물 번호(새로 계산한 번호 사용)
            pstmt.setString(2, article.getBoard_name());
            pstmt.setString(3, article.getBoard_pass());
            pstmt.setString(4, article.getBoard_subject());
            pstmt.setString(5, article.getBoard_content());
            pstmt.setString(6, ""); // 답글 파일 업로드 없음
            pstmt.setInt(7, board_re_ref); 
            pstmt.setInt(8, board_re_lev);
            pstmt.setInt(9, board_re_seq);
            pstmt.setInt(10, 0); // 조회수 = 새 글이므로 0
            
            insertCount = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("insertReplyArticle() 에러 - " + e.getMessage());
        } finally {
            close(rs);
            close(pstmt);
        }
        
        return insertCount;
    }

    
    // 조회수 증가
    public int updateReadcount(int board_num) {
        int updateCount = 0;
        
        PreparedStatement pstmt = null;
        
        try {
            // board_num 에 해당하는 게시물의 기존 조회수(readcount) + 1
            String sql = "UPDATE board SET board_readcount=board_readcount+1 WHERE board_num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, board_num);
            updateCount = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("updateReadcount() 에러 - " + e.getMessage());
        } finally {
            close(pstmt);
        }
        
        return updateCount;
    }
    
    
    //글삭제 
    public int deleteArticle(int board_num) {
    	
    	int deleteCount = 0;
    	PreparedStatement pstmt = null;
    	
    	try {
			String sql = "DELETE FROM board where board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			deleteCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
    			
    	return deleteCount;
    }
    
    
    
    
}




















