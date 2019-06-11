package adapter;

import adapter.model.AccountModel;
import org.asyou.sequoia.query.QueryMatcher;
import org.junit.Test;

/**
 * Created by steven on 17/10/13.
 */
public class TestDelete extends TestBaseInit{
    @Test
    public void Test_deleteAll() throws Exception {
        AccountModel query = getQuery();
        query.setName("name");
        AccountModel value = new AccountModel();
        value.setName("name-");

        System.out.println(
                AdapterFactory.getAdapter().collection("cl")
                        .delete()
                        .deleteMany());

        System.out.println(AdapterFactory.getAdapter().collection("cl").find().findMany());
    }
}
