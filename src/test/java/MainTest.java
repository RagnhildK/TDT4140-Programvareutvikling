/*import org.junit.jupiter.api.Test;
 */


import static org.junit.Assert.*;

import app.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MainTest {
    /*
     * Hoved testklasse. Tester ved bruk av JUnit.
     */

    private static String brukernavn = "truls";
    private static String passord = "123";
    private static String fag = "ABC1234";

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dato = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat tidspunkt = new SimpleDateFormat("HH-mm");

    @org.junit.BeforeClass
    public static void opprettEmne() {
        try {
            Database.addEmne(fag, "Test emne");
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

    @org.junit.Before
    public void opprettBruker() {
        UserManager._aktivtEmne=fag;
        UserManager._bruker=brukernavn;
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
    @org.junit.After
    public void slettBruker() {
        try {
            assertTrue(Database.deleteBruker(brukernavn));
        }catch(Exception e){
            fail();
        }
    }
    @org.junit.AfterClass
    public static void slettEmne() {
        try {
            assertTrue(Database.deleteEmne(fag));
        }catch(Exception e){
            fail();
        }
    }
    @org.junit.Test
    public void sjekkLogin() {
        try {
            assertTrue(UserManager.checkLogin(brukernavn, passord));
        }catch(Exception e){
            fail();
        }
    }
    @org.junit.Test
    public void sjekkBooking() {
        assertTrue(UserManager.addSaltid(dato.format(calendar.getTime()),tidspunkt.format(calendar.getTime()),tidspunkt.format(calendar.getTime()),"15"));
        assertTrue(UserManager.addStudassPåSal(dato.format(calendar.getTime()), tidspunkt.format(calendar.getTime()), "15"));
        assertTrue(UserManager.booking(dato.format(calendar.getTime()),tidspunkt.format(calendar.getTime()),brukernavn));
    }
    @org.junit.Test
    public void sjekkRolle(){
        assertTrue(Database.updateRolle(fag,brukernavn,"student"));
    }
    @org.junit.Test
    public void sjekkMelding(){
        assertTrue(Database.addMelding(brukernavn,brukernavn,"hello"));
    }
    @org.junit.Test
    public void sjekkInnlevering(){
        assertTrue(Database.addOving(UserManager._aktivtEmne,"Øving Test","Øving Test", "2030-01-01"));
        File fil = new File("test.pdf");
        assertTrue(Database.addInnlevering(Database.getMaxID("OvingID", "Oving"), brukernavn, "Øving Test", fil));
        assertTrue(Database.addRetting(Database.getMaxID("InnleveringID", "Innlevering"), brukernavn, "1", "Ok"));
    }






}