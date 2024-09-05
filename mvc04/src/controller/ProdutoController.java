package controller;

import model.Pedido;
import model.Produto;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    private List<Observer> obss = new ArrayList<>();
    private List<Produto> tabelaProdutos = new ArrayList<>();
    private List<Pedido> tabelaPedidos = new ArrayList<>();
    private int idxProduto;

    public ProdutoController() {
        tabelaProdutos.add(new Produto("calça", 83));
        tabelaProdutos.add(new Produto("camisa", 57));
        tabelaProdutos.add(new Produto("gravata", 15.7));
        tabelaProdutos.add(new Produto("tenis", 235.8));

    }

    public void addObserver(Observer o) {
        obss.add(o);
    }

    public void criarProdutoAnterior() {
        idxProduto = (idxProduto - 1);
        if (idxProduto == -1)
            idxProduto = tabelaProdutos.size() - 1;
    }

    public void criarProdutoPosterior() {
        idxProduto = (idxProduto + 1) % tabelaProdutos.size();
    }

    public void adicionarProduto() {
        Produto prod = tabelaProdutos.get(idxProduto);
        Pedido alvo = null;
        for (Pedido ped : tabelaPedidos) {
            if (ped.getProduto() == prod) {
                alvo = ped;
                break;
            }
        }
        if (alvo == null) {
            alvo = new Pedido(prod);
            tabelaPedidos.add(alvo);
        } else
            alvo.addQtdade();
    }

    public void concluirPedido() {
        tabelaPedidos.clear();
        idxProduto = 0;
    }

    public Object getValorPedido(int rowIndex, int colIndex) {
        Pedido p = tabelaPedidos.get(rowIndex);
            switch (colIndex) {
                case 0:
                    return p.getProduto().getNome();
                case 1:
                    return p.getQtdade();
                default:
                    return p.getPcoTotal();
            }
    }

    public int getQuantosProduto() {
        return tabelaProdutos.size();
    }

    public String getNomeProduto(int i) {
         return tabelaProdutos.get(i).getNome();
     }

    public double getTotalPedido() {
        double total = 0;
        for (Pedido ped : tabelaPedidos) {
            total += ped.getPcoTotal();
        }
        return total;
    }

    public int getQuantosPedidos() {
        return tabelaPedidos.size();
    }
}
