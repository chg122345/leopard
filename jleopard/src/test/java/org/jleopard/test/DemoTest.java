package org.jleopard.test;

import java.util.ArrayList;
import java.util.List;

import org.jleopard.core.sql.InsertSql;
import org.jleopard.core.sql.JoinSql;
import org.jleopard.exception.SqlSessionException;
import org.jleopard.pageHelper.PageInfo;
import org.jleopard.session.SqlSession;
import org.jleopard.session.sessionFactory.SqlSessionFactory;

/**
 * @Copyright (c) 2018, Chen_9g 陈刚 (80588183@qq.com).
 * @DateTime Sep 2, 2018 5:44:08 PM
 * 
 * Find a way for success and not make excuses for failure.
 *
 */
public class DemoTest {

	public static void main(String[] args) {
		 User user=new User();
	        user.setId("1111123");
	        user.setName("jleopardDEMO");
	        user.setPassword("123456789");
	        Reply r = new Reply();
	        r.setId(3L);
	        r.setContent("content ..");
	        r.setUser_id(user);
	        List<Reply> list = new ArrayList<>();
	        list.add(r);
	        Article a = new Article();
	        a.setId("10000");
	        a.setTitle("这是标题");
	        a.setStatus(Byte.valueOf("1"));
	        a.setUser_id(user);
	        a.setReplys(list);
			r.setArticle_id(a);
	        InsertSql insert = new InsertSql(a);
	        System.out.println("Sql 语句："+insert.getSql());
//	        JoinSql jsql = new JoinSql(Article.class, Reply.class,User.class,Post.class);
//	        System.out.println(jsql.getSql());
//	        System.out.println("Sql value："+insert.getPkValue());
		
	//	System.out.println((User.class == Object.class));
	        SqlSessionFactory factory= SqlSessionFactory.Builder.build("classpath:config.xml");
	        SqlSession session=factory.openSession();
	        try {
	        //	System.out.println(session.getToPage(User.class,1,2,"where name = ?","leopard").getList());
	        	//System.out.println(session.getByJoin(Article.class, new Class<?>[]{User.class},"article.id=?",101));
				//session.save(a);
	        	//session.commit();
	            PageInfo pg=session.getJoinToPage(Article.class,new Class[]{User.class},1,9,"","");
	            List<Article> lists= (List<Article>) pg.getList();
	            for (Article ar :lists){
	                System.out.println("结果："+ar);
	            }
	            System.out.println();
	        } catch (SqlSessionException e) {
	            e.printStackTrace();
	        }
	}

	/*public static void main(String[] args) {
		Article a = new Article();
		a.setId("10000");
		a.setTitle("这是标题");
		//a.setStatus(Byte.valueOf("1"));
		InsertSql insert = new InsertSql(a);
		System.out.println("Sql 语句："+insert.getSql());
		System.out.println(insert.getValues());
		long b = 0;
		System.out.println(b);
		Object ob = b;
		System.out.println(ob.getClass());
	}*/
}
