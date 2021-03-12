# Projeto Rh

## Tecnologias

* Java 8
* Spring bootstrap 2.0
* Angular JS 1.6
* MS SQL Server 2014
* Git
* Node Js v10 
* SQL Server Management Studio
* Eclipse
* VS Code
* Lombok

## Baixando dependências

Após criar sua conta no Git Lab, e ter liberação de acesso ao projeto, copie o linke para clone:
* Para fazer o download, vá até o disco local C: e cria uma pasta com nome de "git".
* Dentro da pasta git, click com botão direito do mouse e escolha a opção "Git bash here".
* Digite o comando "git clone" + o link copiado. 

obs: Caso erre o usuário do git, e necessita apagar o usuário salvo basta ir em:
 Gerenciador de Credenciais - > Credenciais do Windows -> Vá até as credencias do git e remova o registro.

## Cliente -> Front End
### Instalando dependências do cliente

*Para abrir o diretório com os arquivos dentro do VS Code, basta ir na opção:
 File -> Open Folder -> Em seguida achar a raiz do projeto (C:\git\interno-rh-sistema\rhClient\cliente).
 
*Dentro do VS Code,  abra o terminal e digite os seguintes comandos para finalizar a configuração.

npm install
npm install -g bower
npm install -g gulp
bower install
npm install natives

*Se preferir pode optar por executar o arquivo "rhClient/setup.sh", que ele mesmo já executará todos os comandos
listados acima.

### Iniciando a aplicação

gulp serve

## SQL Server -> Banco de dados
### Instalando SQl Server 
*Logo após a conclusão do download, será aberta a janela para 
instalação do SQL Server.  Selecione a opção: Nova instalação autônoma do 
SQL Server ou adicionar recursos a uma instalação existente.

*Vá avançando as configurações, até chegar em "configuração da instância"
-> selecione a opção “Instância nomeada” -> Digite o nome que deseja aplicar a instância -> Avançar

*O próximo passo é o mais importante da instalação: 
selecione a opção Modo Misto (autenticação do SQL Server e do Windows); 
logo abaixo, defina a senha inicial do usuário “sa”, que é o usuário administrador padrão.

Feito tudo isso, basta continuar avançando e finalizar a instalação.

### Instalando SQL Server Management Studio
*Siga avançando as opções, até finalizar toda instalação.

*Finalizado a instalação execute o SQL Server Management Studio. 
Na tela de logon, siga as seguintes informações:
-> Tipo de servidor: Mecanismos de banco de dados
-> Nome do servidor: "Nome da instancia criada na instalação do SQL Server"
-> Autenticação: Autenticação de SQL Server	
-> Logon: "sa"
-> Senha: "Senha que criou na instalação do SQL Server"

### Restaurando banco de dados.
*Baixe o arquivo de backup gerado pela mesma versão em que o seu SQL Server está.
Copie o mesmo, dentro da pasta de backup dentro da raiz do projeto (C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\Backup)

Dentro do SQL Server Management Studio. Clique com o botão direito na opção Banco de dados -> Restaurar banco de dados
Escolha a opção dispositivo - > abra a caixa de busca -> Adicionar - > Selecione o arquivo de backup e confirme.

### Liberando porta 1433
*Em Pesquisar do Windows, digite Firewall do Windows;
No menu da esquerda, clique em Configurações avançadas;
No menu da esquerda, clique em Regras de Saída;
No menu da direita, clique em Nova Regra;
Na parte central da tela, escolha a opção Porta e clique em Avançar;
Escolha a opção TCP, e em Portas locais específicas digite 1433 e clique em Avançar;
Escolha a opção Permitir a conexão e clique em Avançar;
Mantenha as opções marcadas e clique em Avançar;
Digite um nome, por exemplo SQL Server TCP 1433 e clique em Avançar; e,
Concluir

Obs: Faça o mesmo para a regras de entrada.

*Abra o SQL Server Configuration Manager
 Configurações de rede  do SQLServer-> Protocolos para ... -> TCP/IP ->  Propriedades -> Endereços IP -> IPAII -> Porta TCP/IP : 1433
 Deixe o status de todas as opções(Memoria Compartilhada, Pipes nomeados, TCP/IP) como "habilitado" 

