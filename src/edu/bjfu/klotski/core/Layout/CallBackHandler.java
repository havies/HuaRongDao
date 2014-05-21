package edu.bjfu.klotski.core.Layout;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CallBackHandler  implements InvocationHandler{

 
    private Object objOriginal;
	
    public CallBackHandler(Object obj) {

        this.objOriginal = obj ;
 }
    
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		   Object result ;
		   result = method.invoke(this.objOriginal ,args);
		   return result ;
	}

}
