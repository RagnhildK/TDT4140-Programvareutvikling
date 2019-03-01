package app;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class
Database {
    /*
     *  Klassen som kobler opp mot databasen og setter inn/oppdaterer og henter fra den.
     *  Variabler:
     *      Diverse variabler for å koble til IDI sin MySQL database.
     *  Metoder:
     *      sendUpdate(String update)
     *          -Sender en update/sett inn query til databasen
     *      sendQuery(String query)
     *          -Henter fra databasen ved å sende en select query
     *      Resten er ulike querries for å sette inn og hente fra databasen.
     *      Metode navnene og sql spørringene forklarer seg stort sett selv.
     *
     */

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //com.mysql.cj.jdbc.Driver
    static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no/ingebrin_pu?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    static final String USER = "ingebrin";
    static final String PASS = "pu16";

    public static boolean sendUpdate(String update) {
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
            stmt.executeUpdate(update);
            return true;
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
        return false;
    }
    public static String sendQueryString(String query) {
        Connection conn = null;
        Statement stmt = null;
        String info = "";

        try {
            //STEP 3: Open a connection
            Class.forName(JDBC_DRIVER);
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                 info = rs.getString(1);

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
        return info;
    }
    public static ArrayList<HashMap<String,ArrayList<String>>> sendQuery(String query) {
        Connection conn = null;
        Statement stmt = null;
        ArrayList<HashMap<String,ArrayList<String>>> dbOutput = new ArrayList<HashMap<String,ArrayList<String>>>();


        try {
            //STEP 3: Open a connection
            Class.forName(JDBC_DRIVER);
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            //STEP 5: Extract data from result set
            while(rs.next()){
                HashMap<String,ArrayList<String>> set = new HashMap<String, ArrayList<String>>();
                ArrayList<String> values = new ArrayList<String>();
                String info;
                try {
                    for (int i = 2; i < 10; i++){
                        info = rs.getString(i);
                        values.add(info);
                    }
                }
                catch (SQLException e) {
                }
                finally {
                    set.put(rs.getString(1), values);
                    dbOutput.add(set);
                }

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
        return dbOutput;
    }
    public static boolean checkLogin(String brukernavn, String passord) {
        if (brukernavn.equals("") || passord.equals("")){
            return false;
        }
        String sql = "SELECT Passord FROM Bruker WHERE BrukerNavn = '"+brukernavn+"'";
        ArrayList<HashMap<String,ArrayList<String>>> passList = sendQuery(sql);
        String pass ="";
        for (HashMap<String,ArrayList<String>> set : passList) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                pass = entry.getKey();
            }
        }
        if(pass.equals(passord)){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean addBruker(String brukernavn, String navn, String passord) {
        String sql = "INSERT INTO Bruker VALUES ('"+brukernavn+"','"+navn+"','"+passord+"')";
        return sendUpdate(sql);
    }
    public static boolean deleteBruker(String brukernavn) {
        String sql = "DELETE FROM Bruker Where BrukerNavn = '"+brukernavn+"')";
        return sendUpdate(sql);
    }
    public static boolean addRolle(String emneid, String brukernavn, String rolle) {
        String sql = "INSERT INTO BrukerIEmne VALUES ('"+emneid+"','"+brukernavn+"','"+rolle+"')";
        return sendUpdate(sql);
    }
    public static boolean updateRolle(String emneid, String brukernavn, String rolle) {
        String sql = "UPDATE BrukerIEmne SET rolle = '"+rolle+"' WHERE BrukerNavn = '"+brukernavn+"' and EmneID ='"+emneid+"'";
        return sendUpdate(sql);
    }
    public static boolean addEmne(String emneid, String navn) {
        String sql = "INSERT INTO Emne VALUES ('"+emneid+"','"+navn+"')";
        return sendUpdate(sql);
    }
    public static boolean addSaltid(String dato, String fra, String til, String emneid, int varighet, String faglærer) {
        String sql = "INSERT INTO Saltid VALUES ('"+dato+"','"+fra+"','"+til+"','"+emneid+"','"+varighet+"','"+faglærer+"')";
        return sendUpdate(sql);
    }
    public static boolean addStudassPåSal(String dato, String tidspunkt, String emneid, String studass, int varighet) {
        String sql = "INSERT INTO StudassPåSal VALUES ('"+dato+"','"+tidspunkt+"','"+emneid+"','"+studass+"','"+varighet+"')";
        return sendUpdate(sql);
    }
    public static boolean addBooking(int bookingID, String student, String dato, String tidspunkt, String studass ) {
        String sql = "INSERT INTO Booking VALUES ('"+bookingID+"','"+student+"','"+dato+"','"+tidspunkt+"','"+studass+"')";
        return sendUpdate(sql);
    }
    public static ArrayList<HashMap<String,ArrayList<String>>> getUser(String brukernavn) {
        String sql = "Select BrukerNavn, Bruker.Navn as Bruker, Rolle, Emne.EmneID, Emne.Navn as Emne" +
                " From Bruker natural join BrukerIEmne join Emne on (BrukerIEmne.EmneID = Emne.EmneID)";
        if (!brukernavn.equals("all")){
            sql += " Where BrukerNavn = '"+brukernavn+"'";
        }else{
            sql += " order by BrukerNavn asc";
        }
        return sendQuery(sql);
    }
    public static ArrayList<HashMap<String,ArrayList<String>>> getSaltid(String dato, String emneid) {
        String sql = "SELECT Dato, Fra, Til, Varighet FROM Saltid Where Dato = '"+dato+"' and EmneID = '"+emneid+"'";
        return sendQuery(sql);
    }
    public static ArrayList<HashMap<String,ArrayList<String>>> getStudassPåSal(String dato, String emneid) {
        String sql = "SELECT * FROM StudassPåSal Where Dato = '"+dato+"' and EmneID = '"+emneid+"'";
        return sendQuery(sql);
    }
    public static ArrayList<HashMap<String,ArrayList<String>>> getMineStudassPåSal(String brukernavn) {
        String sql = "SELECT Dato, Tidspunkt, EmneID, Varighet FROM StudassPåSal Where Studass = '"+brukernavn+"' order by Dato, Tidspunkt";
        return sendQuery(sql);
    }
    public static ArrayList<HashMap<String,ArrayList<String>>> getUnikStudassPåSal(String dato, String emneid, String tidspunkt, String studass) {
        String sql = "SELECT * FROM StudassPåSal Where Dato = '"+dato+"' and Tidspunkt = '"+tidspunkt+"' and EmneID = '"+studass+"' and EmneID = '"+emneid+"'";
        return sendQuery(sql);
    }
    public static ArrayList<HashMap<String,ArrayList<String>>> getBooking(String brukernavn) {
        String sql = "SELECT * FROM Booking Where Student = '"+brukernavn+"' order by StudassPåSalDato, StudassPåSalTidspunkt";
        return sendQuery(sql);
    }
    public static ArrayList<HashMap<String,ArrayList<String>>> getUnikBooking(String dato, String tidspunkt, String studass) {
        String sql = "SELECT * FROM Booking Where StudassPåSalDato = '"+dato+"' and StudassPåSalTidspunkt = '"+tidspunkt+"' and StudassPåSalStudass = '"+studass+"' ";
        return sendQuery(sql);
    }


    public static int getBookingID(){
        int id = 0;
        String sql = "SELECT BookingID FROM Booking " +
                "WHERE BookingID = (SELECT MAX(BookingID) FROM Booking);";
        ArrayList<HashMap<String,ArrayList<String>>> dbOutput = sendQuery(sql);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                String key = entry.getKey();
                id = Integer.parseInt(key);

            }
        }
        return id + 1;
    }
    public static String getRolle(String brukernavn, String emneid){
        String sql = "SELECT Rolle FROM BrukerIEmne Where BrukerNavn = '"+brukernavn+"' and EmneID = '"+emneid+"'";
        return sendQueryString(sql);
    }

    public static void main(String[] args) {
        //System.out.println(checkLogin("admin","admin"));
        /*addBruker("xerox","Xerox", "123");
        addBruker("eric", "Eric", "12345");
        addBruker("dustin", "Dustin", "12345");
        addEmne("TMA4100", "Matte 1");
        addEmne("admin", "admin");
        addRolle("admin", "admin", "admin");
        addRolle("TMA4100", "eric", "faglærer");ø
        addRolle("TMA4100","dustin", "studass");
        addRolle("TDT4100","dustin", "studass");
        updateRolle("TMA4100","dustin", "student");
        addSaltid("2019-02-13","12:00", "16:00", "TMA4100", 15, "charlie");
        addStudassPåSal("2019-02-14","12:00", "TMA4100", "bob", 15);
        addBooking(2,"dustin", "2019-02-14", "12:00", "bob");
        addStudassPåSal("2019-02-19","12:00", "TMA4100", "bob", 15);
        addStudassPåSal("2019-02-19","14:00", "TMA4100", "bob", 15);
        addStudassPåSal("2019-02-19","16:00", "TMA4100", "bob", 15);*/

        ArrayList<HashMap<String,ArrayList<String>>> dbOutput = getStudassPåSal("2019-02-21", "TDT4100");
        rsToString(dbOutput);


    }

    public static String rsToString(ArrayList<HashMap<String, ArrayList<String>>> dbOutput) {
        String str = "";
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                String key = entry.getKey();
                ArrayList<String> values = entry.getValue();
                str += "|| Key: " + key + " \t||\t Values: \t";
                for (String v : values) {
                    str += " " + v + " |";
                }
                str += "\n";
            }
        }
        System.out.println(str);
        return str;
    }

}
