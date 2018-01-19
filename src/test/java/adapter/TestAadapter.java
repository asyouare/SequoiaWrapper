package adapter;

import com.sequoiadb.base.CollectionSpace;
import com.sequoiadb.base.Sequoiadb;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.junit.Test;

/**
 * Created by steven on 17/10/20.
 */
public class TestAadapter extends TestBaseInit {
    @Test
    public void Test_Adapter(){
        System.out.println("space names: "+AdapterFactory.getAdapter().getSpaceNames());
        System.out.println("collection names: "+AdapterFactory.getAdapter().getCollectionNames());
    }

    @Test
    public void Test_SUM() throws SequoiaAdapterException {
        System.out.println("idle: " + AdapterFactory.getAdapter().getHost().getIdleConnNum());
        System.out.println("used: " + AdapterFactory.getAdapter().getHost().getUsedConnNum());
    }

    @Test
    public void Test_CreateCL() throws SequoiaAdapterException {
        Test_DropALL();
        Sequoiadb sdb = AdapterFactory.getAdapter().getHost().getNewConnection();

        long time = System.currentTimeMillis();
        CollectionSpace space = sdb.createCollectionSpace("cs1");
        System.out.println(System.currentTimeMillis()-time);

        time = System.currentTimeMillis();
        space.createCollection("cl1");
        System.out.println(System.currentTimeMillis()-time);

        time = System.currentTimeMillis();
        space.createCollection("cl2");
        System.out.println(System.currentTimeMillis()-time);

        sdb.releaseResource();
        AdapterFactory.getAdapter().getHost().closeDatasource();
    }

    @Test
    public void Test_DropALL() throws SequoiaAdapterException {
        Sequoiadb sdb = AdapterFactory.getAdapter().getHost().getNewConnection();
        for(String name : sdb.getCollectionSpaceNames())
            sdb.dropCollectionSpace(name);
        sdb.createCollectionSpace("cs");
        sdb.releaseResource();
        Test_Adapter();
    }
}
