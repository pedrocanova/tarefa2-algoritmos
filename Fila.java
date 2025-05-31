import java.time.LocalDateTime; // Necessário para registrar o horário de processamento

public class Fila {
    private No primeiro; // Referência para o primeiro nó da fila (início)
    private No ultimo;   // Referência para o último nó da fila (fim)
    private int capacidadeMaxima; // Capacidade máxima de documentos na fila
    private int tamanhoAtual;     // Número atual de documentos na fila

    public Fila(int capacidadeMaxima) {
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade maxima da fila deve ser maior que zero.");
        }
        this.primeiro = null;
        this.ultimo = null;
        this.capacidadeMaxima = capacidadeMaxima;
        this.tamanhoAtual = 0; // Inicialmente, a fila está vazia
    }

    /**
     * Construtor padrão da Fila com capacidade máxima de 10, se não especificado.
     */
    public Fila() {
        this(10); // Chama o construtor com capacidade definida
    }


    public boolean filaVazia() {
        return primeiro == null;
    }


    public boolean filaCheia() {
        return tamanhoAtual == capacidadeMaxima;
    }

    public int getTamanhoAtual() {
        return tamanhoAtual;
    }

    public void enfileira(Documento e) {
        if (filaCheia()) {
            throw new RuntimeException("Falha na insercao: Fila de impressao cheia. Capacidade maxima atingida.");
        }

        No novo = new No(e); // Cria um novo nó com o Documento

        if (filaVazia()) {
            primeiro = novo;
        } else {
            ultimo.setProximo(novo); // O antigo último aponta para o novo
        }
        ultimo = novo; // O novo nó se torna o último
        tamanhoAtual++; // Incrementa a contagem de documentos
    }

    public Documento desenfileira() {
        if (filaVazia()) {
            throw new RuntimeException("Falha na remocao: Fila de impressao vazia.");
        }

        Documento temp = primeiro.getInfo(); // Pega o documento do primeiro nó
        temp.setHorarioProcessamento(LocalDateTime.now()); // Registra o horário da impressão!

        primeiro = primeiro.getProximo(); // Move o 'primeiro' para o próximo nó
        tamanhoAtual--; // Decrementa a contagem de documentos

        if (primeiro == null) { // Se a fila esvaziou após a remoção
            ultimo = null;      // Remove a referência do 'ultimo' também
        }
        return temp;
    }

    public String consultaDocumento(String nomeArquivo) {
        if (filaVazia()) {
            return "Fila de impressao vazia. Documento '" + nomeArquivo + "' nao encontrado.";
        }

        No runner = primeiro; // Inicia do primeiro nó
        int posicao = 1;      // Posição na fila (começa em 1 para ser mais intuitivo)

        while (runner != null) {
            if (runner.getInfo().getNomeArquivo().equalsIgnoreCase(nomeArquivo)) {
                // Encontrou o documento
                return String.format(
                    "Documento '%s' encontrado na posicao %d. Solicitado em: %s",
                    nomeArquivo,
                    posicao,
                    runner.getInfo().getHorarioSolicitacao().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                );
            }
            runner = runner.getProximo(); // Avança para o próximo nó
            posicao++;                    // Incrementa a posição
        }
        // Se o loop terminou e o documento não foi encontrado
        return "Documento '" + nomeArquivo + "' nao encontrado na fila de impressao.";
    }


    @Override
    public String toString() {
        if (filaVazia()) {
            return "Fila de impressao vazia.";
        }
        String s = "--- Fila de Impressao (Total: " + tamanhoAtual + "/" + capacidadeMaxima + ") ---\n";
        No runner = primeiro; // Começa do primeiro nó
        int i = 1;
        while (runner != null) {
            s += "[" + i + "] " + runner.getInfo().getNomeArquivo() + " (Usuario: " + runner.getInfo().getNomeUsuario() + ")\n";
            runner = runner.getProximo();
            i++;
        }
        s += "----------------------------------------------------";
        return s;
    }
}

class No {
    private Documento info; // Agora o 'info' armazena um objeto Documento
    private No proximo;

    public No(Documento info) {
        this.info = info;
        this.proximo = null; // Por padrão, o próximo nó é nulo ao ser criado
    }

    // --- Métodos Getters ---

    public Documento getInfo() {
        return info;
    }

    public No getProximo() {
        return proximo;
    }

    // --- Métodos Setters ---

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }

    public void setInfo(Documento info) {
        this.info = info;
    }

    @Override
    public String toString() {
        // Delega a representação em String para o método toString() do Documento
        return info.toString();
    }
}
