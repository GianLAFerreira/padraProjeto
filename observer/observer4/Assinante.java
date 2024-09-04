package observer4;

public class Assinante implements Observer{
    private String nome;
    private String endereco;

    @Override
    public void atualizado(String assinante, Edicao edicao) {
        System.out.println(assinante + " " + edicao.getNumero());
    }
}
