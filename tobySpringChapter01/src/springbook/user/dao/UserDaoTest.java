package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
@DirtiesContext
public class UserDaoTest {
	private UserDao dao;
	private User user1;
	private User user2;
	private User user3;
	
	ApplicationContext context;
	
	@Autowired
	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public static void main(String[] args){
		JUnitCore.main("springbook.user.dao.UserDaoTest");
	}

	@Before
	public void setUp() {
		
		this.dao = context.getBean("userDao", UserDao.class);
		
		DataSource dataSource = new SingleConnectionDataSource(
				"jdbc:mysql://cardi.iptime.org:6306/springtest", "springtest", "springtest1234", true);
		
		dao.setDataSource(dataSource);
		
		this.user1 = new User("gyumee", "박성철", "springno1");
		this.user2 = new User("leegw700", "이길원", "springno2");
		this.user3 = new User("bumjin", "박범진", "springno3");
	}
	
		
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException{
		
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		
		
		User userget2 = dao.get(user2.getId());
		
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
		
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException, ClassNotFoundException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
		
	}
	@Test
	public void count() throws SQLException, ClassNotFoundException{
		
		
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
		user.setName("백기선");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId() + "등록성공");
		
		User user2 = dao.get(user.getId());
		
		if(!user.getName().equals(user2.getName())){
			System.out.println("테스트 실패 (name)");
		}
		else if(!user.getPassword().equals(user2.getPassword())){
			System.out.println("테스트 실패 (password)");
		}else {
			System.out.println("조회 테스트 성공");
		}
		
	}
	*/

}
