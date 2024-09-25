/*
1. FileController solicita ao usuário a pasta que contém as provas.
2. FileManager coleta os arquivos `.java` dentro das subpastas de alunos.
3. FileController organiza os arquivos em um `HashMap<String, List<File>>`, onde a chave é o nome do aluno e a lista contém os arquivos `.java`.
4. StudentFactory cria objetos `Student` para cada aluno, associando a lista de arquivos ao aluno correspondente.
5. CorrectionController orquestra o processo de correção:
   5.1. Para cada aluno, CorrectionService inicia a correção de seus arquivos `.java`.
   5.2. CompilationService compila os arquivos, gerando um objeto `CompilationResult` que captura o status da compilação e possíveis erros.
   5.3. ReflectionService inspeciona os arquivos compilados, utilizando reflexão para verificar a estrutura POO (classes, métodos, atributos, construtores) e gera um objeto `ReflectionResult`.
   5.4. CorrectionService coleta os resultados de compilação e reflexão e encapsula em um objeto `FileResult`, que é adicionado ao `Report` do aluno.
6. ReportManager gerencia a criação dos relatórios de cada aluno.
   6.1. Report armazena os resultados de compilação e reflexão para cada aluno.
7. ReportService organiza o relatório, e PDFGenerator gera um PDF para cada aluno na pasta `Correções`, dentro da pasta raiz definida por `Constants`.
8. ReportController exibe e organiza o relatório final ao professor, com base nos relatórios gerados de cada aluno.
*/


import util.Constants;
import controller.CorrectionController;

public class Main {

    public static void main(String[] args) throws Exception {
        CorrectionController.processCorrections(Constants.ROOT_DIRECTORY);
    }
}
