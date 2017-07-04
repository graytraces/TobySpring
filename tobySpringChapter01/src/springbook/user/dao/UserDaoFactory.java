package springbook.user.dao;

public class UserDaoFactory {
	public UserDao userDao(){
		ConnectionMaker connectionMaker = new SimpleConnectionMaker();
		UserDao userDao = new UserDao(connectionMaker);
		return userDao;
	}

}
