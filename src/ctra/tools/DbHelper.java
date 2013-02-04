package ctra.tools;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class DbHelper
{
	
	static	 Logger			log		= Logger.getLogger(DbHelper.class);
	private static Connection	conn	= null;
	
	
	
	public static Connection getConnection() throws ClassNotFoundException,
			SQLException
	{
		if (conn != null && conn.isValid(10))
		{
			//log.debug("DbHelper : conn.isValid....");
			return conn;
		} else
		{
			log.debug("DbHelper : create new connection....");
			ResourceBundle rsb = ResourceBundle.getBundle("dbConfig");
			String dbIp = rsb.getString("DATABASE_IP");
			String dbName = rsb.getString("DATABASE_NAME");
			String dbUser = rsb.getString("DATABASE_USER");
			String dbPassword = rsb.getString("DATABASE_PASSWORD");
			Class.forName(rsb.getString("DATABASE_DRIVER"));
			String url = "jdbc:mysql://" + dbIp + "/" + dbName;
			conn = DriverManager.getConnection(url, dbUser, dbPassword);
			
			return conn;
		}
		
	}
	
	public void finalize()
	{
		try
		{
			if (conn != null)
			{
				log.debug("finalize() : db connect closed()...");
				conn.close();
				conn = null;
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			log.debug(e.toString());
		}
	}
}
