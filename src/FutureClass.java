import java.util.concurrent.*;

public class FutureClass {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] inputArray = new int[100]; // Initialize the input array
        int[] outputArray = new int[100]; // Create the output array

        // Fill the input array
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = i + 1;
        }

        int numThreads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        Future<?>[] futures = new Future[inputArray.length];

        for (int i = 0; i < inputArray.length; i++) {
            final int index = i;
            futures[i] = executorService.submit(() -> {
                try {
                    Thread.sleep(300);
                    outputArray[index] = inputArray[index] * 2;
                    System.out.println("Processed element " + index);
                }
                catch (InterruptedException e) {

                }
            });
        }

        // Wait for all tasks to complete and retrieve results
        for (int i = 0; i < inputArray.length; i++) {
            futures[i].get();
        }

        executorService.shutdown();

        // Print the output array
        for (int i = 0; i < outputArray.length; i++) {
            System.out.println("Output element " + i + ": " + outputArray[i]);
        }
    }
}
