import java.util.LinkedList;

public class ThreadPool {
    private LinkedList<Runnable> allWorkers = new LinkedList<>();//Очередь тех, кто будет выполнять наши задачи
    private Chanel freeWorkers;// потоки который будут исполнять наши задачи
    private int maxSize;//макисмальный размер pool
    public ThreadPool(int maxSize){
        this.maxSize = maxSize;
        freeWorkers = new Chanel(maxSize);
    }
}
