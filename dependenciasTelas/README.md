# Atividade Continuada de POO
Projeto desenvolvido para a disciplina de Programação Orientada a Objetos, ministrada pelo professor Eduardo Calabria, no curso de Ciência da Computação da CESAR School.

## Equipe

```
- Gabriel Victalino Mancebo
- Leonardo Gutzeit
```


## Funcionalidades Implementadas
- Gestão de Clientes: Cadastro e validação de dados de clientes (CPF, contato, endereço).

- Gestão de Equipamentos: Cadastro de equipamentos do tipo Notebook e Desktop, com validação de unicidade de serial.

- Ordem de Serviço:

    - Inclusão: Criação de nova OS vinculando Cliente e Equipamento previamente cadastrados.

    - Cancelamento: Permite cancelar uma OS aberta mediante justificativa.

    - Fechamento: Finaliza a OS com relatório técnico e status de pagamento.

## Telas e Interface Gráfica

As telas foram desenvolvidas utilizando Java Swing puro e código customizado, priorizando o entendimento da estrutura de componentes e gerenciadores de layout, sem o uso de ferramentas visuais como o WindowsBuilder.

Portanto, todas as bibliotecas gráficas utilizadas fazem parte do JDK padrão do Java (javax.swing.*, java.awt.*).

## Dependências e Arquivos

O projeto utiliza o gerenciador de dependências manual (classpath). Os únicos arquivos .jar externos incluídos no projeto foram os disponibilizados pelo professor para testes unitários, persistência e utilitários:
- junit-platform-commons-1.8.2.jar
- junit-platform-engine-1.8.2.jar
- opentest4j-1.2.0.jar
- PersistenciaObjetos.jar
- junit-jupiter-5.8.2.jar
- junit-jupiter-api-5.8.2.jar
- junit-jupiter-engine-5.8.2.jar
- junit-jupiter-params-5.8.2.jar
- lombok.jar
