package controller;

import modelo.Mao;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParImparController {
    private int minhaVit;
    private int pcVit;
    private List<Mao> maos = new ArrayList<>();
    private List<Observer> obss = new ArrayList<>();
    private int idxMao;
    Random sorteio = new Random();

    public void novo() {
        maos.clear();
        minhaVit = 0;
        pcVit = 0;
        for (Observer o : obss) {
            o.novoJogo();
        }
    }

    public void addObserver(Observer o) {
        obss.add(o);
    }

    public Mao getMao(int index) {
        return maos.get(index);
    }

    public int getSize() {
        return maos.size();
    }

    public void posterior() {
        idxMao = (idxMao + 1) % 6;

        for (Observer o : obss) {
            o.proximaMao();
        }
    }

    public void jogar(boolean jrbPar) {
        int maoPC = sorteio.nextInt(6);

        Mao mao = new Mao(jrbPar, maoPC, idxMao);
        maos.add(mao);
        for (Observer o : obss) {
            o.atualizar(maoPC);
        }

        boolean vencePar = ((maoPC + idxMao)%2) == 0;

        if (mao.isPar() == vencePar) {
            minhaVit++;

            if (minhaVit == 3) {
                for (Observer o : obss) {
                    o.notificarVitoria();
                }
            }
        } else {
            pcVit++;
            if (pcVit == 3) {
                for (Observer o : obss) {
                    o.notificarDerrota();
                }
            }
        }
    }
}
