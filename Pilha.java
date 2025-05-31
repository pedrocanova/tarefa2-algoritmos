import java.time.LocalDateTime; // Necessário para a classe Documento (para registrar horários)
import java.time.format.DateTimeFormatter; // Para formatar a exibição da data/hora no toString do Documento

/**
 * Implementação de uma Pilha (LIFO - Last-In, First-Out) utilizando um array (vetor).
 * Esta pilha é projetada para armazenar objetos do tipo Documento.
 * Segue o modelo de array para implementação de estruturas de dados.
 */
public class Pilha {
    private Documento[] dados;      // Array para armazenar os documentos na pilha.
    private int topo;               // Índice que aponta para a próxima posição disponível no array.
                                    // Também representa o número atual de elementos na pilha.
    private int capacidadeMaxima;   // Define o tamanho máximo de documentos que a pilha pode conter.

    public Pilha(int capacidadeMaxima) {
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade maxima da pilha deve ser maior que zero.");
        }
        this.capacidadeMaxima = capacidadeMaxima;
        this.dados = new Documento[capacidadeMaxima]; // Cria o array com o tamanho especificado
        this.topo = 0; // Inicialmente, o topo está em 0, indicando que a pilha está vazia.
                       // O próximo elemento será inserido na posição 0.
    }

    /**
     * Construtor padrão da Pilha.
     * Se nenhuma capacidade for especificada, a pilha terá uma capacidade máxima padrão de 10.
     */
    public Pilha() {
        this(10); // Chama o construtor com capacidade definida, usando 10 como valor padrão.
    }

    public boolean pilhaVazia() {
        return topo == 0;
    }

    public boolean pilhaCheia() {
        return topo == capacidadeMaxima;
    }

    public int getTamanhoAtual() {
        return topo; // O valor de 'topo' diretamente indica a quantidade de elementos.
    }

    public void empilha(Documento e) {
        if (pilhaCheia()) {
            throw new RuntimeException("Falha na insercao: Pilha de reimpressao cheia. Capacidade maxima atingida.");
        }
        dados[topo] = e; // Insere o documento na posição indicada por 'topo'
        topo++;          // Incrementa 'topo' para apontar para a próxima posição livre
    }

    public Documento desempilha() {
        if (pilhaVazia()) {
            throw new RuntimeException("Falha na remocao: Pilha de reimpressao vazia.");
        }
        topo--; // Primeiro, decrementa 'topo' para apontar para o último elemento válido
        Documento temp = dados[topo]; // Pega o documento que está no (novo) topo
        dados[topo] = null; // Opcional, mas recomendado: Limpa a referência no array para GC
        temp.setHorarioProcessamento(LocalDateTime.now()); // Registra o horário da reimpressão
        return temp;
    }

    public String consultaDocumento(String nomeArquivo) {
        if (pilhaVazia()) {
            return "Pilha de reimpressao vazia. Documento '" + nomeArquivo + "' nao encontrado.";
        }

        for (int i = topo - 1; i >= 0; i--) {
            if (dados[i] != null && dados[i].getNomeArquivo().equalsIgnoreCase(nomeArquivo)) {
                int posicaoDoTopo = topo - i;

                return String.format(
                    "Documento '%s' encontrado na pilha. Posicao a partir do topo: %d. Solicitado em: %s",
                    nomeArquivo,
                    posicaoDoTopo,
                    // Garante que o formatador de data/hora seja usado
                    dados[i].getHorarioSolicitacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                );
            }
        }
        return "Documento '" + nomeArquivo + "' nao encontrado na pilha de reimpressao.";
    }

    // --- 5. Implementação do toString() ---

    @Override
    public String toString() {
        if (pilhaVazia()) {
            return "Pilha de reimpressao vazia.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Pilha de Reimpressao (Total: ").append(topo).append("/").append(capacidadeMaxima).append(") ---\n");

        // Percorre a pilha do topo para a base, exibindo cada documento
        // O elemento no topo está em dados[topo - 1]
        for (int i = topo - 1; i >= 0; i--) {
            // Calcula a posição a partir do topo (1 para o topo, 2 para o abaixo, etc.)
            int posicaoDoTopo = topo - i;
            sb.append("[").append(posicaoDoTopo).append(" - Topo] ");
            // Adiciona as informações do documento (apenas nome do arquivo e usuário para concisão)
            sb.append(dados[i].getNomeArquivo()).append(" (Usuário: ").append(dados[i].getNomeUsuario()).append(")\n");
        }
        sb.append("----------------------------------------------------");
        return sb.toString();
    }
}