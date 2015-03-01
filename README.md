#Arquitetura

	- Java SE 7
	- Maven: gestão das dependências
	- Grizzly: framework servidor HTTP, utilizado para infraestrutura para testes dos serviços
	- Junit4: framework utilizado na implementação dos testes unitários
	- Log4J: framework registro de logs de execução
	- Jersey: implementação da especificação JAX-RS (JSR-311) para criação de serviços REST
	- hsqldb: banco de dados, persistencia em modo memória utilizado na execução do CRUD
	- API para consulta de endereço por cep - http://postmon.com.br/

#Instruções para validação - História 1

Considerando que o serviço proposto será exposto na web e consumido por aplicações mobile. O mesmo será desenvolvido seguindo os princípios *RestFull*. O serviço REST roda em cima do protocolo HTTP e possibilita a recuperação de recursos(entidades) do sistema através de URLs. Para recuperar endereço por CEP faremos uso do método GET (do protocolo HTTP) como regra para solicitar representação deste recurso do servidor. A principal motivação para utilização dessa abordagem é a facilidade e simplicidade na criação e disponibilização de serviços.

Visão geral da implementação:

**src/main/java**

---------------

br.com.netshoes
- Servidor.java - encapsula os detalhes de inicialização do servidor http

br.com.netshoes.model
- Endereco.java - representação do endereço

br.com.netshoes.infrastructure
- ClientAdapter.java - interface do adaptador do serviço do jersey
- JerseyClientAdapter.java - encapsula os detalhes de utilização do cliente http jersey
- ModificadorCaracteres.java - modificador de caracteres utilizado na aplicação as regras de modificação de caracteres do cep

br.com.netshoes.infrastructure.exception
- CepInvalidoException.java
- EnderecoInvalidoException.java
- JerseyClientException.java
- NenhumRegistroEncontrado.java
- RepositoryException.java

- br.com.netshoes.infrastructure.exception.mapper
- CepInvalidoMapper.java
- JerseyClientExceptionMapper.java - mapeamento de exceptions

br.com.netshoes.repository.services
- WebServiceEndereco.java - Interface de acesso ao serviço de busca de endereco
- WebServiceEnderecoExterno.java - Reponsável por acessar o serviço externo de busca de endereco
- WebServiceEnderecoNetshoes.java - Reponsável por acessar o serviço desenvolvido nessa história

br.com.netshoes.resources
- EnderecoResource.java - Servico de busca de endereco cep

**src/test/java**

---------------

br.com.netshoes.resource

- AbstracaoEnderecoResourceTest.java - abstrai a configuração da infraestrutura necessária para execução dos testes
- EnderecoResourceTest.java - valida os cenários de teste descritos nos requisitos

br.com.netshoes.util
- ModificadorCaracteresTest.java - valida o componente de modificação de caracteres de uma string

-----------------------------

O teste foi programado para iniciar o servidor Grizzly (localhost:8080), criar o contexto da aplicação "/netshoes-test", o endereço de busca do recurso desejado, nesse caso o "endereço" está definido no path "/enderecos" seguido do CEP "/{cep}".

> A consulta é feita de forma bem simples. A requisição GET (método HTTP utilizado na recuperação de recursos) é disparada para a URL: http://localhost:8080/netshoes-test/enderecos/{cep_a_consultar}, onde o *{cep_a_consultar}* é substituido pelo número do CEP utilizado na busca do endereço, devendo obrigatóriamente seguir as seguintes regras de formatação (xxxxxxxx ou xxxxx-xxx, onde "xxx" representam valores numéricos). O resultado será entregue no formato JSON.

A validação das respostas geradas pelo serviço serão validadas utilizando os *Asserts* do *junit* visando garantir que estão compatíveis com o resultado esperado em cada um dos cenários de teste implementados.

-----------------------------

Execute a classe de teste existente no diretório "src/test/java/**/resource/EnderecoResourceTest.java"

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

#Instruções para validação - História 2

CRUD do endereço (INCLUIR, CONSULTAR, ATUALIZAR, DELETAR)

Visão geral da implementação:

**src/main/java**

---------------

br.com.netshoes.service
- EnderecoService.java - interface de definição do metodos de manipulação do endereco
- EnderecoServiceImpl.java - implementação

br.com.netshoes.repository
- EnderecoRepository.java - interface de definição do metodos de manipulação do endereco
- EnderecoRepositoryImpl.java - Implementação do crud

**src/test/java**

---------------

br.com.netshoes.repository
- AbstracaoRepositorioTest.java - abstrai a configuração da infraestrutura necessária para execução dos testes
- EnderecoServiceTest.java - validação dos cenários de teste descritos no requisitos

---------------

Execute a classe de teste existente no diretório "src/test/java/**/repository/EnderecoServiceTest.java".

A seguir a descrição dos testes implementados:

> * Executa as operações de inserção, atualização, remoção e consulta do objeto endereço.
```
	@Test
	public void deveExecutarCrudDeEndereco() throws CepInvalidoException;
```

