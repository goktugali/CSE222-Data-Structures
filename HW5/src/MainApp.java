import java.awt.image.BufferedImage;

public class MainApp{

    private Image2D image;

    public MainApp(BufferedImage img, String fileName)
    {
        this.image =  new Image2D(img,fileName);
    }

    /**
     * Starts the main application.
     * Starts the thread 1 using processor.addPixels() method to add pixels to priority queues.
     * Starts the thread 2 using processor.removeEuc() method to remove pixels from priority queue named PQEuc.
     * Starts the thread 3 using processor.removeLex() method to remove pixels from priority queue named PQLex.
     */
    public void start() /* Defines the main program */
    {
        Object smp = new Object();
        final Processor processor = new Processor(image,smp);
        final PrintInfo printInfo = new PrintInfo(processor,smp);

        Thread t1 = new Thread(new Runnable() { // inserts pixel into priority queues.
            @Override
            public void run() {
                try {
                    processor.addPixels();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() { // remove pixels from PQEuc
            @Override
            public void run() {
                try {
                    processor.removeEuc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(new Runnable() { // remove pixels from PQLex
            @Override
            public void run() {
                try {
                    processor.removeLex();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread printer = new Thread(new Runnable() { // printer (driver) thread
            @Override
            public void run() {
                printInfo.print();
            }
        });

        t1.start();
        t2.start();
        t3.start();
        printer.start();
    }

    /**
     * Processor class that has methods which are responsible for adding pixels into priority queues
     * and remove pixels from this priority queues.
     * Related threads will run this methods.
     */
    private static class Processor{

        private GtuPriorityQueue<GtuArrayList<Integer>> PQEuc; // PQ based on EUC comparator
        private GtuPriorityQueue<GtuArrayList<Integer>> PQLex; // PQ based on LEX comparator

        private Image2D img; // 2D image
        private final int size; // image size
        private final Object lock = new Object(); // locker for thread2
        private final Object lock2 = new Object();// locker for thread3
        private final Object lockMain; // shared with printer class, wakes up after the process finished.

        // thread execution counters
        private int added=0;
        private int added2=0;
        private int th1_ctr;
        private int th2_ctr;
        private int th3_ctr;

        private void test() // driver method
        {
            System.out.println("----------------------------------------");
            System.out.println("Process has been finished");
            System.out.println("Details about process : ");
            System.out.println("Image file name : " + img.getFileName() + " size : " + size + "px");
            System.out.println("# Thread1 counter (printed px) : " + th1_ctr);
            System.out.println("# Thread2 counter (printed px) : " + th2_ctr);
            System.out.println("# Thread3 counter (printed px) : " + th3_ctr);
            System.out.println("Current priortiy queues after process finished : ");
            System.out.println("(we expect, they must be empty)");
            System.out.println("PQLex : " + PQLex + "\nPQEuc : " + PQEuc + "\n");
            System.out.println("----------------------------------------");
        }

        private Processor(Image2D image,Object sm)
        {
            img=image;
            size=image.getSize();
            PQEuc=new GtuPriorityQueue<>(new EUCComparator(),size);
            PQLex=new GtuPriorityQueue<>(new LEXComparator(),size);
            lockMain = sm;
        }

        private void addPixels()
        {
            for (int i = 0; i < img.getHeight() ; i++) {
                for (int j = 0; j < img.getWidth() ; j++) {
                    synchronized (lock){
                        GtuArrayList<Integer> v = img.getPixel(i,j);
                        System.out.println("Thread1 : " + v + " " + added);
                        PQEuc.add(v); // insert pixel
                        ++added;
                        if(added>100) // starts thread2 firstly or wake up the thread 3
                            lock.notify();
                    }
                    synchronized(lock2){
                        GtuArrayList<Integer> v = img.getPixel(i,j);
                        PQLex.add(v);// insert pixel
                        ++added2;
                        if(added2>100) // starts thread3 firstly or wake up the thread 3
                            lock2.notify();
                    }
                }
            }
            th1_ctr=added;
        }
        private void removeEuc()
        {
            int processed=0;
            while(processed<size)
            {
                synchronized(lock){
                    try{
                        while(added<100)
                            lock.wait(); // wait until 100 elements are inserted

                        while(PQEuc.isEmpty()) // wait until new elements are inserted
                            lock.wait();
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    System.out.println("Thread3-PQEUC : " + PQEuc.poll() + " " + processed);
                    ++processed;
                }
            }
            th2_ctr=processed;
            if(th3_ctr==processed){
                synchronized (lockMain){ // wake up the thread that prints the results
                    lockMain.notify();
                }
            }
        }

        private void removeLex()
        {
            int processed=0;
            while(processed<size)
            {
                synchronized(lock2){
                    try{
                        while(added2<100) // wait until 100 elements are inserted
                            lock2.wait();

                        while(PQLex.isEmpty()) // wait until new elements are inserted
                            lock2.wait();
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    System.out.println("Thread2-PQLEX : " + PQLex.poll() + " " + processed);
                    ++processed;
                }
            }
            th3_ctr=processed;
            if(th2_ctr==processed){
                synchronized (lockMain){ // wake up the thread that prints the results
                    lockMain.notify();
                }
            }
        }
    }

    /**
     * Printer class that prints the information messages after the
     * image process has been finished.
     */
    private static class PrintInfo{
        private final Processor pc;
        private final Object smp; // shared with processor class

        private PrintInfo(Processor p,Object sm)
        {
            this.pc=p;
            this.smp=sm;
        }

        private void print()
        {
            synchronized (smp){
                    try {
                        smp.wait();
                    }catch(InterruptedException ex){
                        ex.printStackTrace();
                    }
                pc.test();
            }
        }
    }
}