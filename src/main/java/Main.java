
public class Main {
    /*
     * Klassen som kjører automagisk når maven builder.
     * Kjører FXMLMain.main metoden.
     */
    public static void main(String[] args) {
        FXMLMain.main(args);
        //UserManager.main();
    }

    public int add(int x, int y) {
        return x + y;
    }


}
