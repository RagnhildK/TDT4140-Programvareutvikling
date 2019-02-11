public class Main {

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


    public static void main(String[] args) {
        Main hW = new Main();
        System.out.println(hW.helloWorld());
        System.out.println(hW.add(4,5));
        Person p = new Person("Bob", 12);
        System.out.println(p.getName());


    }
}
