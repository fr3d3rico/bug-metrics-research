# bug-metrics-research
This goal is extract metrics from public repositories for a research.
The research is lead by Msc. Jacson Barbosa, UFG.

## Metrics
Todas as metricas foram extraídas utilizando o software Understand (https://scitools.com/feature/metrics/).

## Tools

- Git (https://git-scm.com/)
- Java
- Gradle (https://gradle.org/)
- Understand (https://scitools.com/feature/metrics/)

## How to do?

- Create a directory to clone repository, for example, "c:/repos"

2 - No projeto Java, vá até a classe Confi.java e coloque este caminho na variável "REPOSITORY_DIR".

1 - Download repository, for example, tomcat (https://github.com/apache/tomcat)
Dentro do diretório repos ("c:/repos") clone o projeto que deseja analisar:
git clone https://github.com/apache/tomcat.git

- Dentro do diretório c:/repos crie um chamado report ("c:/repos/report") ou com o nome da sua preferência. Este diretório irá receber os logs gerados pelo Understand. 
Em seguida, vá até a classe Config.java e também altere o caminho para o diretório REPORT_DIR.

- Dentro do diretório c:/repos/report crie um com o nome do projeto em questão, por exemplo, tomcat. Ficando assim: c:/repos/report/tomcat

- Na classe "BugAcademicResearch.java", logo no início altere a variável NOME_PROJETO para "tomcat".

- Agora vamos configurar o Understand.
O understand tem uma restrição. Ao analisar os arquivos, ele vai crescendo o arquivo .udb para 10mb, 50mb e assim por diante. E a cada análise que se faz, fica mais lento e demorado. Então optei por criar um arquivo vazio de análise. E a cada bug, eu adiciono o arquivo objetivo, analiso e extraio as metricas. Feito isso eu removo o arquivo e reinicio ele novamente copiando o arquivo de backup. Desta forma o processamento ficou muito mais rápido.

imagem 1
2
3
4
5
6
- va ate o diretório e copie o arquivo "tomcat.udb". Veja que ele tem somente alguns kb de tamanho.
Cole este arquivo no caminho Config.REPOSITORY_DIR + "backup".

- Para executar o projeto, bastar entrar na raiz do projeto e executar o comando:
gradle run

Bug após bug, o script irá fazer o checkout no commit anterior (~1), tentar adicionar os arquivos do projeto, analysar com o understand, e gerar as metricas.

- O scrpit irá salvar o arquivo csv para cada bug analisado no caminho report/NOME_PROJETO.