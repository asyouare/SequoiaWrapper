package adapter;

import org.asyou.sequoia.common.Convert;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.asyou.sequoia.query.QueryAggregate;
import org.bson.BSONObject;
import org.junit.Test;

public class TestAggregate {
    @Test
    public void Test_Sum() throws SequoiaAdapterException {
        QueryAggregate aggregate = QueryAggregate.create();
        aggregate.group().sum("loanAmountD").avg("loanAmountD").min("loanAmountD").max("loanAmountD").count("_id");

        System.out.println(AdapterFactory.getAdapter().collection("loan_loan").aggregate().matcher(aggregate).values());

    }

    @Test
    public void Test_Query(){
        QueryAggregate aggregate = QueryAggregate.create();
        aggregate.group().sum("loanAmountD").avg("loanAmountD");

        for(BSONObject bsonObject : aggregate.toBsonList())
            System.out.println(bsonObject.toString());
    }
}
