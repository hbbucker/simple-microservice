## Projeto Simples de Microservices

### Compilação do Projeto

Compile e empacote o projeto utilizando Maven:

```bash
./mvnw compile package
```

### Criar container Docker

Utilizar o docker-compose para criar e levantar o ambiente com Docker:

```bash
docker-compose build
docker-compose up -d
```

Será criado um container com PostgreSQL e outro com o Projeto na porta 8091, seguindo a documento a baixo
para utilização da API Rest.

### Escopo
* Clientes
* Produtos
* Pedidos

 
### REST Resources _Pedido_


#### GET /pedido/{id}
    Produces: application/json
    
Retorna os dados do Pedido pelo ID
```json
{
    "id": 1,
    "dataPedido": "20/04/2020",
    "valorTotal": 26,
    "cliente": {
        "id": 1,
        "nome": "HUGO",
        "endereco": "Av. Mato Grosso, 1290",
        "dataDeNascimento": "24/08/1979"
    },
    "itemsPedido": [
        {
            "id": 1,
            "quantidade": 1,
            "valorUnitario": 10,
            "valorTotal": 10,
            "pedido": 1,
            "produto": 1
        },
        {
            "id": 2,
            "quantidade": 2,
            "valorUnitario": 8,
            "valorTotal": 16,
            "pedido": 1,
            "produto": 2
        }
    ]
}          
```     
        
#### POST /pedido
Cria um novo pedido.

    Consumes: application/json
    Produces: application/json

Para criar um novo pedido deve ser enviado os seguintes dados:   
```json
{
    "dataPedido": "20/04/2020",
    "cliente": {
        "id": 1
    }
}
```

Como resposta status 200(OK) e o JSon com o ID do Pedido:
```json
{
    "id": 4,
    "dataPedido": "20/04/2020",
    "valorTotal": null,
    "cliente": {
        "id": 1,
        "nome": null,
        "endereco": null,
        "dataDeNascimento": null,
        "pedidos": null
    },
    "itemsPedido": null
}
```

#### DELETE /pedido/{id}
Apagar um Pedido pelo ID

* Sucesso: Status 204(No Content), quando excluído com sucesso.
* Erro: Status 404(Not Found).

    
#### PUT /pedido/{id}
Atualiza os dados de um pedido pelo ID.

    Consumes: application/json
    Produces: application/json

Corpo da Requisição:        
```json
{
    "dataPedido": "15/04/2020"
}
```

Retorno:
```json
{
    "id": 1,
    "dataPedido": "15/04/2020",
    "valorTotal": null,
    "cliente": null,
    "itemsPedido": null
}
```
  
    
#### GET /pedido/{pedidoId}/{produtoId}/{produtoQtd}
Incluir um Produto(Item) ao pedido.

        Consumes: application/json
        Produces: application/json

Para incluir um produto ao pedido, passar os códigos e quantidade pelo caminho (path) na url.

Ex.:
 ```
     /pedido/1/3/2
             ^ ^ ^ - Quantidade do produto
             | | --- Código do produto
             | ----- Código do pedido  
```


### REST Resources _Cliente_

#### POST /cliente
Incluir Cliente

    Consumes: application/json
    Produces: application/json

Para criar um novo cliente deve enviar o seguinte JSON:

```json
{
    "nome": "HUGO",
    "endereco": "Av. Mato Grosso, 1290",
    "dataDeNascimento": "24/08/1979"
}

```

Retorno: Status 200(OK), com os dados e ID do Cliente

```json
{
    "id": 4,
    "nome": "HUGO BUCKER",
    "endereco": "Av. Mato Grosso, 1290",
    "dataDeNascimento": "24/08/1979"
}
```

#### DELETE /cliente/{id}
Apaga o Cliente pelo ID informado no caminho (path)

* Sucesso: Status 204(No Content), quando excluído com sucesso.
* Erro: Status 404(Not Found).


#### GET /cliente/{id}
Busca um cliente pelo ID

        Produces: application/json

Retorna os dados do Cliente:
```json
{
    "id": 2,
    "nome": "LUIZ",
    "endereco": "Av. Afonso Pena,1000",
    "dataDeNascimento": "20/02/1980",
    "pedidos": []
}
```
Cliente não encotrado: Status 204(No Content)



#### PUT /cliente/{id}
Alterar os dados do Cliente pelo ID passado pelo caminho (path)
        Consumes: application/json
        Produces: application/json

Corpo do Json para alterar o cliente, com os campos que deseja fazer alteração:

```json
{
    "endereco": "Av. Afonso Pena, 1000"
}
```
Retorno: Status 200(OK), com o campo alterado

```json
{
    "id": 2,
    "nome": null,
    "endereco": "Av. Mato Grosso, 500",
    "dataDeNascimento": null,
    "pedidos": null
}
```

### REST Resources _Produto_

#### GET /produto/{id}
Busca um produto pelo ID

        Produces: application/json

Retorna os dados do Produto:
```json
{
    "id": 2,
    "descricao": "FEIJÃO",
    "valor": 8
}

```

#### DELETE /produto/{id}
Apaga o Produto pelo ID informado no caminho (path)

* Sucesso: Status 204(No Content), quando excluído com sucesso.
* Erro: Status 404(Not Found).

#### PUT /produto/{id}
Altera os atributos de um produto, pelo ID informado no caminho (path), e os campos informados no
JSON do corpo da requisição.

        Consumes: application/json
        Produces: application/json
        
Corpo do Json:

```json
{
    "descricao": "FEIJÃO CARIOCA",
    "valor": 4.99
}
```

 
#### POST /produto
Inclui um novo produto

        Consumes: application/json
        Produces: application/json
        
Corpo da resquisição:
```json
{
    "descricao": "MACARRÃO TIPO SPAGETTI",
    "valor": 2.39
}
```
