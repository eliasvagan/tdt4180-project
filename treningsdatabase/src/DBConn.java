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
    public String getOkterN(int n){
        StringBuilder sb = new StringBuilder();
        try {
            this.connect();
            Statement stmt = this.conn.createStatement();
            System.out.println("Creating statement...");
            String sql =
                "SELECT * " +
                "FROM treningsokt " +
                "WHERE oktnotat IS NOT NULL " +
                "ORDER BY dato DESC " +
                "LIMIT " + n + ";";

            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                sb.append(
                "Dato: " + rs.getString("dato") +
                "\t kl." + rs.getString("tidspunkt")+
                "\nVarighet: " + rs.getString("varighet") +
                "\t Form; " + rs.getString("form") + " / 10" +
                "\t Prestasjon: " + rs.getString("prestasjon") + " / 10" +
                "\nNotat til økten: \n" + rs.getString("oktnotat") + "\n"
                );
            }

            rs.close();
            stmt.close();
            this.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public String getOkt(int id) {
        StringBuilder sb = new StringBuilder();
        try {
            this.connect();
            Statement stmt = this.conn.createStatement();
            System.out.println("Creating statement...");
            String sql = "SELECT dato, tidspunkt, varighet, form, prestasjon, oktnotat " +
                "FROM treningsokt WHERE treningsokt.oktid = " + id + ";";

            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                sb.append(
                    "Dato: " + rs.getString("dato") +
                    "\t kl." + rs.getString("tidspunkt")+
                    "\nVarighet: " + rs.getString("varighet") +
                    "\t Form; " + rs.getString("form") + " / 10" +
                    "\t Prestasjon: " + rs.getString("prestasjon") + " / 10" +
                    (rs.getString("oktnotat").equals("") ? ""
                    : "\nNotat til økten: \n" + rs.getString("oktnotat"))
                );
            }

            rs.close();
            stmt.close();
            this.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public String getOvelserOkt(int id) {
        StringBuilder sb = new StringBuilder();
        try {
            this.connect();
            Statement stmt = this.conn.createStatement();
            System.out.println("Creating statement...");
            String sql =
                "SELECT navn, dato, form, aparat, apparatID, tekstBeskrivelse, antallkg, antallSett, prestasjon, tidspunkt, varighet " +
                "FROM ovelse " +
                "NATURAL JOIN treningsoktOvelse " +
                "NATURAL JOIN treningsokt " +
                "WHERE treningsokt.oktid = " + id + ";";

            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sb.append("-> ");
                if (rs.getString("aparat").equals("0")) {
                    sb.append(
                        "\t" + rs.getString("navn") +
                        "\t" + rs.getString("tekstBeskrivelse")
                    );
                } else {
                    sb.append(
                        "\t" + rs.getString("navn") + "\t" +
                        "\t" + "ApparatID = " + rs.getString("apparatID") + "\t" +
                        "\t" + rs.getString("antallkg") + " kg * " +
                               rs.getString("antallSett")
                    );
                }
                sb.append("\n");
            }
            rs.close();
            stmt.close();
            this.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public int getRowCount(String table) {
        int antall = 0;
        try {
            this.connect();
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM " + table);
            rs.next();
            antall = rs.getInt(1);
            rs.close();
            stmt.close();
            this.conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return antall;
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
