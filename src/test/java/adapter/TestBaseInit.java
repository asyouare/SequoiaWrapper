package adapter;

import adapter.model.AccountModel;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by steven on 17/10/18.
 */
public class TestBaseInit {
    @Before
    public void Test_Sequoia_Init() throws SequoiaAdapterException {
//        AdapterFactory.initConfig();
    }

    public AccountModel getQuery(){
        AccountModel accountModel = new AccountModel();
//        accountModel.setId(4321L);
        return accountModel;
    }

    @Test
    public void Test_Config(){
        System.out.println(AdapterFactory.getAdapter().getHost().getConfig().toString());
    }
}
