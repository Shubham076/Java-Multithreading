import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CallableExample {

    public static void main(String[] args) {

        // Creates an ExecutorService with a fixed thread pool size
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Creates a Callable object that returns the square of a number
        Callable<Integer> callableTask = () -> {
            TimeUnit.SECONDS.sleep(1); // simulate a long-running task
            return 2 * 2;
        };

        // Submits the Callable object to the executor service
        Future<Integer> future = executorService.submit(callableTask);

        try {
            // Retrieves the result of the computation from the future
            // This call blocks until the result is available
            Integer result = future.get();
            System.out.println("Result of the Callable: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shuts down the executor service
            executorService.shutdown();
        }
    }
}
