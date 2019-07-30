package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public interface Action {
    /*
     * 각 서블릿 요청을 처리하는 Action 클래스(XXXAction.java)들의 코드를 실행하기 위해
     * 각 Action 클래스의 인스턴스를 생성한 후 각각 별도로 메서드를 호출하기 보다
     * 다형성을 활용하여 Action 클래스들을 동일한 타입으로 참조하면 관리하기 쉬워진다.
     * => 각 Action 클래스들이 구현(implements)할 Action 인터페이스 정의
     *    => Action 클래스에서 구현하는 코드들은 모두 execute() 메서드를 오버라이딩하여 구현하면
     *       하나의 Action 타입으로 하나의 execute() 메서드를 호출하면 공통적으로 실행할 수 있다!
     */
    
    // Action 클래스에서 공통으로 오버라이딩 할 execute() 추상메서드 정의
    // => request 객체와 response 객체를 전달받음
    // => 모든 예외는 외부로 던짐(throws)
    // => Action 클래스에서 작업을 처리한 후 포워딩 URL 과 포워딩 방식을 지정한 후
    //    FrontController 에서 이동할 주소 및 방법을 알려주는 용도로 ActionForward 객체를 리턴 
    ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
}



















