package observer4;

import java.util.ArrayList;
import java.util.List;

public class Revista {
    private String nome;
    private List<Edicao> edicao = new ArrayList<>();

    public Revista(String nome) {
        this.nome = nome;
    }

    public void lancarNovaEdicao(Edicao edicao) {
        this.edicao.add(edicao);
        notificar(nome, edicao);
    }

    private List<Observer> obss = new ArrayList<>();

    public void anexar(Observer nome){
        this.obss.add(nome);
    }

    public void notificar(String nome, Edicao edicao) {
        for (Observer o : obss) {
            o.atualizado(nome, edicao);
        }
    }

    public String getNome() {
        return nome;
    }
}
