import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

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

    public String Input(String what) throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter " + what + ":");
            String s = br.readLine();
            return s;
            /*
            System.out.print("Enter Integer:");
            try{
                int i = Integer.parseInt(br.readLine());
            }catch(NumberFormatException nfe){
                System.err.println("Invalid Format!");
            }*/
    }


    public static void main(String[] args) throws IOException{
        Main m = new Main();
        Database db = new Database();
        while (true){
            String username = m.Input("username");
            if (username.equals("quit")){
                break;
            }
            String password = m.Input("password");
            System.out.print(username+":"+password);
            if(db.checkLogin(username,password)){
                System.out.print("Login success!");
            }
            else {
                System.out.print("Login failed!");
            }
        }


    }
}
