package br.com.bucker.resource;

import br.com.bucker.model.Cliente;
import br.com.bucker.model.Pedido;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidoResourceTest {

    @Test
    @Order(1)
    public void buscarPedidoPeloIdTeste() {
        JsonPath resultado = given()
                .auth().basic("user", "user")
                .when().get("/pedido/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(ContentType.JSON)
                .extract().response()
                .jsonPath();

        assertEquals("HUGO", resultado.getString("cliente.nome"));
        assertEquals(2, resultado.getList("itemsPedido").size());
    }

    @Test
    @Order(2)
    public void incluirPedidoTeste() {
        Pedido pedido = new Pedido();
        pedido.dataPedido = LocalDate.now();
        pedido.cliente = new Cliente();
        pedido.cliente.id = 1l;
        pedido.valorTotal = 0f;
        given()
                .auth().basic("user", "user")
                .contentType(ContentType.JSON)
                .body(pedido)
                .when().post("/pedido")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .contentType(ContentType.JSON)
                .extract().response()
                .jsonPath().getString("valorTotal").equals("0");

    }

    @Test
    @Order(3)
    public void atualizarPedidoPeloIDTeste(){
        Pedido pedido = new Pedido();

        pedido.valorTotal = 1000f;
        pedido.dataPedido = LocalDate.of(2020,04,19);

        JsonPath resultado = given()
                .auth().basic("user", "user")
                .contentType(ContentType.JSON)
                .body(pedido)
                .when().put("/pedido/2")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(ContentType.JSON)
                .extract().response()
                .jsonPath();
        assertEquals("19/04/2020",resultado.getString("dataPedido"));
    }

    @Test
    @Order(4)
    public void adicionarUmItemAoPedidoPeloIDTeste(){
        JsonPath resultado = given()
                .auth().basic("user", "user")
                .contentType(ContentType.JSON)
                .when().get("/pedido/2/2/4")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(ContentType.JSON)
                .extract().response()
                .jsonPath();
        assertEquals(3, resultado.getList("itemsPedido").size());
    }

    @Test
    @Order(5)
    public void apagarPedidoPeloIdTeste() {
        given()
                .auth().basic("user", "user")
                .when().delete("/pedido/3")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
}