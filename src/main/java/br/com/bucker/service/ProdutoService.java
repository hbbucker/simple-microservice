package br.com.bucker.service;

import br.com.bucker.model.Produto;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ProdutoService extends GenericService<Produto> implements IService<Produto> {

    public Produto buscarPeloId(Long id) {
        return Produto.findById(id);
    }

    public List<Produto> buscarProdutoPelaDescricao(String descricao){
        descricao = "%" + descricao + "%";
        return Produto.find("LOWER(descricao) LIKE LOWER(?1)", descricao).list();
    }

    @Transactional
    public Produto incluir(Produto produto) {

        if (produto.isPersistent())
            produto.persist();
        else
            Produto.persist(produto);

        return produto;
    }

    @Transactional
    public Long apagarPeloId(Long id) {
        return Produto.delete("id = ?1", id);
    }

    @Transactional
    public Produto atualizarPeloId(Produto produto) {
        String queryUpdate = stringUpdate(produto);
        Map<String, Object> map = objetoParaMap(produto);
        Produto.update(queryUpdate, map);
        return buscarPeloId(produto.id);
    }
}
