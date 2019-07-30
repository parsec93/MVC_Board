package svc;

import java.sql.Connection;

import dao.MemberDAO;

import static db.JdbcUtil.*;
public class LoginProService {
	
	public boolean isLoginMember(String id, String password) {
		System.out.println("LoginProService");
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstacne();
		memberDAO.setConnection(con);
		
		boolean isLoginMember = memberDAO.selectLoginMember(id, password);
		
		
		close(con);
		
		return isLoginMember;
	}
}
