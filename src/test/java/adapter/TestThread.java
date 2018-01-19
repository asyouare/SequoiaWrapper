package adapter;

import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.net.ConfigOptions;
import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 17/10/26.
 */
public class TestThread {
    @Test
    public void Test_Thread() throws InterruptedException, SequoiaAdapterException {
        SequoiaAdapter adapter = AdapterFactory.getAdapter();
        for(int i=0;i<500;i++){
//            new Thread(new Runnable(){
//                @Override
//                public void run(){
//                    try {
                        int insert = 0;
//                        insert = adapter.collection("cl").insert().insertOne("{a:1}");
                        int size = 0;
                        size = (int) adapter.collection("cl").count().count();
                        int delete = 0;
//                        delete = adapter.collection("cl").delete().deleteOne("{a:1}");
                        int idle = 0;
                        idle = adapter.getHost().getIdleConnNum();
                        int used = 0;
                        used = adapter.getHost().getUsedConnNum();

                        String str = String.format(
                                "threadid: %s, insert: %s, find: %s, delete: %s, idle: %s, used: %s",
                                Thread.currentThread().getId(), insert, size, delete, idle, used);
                        System.out.println(str);
//                    } catch (SequoiaAdapterException e) {
//                        e.printStackTrace();
//                    }
                }
//            }).start();
//        }
//        Thread.sleep(60 * 60 * 1000);
    }

    @Test
    public void Test_ConnectionPool() throws InterruptedException, SequoiaAdapterException {
        System.out.println(AdapterFactory.getAdapter().getHost().getConfig().toString());
        for(int i=0;i<1000;i++) {
            Sequoiadb db = AdapterFactory.getAdapter().getHost().getNewConnection();
            System.out.println(String.format("%s , %s",i+1,db));
        }
        int idle = AdapterFactory.getAdapter().getHost().getIdleConnNum();
        int used =  AdapterFactory.getAdapter().getHost().getUsedConnNum();
        System.out.println(idle + " : " + used);

    }

    @Test
    public void Test_NewConnection() throws InterruptedException, SequoiaAdapterException {
        List<String> addrList = new ArrayList<String>();
        addrList.add("localhost:11810");
        addrList.add("localhost:11811");
        addrList.add("localhost:11812");

        ConfigOptions options = new ConfigOptions();
        options.setConnectTimeout(50000);
        options.setMaxAutoConnectRetryTime(0);

        for(int i=1;i<1000;i++){
            Sequoiadb db = new Sequoiadb(addrList, null, null, options);
            System.out.println(i);
        }
    }

    @Test
    public void Test_NewConnectionThread() throws InterruptedException, SequoiaAdapterException {
        List<String> addrList = new ArrayList<String>();
        addrList.add("localhost:11810");
        addrList.add("localhost:11811");
        addrList.add("localhost:11812");

        ConfigOptions options = new ConfigOptions();
        options.setConnectTimeout(50000);
        options.setMaxAutoConnectRetryTime(0);

        for(int i=1;i<150;i++){
            int a = i;
            new Thread(new Runnable(){
                @Override
                public void run(){
                    Sequoiadb db = new Sequoiadb(addrList, null, null, options);
                    System.out.println(a);
                }
            }).start();
        }

        Thread.sleep(60 * 60 *1000);
    }
}
