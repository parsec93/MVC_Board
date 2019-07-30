package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class JdbcUtil {
    // DB 접속 관련 기능을 수행하는 메서드 정의
    // 1. DB 접속 후 Connection 객체 리턴하는 getConnection() 메서드
    //    => 리턴타입은 Connection, 인스턴스 생성 없이 클래스명만으로 접근하도록 static 메서드로 선언
    public static Connection getConnection() {
        // 설정된 Connection Pool(DBCP) 에서 Connection 객체 가져와서 리턴
        Connection con = null;
        
        try {
            // 톰캣의 컨텍스트 객체(context.xml) 가져오기
            Context initCtx = new InitialContext();
            
            // context.xml 의 <Resource> 정의에 대한 컨텍스트 가져오기
            Context envCtx = (Context)initCtx.lookup("java:comp/env");
            
            // DataSource(커넥션풀 정보) 객체 가져오기 - <Resource> 태그 내의 name 부분 문자열 지정
            DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL");
            
            // 커넥션 풀에 저장된 Connection 객체 가져와서 저장(리턴타입 Connection)
            con = ds.getConnection();
            
            // 트랜잭션 자동 커밋(Auto commit) 해제
            con.setAutoCommit(false); // 수동으로 commit 하도록 지정(미설정 시 기본값 true)
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return con;
        
    }
    
    // 2. 자원 반환을 위한 close() 메서드 정의 - 전달받는 객체에 따라 각각 다른 close() 수행
    //    => 메서드 오버로딩 활용
    public static void close(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void close(PreparedStatement pstmt) {
        // PreparedStatement 대신 Statement 사용해도 무관하나 
        // 보안 상의 문제(SQL 삽입 공격 등)로 PreparedStatement 객체 사용 권장함
        try {
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 오토 커밋 해제로 인한 수동 커밋 또는 롤백 기능의 메서드 정의
    // => Connection 객체를 전달받아 수행
    public static void commit(Connection con) {
        try {
            con.commit();
            System.out.println("Commit Success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void rollback(Connection con) {
        try {
            con.rollback();
            System.out.println("Rollback Success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}












