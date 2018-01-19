package atomic;

import adapter.AdapterFactory;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.asyou.sequoia.transaction.atomic.AtomicTransaction;
import org.asyou.sequoia.transaction.atomic.IAtomicDelegate;
import org.junit.Test;

import javax.sound.midi.Soundbank;

/**
 * Created by steven on 17/10/25.
 */
public class TestAtomic {
    @Test
    public void Test_Atomic() throws Throwable {

        //实现IAtomicDelegate接口的run()方法，把接口做为参数交给AtomicTransaction.execute()自动执行事务
        IAtomicDelegate atomicDelegate = new IAtomicDelegate() {
            @Override
            public boolean run() throws Throwable {
                int a = AdapterFactory.getAdapter().collection("cl").insert().insertOneT("{a:1}");
                int b = AdapterFactory.getAdapter().collection("cl").insert().insertOneT("{a:2}");
                //抛出异常，测试回滚
                int c = 1/0;
                return a == 1   && b == 1;
            }
        };

        //测试执行多次
        for(int i=0; i<10; i++)
            AtomicTransaction.execute(AdapterFactory.getAdapter(), atomicDelegate);
    }
}
