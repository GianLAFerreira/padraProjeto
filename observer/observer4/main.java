package observer4;

import java.util.List;

public class main {
    public static void main(String[] args) {
        Revista r1 = new Revista("R1");
        Revista r2 = new Revista("R2");

        Assinante a1 = new Assinante();
        Assinante a2 = new Assinante();
        Assinante a3 = new Assinante();
        Assinante a4 = new Assinante();
        Assinante a5 = new Assinante();

        r1.anexar(a1);
        r1.anexar(a2);
        r1.anexar(a3);

        r2.anexar(a2);
        r2.anexar(a4);

        WebSite website = new WebSite();

//        website.anexar(r1);
//        website.anexar(r2);

        Edicao edicao1 = new Edicao(1);
        edicao1.addArtigo(new Artigo("T1"));
        edicao1.addArtigo(new Artigo("T2"));

        Edicao edicao2 = new Edicao(2);
        edicao2.addArtigo(new Artigo("T3"));
        edicao2.addArtigo(new Artigo("T4"));
        edicao2.addArtigo(new Artigo("T5"));

        r1.lancarNovaEdicao(edicao1);
        r2.lancarNovaEdicao(edicao2);
    }
}
