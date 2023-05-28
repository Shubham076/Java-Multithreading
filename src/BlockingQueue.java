import java.util.concurrent.*;

class Queue {
    public static void main(String[] args) throws InterruptedException {
        final String POISON_PILL = "POISON_PILL";
        int numConsumers = 3;
        //works similar to java channels
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        CountDownLatch wg = new CountDownLatch(numConsumers);

        // Producer thread
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    queue.put("Message " + i);
                    System.out.println("Produced Message " + i);
                }

                for(int i = 0; i < numConsumers; i++) {
                    queue.put(POISON_PILL);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // Consumers
        for (int i = 0; i < numConsumers; i++) {
            final int consumerId = i;
            new Thread(() -> {
                try {
                    while (true) {
                        String message = queue.take();
                        if (POISON_PILL.equals(message)) {
                            System.out.println("Consumer " + consumerId + " received poison pill. Exiting...");
                            break;
                        }
                        System.out.println("Consumer " + consumerId + " consumed " + message);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    wg.countDown();
                }
            }).start();
        }

        wg.await();
        System.out.println("Completed");
    }
}
