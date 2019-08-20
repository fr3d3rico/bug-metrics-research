# bug-metrics-research
O objetivo deste repositório é disponibilizar o script para extrair métricas de repositórios de softwares públicos. Estas métricas serão utilizadas para uma pesquisa do aluno de doutorando Jacson da UFG.

## Metrics
Todas as métricas foram extraídas utilizando o software Understand (https://scitools.com/feature/metrics/).
As métricas são informações utilizadas para avaliar a qualidade e complexidade de um software.
Este link (https://scitools.com/feature/metrics/) apresenta uma descrição sobre métricas do Understand.

## Tools e Pré-requisitos

- Git (https://git-scm.com/)
- Java (https://www.java.com/pt_BR/download/)
- Gradle (https://gradle.org/)
- Understand (https://scitools.com/feature/metrics/)

## How to do?

- 1 Crie um diretório na raíz de seu computador, por exemplo, "c:\repos". Este diretório será utilizado para clonar o projeto que será analisado.

- 2 Crie um diretório de sua preferência para clonar este projeto. Por exemplo, "c:\repos\script". Dentro deste diretório clone este projeto com o comando git:

git clone https://github.com/fr3d3rico/bug-metrics-research.git

Será criado um diretório chamado "bug-metrics-research". Abra e edite os arquivos com a IDE ou editor de texto de sua preferência.

- 3 No projeto script Java, vá até a classe "Confi.java" e coloque este caminho do passo 1 "c:\\repos\\" na variável "REPOSITORY_DIR". Atenção: Dependendo do seu sistema operacional deve-se colocar duas barras. Como neste exemplo do Windows.

- 4 Volte para o diretório do passo 1 e faça o download/clone do repositório que deseja analisar. Neste caso será analisado o projeto Tomcat(http://tomcat.apache.org/).
No terminal, navegue até o diretório do passo 1 e dê o seguinte comando:

git clone https://github.com/apache/tomcat.git

O projeto clonado será este no sítio github: https://github.com/apache/tomcat.git

- 5 Dentro do diretório "c:\repos" crie um diretório chamado "report" ("c:\repos\report") ou com o nome da sua preferência. Este diretório irá receber os logs gerados pelo Understand no formato "CSV".
Em seguida, vá até a classe "Config.java" e também altere o caminho para o diretório "REPORT_DIR" com o seguinte caminho: "c:\\repos\\report\\"

- 6 Dentro do diretório c:\repos\report\\ crie um com o nome do projeto em questão, por exemplo, "tomcat". Ficando assim: "c:\repos\report\tomcat"

- 7 Na classe "BugAcademicResearch.java", logo no início altere a variável "NOME_PROJETO" para "tomcat".

- 8 Agora vamos configurar o Understand.
O understand tem uma restrição. Ao analisar os arquivos, ele vai crescendo o arquivo de extensão .udb para 10mb, 50mb e assim por diante. E a cada análise que se faz, fica mais lento e demorado. Então optei por criar um arquivo vazio de análise. Este arquivo ficará em um diretório de backup, e a cada bug, eu adiciono ele novamente para que o processamento não fique lento para extrair as métricas. Feito isso eu removo o arquivo e reinicio ele novamente copiando o arquivo do diretório de backup. Desta forma o processamento ficou muito mais rápido.

Após instalar o Understand, abra a ferramenta e crie um novo projeto:
![alt tag](https://github.com/fr3d3rico/bug-metrics-research/blob/master/images/understand-1.png)

Informe os dados do projeto. Por exemplo, para facilitar, caso esteja analisando mais de um projeto ao mesmo tempo, coloque o nome do projeto igual ao nome do projeto analisado, por exemplo, "tomcat".
Informe o diretório raíz do projeto tomcat clonado.
![alt tag](https://github.com/fr3d3rico/bug-metrics-research/blob/master/images/understand-2.png)

Informe somente a tecnologia Java que será analisada.
![alt tag](https://github.com/fr3d3rico/bug-metrics-research/blob/master/images/understand-3.png)

Clique em "Next" e finalize a criaçao do projeto.
![alt tag](https://github.com/fr3d3rico/bug-metrics-research/blob/master/images/understand-4.png)

Em seguida, configure o projeto informando as métricas que serão extraídas.
![alt tag](https://github.com/fr3d3rico/bug-metrics-research/blob/master/images/understand-5.png)
![alt tag](https://github.com/fr3d3rico/bug-metrics-research/blob/master/images/understand-6.png)

Em seguida, navegue até o diretorio onde foi clonado o projeto, por exemplo, c:\repos\tomcat e veja que existe o arquivo "tomcat.udb". Este arquivo tem somente entre 8 e 10kb.

Faça uma cópia deste arquivo para o diretório Config.REPOSITORY_DIR + "backup". Este diretório "backup" deve ser criado e será o local de armazenamento dos arquivos .udb para evitar que o projeto Understand cresça e fique pesado para ser processado.

- 9 Para executar o projeto, bastar entrar na raíz do projeto script e executar o comando:
gradle run

O script irá consultar os bugs da base de dados. Em seguida, irá fazer o checkout 1 commit anterior do commit do bug. Para fazer o checkout no commit anterior é utilizado o comando "git checkout <commit-id>~1".
Em seguida o script verifica quais os arquivos serão analisados e tenta adicioná-los no projeto Understand. As vezes acontece de um commit não ter nenhum arquivo no bug-id. Então neste caso não será analisado. Caso ao menos 1 arquivo esteja presente, o script irá analisar e em seguida solicitar as métricas para o Understand.

- 10 O Understand irá salvar o arquivo csv para cada bug analisado no caminho report/NOME_PROJETO. O script irá copiar este arquivo para o diretório Config.REPORT_DIR para registrar o log.

## Share!

Sinta-se a vontade em fazer o fork, alterar, melhorar ou criar um novo projeto.