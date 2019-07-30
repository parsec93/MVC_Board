package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.LoginProService;
import vo.ActionForward;

public class LoginProAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        System.out.println("LoginProAction");
        
        ActionForward forward = null;
        
        HttpSession session = request.getSession(); // 현재 세션 가져오기
        
        // 현재 세션에 저장된 id 값이 없을 경우 메인 페이지로 이동("잘못된 접근입니다" 출력)
        String sId = (String)session.getAttribute("sId");
        if(sId != null) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('잘못된 접근입니다!')");
            out.println("location.href='Main.bo'");
            out.println("</script>");
        } else {
            // 전달받은 id, password 값 가져오기
            String id = request.getParameter("id");
            String password = request.getParameter("password");
            
            LoginProService loginProService = new LoginProService();
            boolean isLoginMember = loginProService.isLoginMember(id, password);
            // isLoginMember 변수가 true 이면 세션 아이디(sId) 값을 현재 입력받은 아이디로 설정
            // => Main.bo 페이지로 Redirect 방식 포워딩
            if(!isLoginMember) {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('로그인 실패!')");
                out.println("history.back()");
                out.println("</script>");
            } else {
                System.out.println("로그인 성공!");
                session.setAttribute("sId", id);
                
                forward = new ActionForward();
                forward.setPath("Main.bo");
                forward.setRedirect(true);
            }
            
        }    
    return forward;
        
    }

}















