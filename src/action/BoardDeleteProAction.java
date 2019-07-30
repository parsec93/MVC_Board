package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDeleteProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardDeleteProAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        System.out.println("BoardDeleteProAction");
        int board_num = Integer.parseInt(request.getParameter("board_num"));
        String page = request.getParameter("page");
        
        BoardDeleteProService boardDeleteProService = new BoardDeleteProService();
        // 패스워드 확인
        boolean isArticleWriter = boardDeleteProService.isArticleWriter(board_num, request.getParameter("board_pass"));
        
        ActionForward forward = null;
        
        if(!isArticleWriter) { // 패스워드가 일치하지 않을 경우(올바른 작성자가 아닐 경우)
            // "삭제 권한이 없습니다" 경고창 출력 후 이전페이지로 돌아가기
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('삭제할 권한이 없습니다')");
            out.println("history.back()");
            out.println("</script>");
        } else { // 패스워드가 일치할 경우
            // BoardDeleteProService 객체의 removeArticle() 메서드를 호출하여 삭제 후 결과값 리턴받음
            boolean isDeleteSuccess = boardDeleteProService.removeArticle(board_num);
            
            // isDeleteSuccess 판별
            if(!isDeleteSuccess) {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('글 삭제 실패!')");
                out.println("history.back()");
                out.println("</script>");
            } else {
                forward = new ActionForward();
                forward.setPath("BoardList.bo?page=" + page);
                forward.setRedirect(true); // Redirect 방식이므로 생략 불가
            }
        }
        
        return forward;
    }

}
