package modulos.produto;

import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioDataFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import pojo.UsuarioPojo;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API REST do módulo de produto")
public class ProdutoTest {
    private String token;

    @BeforeEach
    public void beforeEach(){
        // Configurando os dados da API da Lojinha
        baseURI = "http://165.227.93.41";
        //  port = 8080;
        basePath = "/lojinha/v2";



        // Obter token usuario admin
        this.token =
                given()
                        .contentType(ContentType.JSON)
                        .body(UsuarioDataFactory.criarUsuario("admin", "admin"))
                        .when()
                        .post("/login")
                        .then()
                        .extract()
                        .path("data.token");
    }

    @Test
    @DisplayName("Validar os limites inferiores a 0.00 proibidos do valor do produto")
    public void testValidarLimitesProibidosValorProduto(){

        // Tentar registrar produto com valor 0.00 e validar a mensagem de erro e o status code
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
            .body(ProdutoDataFactory.criarProdutoComVariacaoValor(0.00))
        .when()
            .post("/produtos")
        .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }

    @Test
    @DisplayName("Validar os limites superiores 7000.00 proibidos do valor do produto")
    public void testValidarLimitesMaiorSeteMilProibidosValorProduto(){

        // Tentar registrar produto com valor 0.00 e validar a mensagem de erro e o status code
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComVariacaoValor(7000.01))
                .when()
                .post("/produtos")
                .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }
}
