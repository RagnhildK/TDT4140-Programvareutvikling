/*import org.junit.jupiter.api.Test;
 */


import static org.junit.Assert.*;

import app.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MainTest {
    /*
     * Hoved mainklasse. Tester med bruk av JUnit.
     */

    private String brukernavn = "truls";
    private String passord = "123";
    private String fag = "TDT4100";

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dato = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tidspunkt = new SimpleDateFormat("HH-mm");

    @org.junit.Test
    public void main() {
        slettBruker(); //I tilfelle brukeren eksisterer
        //Tester basic bruker relaterte funksjoner
        opprettBruker();
        sjekkLogin();
        UserManager._aktivtEmne=fag;
        //sjekkBooking();
        sjekkRolle();
        sjekkMelding();

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
    public void sjekkBooking() {
        assertTrue(UserManager.addSaltid(dato.format(calendar.getTime()),tidspunkt.format(calendar.getTime()),tidspunkt.format(calendar.getTime()),"15"));
        assertTrue(UserManager.addStudassPåSal(dato.format(calendar.getTime()), tidspunkt.format(calendar.getTime()), "15"));
        assertTrue(UserManager.booking(dato.format(calendar.getTime()),tidspunkt.format(calendar.getTime()),brukernavn));
    }
    public void sjekkRolle(){
        assertTrue(Database.updateRolle(fag,brukernavn,"student"));
    }
    public void sjekkMelding(){
        assertTrue(Database.addMelding(brukernavn,brukernavn,"hello"));
    }




    public void slettBruker() {
        try {
            assertTrue(Database.deleteBruker(brukernavn));
        }catch(Exception e){
            fail();
        }
    }





}