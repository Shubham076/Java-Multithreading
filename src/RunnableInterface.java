import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableInterface{

    static class MessageProcesssor implements Runnable {
        int i;
        MessageProcesssor(int i) {
            this.i = i;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(500);
                System.out.println("Processed: " + this.i);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }


    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        ExecutorService service = Executors.newFixedThreadPool(4);
        for (int i: arr) {
            MessageProcesssor m = new MessageProcesssor(i);
            service.execute(m);
        }
        service.shutdown();
        while(! service.isTerminated()) {}
        System.out.println("Completed");
    }
}