public class Dispatcher implements Runnable {
    private Chanel chanel;
    Dispatcher(Chanel chanel){
        this.chanel = chanel;
    }
    public void putSession(Runnable session){
        chanel.put(session);
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
