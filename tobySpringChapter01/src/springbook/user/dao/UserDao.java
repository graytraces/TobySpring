package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import springbook.user.domain.User;

public class UserDao {
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt)throws SQLException{

		Connection c = null;
		PreparedStatement ps = null;
		try{
			c = dataSource.getConnection();
			
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
				
		}catch(SQLException e){
			throw e;
		}finally{
			if(ps != null){
				try{
					ps.close();
				}catch(SQLException e){
					;
				}
			}

			if(c != null){
				try{
					c.close();
				}catch(SQLException e){
					;
				}
			}
		}
	}
	
	public void deleteAll() throws SQLException{
		StatementStrategy strategy = new DeleteAllStatement();
		jdbcContextWithStatementStrategy(strategy);		
	}
	
	
	public int getCount() throws SQLException{
		
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = -1;
		try{
			c = dataSource.getConnection();
			ps = c.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		}catch(SQLException e){
			throw e;
		}finally{
			if(rs != null){
				try{
					rs.close();
				}catch(SQLException e){
					;
				}
			}
			if(ps != null){
				try{
					ps.close();
				}catch(SQLException e){
					;
				}
			}

			if(c != null){
				try{
					c.close();
				}catch(SQLException e){
					;
				}
			}
		}
		
		return count;
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException{
		StatementStrategy strategy = new AddStatement(user);
		jdbcContextWithStatementStrategy(strategy);
		
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException{
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"select * from users where id = ?" );
		ps.setString(1,  id);
		
		
		User user = null;
		ResultSet rs = ps.executeQuery();
		if( rs.next() ){
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		
		rs.close();
		ps.close();
		c.close();
		
		if(user == null)
			throw new EmptyResultDataAccessException(1);
		
		return user;
	}
}
