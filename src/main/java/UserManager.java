import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    /*
     *  Klassen som holder styr på brukeren som er innlogget.
     *  Variabler:
     *      _bruker - Innlogget brukernavn
     *      _rolle - Roller en bruker har. Liste på formen: [[EmneID,Rolle][EmneID,Rolle]..]
     *      _aktivtEmne - Aktivt emne
     *  Metoder:
     *      checkLogin(username, password)
     *          -Sjekker om brukernavn og passord stemmer overens med det som ligger i databasen
     *      booking(String dato, String tidspunkt, String studass)
     *          -Booker for gjeldende bruker
     *      addStudassPåSal(String dato, String tidspunkt, String emneid, String varighet)
     *          -Legger til tid på sal for studass og sjekker at dette ligger innenfor oppgitt saltid.
     *      checkTime(String før, String etter)
     *          -Sjekker hva som er først av tidspunkt
     *      addSaltid(String dato, String fra, String til, String emneid, String tid)
     *          -Legger til saltid     *
     */
    //TODO: Implementere valg av emne ved innlogging og endre aktivtEmne
    //      slik at man får den infoen man skal ha for hvert emne

    public static String _bruker = "";
    public static ArrayList<ArrayList<String>> _rolle = new ArrayList<ArrayList<String>>();
    public static String _aktivtEmne = "TDT4100";

    public static String Input(String what) {
        BufferedReader br = null;
        String input = "";
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter " + what + ":");
            input = br.readLine();
            return input;
        }catch (IOException e){
            System.err.println("Your input was invalid: "+e.getMessage());
        }
        return input;
    }

    public static void main() {
        getUser();
        checkLogin();
    }
    public static void getUser()  {
        String username = Input("username to search for");
        ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getUser(username);
        Database.rsToString(dbOutput);
    }
    public static void checkLogin() {
        while (true){
            String username = Input("username");
            if (username.equals("quit")){
                break;
            }
            String password = Input("password");
            if(Database.checkLogin(username,password)){
                System.out.println("|Login success!");
                ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getUser(username);
                if (dbOutput.isEmpty()){
                    System.err.println("Enda ikke lagt til en rolle til et fag.");

                }else{
                    String rolle = "||Rolle: ";
                    ArrayList<ArrayList<String>> roller = new ArrayList<ArrayList<String>>();
                    for (HashMap<String,ArrayList<String>> set : dbOutput) {
                        for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                            _bruker = entry.getKey();
                            ArrayList<String> values = entry.getValue();
                            ArrayList<String> r = new ArrayList<String>();
                            r.add(values.get(2));
                            r.add(values.get(1));
                            roller.add(r);
                        }
                    }
                    for (ArrayList<String> role : roller){
                        for (String r : role){
                            rolle += r + ": ";
                        }
                        rolle += "| ";
                    }
                    _rolle = roller;
                    System.out.println(rolle);
                    Database.rsToString(dbOutput);
                }
            }
            else {
                System.out.println("|Login failed!");
            }
        }
    }
    public static boolean checkLogin(String username, String password) {

        if(Database.checkLogin(username,password)){
            System.out.println("|Login success!");
            ArrayList<HashMap<String, ArrayList<String>>> dbOutput = Database.getUser(username);
            if (dbOutput.isEmpty()){
                System.err.println("Enda ikke lagt til en rolle til et fag.");

            }else{
                String rolle = "||Rolle: ";
                ArrayList<ArrayList<String>> roller = new ArrayList<ArrayList<String>>();
                for (HashMap<String,ArrayList<String>> set : dbOutput) {
                    for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                        _bruker = entry.getKey();
                        ArrayList<String> values = entry.getValue();
                        ArrayList<String> r = new ArrayList<String>();
                        r.add(values.get(2));
                        r.add(values.get(1));
                        roller.add(r);
                    }
                }
                for (ArrayList<String> role : roller){
                    for (String r : role){
                        rolle += r + ": ";
                    }
                    rolle += "| ";
                }
                _rolle = roller;

                System.out.println(rolle);
                Database.rsToString(dbOutput);
                return true;
            }
        }
        else {
            System.out.println("|Login failed!");
            return false;
        }
        return false;
    }


    public static boolean booking(String dato, String tidspunkt, String studass){
        ArrayList<HashMap<String,ArrayList<String>>> booking = Database.getUnikBooking(dato, tidspunkt, studass);
        if (!booking.isEmpty()){
            return false;
        }
        return Database.addBooking(Database.getBookingID(), _bruker, dato, tidspunkt, studass);
    }
    public static boolean addStudassPåSal(String dato, String tidspunkt, String emneid, String varighet) {
        ArrayList<HashMap<String,ArrayList<String>>> dbOutput = Database.getSaltid(dato, emneid);
        for (HashMap<String,ArrayList<String>> set : dbOutput) {
            for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                ArrayList<String> val = entry.getValue();
                if (checkTime(val.get(0), tidspunkt) && checkTime(tidspunkt, val.get(1))){
                    //System.out.println("Success");
                    return Database.addStudassPåSal(dato, tidspunkt, emneid, _bruker, Integer.parseInt(varighet));
                }
            }
        }

        return false;
    }

    private static boolean checkTime(String før, String etter) {
        //System.out.println("Før; " + før + " Etter: " + etter);
        if (Integer.parseInt(før.substring(0,2)) < Integer.parseInt(etter.substring(0,2))) {
            return true;
        }
        else if (Integer.parseInt(før.substring(0,2)) == Integer.parseInt(etter.substring(0,2))
            && (Integer.parseInt(før.substring(3)) <= Integer.parseInt(etter.substring(3)))){
            return true;
        }

        return false;
    }

    public static boolean addSaltid(String dato, String fra, String til, String emneid, String tid) {
        return (Database.addSaltid(dato, fra, til, emneid, Integer.parseInt(tid) , _bruker));
    }
}
