package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.domain.User;

public class UserDaoTest {
	
	public static void main(String[] args){
		JUnitCore.main("springbook.user.dao.UserDaoTest");
	}
		
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException{
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		User user1 = new User("gyumee", "�ڼ�ö", "springno1");
		User user2 = new User("leegw700", "�̱��", "springno2");

		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		
		
		User userget2 = dao.get(user2.getId());
		
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
		
	}
	
	@Test
	public void count() throws SQLException, ClassNotFoundException{
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user1 = new User("gyumee", "�ڼ�ö", "springno1");
		User user2 = new User("leegw700", "�̱��", "springno2");
		User user3 = new User("bumjin", "�ڹ���", "springno3");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	/*
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user = new User();
		user.setId("whiteship");
		user.setName("��⼱");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId() + "��ϼ���");
		
		User user2 = dao.get(user.getId());
		
		if(!user.getName().equals(user2.getName())){
			System.out.println("�׽�Ʈ ���� (name)");
		}
		else if(!user.getPassword().equals(user2.getPassword())){
			System.out.println("�׽�Ʈ ���� (password)");
		}else {
			System.out.println("��ȸ �׽�Ʈ ����");
		}
		
	}
	*/

}
