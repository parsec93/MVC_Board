package vo;

public class ActionForward {
    /*
     * 컨트롤러에서 클라이언트 요청을 받아 Action 클래스에서 처리한 후 뷰 페이지로 포워딩(이동)할 때
     * 이동할 뷰 페이지의 URL 주소와 포워딩 방식을 관리하는 ActionForward 클래스 정의
     */
    private String path; // 포워딩 URL 주소 저장할 변수
    private boolean isRedirect; // 포워딩 방식 저장할 변수(true : Redirect 방식, false : Dispatcher 방식)
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public boolean isRedirect() {
        return isRedirect;
    }
    
    public void setRedirect(boolean isRedirect) {
        this.isRedirect = isRedirect;
    }
    
    
}
