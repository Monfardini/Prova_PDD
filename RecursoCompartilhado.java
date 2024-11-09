class RecursoCompartilhado {
    private int leitoresAtivos = 0;
    private boolean escritorAtivo = false;

    public synchronized void iniciarLeitura(int leitorId) throws InterruptedException {
        while (escritorAtivo) {
            System.out.println("Leitor " + leitorId + " aguardando para ler.");
            wait();
        }
        leitoresAtivos++;
        System.out.println("Leitor " + leitorId + " está lendo.");
    }

    public synchronized void finalizarLeitura(int leitorId) {
        leitoresAtivos--;
        System.out.println("Leitor " + leitorId + " terminou de ler.");
        if (leitoresAtivos == 0) {
            notifyAll(); 
        }
    }

    public synchronized void iniciarEscrita(int escritorId) throws InterruptedException {
        while (escritorAtivo || leitoresAtivos > 0) {
            System.out.println("Escritor " + escritorId + " aguardando para escrever.");
            wait();
        }
        escritorAtivo = true;
        System.out.println("Escritor " + escritorId + " está escrevendo.");
    }

    public synchronized void finalizarEscrita(int escritorId) {
        escritorAtivo = false;
        System.out.println("Escritor " + escritorId + " terminou de escrever.");
        notifyAll(); 
    }
}

class Leitor implements Runnable {
    private final int id;
    private final RecursoCompartilhado recurso;

    public Leitor(int id, RecursoCompartilhado recurso) {
        this.id = id;
        this.recurso = recurso;
    }

    @Override
    public void run() {
        try {
            recurso.iniciarLeitura(id);
            Thread.sleep(100); 
            recurso.finalizarLeitura(id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Leitor " + id + " foi interrompido.");
        }
    }
}

class Escritor implements Runnable {
    private final int id;
    private final RecursoCompartilhado recurso;

    public Escritor(int id, RecursoCompartilhado recurso) {
        this.id = id;
        this.recurso = recurso;
    }

    @Override
    public void run() {
        try {
            recurso.iniciarEscrita(id);
            Thread.sleep(200);
            recurso.finalizarEscrita(id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Escritor " + id + " foi interrompido.");
        }
    }
}


