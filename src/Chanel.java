import java.util.LinkedList;

public class Chanel{
    private final int maxCount;//кол-во объектов, которые мы готовы принимать(отличается от числа в Server
    private final LinkedList<Runnable> queue = new LinkedList<Runnable>();//наша очередь
    private static final Object lock = new Object();

    public Chanel(int maxCount){
        this.maxCount=maxCount;
    }
    void put(Runnable x){
        synchronized (lock){
            while(queue.size()>= maxCount){ //пока кто то не освободит
                try {
                    lock.wait();
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(x);
            lock.notifyAll();
        }
    }
    public Runnable take(){
        synchronized (lock) {
            while (queue.size() == 0) {//пока кто то не положит значение
                try {
                    lock.wait();
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.notifyAll();
            return queue.removeFirst();
        }
    }
}
