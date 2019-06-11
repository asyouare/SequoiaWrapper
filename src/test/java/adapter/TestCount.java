package adapter;

import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.junit.Test;

/**
 * Created by steven on 17/10/17.
 */
public class TestCount extends TestBaseInit {
    @Test
    public void Test_Count() throws SequoiaAdapterException {
        SequoiaAdapter adapter = AdapterFactory.getAdapter();
        long count = adapter.collection("cl").count().count();
        System.out.println(count);
    }
}
