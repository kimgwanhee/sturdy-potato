package kr.or.ddit.mvc.annotation;

import java.lang.reflect.Method;


/**
 * 특정 조건에(URIMappingCondition) 해당하는 요청을 처리할 수 있는 핸들러 객체와 메소드에 대한 정보를 가진 객체
 */
public class URIMappingInfo {
	private URIMappingCondition mappingCondition;
	private Object commandHandler;
	private Method handlerMethod;
	
	public URIMappingInfo(URIMappingCondition mappingCondition, Object commandHandler, Method handlerMethod) {
		super();
		this.mappingCondition = mappingCondition;
		this.commandHandler = commandHandler;
		this.handlerMethod = handlerMethod;
	}

	//immutable객체 만들겠다는..-> 수정불가
	public URIMappingCondition getMappingCondition() { 
		return mappingCondition;
	}

	public Object getCommandHandler() {
		return commandHandler;
	}

	public Method getHandlerMethod() {
		return handlerMethod;
	}
}
