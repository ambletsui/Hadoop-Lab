import java.sql.*;

public class HiveJDBC1 {

		//Assume using HiveServer2, hive-0.12.0
		
		public static final String DB_DRIVER_NAME_HIVE2 = "org.apache.hive.jdbc.HiveDriver";
		public static final String DB_DRIVER_NAME_HIVE ="org.apache.hadoop.hive.jdbc.HiveDriver";
		public static final String DB_CONN_URL_STANDALONE = "jdbc:hive2://localhost:10000/default";
		public static final String DB_CONN_URL_EMBEDDED = "jdbc:hive://";
		public static final String  DB_USER = "APP";
		public static final String DB_PWD= "mine";
		
		public static void main(String[] args) throws SQLException{
			
			try {
				Class.forName(DB_DRIVER_NAME_HIVE2);
				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
				System.exit(1);
			}
			
			Connection conn = DriverManager.getConnection(DB_CONN_URL_STANDALONE, DB_USER, DB_PWD);
			Statement st = conn.createStatement();
			
			String tableName = "ambletest1";
			//Create a new table in Hive
			st.execute("DROP TABLE IF EXISTS " + tableName);
			st.execute("CREATE TABLE " + tableName + "(id int, name string)" );
			System.out.println("Table Created.");
					//+ "ROW FORMAT DELIMITED " + "FIELDS TERMINATED BY '\t' " + "STORED AS TEXTFILE");
			
			//Display the table
			String sql = "SHOW TABLES '" + tableName + "'";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				System.out.println(rs.getString(1));
			}
			
			//Use select statement
			sql = "SELECT * FROM " + tableName;
			rs = st.executeQuery(sql);
			while (rs.next()) {
				System.out.println(String.valueOf(rs.getInt(1)) + "\t" + rs.getString(2));
			}
			
			rs.close();
			st.close();
			conn.close();
		}
}
