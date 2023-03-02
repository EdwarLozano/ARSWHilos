import java.util.LinkedList;
import java.util.List;
public class PrimeFinderThread extends Thread {
    private List<Integer> primes;
    int a,b;
    boolean sleeping;

    public PrimeFinderThread(int a, int b) {
        super();
        this.primes = new LinkedList<>();
        this.a = a;
        this.b = b;
    }

    public void run(){
        int i =0;
        for (i = a; i < b; i++) {
            if(sleeping) {
                try {
                    this.sleep();
                }
                catch(Exception e) {System.out.println("error al dormir "+e);}
            }
            else {
                if (isPrime(i)) {
                    primes.add(i);
                }
            }
        }
    }

    public synchronized void sleep() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    boolean isPrime(int n) {
        boolean ans;
        if (n > 2) {
            ans = n % 2 != 0;
            for (int i = 3; ans && i * i <= n; i += 2) {
                ans = n % i != 0;
            }
        } else {
            ans = n == 2;
        }
        return ans;
    }


    public List<Integer> getPrimes (){
        return primes;
    }

    public int getSize (){
        return primes.size();
    }

    public void changeSleep() {
        sleeping = true;
    }

    public synchronized void wakeUp() {
        sleeping = false;
        this.notifyAll();
    }
}
