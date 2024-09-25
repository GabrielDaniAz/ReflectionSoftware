# Reflection Software - Sistema de Correção Automática de Provas Práticas de POO

Este projeto é um sistema de correção automática para provas práticas de Programação Orientada a Objetos (POO) em Java. O sistema utiliza técnicas de reflexão para inspecionar e avaliar o código enviado pelos alunos, gerando relatórios detalhados sobre a correção, incluindo resultados de compilação, análise de métodos, atributos, construtores, e outros elementos da POO.

## Visão Geral

O software foi projetado para automatizar o processo de correção de provas de POO. Ele realiza as seguintes tarefas:

1. **Carregamento dos Arquivos de Alunos**: O sistema navega pela estrutura de diretórios especificada, busca arquivos `.java` e os organiza por aluno.
2. **Compilação do Código**: Verifica se o código de cada aluno compila corretamente e armazena os resultados.
3. **Análise com Reflexão**: Usa a API de reflexão para inspecionar a estrutura das classes, verificando a presença de métodos, atributos, e classes específicas, conforme as especificações.
4. **Geração de Relatórios**: Ao final, um relatório detalhado (futuramente em PDF) é gerado para cada aluno, contendo informações sobre a compilação e análise de reflexão.


### Fluxo de Execução

1. **Início no Main**:
   - O `Main.java` inicia o processo chamando o método `CorrectionController.processCorrections()`, passando o diretório que contém os arquivos dos alunos.

2. **FileController**:
   - O `FileController` navega pelo diretório e coleta os arquivos `.java`, organizando-os em um `HashMap` que associa cada aluno aos seus arquivos.

3. **StudentFactory**:
   - Para cada conjunto de arquivos, a `StudentFactory` cria um objeto `Student`, representando cada aluno.

4. **CorrectionService**:
   - O `CorrectionService` realiza a correção de cada aluno, chamando o `CompilationService` para compilar os arquivos e o `ReflectionService` para analisar a estrutura do código.

5. **ReportService e ReportManager**:
   - Ao final do processo, os resultados são agregados em um relatório por meio do `ReportService`. O `ReportManager` então futuramente gerará os arquivos PDF e os organizará na pasta especificada.

### Exemplo de Uso

1. **Configuração do Caminho da Pasta**:
   No arquivo `Constants.java`, configure o caminho da pasta raiz onde estão armazenados os códigos dos alunos. Por exemplo:
   
   ```java
   public class Constants {
       public static final String ROOT_DIRECTORY = "C:/Users/gabri/Dev/ProvasAlunos";
   }


