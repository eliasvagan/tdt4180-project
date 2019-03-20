import java.sql.*;
import java.util.Properties;

public abstract class DBConn {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://remotemysql.com:3306/kWRDsX4WgA";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/treningsdagbok";

    //  Database credentials
    static final String USER = "kWRDsX4WgA";
    static final String PASS = "uRSbg92smi";
    static final Boolean USING_PASS = true;

    private Connection conn;
    public DBConn () {
    }
    public void connect() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, USING_PASS ? PASS : null);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to connect, " + e.getMessage(), e);
        }
    }

    public void disconnect() {
        try {
            this.conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    public String getConnection() {
        return this.conn.toString();
    }
    public void update(String sql) {
        try {
            this.connect();
            System.out.println("Creating statement...\n" + sql);
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Successfully executed query.");
            stmt.close();
            this.conn.close();

        } catch(Exception e) {
            throw new RuntimeException("Feil ved SQL-query", e);
        }

    }

    public String select(String[] columns, String table) {
        Statement stmt = null;
        StringBuilder result = new StringBuilder();
        try{
            this.connect();

            System.out.println("Creating statement...");
            stmt = this.conn.createStatement();

            String sql ="SELECT " + String.join(", ", columns) + " FROM " + table;
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                for (String col: columns) {
                    result.append(rs.getString(col) + " ");
                }
                result.append("\n");
            }

            rs.close();
            stmt.close();
            this.conn.close();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)
                    conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }
        return result.toString();
    }
}
