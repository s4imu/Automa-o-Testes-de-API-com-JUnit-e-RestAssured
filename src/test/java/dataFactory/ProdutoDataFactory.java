package dataFactory;

import pojo.ComponentePojo;
import pojo.ProdutoPojo;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDataFactory {

    public static ProdutoPojo criarProdutoComVariacaoValor(double valor) {
        ProdutoPojo produto = new ProdutoPojo();
        ComponentePojo componente = new ComponentePojo();
        List<ComponentePojo> produtoComponentes = new ArrayList<>();
        List<String> produtoCores = new ArrayList<>();

        componente.setComponenteNome("Controle");
        componente.setComponenteQuantidade(1);

        produtoComponentes.add(componente);

        produto.setProdutoNome("Playstation 6");
        produto.setProdutoValor(valor);
        produtoCores.add("Preto");
        produtoCores.add("Branco");
        produto.setProdutoCores(produtoCores);
        produto.setProdutoUrlMock("");
        produto.setComponentes(produtoComponentes);

        return produto;
    }
}
