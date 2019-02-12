import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    private String rolle = "";
    //This is the main class
    public String helloWorld() {
        return "Hello World!";
    }

    public int add(int x, int y) {
        return x + y;
    }

    public int subtract(int x, int y) {
        return x - y;
    }

    public int multiply(int x, int y) {
        return x * y;
    }

    public static String Input(String what) throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter " + what + ":");
            return br.readLine();
            /*
            System.out.print("Enter Integer:");
            try{
                int i = Integer.parseInt(br.readLine());
            }catch(NumberFormatException nfe){
                System.err.println("Invalid Format!");
            }*/
    }




    public static void main(String[] args) throws IOException{
        checkLogin();


    }

    private static void checkLogin() throws IOException {
        Main m = new Main();
        Database db = new Database();
        while (true){
            String username = Input("username");
            if (username.equals("quit")){
                break;
            }
            String password = Input("password");
            if(db.checkLogin(username,password)){
                System.out.println("Login success!");
                if(username.equals("admin")){
                    m.rolle = "admin";
                }
            }
            else {
                System.out.println("Login failed!");
            }
        }
    }
}
