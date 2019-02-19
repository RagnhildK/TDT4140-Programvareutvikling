import java.util.Date;

public class Main {

    public static void main(String[] args) {
        //FXMLMain.main(args);
        UserManager.main();
        System.out.println(UserManager.addStudassPåSal("2019-02-12","13:00", "TMA4100", 15));
    }

    public int add(int x, int y) {
        return x + y;
    }


}
