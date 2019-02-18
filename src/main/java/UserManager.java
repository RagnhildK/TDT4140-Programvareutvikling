import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    public static String _bruker = "";
    public static ArrayList<ArrayList<String>> _rolle = new ArrayList<ArrayList<String>>();


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

    public static void booking(String dato, String tidspunkt, String studass){
       Database.addBooking(10, _bruker, dato, tidspunkt, studass);
    }
}
