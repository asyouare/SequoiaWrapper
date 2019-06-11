package adapter;

import adapter.model.JsonResult;
import com.google.gson.stream.JsonWriter;
import adapter.model.AccountModel;
import adapter.model.UserModel;
import org.asyou.sequoia.common.Convert;
import org.asyou.sequoia.type.*;
import org.bson.*;
import org.bson.types.*;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.util.*;

import static org.asyou.sequoia.model.Matchers.and;
import static org.asyou.sequoia.model.Updates.addtoset;
import static org.asyou.sequoia.model.Updates.inc;
import static org.asyou.sequoia.model.Updates.set;

/**
 * Created by steven on 17/7/14.
 */
public class TestGson {
    @Test
    public void Test_RunTime(){
        System.out.println(1);
    }

    @Test
    public void Test_UserModel(){
        UserModel user = new UserModel();

        String str = Convert.getGson().toJson(user);
        System.out.println(str);
    }

    @Test
    public void Test_Thread(){
        for(int a=0;a<1;a++) {
            long s = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 10; j++) {
                            UserModel user = new UserModel();
                            user.set_id(new ObjectId());
                            user.setName("abc000");
                            String str = Convert.toJson(user);
                            UserModel um = Convert.toModel(str, UserModel.class);
                        }
                    }
                });
                t1.start();
            }

            while (Thread.activeCount() > 2) {
            }
            System.err.println(System.currentTimeMillis() - s);
        }
    }

    @Test
    public void Test_Stream() throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out));
        writer.beginObject().name("name").value("abc").endObject();
        writer.flush();
//        writer.close();
    }

    @Test
    public void Test_SingleGson(){
        DecimalObject decimalObject = new DecimalObject(0.0003,4);

        UserModel user = new UserModel();
        user.set_id(new ObjectId());
        user.setName("abc");
        user.setDecimal(decimalObject.sub(0.0001));

        AccountModel accountModel = new AccountModel();
        accountModel.setId(123L);
        accountModel.setName("steven");
        accountModel.setAge(123);
        accountModel.setMoney(100.01);
        accountModel.setDate(new Date());

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

    public AccountModel getModel(){
        AccountModel accountModel = new AccountModel();
        accountModel.setId(123L);
        accountModel.setName("steven");
        accountModel.setAge(123);
        accountModel.setMoney(100.01);
        accountModel.setDate(new Date());

        UserModel userModel = new UserModel();
        userModel.setName("name");

        List<UserModel> userList = new ArrayList<>();
        userList.add(userModel);
        accountModel.setList(userList);

//        Map<String,String> map = new HashMap<>();
//        map.put("mKey1", "mValue1");
//        map.put("mKey2", "mValue2");
//        List<Map<String,String>> list = new ArrayList<>();
//        list.add(map);
//        accountModel.setListMap(list);
        return accountModel;
    }

    public void initBson(){
    }

    @Test
    public void Test_Model() throws Exception {
        initBson();

        BasicBSONObject bo = new BasicBSONObject();
        bo.put("id",1);

        System.out.println(Convert.toJson(getModel()));

//        System.out.println(QueryMatcher.create(bo).and().dateFromTo(ShortDateFromTo.fromField("date")).dateTimeFromTo(DateTimeFromTo.fromField("dateTime")));
    }

    @Test
    public void test_var(){
        String json = "{date:null}";
        AccountModel model = Convert.toModel(json,AccountModel.class);
        System.out.println(Convert.toJson(model));
    }

    @Test
    public void Test_Map(){
        String str = Convert.toJson(getModel());
        AccountModel accountModel = Convert.toModel(str,AccountModel.class);

        System.out.println(accountModel.getList().get(0).getName());
    }

    @Test
    public void Test_List(){
        JsonResult<List<UserModel>> jsonResult = new JsonResult<>();

        List<UserModel> userList = new ArrayList<>();
        UserModel userModel = new UserModel();
        userModel.setName("name");
        userList.add(userModel);
        jsonResult.setData(userList)
                .setHaveError(false)
                .setCode("666")
                .setErrMsg("")
        ;

        String jsonReusltStr = Convert.toJson(jsonResult);

        System.out.println(jsonReusltStr);

        JsonResult<List<UserModel>> jsonResult1 = Convert.toModel(jsonReusltStr, JsonResult.class);
        List<UserModel> data = jsonResult1.getData();

        System.out.println(data.get(0).getClass().getName());
        UserModel userModel2 = data.get(0);
        System.out.println(userModel2.getName());
        System.out.println(jsonResult1);
    }

    @Test
    public void Test_Date(){
        Instant instant = Instant.now();
        System.out.println(instant.getNano());
        System.out.println(instant);
    }

    /*

    gson.findMany(gson).as(gson.class);

    QueryMatcher.create(gson).or.and.not

    gson.findMany(QueryMatcher.create(gson)).as(gson.class);


     */
}
