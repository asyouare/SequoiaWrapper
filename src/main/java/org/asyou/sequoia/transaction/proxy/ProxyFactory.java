package org.asyou.sequoia.transaction.proxy;

import org.asyou.sequoia.dao.SequoiaAdapter;

/**
 * Created by steven on 17/8/24.
 */
public class ProxyFactory {
    private static Class<? extends IProxy> proxyTemplate;

    public static Class<? extends IProxy> getProxy() {
        if (proxyTemplate == null)
            setProxyTemplate(InterceptorProxy.class);
        return proxyTemplate;
    }

    public synchronized static void setProxyTemplate(Class<? extends IProxy> proxyTemp) {
        proxyTemplate = proxyTemp;
    }

    public static<T> T create(Class<T> targetClass, SequoiaAdapter adapter){
//        if (InterceptorProxy.class.isAssignableFrom(getProxy()))
//            return new InterceptorProxy<T>(gson).create(targetClass);
//        else if (InvocationProxy.class.isAssignableFrom(getProxy()))
//            return new InvocationProxy<T>(gson).create(targetClass);
        T result = null;
        try {
            result = (T) getProxy().newInstance().create(targetClass, adapter);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public static<T> T create(Class<T> targetClass, SequoiaAdapter gson){
////        this.delegate = TransactionDelegateFactory.getTransactionDelegate();
//        net.sf.cglib.proxy.Enhancer enhancer = new net.sf.cglib.proxy.Enhancer();
//        enhancer.setSuperclass(targetClass);
//        //回调方法
//        enhancer.setCallback(new InterceptorProxy<T>(gson));
//        //创建代理对象
//        return (T) enhancer.create();
//    }
}
