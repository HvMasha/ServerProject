
public class WorkerThread implements Runnable { //Это наш рабочий поток
    private Thread thread;
    private ThreadPool threadPool;
    private Session currentTask;
    private Object lock = new Object();

    public WorkerThread(ThreadPool threadPool){
        this.threadPool = threadPool;
        //Create new thread. Start Thread.
        this.thread = new Thread(this);
        thread.start();
    }
    public void run(){
        //Пока нам не пришел task мы ждем
        synchronized (lock) {
            while (currentTask == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.notifyAll();
        }
        //if currentTask != null, run it. No need to create new thread. Just call run() method of session
        Thread currentThread = new Thread(currentTask);
        currentThread.start();//Правильно, если я тут вызываю run() ?
        //after task completed (or failed) inform threadPool that workerThread is free(run threadPool.onTaskCompleted(this))
        threadPool.onTaskCompleted(this);//Как его запускать?
    }
    public void excute (Runnable task){
        //set this task to currentTask, notify waiting threads
        synchronized (lock) {
            this.currentTask = currentTask;
            lock.notifyAll();
        }
    }
}
