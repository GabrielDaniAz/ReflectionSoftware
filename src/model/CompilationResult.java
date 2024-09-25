package model;

import java.io.File;
import java.util.List;

// Classe para armazenar o resultado da compilação de um arquivo .java.
public class CompilationResult {
    
    private File file;
    private final boolean success;
    private final List<String> messages;

    public CompilationResult(File file, boolean success, List<String> messages){
        this.file = file;
        this.success = success;
        this.messages = messages;
    }

    public File getFile(){
        return file;
    }

    public boolean isSuccess(){
        return success;
    }

    public List<String> getMessages() {
        return messages;
    }

    /**
     * Gera detalhes completos sobre a compilação para o relatório.
     * 
     * @return String com os detalhes da compilação.
     */
    public String generateDetails() {
        StringBuilder details = new StringBuilder();
        details.append("\t- Status: ").append(isSuccess() ? "Sucesso" : "Falhou").append("\n");
        details.append("\t- Mensagens: ").append("\n");
        if (messages.size() == 0) {
            details.append("\t\t> Nenhum erro de compilação\n");
        }
        
        for (String message : messages) {
            details.append("\t\t> ").append(message).append("\n");
        }
        
        return details.toString();
    }
}
