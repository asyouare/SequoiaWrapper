package adapter;

import adapter.model.AccountModel;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.asyou.sequoia.query.QueryMatcher;
import org.asyou.sequoia.query.QueryModifier;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Created by steven on 17/10/13.
 */
public class TestUpdate extends TestBaseInit {
    @Test
    public void Test_Update() throws SequoiaAdapterException {
        AccountModel query = getQuery();
        query.setName("name");
        AccountModel value = new AccountModel();
        value.setName("name-");

        int result = AdapterFactory.getAdapter().collection("cl")
                .update()
                .matcher(QueryMatcher.create(query).contain())
                .modifier(QueryModifier.create(value))
                .updateMany();

        System.out.println(result);


    }

}
