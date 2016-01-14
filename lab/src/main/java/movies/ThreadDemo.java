package movies;

/**
 * A quick demonstration of Threading in Java
 * @author Joel Ross
 *
 * This class has several nested classes that run asynchronously in parallel
 */
public class ThreadDemo {

	//A function that counts up when run
	public static class RunUp implements Runnable {
		public void run() {
			for(int i=0; i<10; i++) {				
				System.out.println(i);
				try {
					Thread.sleep(50); //wait for 50ms
				} catch (InterruptedException e) {}
			}
		}
	}

	//A function that counts down when run
	public static class RunDown implements Runnable {
		public void run() {
			for(int i=0; i>-10; i--) {
				System.out.println(i);
				try {
					Thread.sleep(50); //wait for 50ms
				} catch (InterruptedException e) {}
			}
		}
	}

	/** Be sure and run this multiple times! **/
	public static void main(String[] args) {
		new Thread(new RunUp()).start();
		new Thread(new RunDown()).start();
	}
}
