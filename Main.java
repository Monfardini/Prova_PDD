public class Main {
    public static void main(String[] args) {
        RecursoCompartilhado recurso = new RecursoCompartilhado();

        int numeroDeLeitores = 10;
        int numeroDeEscritores = 10;

        for (int i = 1; i <= numeroDeLeitores; i++) {
            new Thread(new Leitor(i, recurso)).start();
        }

        for (int i = 1; i <= numeroDeEscritores; i++) {
            new Thread(new Escritor(i, recurso)).start();
        }
    }
}