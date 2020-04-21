package br.com.bucker.service;

import br.com.bucker.model.Produto;
import br.com.bucker.model.Produto;
import br.com.bucker.service.ProdutoService;
import br.com.bucker.service.ProdutoService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoServiceTest {


    @Inject
    ProdutoService produtoService;

    @Test
    @Order(1)
    public void buscarProdutoPeloIdTest(){
        Produto produto = produtoService.buscarPeloId(1l);
        assertEquals("ARROZ", produto.descricao);
    }

    @Test
    @Order(2)
    public void incluirProdutoTeste(){
        Produto produto = new Produto();
        produto.descricao = "ARROZ";
        produto.valor = 10f;
        produto = produtoService.incluir(produto);
        assertEquals("ARROZ", produto.descricao);
        assertNotNull(produto.id);
    }

    @Test
    @Order(3)
    public void buscarProdutoPelaDescricaoTeste(){
        assertEquals(2, produtoService.buscarProdutoPelaDescricao("arr").size());
    }

    @Test
    @Order(4)
    public void atualizarProdutoPeloIDTeste(){
        Produto produto = new Produto();
        produto.descricao = "ARROZ";
        produto.id = 1l;
        produto = produtoService.atualizarPeloId(produto);
        assertEquals("ARROZ", produto.descricao);
    }

    @Test
    @Order(5)
    public void apagarProdutoPeloIdTeste(){
        assertEquals(1, produtoService.apagarPeloId(4l));
    }

}
