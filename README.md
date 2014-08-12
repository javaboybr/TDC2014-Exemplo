TDC2014-Exemplo
===============

Este projeto consiste nos exemplos mostrados durante minha palestra no TDC 2014.

Para utilizar os testes basta importar o projeto no Netbeans.

É necessário configurar o servidor de aplicações compativel com Java EE 7 para que seja possivel utilizar a API de concorrencia.

Também será necessário configurar os recursos JDNI no console de administrador do servidor de aplicações, em Recursos Concorrentes (Concurrent Resources) adicione uma ManagedThreadFactory com o nome "MyExec" e um ManagedExecutorService com o nome "MyThreadFacs".
