import java.time.LocalDateTime; // Necessário para registrar o horário de processamento

/**
 * Implementação de uma Fila (FIFO) utilizando uma lista encadeada.
 * Esta fila armazena objetos do tipo Documento.
 */
public class Fila {
    private No primeiro; // Referência para o primeiro nó da fila (início)
    private No ultimo;   // Referência para o último nó da fila (fim)
    private int capacidadeMaxima; // Capacidade máxima de documentos na fila
    private int tamanhoAtual;     // Número atual de documentos na fila

    /**
     * Construtor da Fila com uma capacidade máxima definida.
     *
     * @param capacidadeMaxima A capacidade máxima de documentos que a fila pode conter.
     */
    public Fila(int capacidadeMaxima) {
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade máxima da fila deve ser maior que zero.");
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

    /**
     * Verifica se a fila está vazia.
     * @return true se a fila não contém documentos, false caso contrário.
     */
    public boolean filaVazia() {
        return primeiro == null;
    }

    /**
     * Verifica se a fila atingiu sua capacidade máxima.
     * @return true se a fila está cheia, false caso contrário.
     */
    public boolean filaCheia() {
        return tamanhoAtual == capacidadeMaxima;
    }

    /**
     * Retorna o número atual de documentos na fila.
     * @return O número de documentos.
     */
    public int getTamanhoAtual() {
        return tamanhoAtual;
    }

    /**
     * Adiciona um documento ao final da fila.
     *
     * @param e O objeto Documento a ser adicionado.
     * @throws RuntimeException Se a fila estiver cheia.
     */
    public void enfileira(Documento e) {
        if (filaCheia()) {
            throw new RuntimeException("Falha na inserção: Fila de impressão cheia. Capacidade máxima atingida.");
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

    /**
     * Remove e retorna o primeiro documento da fila.
     * Registra o horário de processamento no documento removido.
     *
     * @return O primeiro objeto Documento da fila.
     * @throws RuntimeException Se a fila estiver vazia.
     */
    public Documento desenfileira() {
        if (filaVazia()) {
            throw new RuntimeException("Falha na remoção: Fila de impressão vazia.");
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

    /**
     * Consulta a posição de um documento na fila pelo nome do arquivo.
     *
     * @param nomeArquivo O nome do arquivo a ser consultado.
     * @return Uma String com a posição e o horário de solicitação do documento,
     * ou uma mensagem indicando que o documento não foi encontrado.
     */
    public String consultaDocumento(String nomeArquivo) {
        if (filaVazia()) {
            return "Fila de impressão vazia. Documento '" + nomeArquivo + "' não encontrado.";
        }

        No runner = primeiro; // Inicia do primeiro nó
        int posicao = 1;      // Posição na fila (começa em 1 para ser mais intuitivo)

        while (runner != null) {
            if (runner.getInfo().getNomeArquivo().equalsIgnoreCase(nomeArquivo)) {
                // Encontrou o documento
                return String.format(
                    "Documento '%s' encontrado na posição %d. Solicitado em: %s",
                    nomeArquivo,
                    posicao,
                    runner.getInfo().getHorarioSolicitacao().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                );
            }
            runner = runner.getProximo(); // Avança para o próximo nó
            posicao++;                    // Incrementa a posição
        }
        // Se o loop terminou e o documento não foi encontrado
        return "Documento '" + nomeArquivo + "' não encontrado na fila de impressão.";
    }


    /**
     * Retorna uma representação em String do conteúdo da fila.
     * @return Uma String listando os documentos na fila, ou "fila vazia".
     */
    @Override
    public String toString() {
        if (filaVazia()) {
            return "Fila de impressão vazia.";
        }
        String s = "--- Fila de Impressão (Total: " + tamanhoAtual + "/" + capacidadeMaxima + ") ---\n";
        No runner = primeiro; // Começa do primeiro nó
        int i = 1;
        while (runner != null) {
            s += "[" + i + "] " + runner.getInfo().getNomeArquivo() + " (Usuário: " + runner.getInfo().getNomeUsuario() + ")\n";
            runner = runner.getProximo();
            i++;
        }
        s += "----------------------------------------------------";
        return s;
    }
}
/**
 * Representa um nó (elemento) na lista encadeada que forma a Fila ou a Pilha.
 * Cada nó armazena um objeto Documento e uma referência para o próximo nó.
 */
class No {
    private Documento info; // Agora o 'info' armazena um objeto Documento
    private No proximo;

    /**
     * Construtor para criar um novo nó.
     *
     * @param info O objeto Documento a ser armazenado neste nó.
     */
    public No(Documento info) {
        this.info = info;
        this.proximo = null; // Por padrão, o próximo nó é nulo ao ser criado
    }

    // --- Métodos Getters ---

    /**
     * Retorna o objeto Documento armazenado neste nó.
     * @return O objeto Documento.
     */
    public Documento getInfo() {
        return info;
    }

    /**
     * Retorna a referência para o próximo nó na lista encadeada.
     * @return O próximo nó.
     */
    public No getProximo() {
        return proximo;
    }

    // --- Métodos Setters ---

    /**
     * Define a referência para o próximo nó.
     * @param proximo A referência para o próximo nó.
     */
    public void setProximo(No proximo) {
        this.proximo = proximo;
    }

    /**
     * Define o objeto Documento a ser armazenado neste nó.
     * @param info O objeto Documento.
     */
    public void setInfo(Documento info) {
        this.info = info;
    }

    /**
     * Retorna uma representação em String do objeto No, usando o toString do Documento.
     * @return Uma String com os detalhes do documento armazenado no nó.
     */
    @Override
    public String toString() {
        // Delega a representação em String para o método toString() do Documento
        return info.toString();
    }
}
