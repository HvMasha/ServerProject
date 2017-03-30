import java.util.LinkedList;

public class ThreadPool {//Здесь хранятся все рабочие потоки
    private LinkedList<Runnable> allWorkers = new LinkedList<>();// очередь тех, кто будет выполнять наши задачи
    private Channel freeWorkers;// свободные потоки
    private int maxSize;//макисмальный размер pool-а
    private Object lock = new Object();// монитор

    public ThreadPool(int maxSize){
        this.maxSize = maxSize;
        //Создаем freeWorker-a
        WorkerThread workerThread = new WorkerThread(this);//? Какой ThreadPool тут писать???
        allWorkers.addLast(workerThread);
        freeWorkers = new Channel(maxSize);
        freeWorkers.put(workerThread);
    }

    public void execute (Runnable task) throws InterruptedException {
        synchronized (lock) {
            while(freeWorkers.size() == maxSize && allWorkers.size() == maxSize) {//Проверяем, заняты ли рабочие потоки и не достигли ли мы максимального кол-ва потоков
                lock.wait();// Если нет места, то отправляем его подождать
            }
            if (freeWorkers.size() == maxSize)//Если мы вышли из while, то одно из условий не выполнилось
            {
                //Если не достигли максимума в allWorkers, то добавляем еще один freeWorkers и добавляем поток в allWorkers
                WorkerThread workerThread = new WorkerThread(this);//? Какой ThreadPool тут писать???
                freeWorkers.put(workerThread);
                allWorkers.addLast(workerThread);
            } else {//Это значит у нас есть свободный поток в freeWorkers
                //Берем свободный workerThread из freeWorkers
                WorkerThread freeWorker = (WorkerThread) freeWorkers.take();
                //Даем ему task на выполнение
                freeWorker.excute(task);
            }
            lock.notifyAll();//??? В том ли он месте стоит ?
        }
    }

    void onTaskCompleted(WorkerThread workerThread){
        //Перемещаем workerThread в freeWorkers
        freeWorkers.put(workerThread);
    }
}
