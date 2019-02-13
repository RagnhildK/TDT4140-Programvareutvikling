import java.sql.*;

public class Database {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no/ingebrin_pu?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    static final String USER = "ingebrin";
    static final String PASS = "pu16";

    public static boolean checkLogin(String brukernavn, String passord) {
        Connection conn = null;
        Statement stmt = null;
        String pass = "";
        try {
            //STEP 3: Open a connection
            Class.forName(JDBC_DRIVER);
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT Passord FROM Bruker WHERE BrukerNavn = '"+brukernavn+"'";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                pass = rs.getString("Passord");

            }

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        //System.out.println("Goodbye!");
        if(pass.equals(passord)){
            return true;
        }
        else{
            return false;
        }
    }
    public static void addBruker(String brukernavn, String navn, String passord) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 3: Open a connection
            Class.forName(JDBC_DRIVER);
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO Bruker VALUES ('"+brukernavn+"','"+navn+"','"+passord+"')";
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        //System.out.println("Goodbye!");
    }
    public static void addRolle(String emneid, String brukernavn, String rolle) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 3: Open a connection
            Class.forName(JDBC_DRIVER);
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO BrukerIEmne VALUES ('"+emneid+"','"+brukernavn+"','"+rolle+"')";
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        //System.out.println("Goodbye!");
    }
    public static void updateRolle(String emneid, String brukernavn, String rolle) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 3: Open a connection
            Class.forName(JDBC_DRIVER);
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE BrukerIEmne SET rolle = '"+rolle+"' WHERE BrukerNavn = '"+brukernavn+"' and EmneID ='"+emneid+"'";
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        //System.out.println("Goodbye!");

    }
    public static void addEmne(String emneid, String navn) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 3: Open a connection
            Class.forName(JDBC_DRIVER);
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO Emne VALUES ('"+emneid+"','"+navn+"')";
            stmt.executeUpdate(sql);

            //STEP 5: Extract data from result set
            /*while(rs.next()){
                //Retrieve by column name
                String pass = rs.getString("Passord");

            }*/

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        //System.out.println("Goodbye!");

    }
    public static void addSaltid(String dato, String fra, String til, String emneid, int varighet, String faglærer) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 3: Open a connection
            Class.forName(JDBC_DRIVER);
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO Saltid VALUES ('"+dato+"','"+fra+"','"+til+"','"+emneid+"','"+varighet+"','"+faglærer+"')";
            stmt.executeUpdate(sql);

            //STEP 5: Extract data from result set
            /*while(rs.next()){
                //Retrieve by column name
                String pass = rs.getString("Passord");

            }*/

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        //System.out.println("Goodbye!");

    }
    public static void addStudassPåSal(String dato, String tidspunkt, String studass, int varighet) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 3: Open a connection
            Class.forName(JDBC_DRIVER);
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO StudassPåSal VALUES ('"+dato+"','"+tidspunkt+"','"+studass+"','"+varighet+"')";
            stmt.executeUpdate(sql);

            //STEP 5: Extract data from result set
            /*while(rs.next()){
                //Retrieve by column name
                String pass = rs.getString("Passord");

            }*/

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        //System.out.println("Goodbye!");

    }
    public static void addBooking(int bookingID, String student, String dato, String tidspunkt, String studass ) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 3: Open a connection
            Class.forName(JDBC_DRIVER);
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO Booking VALUES ('"+bookingID+"','"+student+"','"+dato+"','"+tidspunkt+"','"+studass+"')";
            stmt.executeUpdate(sql);

            //STEP 5: Extract data from result set
            /*while(rs.next()){
                //Retrieve by column name
                String pass = rs.getString("Passord");

            }*/

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        //System.out.println("Goodbye!");

    }

    public static void main(String[] args) {
        System.out.println(checkLogin("admin","12345"));
        //addBruker("eric", "Eric", "12345");
        //addBruker("dustin", "Dustin", "12345");
        //addEmne("TMA4100", "Matte 1");
        //addRolle("TMA4100", "eric", "faglærer");
        //addRolle("TMA4100","dustin", "studass");
        //updateRolle("TMA4100","dustin", "student");
        //addSaltid("2019-02-13","12:00", "16:00", "TMA4100", 15, "charlie");
        //addStudassPåSal("2019-02-14","12:00", "bob", 15);
        //addBooking(2,"dustin", "2019-02-14", "12:00", "bob");
    }

}
