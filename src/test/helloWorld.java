package test;


public class helloWorld {
	
	public String helloWorld() {
		return "Hello World!";
	}
	
	public int add(int x, int y) {
		return x + y;
	}
	
	
	public static void main(String[] args) {
		helloWorld hW = new helloWorld();
		System.out.println(hW.helloWorld());
		System.out.println(hW.add(4,5));
	}
}
