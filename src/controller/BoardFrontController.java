package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardDeleteFormAction;
import action.BoardDeleteProAction;
import action.BoardDetailAction;
import action.BoardListAction;
import action.BoardModifyFormAction;
import action.BoardModifyProAction;
import action.BoardReplyFormAction;
import action.BoardReplyProAction;
import action.BoardWriteProAction;
import vo.ActionForward;

// 서블릿 주소가 XXX.bo 로 끝나는 주소들을 모두 매핑
// ex. BoardList.bo, BoardWriteForm.bo 등
@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet {
    // GET 방식과 POST 방식을 모두 하나의 메서드로 처리하기 위해
    // doGet() 메서드와 doPost() 메서드에서 공통된 메서드인 doProcess() 메서드를 호출하도록 함
    // => doGet(), doPost() 는 오버라이딩하고, doProcess() 는 새로 정의(매개변수는 동일해야함)
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("BoardFrontController");
        
        // POST 방식일 때의 한글 처리
        request.setCharacterEncoding("UTF-8");
        
        // URL 주소 정보 가져오기
//        System.out.println(request.getRequestURI()); // "/MVC_Board/BoardList.bo"
//        System.out.println(request.getContextPath()); // "/MVC_Board"
//        System.out.println(request.getServletPath()); // "BoardList.bo"
        
//        String requestURI = request.getRequestURI(); // "/MVC_Board/BoardList.bo"
//        String contextPath = request.getContextPath(); // "/MVC_Board"
//        String command = requestURI.substring(contextPath.length());
        // "/MVC_Board/BoardList.bo" 문자열에서 "/MVC_Board" 문자열 길이에 해당하는 인덱스부터 문자열 추출
        // => 서블릿 주소인 "/MVC_Board" 문자열 추출됨
//        System.out.println(command);
        
        String command = request.getServletPath(); // 위의 코드를 간략하게 축약(서블릿 주소 가져오기)
        
        // Action 인터페이스와 ActionForward 클래스의 참조변수 선언
        Action action = null;
        ActionForward forward = null;
        
        // if문을 사용하여 서블릿 주소 판별 후 수행할 작업 기술
        // => ex) Action 클래스 인스턴스를 생성하여 처리할 작업 수행 후 ActionForward 객체 리턴
        //        View 페이지로 포워딩을 위해 ActionForward 객체를 생성하여 주소를 지정
        if(command.equals("/Main.bo")) { // 입력된 서블릿 주소가 /Main.bo 인지 판별
            // 단순히 View 페이지로 포워딩을 시키면 되기 때문에 Action 클래스 인스턴스 생성 필요 없이
            // 바로 ActionForward 인스턴스 생성하여 포워딩할 주소(/main.jsp) 지정
            forward = new ActionForward();
            forward.setPath("/main.jsp");
        } else if(command.equals("/BoardWriteForm.bo")) {
            forward = new ActionForward();
            forward.setPath("/board/qna_board_write.jsp");
        } else if(command.equals("/BoardWritePro.bo")) {
            // Action 인터페이스를 구현한 XXXAction 클래스의 인스턴스를 생성하여
            // execute() 메서드를 호출하여 요청 작업을 처리한 후 ActionForward 객체 리턴받아야 한다.
            action = new BoardWriteProAction(); // Action 클래스 인스턴스 생성
            
            try {
                // Action 클래스의 execute() 메서드를 호출하여 request, response 객체 전달 후
                // 실행 결과에 해당 포워딩 정보를 ActionForward 객체로 리턴
                // => throws Exception 코드에 의해 try~catch 의무가 발생함!
                forward = action.execute(request, response);
                // => 포워딩 방식은 Action 클래스 내에서 결정하므로 아직 알 수 없다!
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }else if(command.equals("/BoardList.bo")) {
        	action = new BoardListAction();
        	
        	try {
                forward = action.execute(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(command.equals("/BoardDetail.bo")) {
        	action = new BoardDetailAction();        	
        	try {
                forward = action.execute(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(command.equals("/BoardReplyForm.bo")) {
        	action = new BoardReplyFormAction();
        	try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else if(command.equals("/BoardModifyForm.bo")) {
        	action = new BoardModifyFormAction();
        	try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else if(command.equals("/BoardDeleteForm.bo")) {
        	action = new BoardDeleteFormAction();
        	try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else if(command.equals("/BoardModifyPro.bo")) {
        	action = new BoardModifyProAction();
        	try {
        		forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
        } else if(command.equals("/BoardReplyPro.bo")) {
            action = new BoardReplyProAction();
            
            try {
                forward = action.execute(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(command.equals("/BoardDeleteForm.bo")) {
            forward = new ActionForward();
            forward.setPath("/board/qna_board_delete.jsp");
        } else if(command.equals("/BoardDeletePro.bo")) {
            action = new BoardDeleteProAction();
            
            try {
                forward = action.execute(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        // if문에서 서블릿 주소 판별 후 요청 처리 뒤에 포워딩할 페이지를 ActionForward 객체로 생성했기 때문에
        // ActionForward 객체 내의 포워딩 방식에 따라 지정된 URL 로 각각 다른 방식의 포워딩이 필요함
        if(forward != null) { // 예외 발생 시를 제외한 나머지(ActionForward 객체가 null 이 아닐 때)
            
            // Redirect or Dispatcher 방식 판별
            if(forward.isRedirect()) { // Redirect 방식일 경우
                response.sendRedirect(forward.getPath()); // 지정된 주소로 리다이렉트 방식 포워딩
            } else { // Dispatcher 방식일 경우
                // request 객체로부터 Dispatcher 객체를 가져오기(매개변수로 포워딩 주소 전달)
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
                // Dispatcher 객체의 forward() 메서드 호출하여 포워딩(매개변수로 request, response 객체 전달)
                dispatcher.forward(request, response);
            }
            
        }
        
        
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }
    
}
















