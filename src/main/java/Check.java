import javafx.scene.control.Label;

public class Check {
    public Check() {
    }

    public static boolean checkTidspunkt(String tid) {
        boolean bool = false;
        try {
            Integer.parseInt(tid.substring(0, 2));
            Integer.parseInt(tid.substring(3));
            if (tid.substring(2, 3).equals(":") && tid.length() == 5) {
                bool = true;
            }
        } catch (Exception e) {
            bool = false;
        } finally {
            return bool;
        }
    }
    public static boolean checkDato(String dato) {
        boolean bool = false;
        try {
            Integer.parseInt(dato.substring(0, 4));
            Integer.parseInt(dato.substring(5, 7));
            Integer.parseInt(dato.substring(8));
            if (dato.substring(4, 5).equals("-") && dato.substring(7, 8).equals("-") && dato.length() == 10) {
                bool = true;
            }
        } catch (Exception e) {
            bool = false;
        } finally {
            return bool;
        }
    }
    public static boolean future(String nu, String b) {
        boolean ok = false;
        if (Integer.parseInt(nu.substring(0,4)) < Integer.parseInt(b.substring(0,4))){
            ok = true;
        }else if (Integer.parseInt(nu.substring(0,4)) == Integer.parseInt(b.substring(0,4))){
            if (Integer.parseInt(nu.substring(5,7)) < Integer.parseInt(b.substring(5,7))){
                ok = true;
            }else if (Integer.parseInt(nu.substring(5,7)) == Integer.parseInt(b.substring(5,7))){
                if (Integer.parseInt(nu.substring(8)) <= Integer.parseInt(b.substring(8))){
                    ok = true;
                }
            }
        }
        return ok;
    }

}