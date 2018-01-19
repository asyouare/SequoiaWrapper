package proxy.dynamic;

import adapter.AdapterFactory;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.bson.BasicBSONObject;

/**
 * Created by steven on 2017/7/19.
 */
public class PeopleTalk {
    public PeopleTalk() throws Exception {
//        int inx = 2; //newInstance is 7
//        String className = Thread.currentThread().getStackTrace()[inx].getClassName();
//        String methodName = Thread.currentThread().getStackTrace()[inx].getMethodName();
//        int lineNumber = Thread.currentThread().getStackTrace()[inx].getLineNumber();
//        System.out.println(className + " > " + methodName + " > " + lineNumber);

//        if ("newInstance0".equals(methodName)){
//            //todo create instance
//        }else{
//            //todo new constructor
//        }
        System.out.println("---- init PeopleTalk");
    }

//    public ISport ps = (ISport) InvocationProxy.newInstance(PeopleSport.class);
//    public ISport ps = (ISport) InterceptorProxy.newInstance(PeopleSport.class);
//    public static ISport ps = (ISport) ProxyFactory.create(PeopleSport.class, AdapterFactory.getAdapterLog());
//    public ISport ps = new PeopleSport();

    public void talk() throws SequoiaAdapterException {
//        ps.jump();

        AdapterFactory.getAdapter().collection("cl").insert().insertOneT("{a:1}");
        AdapterFactory.getAdapter().collection("cl").insert().insertOneT(new BasicBSONObject("name","123"));
//        int a = 1/0;
        System.out.println("i'm walking...");
        sing(1);
        drink("");
    }

    public void sing(int a) throws SequoiaAdapterException {
        System.out.println("i'm singing...");
        AdapterFactory.getAdapter().collection("cl").insert().insertOneT(new BasicBSONObject("name","789"));
//        int a = 1/0;
    }

    public void drink(String str) throws SequoiaAdapterException {
        System.out.println("i'm drinking...");
        AdapterFactory.getAdapter().collection("cl").insert().insertOneT(new BasicBSONObject("name","steven"));
//        int a = 1/0;
//        throw new IllegalArgumentException("input is error");
    }

}
