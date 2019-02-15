import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    public static String _rolle = "";

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
                    ArrayList<String> roller = new ArrayList<String>();
                    for (HashMap<String,ArrayList<String>> set : dbOutput) {
                        for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                            ArrayList<String> values = entry.getValue();
                            roller.add(values.get(1));
                        }
                    }
                    for (String r : roller){
                        rolle += r + ", ";
                    }
                    if (roller.contains("admin")){
                        _rolle = "admin";
                    }else if (roller.contains("faglærer")){
                        _rolle = "faglærer";
                    }else if (roller.contains("studass")){
                        _rolle = "studass";
                    }else if (roller.contains("student")){
                        _rolle = "student";
                    }
                    System.out.println(rolle);
                    Database.rsToString(dbOutput);
                }
            }
            else {
                System.out.println("|Login failed!");
            }
        }
    }
}
