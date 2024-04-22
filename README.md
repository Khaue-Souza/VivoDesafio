**Curl para testar no postman o CRUD**

**Listar:**
curl --location 'localhost:8080/clientes'

**Adicionar:**
curl --location 'localhost:8080/clientes' \
--header 'Content-Type: application/json' \
--data-raw '{

    "name":"NomeCliente",
    "email":"cliente.email@hotmail.com",
    "document": "123456789"
}'

**Atualizar:**
curl --location --request PUT 'http://localhost:8080/clientes/1' \
--header 'Content-Type: application/json' \
--data-raw '{
   
    "name": "Nome Atualizado",
    "email": "email.atualizado@example.com",
    "document": "987654321"
}'
