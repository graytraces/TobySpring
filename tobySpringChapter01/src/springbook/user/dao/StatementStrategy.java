package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementStrategy {
	public PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
