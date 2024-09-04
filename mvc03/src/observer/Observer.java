package observer;

public interface Observer {

    void novoJogo();
    void proximaMao();
    void atualizar(int maoPc);
    void notificarVitoria();
    void notificarDerrota();
}
