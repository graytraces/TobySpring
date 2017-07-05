package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {
	@Bean
	public UserDao userDao(){
		UserDao userDao = new UserDao(connectionMaker());
		return userDao;
	}

	@Bean
	private ConnectionMaker connectionMaker() {
		ConnectionMaker connectionMaker = new SimpleConnectionMaker();
		return connectionMaker;
	}

}
