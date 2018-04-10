
@Copyright by SONG

前期工作:
1.Create New Project - New Modules - Spring Init
2.配置 Database
3.修改 application.yml + Application.java + ApplicationTest.java


https://spring.io/guides/gs/accessing-data-jpa/


关于Spring Data
Spring社区的一个顶级工程，主要用于简化数据（关系型&非关系型）访问，如果我们使用Spring Data来开发程序的话，那么可以省去很多低级别的数据访问操作，如编写数据查询语句、DAO类等，我们仅需要编写一些抽象接口并定义相关操作即可，Spring会在运行期间的时候创建代理实例来实现我们接口中定义的操作。

关于Spring Data子项目
Spring Data拥有很多子项目，除了Spring Data Jpa外，还有如下子项目。
Spring Data Commons
Spring Data MongoDB
Spring Data Redis
Spring Data Solr
Spring Data Gemfire
Spring Data REST
Spring Data Neo4j

关于Spring Data Jpa
Spring Data Jpa是Spring Data的一个子项目，主要用于简化数据访问层的实现，使用Spring Data Jpa可以轻松实现增删改查、分页、排序等。



Spring Boot + Spring Data Jpa:
1、添加POM.XML文件
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>

  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>6.0.6</version>
  </dependency>

  配置 application.yml:
  spring.jpa.show-sql = true
  logging.level.org.springframework.data=DEBUG
  # spring.jpa.database = MYSQL
  # spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
  spring.jpa.hibernate.ddl-auto=

  ddl-auto：自动创建表的方式，共有5种：update、create-drop、create、none、validate，分别表示：
  update-创建表，如表结构有变则更新；
  create-drop-每次项目启动删除之前表并新建，项目停止时删除所有表
  create-每次项目启动删除之前表并新建；
  none-不使用自动创建功能；
  validate-验证表结构等，但不对数据库做修改。


  application.yml 主配置文件
  spring:
    profiles:
      active: pro #使用生成环境配置

  application-pro.ym 子配置文件


2、编写实体类User
@Entity
@NamedQuery(name = "User.findByName", query = "select name,address from User u where u.name=?1")
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name = "name")
    String name;
    @Column(name = "address")
    String address;
  ...
}
@NamedQuery注解，大致意思就是让我们在Repository接口中定义的findByName方法不使用默认的查询实现，取而代之的是使用这条自定义的查询语句去查询，如果这里没有标注的话，会使用默认实现的。

3、编写Repository接口
public interface UserJpaRepository extends JpaRepository<User,Long> {

}
UserJpaRepository接口实现了JpaRepository接口；

实际上JpaRepository实现了PagingAndSortingRepository接口，PagingAndSortingRepository接口实现了CrudRepository接口，CrudRepository接口实现了Repository接口；

简单说明下：
Repository接口是一个标识接口，里面是空的；
CrudRepository接口定义了增删改查方法；
PagingAndSortingRepository接口用于分页和排序；
由于JpaRepository接口继承了以上所有接口，所以拥有它们声明的所有方法；
另外注意下，以findAll方法为例，JpaRepository接口返回的是List, PagingAndSortingRepository和CrudRepository返回的是迭代器；

public interface UserRepository extends Repository<User, Long>
{

    List<User> findByNameAndAddress(String name, String address);

    @Query(value = "from User u where u.name=:name")
    List<User> findByName1(@Param("name") String name);

    @Query(value = "select * from #{#entityName} u where u.name=?1", nativeQuery = true)
    List<User> findByName2(String name);

    List<User> findByName(String name);
}

UserRepository接口主要定义了一些查询方法；

比如这里的findByNameAndAddress和findByName方法，我们是不需要额外定义其它查询语句就可以直接执行的，Spring Data Jpa会根据实体类的属性名字以及方法名自动实现该方法；PS:由于我们在实体类中声明了@NamedQuery注解，实际上findByName方法会使用@NamedQuery注解标注的查询语句去查询；
另外这里的findByName1方法使用了HQL语句查询；
findByName2方法使用了原始的sql语句查询；

4、编写Service
@Service
@Transactional
public class UserServiceImpl implements IUserService
{
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll()
    {
        return userJpaRepository.findAll();
    }

    public List<User> findByName(String name)
    {
        List<User> userList1 = userRepository.findByName1(name);
        List<User> userList2 = userRepository.findByName2(name);
        List<User> userList3 = userRepository.findByNameAndAddress(name, "3");
        System.out.println("userList1:" + userList1);
        System.out.println("userList2:" + userList2);
        System.out.println("userList3:" + userList3);
        return userRepository.findByName(name);
    }

    public void saveUser(User book)
    {
        userJpaRepository.save(book);
    }

    public User findOne(long id)
    {
        System.out.println("Cached Pages");
        return userJpaRepository.findOne(id);
    }

    public void delete(long id)
    {
        userJpaRepository.delete(id);
    }
}

