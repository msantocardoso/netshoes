#Descrição da Solução

Considerando que o serviço proposto será exposto na web e consumido por aplicações mobile. O mesmo será desenvolvido seguindo os princípios *RestFull*. O serviço REST roda em cima do protocolo HTTP e possibilita a recuperação de recursos(entidades) do sistema através de URLs. Para recuperar endereço por CEP faremos uso do método GET (do protocolo HTTP) como regra para solicitar representação deste recurso do servidor. A principal motivação para utilização dessa abordagem é a facilidade e simplicidade na criação e disponibilização de serviços.

#Arquitetura

	- Java SE 8
	- Grizzly: framework servidor HTTP, utilizado para infraestrutura para testes dos serviços
	- Junit4: framework utilizado na implementação dos testes unitários
	- Log4J: framework registro de logs de execução
	- Jersey: implementação da especificação JAX-RS (JSR-311) para criação de serviços REST

#Instruções para validação

Basta executar o teste existente no diretório "src/test/java/**/resource/EnderecoResourceTest.java".

O teste foi programado para iniciar o servidor Grizzly (localhost:8080), criar o contexto da aplicação "/netshoes-test", o endereço de busca do recurso desejado, nesse caso o "endereço" está definido no path "/enderecos" seguido do CEP "/{cep}".

> A consulta é feita de forma bem simples. A requisição GET (método HTTP utilizado na recuperação de recursos) é disparada para a URL: http://localhost:8080/netshoes-test/enderecos/{cep_a_consultar}, onde o *{cep_a_consultar}* é substituido pelo número do CEP utilizado na busca do endereço, devendo obrigatóriamente seguir as seguintes regras de formatação (xxxxxxxx ou xxxxx-xxx, onde "xxx" representam valores numéricos). O resultado será entregue no formato JSON.

A validação das respostas geradas pelo serviço serão validadas utilizando os *Asserts* do *junit* visando garantir que estão compatíveis com o resultado esperado em cada um dos cenários de teste implementados:

#Cenários de Teste

Foram implementados os seguintes cenários de teste:

> * Dispara requisição GET para o servidor passando o CEP (válido) "01313001" da "Avenida nove de julho, Bela Vista São Paulo - SP".
	- URL de solicitação -> GET : <http://localhost:8080/netshoes-test/enderecos/01313001>
```
	@Test
	public void deveRetornarLogradouroAvenidaNoveDeJulhoPorCep();
```

> * Dispara requisição GET para o servidor passando o CEP (válido) "11013006", porém este não possui endereço relacionado. O serviço deverá aplicar as regras descritas do critérios de aceitação deste cenário e retornar o endereço relacionado ao CEP "11013000".
	- URL de solicitação -> GET : <http://localhost:8080/netshoes-test/enderecos/11013006>

```
	@Test
	public void deveSimularCepValidoDiversasBuscasDeEndereco();
```

> * Dispara requisição GET para o servidor passando o CEP (válido) "12345678", porém este não possui endereço relacionado. O serviço deverá aplicar as regras descritas do critérios de aceitação deste cenário e retornar o endereço relacionado ao CEP "12340000".
	- URL de solicitação -> GET : <http://localhost:8080/netshoes-test/enderecos/12345678>
```
	@Test
	public void deveSimularVariasTentativasDeBuscaDeEndereco();
```

> * Dispara requisição GET para o servidor passando o CEP (inválido) "00000000". O serviço deverá responder com erro e retornar uma mensagem *"Cep inválido!"*.
	- URL de solicitação -> GET : <http://localhost:8080/netshoes-test/enderecos/00000000>
```
	@Test(expected=UniformInterfaceException.class)
	public void deveLancarExceptionAoPassarUmaSequenciaDeZeros();
```

> * Dispara requisição GET para o servidor passando o CEP (inválido) "00000000". O serviço deverá responder com erro, contendo status code BAD_REQUEST (400) e uma mensagem de erro *"Cep inválido!"*.
	- URL de solicitação -> GET : <http://localhost:8080/netshoes-test/enderecos/00000000>
```
	@Test
	public void deveRetornarStatusCodeBadRequestEMsgCepInvalidoAoPassarUmaSequenciaDeZeros();
```

> * Dispara requisição GET para o servidor passando o CEP (inválido) "00000-000". O serviço deverá responder com erro, contendo status code BAD_REQUEST (400) e uma mensagem de erro *"Cep inválido!"*.
	- URL de solicitação -> GET : <http://localhost:8080/netshoes-test/enderecos/00000-000>
```
	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoUtilizarCepInvalidoComZeros();
```

> * Dispara requisição GET para o servidor passando o CEP (inválido) "12345 *menos de 8 caracteres*". O serviço deverá responder com erro, contendo status code BAD_REQUEST (400) e uma mensagem de erro *"Cep inválido!"*.
	- URL de solicitação -> GET : <http://localhost:8080/netshoes-test/enderecos/12345>
```
	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCepComMenosDeOitoCaracteres();
```

> * Dispara requisição GET para o servidor passando o CEP (inválido) "1#@$%&". O serviço deverá responder com erro, contendo status code BAD_REQUEST (400) e uma mensagem de erro *"Cep inválido!"*.
	- URL de solicitação -> GET : <http://localhost:8080/netshoes-test/enderecos/1#@$%&>
```
	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCaracteresEspeciaisNoCep();
```

> * Dispara requisição GET para o servidor passando o CEP (inválido) "0123456789 *menos de 8 caracteres*". O serviço deverá responder com erro, contendo status code BAD_REQUEST (400) e uma mensagem de erro *"Cep inválido!"*.
	- URL de solicitação -> GET : <http://localhost:8080/netshoes-test/enderecos/0123456789>
```
	@Test
	public void deveStatusCodeBadRequestEMsgCepInvalidoAoPassarCepComMaisDeOitoCaracteres();
```
