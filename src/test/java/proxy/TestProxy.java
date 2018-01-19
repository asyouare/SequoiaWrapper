package proxy;

import adapter.model.AccountModel;
import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import proxy.dynamic.ITalk;
import proxy.dynamic.PeopleTalk;
import adapter.AdapterFactory;
import org.asyou.sequoia.transaction.proxy.*;
import org.junit.Test;

/**
 * Created by steven on 2017/7/19.
 */
public class TestProxy {
    @Test
    public void Test_DynamicProxy() throws Throwable {
        AdapterFactory.initConfig();
        ITalk italk = (ITalk) ProxyFactory.create(PeopleTalk.class, AdapterFactory.getAdapter());
        italk.talk();

    }

    static PeopleTalk peopleTalkService = ProxyFactory.create(PeopleTalk.class, AdapterFactory.getAdapter());

    @Test
    public void Test_CglibProxy() throws SequoiaAdapterException {
        System.out.println(AdapterFactory.getAdapter().getHost().getUsedConnNum());

        try {
            peopleTalkService.talk();
        }catch (Exception e){
            // todo 回滚后执行
        }

        System.out.println(AdapterFactory.getAdapter().getHost().getUsedConnNum());
    }

    @Test
    public void Test_Conn() throws SequoiaAdapterException {
        System.out.println(AdapterFactory.getAdapter().getHost().getUsedConnNum());
        System.out.println(AdapterFactory.getAdapter().getHost().getUsedConnNum());
    }

    @Test
    public void Test_ThreadFindOne() throws InterruptedException, SequoiaAdapterException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
            SequoiaAdapter adapter = AdapterFactory.getAdapter();
                for (int i = 0; i < 1000; i++) {
                    try {
                        AccountModel r = adapter.find().as(AccountModel.class).findOne();
                        System.out.println(String.format(
                                "index: %S, idle: %s, used: %s",
                                i+1,
                                adapter.getHost().getIdleConnNum(),
                                adapter.getHost().getUsedConnNum()));

                    } catch (SequoiaAdapterException e) {
                        e.printStackTrace();
                        try {
                            Thread.sleep(60 * 60 * 1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    }
//                    try {
//                        peopleTalk.talk();
//                    } catch (SequoiaAdapterException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });

        t1.start();
        t1.join();
    }
}
