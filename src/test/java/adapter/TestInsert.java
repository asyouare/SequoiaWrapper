package adapter;

import adapter.model.AccountModel;
import org.asyou.sequoia.common.Convert;
import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by steven on 17/10/13.
 */
public class TestInsert extends TestBaseInit {
    public List<String> getList(){
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 10;i ++){
            list.add("{id:" + i + "}");
        }
        return list;
    }

    public List<AccountModel> getModels(){
        List<AccountModel> list = new ArrayList<>();
        for(int i = 0; i < 10;i ++){
            AccountModel model = new AccountModel();
            model.setId(Long.valueOf(i));
            model.setName("name"+i);
            model.setDate(new Date());
            model.setLocalDate(LocalDate.now());
            model.setLocalDateTime(LocalDateTime.now().plusDays(i));
            list.add(model);
        }
        return list;
    }

    @Test
    public void Test_InsertMany() throws SequoiaAdapterException {
        SequoiaAdapter adapter = AdapterFactory.getAdapter();
        adapter.insert().as(AccountModel.class).insertMany(getModels());
        System.out.println(adapter.collection("cl").find("{}").findMany());
    }

    @Test
    public void Test_InsertOne() throws SequoiaAdapterException {
        AccountModel accountModel = new AccountModel();
        accountModel.setId(4321L);
        accountModel.setName("steven");
        accountModel.setLocalDateTime(LocalDateTime.now());

        AdapterFactory.getAdapter().insert().insertOne(accountModel);

        List<AccountModel> list = AdapterFactory.getAdapter().find().as(AccountModel.class).findMany();
        for(AccountModel am : list)
            System.out.println("list:   " + Convert.toJson(am));

    }

    @Test
    public void Test_Timestamp(){
        AccountModel accountModel = new AccountModel();
        accountModel.setId(4321L);
        accountModel.setName("steven");
        accountModel.setLocalDateTime(LocalDateTime.now());

        System.out.println(Convert.toJson(accountModel));
    }

}
