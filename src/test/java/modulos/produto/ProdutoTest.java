package modulos.produto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API REST do módulo de produto")
public class ProdutoTest {

    @Test
    @DisplayName("Validar os limites proibidos do valor do produto")
    public void testValidarLimitesProibidosValorProduto(){
        // Configurando os dados da API da Lojinha
        baseURI = "http://165.227.93.41";
        //  port = 8080;
        basePath = "/lojinha/v2";

        // Obter token usuario admin
        String token =
        given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                    "  \"usuarioLogin\": \"admin\",\n" +
                    "  \"usuarioSenha\": \"admin\"\n" +
                    "}")
        .when()
            .post("/login")
        .then()
            .extract()
            .path("data.token");

        // Tentar registrar produto com valor 0.00 e validar a mensagem de erro e o status code
        given()
            .contentType(ContentType.JSON)
            .header("token", token)
            .body("{\n" +
                    "  \"produtoNome\": \"Playstation 6\",\n" +
                    "  \"produtoValor\": 0.00,\n" +
                    "  \"produtoCores\": [\n" +
                    "    \"Preto\", \"Branco\"\n" +
                    "  ],\n" +
                    "  \"produtoUrlMock\": \"\",\n" +
                    "  \"componentes\": [\n" +
                    "    {\n" +
                    "      \"componenteNome\": \"Controle\",\n" +
                    "      \"componenteQuantidade\": 1\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}")
        .when()
            .post("/produtos")
        .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }
}
