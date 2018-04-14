package com.leopard.test;

import com.leopardframework.core.annotation.Column;
import com.leopardframework.core.annotation.Table;
import com.leopardframework.core.enums.Primary;
import com.leopardframework.core.session.Session;
import com.leopardframework.core.session.sessionFactory.SessionFactory;
import com.leopardframework.generator.GeneratorFactory;
import com.leopardframework.loadxml.XmlFactoryBuilder;
import com.leopardframework.page.PageInfo;
import com.leopardframework.plugins.DBPlugin;
import com.leopardframework.test.entity.Student;
import com.leopardframework.test.entity.User;
import com.leopardframework.util.ClassUtil;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2018, Chen_9g 陈刚 (80588183@qq.com).
 * <p>
 * DateTime 2018/4/7
 * <p>
 * A novice on the road, please give me a suggestion.
 * 众里寻他千百度，蓦然回首，那人却在，灯火阑珊处。
 * Find a way for success and not make excuses for failure.
 */
public class UserTest {

    @Test
    public void TTTT(){
        try {
           Class<?> cls= Class.forName("com.leopard.entity.User");
            Table table=cls.getAnnotation(Table.class);
            String tableName=table.value();
            if(tableName==null||"".equals(tableName)){
                tableName=cls.getSimpleName();
            }
            System.out.println(tableName);
            String columnName=null;
            Primary pk=null;
            boolean un;
            Field[] fields =cls.getDeclaredFields();
            for(Field field:fields){
                System.out.println(field.getName()+" 变量类型： "+field.getType());
                System.out.println(field.getType() == long.class);
                Column column=field.getDeclaredAnnotation(Column.class);
                columnName=column.value().toUpperCase();
                pk=column.isPrimary();
                if(columnName==null||"".equals(columnName)){
                    columnName=field.getName().toUpperCase();
                }if(pk==Primary.NO){
                      //  continue;
                }
              //  System.out.println(field.getType()==Long.class);
               // System.out.println(columnName+"   "+ pk + " "+un);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* @Test
   public void sqlTest(){
        User user=new User();
        user.setId(111);
        user.setName("AAAAA");
        user.setPhone("15770549440");
       // SqlBuilder SS=new SqlBuilder();
       // Map<String,List<Object>> sqlv= SqlBuilder.getSaveSqlValues(user);
        List<Object> values=null;
        for (Map.Entry<String,List<Object>> sql : sqlv.entrySet()) {
            System.out.println(sql.getKey());
            values = sql.getValue();
        }
        for(int i=0;i<values.size();++i){
            System.out.println(values.get(i)+"  "+ (i+1));
        }
     //   String sql=sqlv.
       // System.out.println(sql);
    }*/

    /*@Test
    public void SaveUserTest(){
        Session session=SessionFactory.openSession();
        User u=new User();
        u.setId(6);
        u.setName("leopard");
        u.setPhone("15770549440");
        u.setAddress("China");
        try {
            int i=session.Save(u);
            System.out.println(" Success "+i);
            session.closeSession();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void SaveUserTest2(){
        Session session=SessionFactory.openSession();
        List<User> users=new ArrayList<>();
        for(int id=10;id<20;id++){
            users.add(new User(id,"leopard","15770549440"));
        }
        int temp;
        try {
            temp=session.SaveMore(users);
            System.out.println(temp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getPK(){
        Session session=SessionFactory.openSession();
        try {
           int temp=session.Delete(User.class,1,2,3,4,5,6,10085);
           System.out.println(" 结果："+temp);
            session.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(FieldUtil.getPrimaryKeys(User.class).get(0));
    }

    @Test
    public void Delobj(){
        Session session=SessionFactory.openSession();
        User user=new User();
        user.setId(9);
       user.setPhone("15770549440");
       user.setName("leopard");
        try {
            int temp=session.Delete(user);
            System.out.println(" 结果："+temp);
            session.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void InsertSqlTest(){
        User user=new User();
     //   user.setId(9);
        user.setPhone("15770549440");
        user.setName("leopard");
      //  user.setAddress("China");
       // System.out.println("Sql value："+FieldUtil.getAllColumnName_Value(user));
        Sql insert=new CreateTableSql(User.class);

        System.out.println("Sql 语句："+insert.getSql());
        System.out.println("Sql value："+insert.getValues());
       *//* for(int i=0;i<insert.getValues().size();++i){

            System.out.print(insert.getValues().get(i)+" ");
        }*//*


    }

    @Test
    public void tableUTest(){

        System.out.println(TableUtil.getAllTableName("com.leopardframework").size());
        URL xmlpath = this.getClass().getClassLoader().getResource("");
        System.out.println(xmlpath);
    }

    @Test
    public void newTest(){
        Session session=SessionFactory.openSession();
        User user=new User();
      //  user.setId(100);
        user.setPhone("15770549440");
       // user.setName("newleopard");
      //  user.setAddress("GXF");
        try {
            int temp=session.Save(user);
           *//* for (User u :temp){
                System.out.println(u.toString());
            }*//*
            System.out.println(" 结果："+temp);
            session.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void pageTest(){
        Session session=SessionFactory.openSession();
        try {
            PageInfo temp=session.Get(User.class,3,8);
            List <User>users=temp.getList();
            for (User u :users){
                System.out.println(u.toString());
            }
            temp.description();
           //  System.out.println(" 结果："+temp);
            session.closeSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    @Test
    public void createTableTest(){

        try {
            Session session=SessionFactory.openSession("classpath:config.xml");
            User u=new User();
            u.setId(10086);
            System.out.println("第二次"+session.Get(u));
            session.Stop();
            System.out.println(session.Get(User.class));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void TableTest(){
       Set set=ClassUtil.getClassSetByPackagename(SessionFactory.Config.getEntityPackage());
       System.out.println(set);
       System.out.println(new Date(12));
    }
    @Test
    public void xmlTest(){
        XmlFactoryBuilder builder=new XmlFactoryBuilder(ClassLoader.getSystemResource("config.xml").getPath());
        XmlFactoryBuilder.XmlFactory factory=builder.getFactory();
        Student db=(Student) factory.getBean("student");
       System.out.println(db.toString());
       System.out.println(factory.getEntityPackage());
       System.out.println(" 路径："+System.getProperty("user.dir"));
    }

    @Test
    public void Test(){
        Session session=SessionFactory.openSession("classpath:config.xml");  //获取session  传入我们的配置文件
            User user=new User();
            user.setId(10086);
            user.setName("Leopard");
            user.setPhone("10010");
            user.setAddress("China");
            List list=new ArrayList();
            list.add(user);
        try {
            session.Save(user);  //传一个具体的对象
            session.SaveMore(list);  //多个对象放入list 好比批量操作，实际上并没有用到批量
            session.Delete(user); //删除条件即为对象的数据
            session.Delete(User.class, 10086, 10010, 10000); //根据唯一主键删除数据 ,传一个或多个主键值
            session.Update(user,10086);//根据主键修改数据  目标数据是该对象里的数据
            session.Get(User.class); // 查询所有数据
            session.Get(user);   //查询单条数据 查询条件即为对象的数据  如果匹配到多条数据，则只返回第一条
            session.Get(User.class,10000,10086);// 一样按主键查找
            session.Get(User.class,"where id=? order by id desc",10086);  //自定义条件查询 动态sql
            session.Get(User.class,1,5);  //分页查询  查询第一页数据  每页显示5 条数据 PageInfo来接收（下次再介绍）
            session.Get("","");  //自定义动态sql 返回的是结果集
            session.Stop();  //每执行完一次都要将其暂停
            session.Close();  // 关闭此次Session 下次要用时要重新获取

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void GenTest(){
        Session session=SessionFactory.openSession("classpath:config.xml");
        System.out.println(SessionFactory.Config.getConnection());
        System.out.println(SessionFactory.Config.getEntityPackage());
        System.out.println(SessionFactory.Config.getGeneratorPackage());
        System.out.println(DBPlugin.class.getName());
       /* Jqubian jj=new Jqubian();
        jj.setGxfId(111);
        jj.setGxfLong(5454);
        jj.setGxfName("GGG");
        jj.setGxfPrice(2.5);
        jj.setGxfPrice2(2.5f);
        jj.getGxfStatus(TRUE.equals(true));
        jj.setGxfCreate(new Date(2018,02,05));
        System.out.println(jj);*/
    }

}