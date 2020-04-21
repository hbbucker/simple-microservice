package br.com.bucker.resource;

import br.com.bucker.model.Cliente;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.ws.rs.core.Response;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteResourceTest {

    @Test
    @Order(1)
    public void buscarClientePeloIdTeste() {
        given()
                .auth().basic("user", "user")
                .when().get("/cliente/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(ContentType.JSON)
                .extract().response()
                .jsonPath().getString("nome")
                .equals("HUGO");
    }

    @Test
    @Order(2)
    public void incluirClienteTeste(){
        Cliente cliente = new Cliente();
        cliente.nome = "JOÃO";
        cliente.dataDeNascimento = LocalDate.of(1992,03,13);
        cliente.endereco = "Rua dos cafezais 123";
        given()
                .auth().basic("user", "user")
                .contentType(ContentType.JSON)
                .body(cliente)
                .when().post("/cliente")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .contentType(ContentType.JSON)
                .extract().response()
                .jsonPath().getString("nome")
                .equals("JOÃO");
    }


    @Test
    @Order(3)
    public void atualizarClientePeloIDTeste(){
        Cliente cliente = new Cliente();
        cliente.nome = "JOÃO";
        cliente.dataDeNascimento = LocalDate.of(1992,03,13);
        cliente.endereco = "Rua dos cafezais 123";
        given()
                .auth().basic("user", "user")
                .contentType(ContentType.JSON)
                .body(cliente)
                .when().put("/cliente/2")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(ContentType.JSON)
                .extract().response()
                .jsonPath().getString("nome")
                .equals("JOÃO");
    }

    @Test
    @Order(4)
    public void apagarClientePeloIdTeste(){
        given()
                .auth().basic("user", "user")
                .when().delete("/cliente/3")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

    }
}