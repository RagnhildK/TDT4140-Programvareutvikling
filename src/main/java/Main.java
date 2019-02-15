

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


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




    public static void main(String[] args) {
        UserManager.main();
    }


}