## Servidor -> Backend
======================================
PROFILES 
A aplicação tem 3 profiles configurados (dev, homolog e prod). O dev é nosso ambiente de desenvolvimento local. O homolog é nossa homologação interna que está na amazon. A prod é o SGP do cliente, que hoje é vista como prod. O profile de dev é o profile padrão. Ao dar mvn package, spring-boot:run ou levantar a aplicação no tomcat o profile de dev sejá levantado por padrão.
=======================================

###Instalando dependências do servidor.
Antes de começar as configurações, adicione todos os arquivos do projeto ao repositório.

*Configurando credencias de acesso
  Dentro da pasta "C:\Git\interno-rh-sistema" copie o arquivo "applicationDatasourceExternal" para um local de fácil acesso (recomendo na raiz do disco C:):
 Abra o arquivo e coloque o usuário e senha criadas para acesso ao banco de dados. 
 
 Vá em varizes do ambiente -> Variáveis do sistema -> Novo - > 
	Nome da variável: DATASOURCE_EXTERNAL_PATH
	Valor da variável : "copie o caminho do arquivo applicationDatasourceExternal que alterou com suas credencias" 

*Configurando Eclipse
	1ª Dentro do Eclipse, click em debug -> debug configurations -> Maven build -> Botão direito do mouse + new configuration ->:
 Name: Migrate - DEV	
 Base Directory: -> File system, selecione o projeto.
 Goals :	flyway:migrate -Dflyway.validateOnMigrate=false -Dflyway.outOfOrder=true

*Vá até parâmetros, e adicione  as seguintes informações: 
 Name: flyway.url
	Value: jdbc:sqlserver://localhost\SQLEXPRESS:1433;databaseName=rhgoiania_dev
 Name: flyway.user
	Value:sa
 Name: flyway.password
	Value: Senha criada para o usuário SA do SQL Server.
	
Finalizado a configuração, execute o Migrate - DEV nas opções de debug.

	2ªDentro do Eclipse, click em debug -> debug configurations -> Maven build -> Botão direito do mouse + new configuration ->:
 Name: Run - DEV	
 Base Directory: -> File system, selecione o projeto.
 Goals :	spring-boot:run###Instalando dependências do servidor.
Antes de começar as configurações, adicione todos os arquivos do projeto ao repositório.

*Configurando credencias de acesso
  Dentro da pasta "C:\Git\interno-rh-sistema" copie o arquivo "applicationDatasourceExternal" para um local de fácil acesso (recomendo na raiz do disco C:):
 Abra o arquivo e coloque o usuário e senha criadas para acesso ao banco de dados. 
 
 Vá em variáveis do ambiente -> Variáveis do sistema -> Novo - > 
	Nome da variável: DATASOURCE_EXTERNAL_PATH
	Valor da variável : "copie o caminho do arquivo applicationDatasourceExternal que alterou com suas credencias" 

*Configurando Eclipse
	1ª Dentro do Eclipse, click em debug -> debug configurations -> Maven build -> Botão direito do mouse + new configuration ->:
 Name: Migrate - DEV	
 Base Directory: -> File system, selecione o projeto.
 Goals :	flyway:migrate -Dflyway.validateOnMigrate=false -Dflyway.outOfOrder=true

*Vá até parâmetros, e adicione  as seguintes informações: 
 Name: flyway.url
	Value: jdbc:sqlserver://localhost\SQLEXPRESS:1433;databaseName=rhgoiania_dev
 Name: flyway.user
	Value:sa
 Name: flyway.password
	Value: Senha criada para o usuário SA do SQL Server.
	
Finalizado a configuração, execute o Migrate - DEV nas opções de debug.

	2ªDentro do Eclipse, click em debug -> debug configurations -> Maven build -> Botão direito do mouse + new configuration ->:
 Name: Run - DEV	
 Base Directory: -> File system, selecione o projeto.
 Goals :	spring-boot:run


## Links de referencia
http://git.gittech.info/rh/interno-rh-sistema/issues/174
https://kb.elipse.com.br/tutorial-de-instalacao-e-configuracao-do-microsoft-sql-server-express-2017/
http://suporte.quarta.com.br/Suporte/LiberarPorta1433.htm
https://www.sqlshack.com/how-to-use-sql-server-configuration-manager/
https://www.habaneroconsulting.com/stories/insights/2015/tcpip-is-disabled-by-default-in-microsoft-sql-server-2014
https://stackoverflow.com/questions/18841744/jdbc-connection-failed-error-tcp-ip-connection-to-host-failed
https://fernandogasparjr.wordpress.com/2014/01/30/instalando-sql-server-2012-express/
	

