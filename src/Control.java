import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;
    private boolean over = false;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];

    /**
     * Constructor que le indica a los threads los rangos en loq eu debe trabajar e inicializa los threads
     */
    private Control() {
        super();

        this.pft = new PrimeFinderThread[NTHREADS];

        int i;
        for (i = 0; i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i * NDATA, MAXVALUE + 1);
    }

    /**
     * Método que inciialzia el contructor
     */
    public static Control newControl() {
        return new Control();
    }

    /**
     * Método que verifica si un thread ya terminó su tarea
     */
    private void isOver() {
        boolean t = true;
        for (int i = 0; i < NTHREADS; i++) {
            if (pft[i].isAlive()) {
                t = false;
                break;
            }
        }
        over = t;

    }

    /**
     * Método que hace que cada hilo encuentre los primos de su rango y luego imprima la cantidad de estos encontrada
     */
    @Override
    public void run() {
        for (int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }
        Scanner sc = new Scanner(System.in);
        long maxT = System.currentTimeMillis() + TMILISECONDS;
        while (!over) {

            if (System.currentTimeMillis() >= maxT) {

                for (int i = 0; i < NTHREADS; i++) {
                    System.out.println("El hilo " + i + " hallo " + pft[i].getSize() + " numeros primos");
                    pft[i].changeSleep();
                }
                boolean enter = true;
                while (enter) {
                    System.out.println("Presione enter para continuar");
                    if (sc.nextLine().equals("")) {

                        for (int i = 0; i < pft.length; i++) {

                            //pft[i].notify();
                            pft[i].wakeUp();
                        }

                        maxT = System.currentTimeMillis() + TMILISECONDS;
                    }
                    enter = false;
                    break;
                }

            }
            isOver();
        }

        for (int i = 0; i < pft.length; i++) {
            System.out.println("El hilo " + i + " encontro " + pft[i].getSize() + " numeros primos");
        }
        System.out.println("Proceso finalizado");
        System.exit(0);
    }

}
