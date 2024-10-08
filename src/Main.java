import controller.AppController;
// import org.json.JSONObject;

public class Main {
    private static AppController app;
    // "C:/Users/gabri/Dev/ProvasAlunos"

    public static void main(String[] args) throws Exception {
        // Verifica se foi passado um argumento de diretório via linha de comando
        if (args.length < 2) {
            throw new IllegalArgumentException("O diretório de correção e o JSON devem ser fornecidos como argumentos.");
        }

        // Instalar a biblioteca Gson
        String rootDirectory = args[0];  // Usa o primeiro argumento como diretório
        // String jsonConfig = args[1]; // Usa o segundo argumento como o JSON

        // JSONObject jsonObject = new JSONObject(jsonConfig);

        // Inicializa o AppController com o diretório
        app = new AppController(rootDirectory);
        app.start();
    }
}
