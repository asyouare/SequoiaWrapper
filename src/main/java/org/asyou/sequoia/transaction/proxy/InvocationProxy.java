package org.asyou.sequoia.transaction.proxy;

import net.sf.cglib.proxy.MethodProxy;
import org.asyou.sequoia.dao.SequoiaAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by steven on 17/8/9.
 */
public class InvocationProxy<T> extends AProxy<T> implements InvocationHandler {

//    public InvocationProxy(SequoiaAdapter gson){
//        this.gson = gson;
//        this.delegate = new TransactionDelegate(this.gson);
//    }

    public T create(Class<T> targetClass, SequoiaAdapter adapter){
//        System.out.println("invocation " + targetClass.getFieldName());
        this.adapter = adapter;
        this.delegate = new TransactionDelegate(this.adapter);
        try {
            targetObject = (T) targetClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) java.lang.reflect.Proxy.newProxyInstance(targetClass.getClassLoader(),targetClass.getInterfaces(),this);
    }

    @Override
    public T invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invocation(targetObject, method,args, null);
    }

    //动态代理实例化后所有方法的入口，可以实现拦截、过滤等功能，此处我们用来实现自动化事务
    //此处同一个对象嵌套调用不会导致循环调用
    @Override
    protected T execute(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        T result = null;
        result = (T) method.invoke(proxy,args); //for dynamic proxy
        return result;
    }
}
