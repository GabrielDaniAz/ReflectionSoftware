# Reflection Software - Sistema de Correção Automática de Exercícios Práticos de POO

O **Reflection Software** é uma solução projetada para automatizar a correção de exercícios práticos de Programação Orientada a Objetos (POO) em Java. Utilizando técnicas avançadas de reflexão, o sistema analisa o código enviado pelos alunos e gera relatórios detalhados com informações sobre compilação, estrutura das classes, métodos, atributos e construtores, além de atribuir notas automaticamente com base nas especificações do gabarito.

---

## Visão Geral

O software automatiza o processo de avaliação de exercícios práticos de POO. As principais funcionalidades incluem:

1. **Carregamento de Arquivos**:
   - Navega pelas pastas especificadas para encontrar os arquivos `.java` dos alunos e os arquivos de gabarito.
   - Descompacta automaticamente os arquivos enviados, caso estejam compactados.

2. **Compilação do Código**:
   - Verifica se o código dos alunos compila corretamente e registra os resultados.

3. **Análise de Estruturas**:
   - Utiliza a API de reflexão para comparar o código dos alunos com os gabaritos.
   - Avalia atributos, métodos e construtores com base nas especificações do gabarito.

4. **Atribuição de Notas**:
   - Usa a anotação `@Nota` do pacote `com.gabrieldani` para atribuir pontuações específicas a cada elemento (atributos, métodos e construtores).
   - Usa a anotação `@Especificacao` do pacote `com.gabrieldani` para atribuir a similaridade dos nomes e se os atributos devem ser privados.

5. **Geração de Relatórios em PDF**:
   - Gera relatórios detalhados para cada aluno, contendo informações sobre a compilação e os erros encontrados, além de incluir o nome do aluno no cabeçalho do PDF.

---

## Preparando o Ambiente

### 1. Pasta de Gabarito
- **Estrutura**:
  - A pasta raiz do gabarito deve ter o nome geral do exercício.
  - Dentro da pasta raiz, crie subpastas para cada etapa ou exercício (os nomes das subpastas devem estar organizados em ordem alfanumérica).
  - Cada subpasta deve conter os arquivos `.java` que servirão como gabarito.

- **Anotação `@Nota`**:
  - Utilize a biblioteca `com.gabrieldani` para atribuir notas aos elementos do gabarito:
    ```java
    @Nota(valor = 3)
    public void exemploMetodo() {
        // Exemplo de método anotado com nota
    }
    ```

- **Anotação `Especificacao`**:
   - Utilize a biblioteca `com.gabrieldani` para definir atributos e similaridade dos nomes:
   ```java
   @Especificacao(atributos = false, construtores = true, similaridade = 0.7) // construtores está presente mas penso em retirar.
   public class Classe {
      //Exemplo de classe anotado com Especificação
   }
   ```

- **Baixando a Biblioteca**:
  - Faça o download da biblioteca `com.gabrieldani` no seguinte link: [ReflectionLib v1.0.1](https://github.com/GabrielDaniAz/ReflectionLib/releases/tag/v1.0.1).

### 2. Pasta de Códigos dos Alunos
- **Estrutura**:
  - A pasta raiz deve conter apenas subpastas com o nome de cada aluno.
  - Dentro de cada subpasta, devem estar os arquivos `.java` enviados pelo aluno.

- **Arquivos Compactados**:
  - Caso os arquivos sejam enviados compactados, o software descompacta automaticamente e organiza a estrutura conforme necessário.

### 3. Diretório de Saída dos Relatórios
- Informe o diretório onde deseja salvar os PDFs gerados. Caso o diretório não exista, ele será criado automaticamente.

---

## Executando o Software

Para executar o software, utilize o comando:

```bash
mvn exec:java -Darg1="<Caminho da Pasta do Gabarito>" -Darg2="<Caminho da Pasta dos Alunos>" -Darg3="<Caminho do Diretório para os PDFs>" -Darg4="<Nome da Etapa a Ser Corrigida>"
```

---

## Exemplos de Execução

```bash
mvn exec:java -Darg1="C:/Provas/Gabarito" -Darg2="C:/Provas/Alunos" -Darg3="C:/Provas/Relatorios" -Darg4="1"
```

# Argumentos:

- Caminho do Gabarito: Diretório onde está armazenada a pasta raiz do gabarito.
- Caminho dos Alunos: Diretório contendo os códigos dos alunos (podendo estar compactados).
- Caminho para os Relatórios: Diretório onde os PDFs das correções serão salvos.
- Nome do Exercício: Nome de uma das subpastas da pasta de gabarito que indica até onde a correção será realizada.

--- 

## Funcionamento do Sistema

1. O sistema percorre as pastas configuradas, descompacta os arquivos enviados pelos alunos (se necessário) e organiza a estrutura conforme especificado.
2. Compila os arquivos `.java` de cada aluno e registra o sucesso ou falhas de compilação.
3. Compara as classes dos alunos com os gabaritos utilizando a API de reflexão:
   - Verifica métodos, atributos e construtores presentes.
   - Avalia cada elemento com base na anotação `@Nota` do gabarito.
4. Gera relatórios detalhados em PDF para cada aluno, destacando:
   - Resultados da compilação.
   - Elementos ausentes ou incorretos.
   - Pontuações atribuídas a cada etapa.

---

## Considerações Importantes

- A ordem alfanumérica das subpastas do gabarito é utilizada pelo sistema para organizar as etapas. Certifique-se de nomear as subpastas corretamente.
- Caso algum aluno não possua arquivos `.java` correspondentes à etapa especificada, o relatório gerado indicará as ausências.
- O software é compatível com o **JDK 22**. Certifique-se de ter o JDK instalado no ambiente.

---

## Tecnologias Utilizadas

- **Java**: Linguagem principal do projeto.
- **Reflection API**: Para análise das classes e comparação com o gabarito.
- **Apache Maven**: Gerenciador de dependências e execução do projeto.
- **iText**: Biblioteca para geração de relatórios em PDF.




