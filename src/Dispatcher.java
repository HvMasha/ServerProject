public class Dispatcher implements Runnable {
    private Channel chanel;
    private ThreadPool threadPool;

    Dispatcher(Channel chanel, ThreadPool threadPool){
        this.chanel = chanel;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        while(true){
            Runnable session = chanel.take();
            Thread thread = new Thread(session);
            thread.start();
        }
    }
}
