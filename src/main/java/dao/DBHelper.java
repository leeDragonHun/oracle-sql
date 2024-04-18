package dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBHelper {
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn	= null;
		String dbUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
		String dbUser = "admin";
		// 보안이슈로 로컬에서 설정파일 로드
		FileReader fr = new FileReader("C:\\dev\\auth\\oracle.properties");
		Properties prop = new Properties();
		prop.load(fr);
		String dbPw = prop.getProperty("dbPw");
		conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
		return conn;
	}
	
	//
	public static void main(String[] args) throws Exception {
		Connection conn = new DBHelper().getConnection();
		System.out.println(conn);
	}
}
