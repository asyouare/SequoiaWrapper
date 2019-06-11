package adapter;

import adapter.model.AccountModel;
import adapter.model.UserModel;
import org.asyou.sequoia.common.Convert;
import org.asyou.sequoia.type.DecimalObject;
import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by steven on 17/10/24.
 */
public class TestBson {
    @Test
    public void Test_SingleGson(){
        DecimalObject decimalObject = new DecimalObject(0.0003,4);

        UserModel user = new UserModel();
        user.set_id(new ObjectId());
        user.setName("abc");
        user.setDecimal(decimalObject.sub(0.0001));

        AccountModel accountModel = new AccountModel();
//        accountModel.setId(123L);
//        accountModel.setName("steven");
//        accountModel.setAge(123);
//        accountModel.setMoney(100.01);
        accountModel.setInstant(Instant.now());
        accountModel.setDate(new Date());
        accountModel.setLocalDateTime(LocalDateTime.now());

//        List<UserModel> list = new ArrayList<>();
//        list.add(user);
//        accountModel.setList(list);
//
//        Map<String,UserModel> map = new HashMap<>();
//        map.put("key", user);
//        accountModel.setMap(map);

        String accstr = Convert.toJson(accountModel);

        BSONObject bo = Convert.toModel(accstr);

        System.out.println("Source     : " + accstr);

        System.out.println("JSONString : " + Convert.toJson(bo));

        System.out.println("BSONObject : " + bo.toString().replace(" ",""));
    }

    @Test
    public void Test_Format() {
        System.out.println(String.format("name: %s%n","abc")+String.format("name: %s \r\n","xyz"));
    }
}
