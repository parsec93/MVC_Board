package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyFormAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("BoardReplyFormAction");
        
        // 게시물 번호(board_num), 현재 페이지(page) 파라미터 가져오기
        int board_num = Integer.parseInt(request.getParameter("board_num"));
        String page = request.getParameter("page");
        
        // BoardDetailService 클래스의 getArticle() 메서드를 호출하여 게시물 정보 가져오기
        BoardDetailService boardDetailService = new BoardDetailService();
        BoardBean article = boardDetailService.getArticle(board_num);
        
        // 현재 게시물 정보(article), 현재 페이지(page) request 객체에 저장
        request.setAttribute("article", article);
        request.setAttribute("page", page);
        
        ActionForward forward = new ActionForward();
        forward.setPath("/board/qna_board_reply.jsp");
        forward.setRedirect(false);
        return forward;
    }

}
