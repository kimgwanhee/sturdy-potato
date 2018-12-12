package kr.or.ddit.listener;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import kr.or.ddit.vo.MemberVO;

public class CustomHttpSessionAttributeListener implements HttpSessionAttributeListener {

    public void attributeAdded(HttpSessionBindingEvent event)  {
    	if("authMember".equals(event.getName())) {
	    	ServletContext application = event.getSession().getServletContext();
	    	Set<MemberVO> applicationUsers = (Set<MemberVO>) application.getAttribute("applicationUsers");
	    	boolean result = applicationUsers.add((MemberVO)event.getValue());
	    	System.err.println(result);
    	}
    }

    public void attributeRemoved(HttpSessionBindingEvent event)  { 
    	if("authMember".equals(event.getName())) {
	    	ServletContext application = event.getSession().getServletContext();
	    	Set<MemberVO> applicationUsers = (Set<MemberVO>) application.getAttribute("applicationUsers");
	    	boolean result = applicationUsers.remove((MemberVO)event.getValue());
	    	System.err.println(result);
    	}
    }

    public void attributeReplaced(HttpSessionBindingEvent event)  { 
    }
	
}
