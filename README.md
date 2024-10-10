# Reflection Software - Sistema de Correção Automática de Exercícios Práticos de POO

Este projeto é um sistema de correção automática para exercícios práticos de Programação Orientada a Objetos (POO) em Java. O sistema utiliza técnicas de reflexão para inspecionar e avaliar o código enviado pelos alunos, gerando relatórios detalhados sobre a correção, incluindo resultados de compilação, análise de métodos, atributos, construtores, e outros elementos da POO.

## Visão Geral

O software foi projetado para automatizar o processo de correção de exercícios de POO. Ele realiza as seguintes tarefas:

1. **Carregamento dos Arquivos de Alunos**: O sistema navega pela estrutura de diretórios especificada, busca arquivos `.java` e os organiza por aluno.
2. **Compilação do Código**: Verifica se o código de cada aluno compila corretamente e armazena os resultados.
3. **Análise com Reflexão**: Usa a API de reflexão para inspecionar a estrutura das classes, verificando a presença de métodos, atributos, e classes específicas, conforme as especificações.
4. **Geração de Relatórios**: Ao final, um relatório detalhado (futuramente em PDF) é gerado para cada aluno, contendo informações sobre a compilação e análise de reflexão.

### Exemplo de Uso

1. **Execução do Programa**:
   Para executar o programa, utilize o comando:
   
   ```java
   mvn clean install
   mvn exec:java


