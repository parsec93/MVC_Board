package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static db.JdbcUtil.*;

public class MemberDAO {
	private static MemberDAO instance;
	private MemberDAO() {}
	
	public static MemberDAO getInstacne() {
		if(instance == null) {
			instance = new MemberDAO();
		}
		
		return instance;
	}
	
	Connection con;
	
	public void setConnection(Connection con) {
		this.con = con;
	}
	
	
	public boolean selectLoginMember(String id, String password) {
		boolean isLoginMember = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from loginMember where id = ? and password=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
				isLoginMember = true;
			}
			
			
		} catch (Exception e) {
			System.out.println("selectLoginMember 실패!" + e.getMessage());
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return isLoginMember;
	}
	
	
	
	
	
	
	
	
	
}
