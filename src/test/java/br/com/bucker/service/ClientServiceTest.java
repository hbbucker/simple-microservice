package br.com.bucker.service;

import br.com.bucker.model.Cliente;
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
public class ClientServiceTest {


    @Inject
    ClienteService clienteService;

    @Test
    @Order(1)
    public void buscarClientePeloIdTest() {
        Cliente cliente = clienteService.buscarPeloId(1l);
        assertEquals("HUGO", cliente.nome);
    }

    @Test
    @Order(2)
    public void incluirClienteTeste() {
        Cliente cliente = new Cliente();
        cliente.nome = "JOÃO";
        cliente.dataDeNascimento = LocalDate.of(1992, 03, 13);
        cliente.endereco = "Rua dos cafezais 123";
        cliente = clienteService.incluir(cliente);
        assertEquals("JOÃO", cliente.nome);
        assertNotNull(cliente.id);
    }

    @Test
    @Order(3)
    public void atualizarClientePeloIDTeste() {
        Cliente cliente = new Cliente();
        cliente.nome = "JOÃO";
        cliente.dataDeNascimento = LocalDate.of(1992, 03, 13);
        cliente.endereco = "Rua dos cafezais 123";
        cliente.id = 1l;
        cliente = clienteService.atualizarPeloId(cliente);
        assertEquals("JOÃO", cliente.nome);

    }

    @Test
    @Order(4)
    public void apagarClientePeloIdTeste() {
        assertEquals(1, clienteService.apagarPeloId(4l));
    }
}
