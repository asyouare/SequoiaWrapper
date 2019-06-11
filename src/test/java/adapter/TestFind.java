package adapter;

import adapter.model.UserModel;
import org.asyou.sequoia.Page;
import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.asyou.sequoia.query.QueryMatcher;
import org.asyou.sequoia.type.DateTimeFactory;
import org.asyou.sequoia.type.DateTimeFromTo;
import org.junit.Test;

import java.util.List;

public class TestFind {
    @Test
    public void Test_Find() throws SequoiaAdapterException {

        UserModel queryModel = new UserModel();
        UserModel valueModel = new UserModel();

        SequoiaAdapter adapter = AdapterFactory.getAdapter();

        Page<UserModel> page = adapter.find(queryModel).page(0,20);

        adapter.insert().insertOne(valueModel);

        adapter.update().updateMany(queryModel, valueModel);

        adapter.delete().deleteOne(valueModel);
    }

    @Test
    public void Test_FindMany() throws SequoiaAdapterException {
        QueryMatcher matcher = QueryMatcher.create();
        matcher.dateTimeFromTo(DateTimeFromTo.fromField("addDate"));
        List<String> list = AdapterFactory.getAdapter().collection("loan_loan").find("{}").matcher(matcher).findMany();
        for(String str : list)
            System.out.println(str);
    }
    
    @Test
    public void Test_Page() throws SequoiaAdapterException {
        Page<String> page = AdapterFactory.getAdapter().collection("cl").find("{}").page(0,20);
        System.out.println(page.getTotalCount());
    }
}
