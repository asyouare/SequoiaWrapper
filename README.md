>### **结构说明**
---
- #### **服务器对象**
```text
SequoiaAdapter  ：数据库适配器，一个适配器操作一个数据库服务器对象。
SequoiaHost	    ：数据库服务器，包含在SequoiaAdapter中，单例模式。
Config		    ：数据库配置选项，包含在SequoiaHost中。

```
通常SequoiaHost对象不需要显式的创建，Config管理器会维护一个全局数据库配置列表，根据列表自动创建SequoiaHost对象，使用时只需要创建SequoiaAdapter对象即可，AdapterId与Config中的ID要一致。

SequoiaAdapter对象是线程安全的，推荐使用单例模式。
- #### **操作器对象**
```text
Find		：查找操作器，SequoiaAdapter中直接获取。
Page		：分页查找操作器，SequoiaAdapter中直接获取。
Insert		：插入操作器，SequoiaAdapter中直接获取。
Update		：更新操作器，SequoiaAdapter中直接获取。
Delete		：删除操作器，SequoiaAdapter中直接获取。
Count   	：统计数量操作器，SequoiaAdapter中直接获取。
Aggregate	：聚合函数操作器，SequoiaAdapter中直接获取 
```
- #### **查询器对象**
```text
IQuery		    ：查询器接口。
QueryMatcher	：条件匹配器。
QueryAggregate	：聚合函数生成器
QueryObject	    ：IQuery生成器
```
- #### **使用Maven安装**
```xml
<!--http://maven.asyou.cc:8081/repository/maven-public/-->  
<dependency>  
    <groupId>org.asyou</groupId>  
    <artifactId>asyou-sequoiawrapper</artifactId>  
    <version>1.1</version>  
</dependency>  
```

- #### **如何配置**

你可以使用最简单的方式进行配置：
```java
Config dataConfig = new Config("adapter_data","database_name",addressList);
```
也可以使用更详细的配置参数：
```java
ConfigOptions configOptions = ....
DatasourceOptions datasourceOptions = ....
Config logConfig = new Config("adapter_log","database_name","user_name","password",addressList,configOptions,datasourceOptions);
```
将配置装入Sequoia管理器，多个数据库服务器可以配置多个Config：  
```java
SequoiaHostManager.addHost(
    dataConfig,
    logConfig
);
```

装入后，会自动生成SequoiaHost对象，这个SequoiaHost是单例模式，也是SequoiaAdapter对象创建的必须条件。Config中的adapterId必须是唯一的，构造SequoiaAdapter时给出的ID与Config中的adapterId是一致的。

- #### **如何连接数据库**
数据库的连接操作已经集成在操作器内部，你可以忽略数据库连接的任何操作，而只关心操作本身：
```java
SequoiaAdapter adapter = SequoiaAdapter.create("adapter_data")

adapter.find()
adapter.insert()
adapter.update()
adapter.delete()
adapter.count()
adapter.aggregate()
```
为了满足某些特殊的需求，我们也提供了原生接口的获取：
```java
Sequoiadb sdb = SequoiaAdapter.getHost().getNewConnection();
```
- #### **如何使用查询器**

我们提供了两个快速查询器，QueryMatcher用来做条件匹配，QueryAggregate用来生成聚合函数。
```java
//查询器的构造支持3种数据类型：Json字符串、Bson、Pojo类。
//这里我们使用了Json字符串，如下：

QueryMatcher queryMatcher = QueryMatcher.create("{a:1,b:2,c:'string'}").or().not();
System.out.println(queryMatcher.toBSONObject().toString());

->{$not:[{$or:[{a:1},{b:2},{c:"string"}]}]}
```
下面一次性获得统计amount字段的求和结果，和price字段的平均值结果。
```java
QueryAggregate queryAggregate = QueryAggregate.create();
queryAggregate.match(queryMatcher).group().sum("amount").avg("price");

Map<String,Number> values = sequoiaAdapter.aggregate().match(queryAggregate).values();
```
values中的key默认是使用的函数名称，上面使用了sum和avg，也可以自定义返回名称。

- #### **如何自定义查询条件**
对于极其复杂的查询条件，快速查询器无法满足，可以使用QueryObject用来装入你的自定义查询条件。
```java
QueryObject query = QueryObject.create(or(and(eq("a",1),eq("b",2),eq("c","string"))));
```
聚合操作也同样支持自定义条件：
```java
BSONObject match = new BasicBSONObject("$match", new BasicBSONObject());
BSONObject group = new BasicBSONObject("$group", new BasicBSONObject("amount", new BasicBSONObject("sum", "price")));

List<BSONObject> queryList = new ArrayList<>();
queryList.add(match);
queryList.add(group);
queryAggregate.aggregate(queryList);
```
将自定义条件match和group传入，aggregate会返回List<BSONObject>执行结果。

- #### **自动事务处理**
- ProxyFactory

ProxyFactory提供业务层自动事务处理，使用ProxyFactory创建Service对象即可，无须关心配置和实现，只关注业务逻辑即可。ProxyFactory使用了cglib的方法拦截器，实现事务的拦截式处理。

把一个业务类用ProxyFactory进行实例化，并绑定对应的SequoiaAdapter。业务对象对SequoiaAdapter的所有操作都将自动执行事务。

这里显式使用静态声明，避免其它MVC框架冲突。如jfinal会为每个请求重新实例化Controller层，导致性能上的开销和浪费。
```java
static ServiceA server = ProxyFactory.create(ServiceA.class, SequoiaAdapter.create("adapter_data"));
```

- AtomicTransaction

在某些情况下需要对事务有更多灵活可控的需求，AtomicTransaction更接近原生方法，做为替代方案提供一种简单实现，方法如下：
```java
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
```
事务规则说明：
1. ProxyFactory和AtomicTransaction的事务都是基于线程识别的，在同一线程中共享同一事务。
2. ProxyFactory和AtomicTransaction在同一个线程内不能嵌套执行，否则会导致线程安全问题，使事务失效或抛出异常。
3. ProxyFactory创建的对象应该保证线程安全问题，不要在业务类中存放非线程安全的变量或代码。
4. ProxyFactory创建的对象中包含了另一个ProxyFactory创建的对象，也应该声明为静态static，无论包含多少ProxyFactory的对象，它们都在同一个事务中。


> 正在进行完善，如有其它问题请多多指正！
