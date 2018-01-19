package org.asyou.sequoia.transaction.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.exception.ProxyDelegateException;

import java.lang.reflect.Method;

/**
 * Created by steven on 17/8/9.
 */
public class InterceptorProxy<T> extends AProxy<T> implements MethodInterceptor {

//    public InterceptorProxy(SequoiaAdapter gson){
//        this.gson = gson;
//        this.delegate = new TransactionDelegate(this.gson);
//    }

    /**
     * 创建代理对象
     * @param targetClass
     * @return
     */
    public T create(Class<T> targetClass,SequoiaAdapter adapter){
//        System.out.println("invocation " + targetClass.getFieldName());
        this.adapter = adapter;
        this.delegate = new TransactionDelegate(this.adapter);
        net.sf.cglib.proxy.Enhancer enhancer = new net.sf.cglib.proxy.Enhancer();
        enhancer.setSuperclass(targetClass);
        //回调方法
        enhancer.setCallback(this);
        //创建代理对象
        return (T) enhancer.create();
    }

    //动态代理实例化后所有方法的入口，可以实现拦截、过滤等功能，此处我们用来实现自动化事务
    //此处同一个对象嵌套调用会导致递归，需要对每次调用进行判断
    @Override
    public T intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return invocation(proxy, method, args, methodProxy);
    }

    @Override
    protected T execute(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        T result = null;
        result = (T) methodProxy.invokeSuper(proxy, args); //for cglib intercept
        return result;
    }
}