> * Tentar inserir um endereco sem passar o campo rua nulo. Será lançado uma exceção <EnderecoInvalidoException>
```
    @Test(expected = EnderecoInvalidoException.class)
	public void deveLancaEnderecoInvalidoExceptionAoTentarInserirUmEnderecoSemARua();
```

> * Tentar inserir um endereco sem passar o campo Cidade nulo. Será lançado uma exceção <EnderecoInvalidoException>
```
    @Test(expected = EnderecoInvalidoException.class)
	public void deveLancaEnderecoInvalidoExceptionAoTentarInserirUmEnderecoSemCidade();
```

> * Tentar inserir um endereco sem passa o campo Numero nulo. Será lançado uma exceção <EnderecoInvalidoException>
```
    @Test(expected = EnderecoInvalidoException.class)
	public void deveLancaEnderecoInvalidoExceptionAoTentarInserirUmEnderecoSemNumero();
```

> * Tentar inserir um endereco sem passar o campo Estado nulo. Será lançado uma exceção <EnderecoInvalidoException>
```
    @Test(expected = EnderecoInvalidoException.class)
	public void deveLancaEnderecoInvalidoExceptionAoTentarInserirUmEnderecoSemEstado();
```

> * Tentar inserir um endereco sem passar o campo Cep nulo. Será lançado uma exceção <CepInvalidoException>
```
    @Test(expected = CepInvalidoException.class)
	public void deveLancaEnderecoInvalidoExceptionAoTentarInserirUmEnderecoSemCep();
```

> * Tentar alterar um endereco sem passar o campo Cep nulo. Será lançado uma exceção <CepInvalidoException>
```
    @Test(expected = CepInvalidoException.class)
 	public void deveLancaEnderecoInvalidoExceptionAoTentarAlterarEnderecoSemCep();
```

> * Tentar alterar um endereco sem passar o campo Cep nulo. Será lançado uma exceção <EnderecoInvalidoException>
```
    @Test(expected = EnderecoInvalidoException.class)
 	public void deveLancaEnderecoInvalidoExceptionAoTentarAlterarEnderecoSemRua();
```

> * Tentar alterar um endereco sem passar o campo Numero nulo. Será lançado uma exceção <EnderecoInvalidoException>
```
    @Test(expected = EnderecoInvalidoException.class)
    public void deveLancaEnderecoInvalidoExceptionAoTentarAlterarEnderecoSemNumero();
```

> * Tentar alterar um endereco sem passar o campo Cidade nulo. Será lançado uma exceção <EnderecoInvalidoException>
```
    @Test(expected = EnderecoInvalidoException.class)
    public void deveLancaEnderecoInvalidoExceptionAoTentarAlterarEnderecoSemCidade() {
```

> * Tentar alterar um endereco sem passar o campo Estado nulo. Será lançado uma exceção <EnderecoInvalidoException>
```
    @Test(expected = EnderecoInvalidoException.class)
    public void deveLancaEnderecoInvalidoExceptionAoTentarAlterarEnderecoSemEstado();
```

> * Lança uma exceção <CepInvalidoException> ao tenta persistir endereco passando um cep inválido.
```
    @Test(expected = CepInvalidoException.class)
	public void deveLancarCepInvalidoExceptionAoTentarPersistirEndereco();
```

#Resposta - Questão 3

Algoritmo de busca do primeiro caracterer que não se repete em uma string

Visão geral da implementação:

**src/main/java**

---------------

br.com.netshoes.stream
- Stream.java - Interface pré-definida na descrição da questão
- StreamImpl.java - Implementação da regra de identificação do primeiro digito que não se repete em um texto.

**src/test/java**

---------------

br.com.netshoes.stream
- StreamTest.java - classe de teste dos stream

---------------

Execute a classe de teste no diretório "src/test/java/**/stream/StreamTest.java"

A seguir a descrição dos testes implementados:

> *Retorna 'b' como primeiro caracter que não se repete
```
	@Test
	public void deveRetornarbComoCaracterQueNaoSeRepete();
```
> * Retorna 'z' como primeiro caracter que não se repete
```
	@Test
	public void deveRetornarzComoCaracterQueNaoSeRepete();
```
> * Lança NotFounException, quando não houver nenhum registro
```
	@Test(expected=NotFoundException.class)
	public void deveLancarNotFoundException();
```

#Resposta - Questão 4

O navegador fará uma requisição HTTP para o servidor, no cabecalho da solicitação será adicionando diversas informações como identificação do solicitante "User-Agent: Mozilla/5.0 (X11; U; Linux i686; en-US)...", método GET/ versão do protocolo "HTTP/1.1", tipo de dado esperado "Accept: text/xml,application/xml,application/xhtml", dentre outros... O servidor recebará a solicitação, realiza o processamento e devolve o resultado para o solicitante. Ao receber a resposta o navegador iniciará solicitação, carregamento dos recursos estáticos e processamento da página. Para cada recurso estático o dispositivo fará uma nova solicitação ao servidor, que o entregará o recurso, o navegador irá processá-lo e o finalizar disponibilizar a página ao usuário.
