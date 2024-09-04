package observer4;

import java.util.ArrayList;
import java.util.List;

public class Edicao {

    private int numero;
    private List<Artigo> artigos = new ArrayList<>();

    public Edicao(int numero) {
        this.numero = numero;
    }

    public void addArtigo(Artigo nomes) {;
        artigos.add(nomes);
    }

    public int getNumero() {
        return numero;
    }
}
