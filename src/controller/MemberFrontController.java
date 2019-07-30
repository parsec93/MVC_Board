package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.LoginProAction;
import action.LogoutProAction;
import vo.ActionForward;

@WebServlet("*.me")
public class MemberFrontController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getServletPath();
		
		Action action = null;
		ActionForward forward = null;
		
		
		
		if(command.equals("/LoginForm.me")) {
			forward = new ActionForward();
			forward.setPath("/member/LoginForm.jsp");
		}else if(command.equals("/LoginPro.me")){
			action = new LoginProAction();
			
			try {
				
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/LogoutPro.me")) {
			action = new LogoutProAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		
		
		
		
		if(forward != null) {
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				
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
