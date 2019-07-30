package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyFormAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        System.out.println("BoardModifyFormAction");
        
        // board_num 파라미터 가져오기
        int board_num = Integer.parseInt(request.getParameter("board_num"));
//        String page = request.getParameter("page");
        // => Dispatcher 방식으로 이동했으므로 page 파라미터는 URL에 아직 존재한다.
        //    따라서, 현재 Action 클래스에서 사용하지 않기 때문에 별도로 가져올 필요 없음
        
        // 기존 게시물 1개의 정보 가져오기 - BoardDetailService 객체에 이미 존재하는 메서드 호출
        BoardDetailService boardDetailService = new BoardDetailService();
        BoardBean article = boardDetailService.getArticle(board_num);
        
        // request 객체에 BoardBean 객체 저장
        request.setAttribute("article", article);
        
        ActionForward forward = new ActionForward();
        forward.setPath("/board/qna_board_modify.jsp");
        forward.setRedirect(false); // Dispatcher 방식 지정
        return forward;
    }

}
