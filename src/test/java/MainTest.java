/*import org.junit.jupiter.api.Test;
 */


import static org.junit.Assert.*;

import app.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainTest {

    private String brukernavn = "truls";
    private String passord = "123";
    @org.junit.Test
    public void main() {
        slettBruker(); //I tilfelle brukeren eksisterer
        //Tester
        opprettBruker();
        sjekkLogin();
        slettBruker();
    }
    public void opprettBruker() {
        try {
            Database.addBruker(brukernavn, "Truls", passord);
            ArrayList<HashMap<String, ArrayList<String>>> user = Database.getBruker("Truls");
            for (HashMap<String,ArrayList<String>> set : user) {
                for (Map.Entry<String, ArrayList<String>> entry : set.entrySet()) {
                    String key = entry.getKey();
                    assertEquals(brukernavn, key);
                }
            }
        }catch(Exception e){
            fail();
        }
    }
    public void sjekkLogin() {
        try {
            assertTrue(UserManager.checkLogin(brukernavn, passord));

        }catch(Exception e){
            fail();
        }
    }
    public void slettBruker() {
        try {
            assertTrue(Database.deleteBruker(brukernavn));
        }catch(Exception e){
            fail();
        }
    }

}