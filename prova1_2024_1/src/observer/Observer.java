package observer;

public interface Observer {

    void exibirImagemCarta(int index, int r, int c);
    void tratarPrimeiraCarta(int index, int r, int c);
    void tratarSegundaCarta(int index, int r, int c);
    void exibirMensagemVitoria();
    void exibirMensagemParEncontrado();
    void exibirMensagemCartasDiferentes();
}
