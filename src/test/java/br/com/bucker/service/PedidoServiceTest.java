package br.com.bucker.service;

import br.com.bucker.model.Cliente;
import br.com.bucker.model.ItemPedido;
import br.com.bucker.model.Pedido;
import br.com.bucker.model.Produto;
import br.com.bucker.service.PedidoService;
import br.com.bucker.service.ProdutoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jackson.JacksonUtils;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidoServiceTest {


    @Inject
    PedidoService pedidoService;

    @Inject
    ProdutoService produtoService;


    @Test
    @Order(1)
    public void buscarPedidoPeloIdTest(){
        Pedido pedido = pedidoService.buscarPeloId(1l);
        assertEquals(2, pedido.itemsPedido.size());
    }

    @Test
    @Order(2)
    public void buscarPedidosPeloIdCliente() throws JsonProcessingException {
        Pedido pedido = pedidoService.buscarPeloIdCliente(1l);
        assertNotNull(pedido);
        assertNotEquals (0, pedido.itemsPedido.size());
    }

    @Test
    @Order(3)
    public void incluirPedidoTeste(){
        Pedido pedido = new Pedido();
        pedido.cliente = new Cliente();
        pedido.cliente.id = 1l;
        pedido.dataPedido = LocalDate.now();

        for (int i = 0; i < 2; i++){
            Produto produto = produtoService.buscarPeloId(i+1l);
            pedido.addicionarItem(produto, 2l);
        }

        pedido = pedidoService.incluir(pedido);
        assertNotNull(pedido.id);
        assertEquals(2, pedido.itemsPedido.size());
        assertNotNull(pedido.itemsPedido.get(0).id);

    }

    @Test
    @Order(4)
    public void atualizarPedidoPeloIDTeste(){
        Pedido pedido = new Pedido();
        pedido.id = 1l;
        pedido.dataPedido = LocalDate.of(1990, 8, 10);
        pedido = pedidoService.atualizarPeloId(pedido);
        assertEquals(LocalDate.of(1990, 8, 10), pedido.dataPedido);
    }

    @Test
    @Order(5)
    public void adicionarUmItemAoPedidoPeloIDTeste(){
        Pedido pedido = new Pedido();
        pedido.id = 1l;

        Produto produto = produtoService.buscarPeloId(3l);
        pedido.addicionarItem(produto, 2l);

        pedido = pedidoService.atualizarPeloId(pedido);
        assertEquals(3, pedido.itemsPedido.size());
    }

    @Test
    @Order(6)
    public void apagarPedidoPeloIdTeste(){
        assertEquals(1, pedidoService.apagarPeloId(4l));
    }

}
