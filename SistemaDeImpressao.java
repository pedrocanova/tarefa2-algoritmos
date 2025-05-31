import java.io.BufferedReader; // Para ler o arquivo linha por linha
import java.io.FileReader;    // Para abrir o arquivo
import java.io.IOException;   // Para lidar com erros de leitura/escrita de arquivo

/**
 * Classe principal que simula o sistema de gerenciamento de impressão.
 * Lê documentos de um arquivo de entrada e os processa na Fila de Impressão
 * ou na Pilha de Reimpressão.
 */
public class SistemaDeImpressao {

    public static void main(String[] args) {
        // Crie instâncias da sua Fila e Pilha
        // Defina capacidades, por exemplo, 5 para a fila e 3 para a pilha
        Fila filaImpressao = new Fila(5);
        Pilha pilhaReimpressao = new Pilha(3);

        String nomeArquivoEntrada = "documentos.txt"; // Nome do seu arquivo de entrada

        System.out.println("--- INICIANDO SIMULAÇÃO DO SISTEMA DE IMPRESSÃO ---");
        System.out.println("Lendo documentos do arquivo: " + nomeArquivoEntrada + "\n");

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoEntrada))) {
            String linha;
            int numeroLinha = 0;
            while ((linha = reader.readLine()) != null) {
                numeroLinha++;
                // Pula linhas vazias
                if (linha.trim().isEmpty()) {
                    continue;
                }

                System.out.println("Processando linha " + numeroLinha + ": " + linha);
                String[] partes = linha.split(","); // Divide a linha por vírgulas

                if (partes.length == 3) {
                    String tipoOperacao = partes[0].trim();
                    String nomeArquivo = partes[1].trim();
                    String nomeUsuario = partes[2].trim();

                    Documento novoDocumento = new Documento(nomeArquivo, nomeUsuario);

                    if (tipoOperacao.equalsIgnoreCase("IMPRIME")) {
                        try {
                            filaImpressao.enfileira(novoDocumento);
                            System.out.println("  -> Documento '" + nomeArquivo + "' enfileirado para impressão.");
                        } catch (RuntimeException e) {
                            System.out.println("  -> ERRO ao enfileirar '" + nomeArquivo + "': " + e.getMessage());
                        }
                    } else if (tipoOperacao.equalsIgnoreCase("REIMPRIME")) {
                        try {
                            pilhaReimpressao.empilha(novoDocumento);
                            System.out.println("  -> Documento '" + nomeArquivo + "' empilhado para reimpressão.");
                        } catch (RuntimeException e) {
                            System.out.println("  -> ERRO ao empilhar '" + nomeArquivo + "': " + e.getMessage());
                        }
                    } else {
                        System.out.println("  -> AVISO: Tipo de operação desconhecido na linha " + numeroLinha + ": " + tipoOperacao);
                    }
                } else {
                    System.out.println("  -> AVISO: Formato de linha inválido na linha " + numeroLinha + ". Ignorando: " + linha);
                }
                System.out.println(); // Pula uma linha para melhor visualização
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de entrada '" + nomeArquivoEntrada + "': " + e.getMessage());
            System.err.println("Certifique-se de que o arquivo 'documentos.txt' está no mesmo diretório do programa.");
        }

        System.out.println("\n--- ESTADO FINAL DAS ESTRUTURAS APÓS A LEITURA DO ARQUIVO ---");
        System.out.println("\n" + filaImpressao);
        System.out.println("\n" + pilhaReimpressao);

        // --- Exemplo de Operações Manuais e Consultas (opcional, para demonstrar) ---
        System.out.println("\n--- REALIZANDO ALGUMAS OPERAÇÕES DE TESTE ---");

        // Processar alguns documentos da fila
        if (!filaImpressao.filaVazia()) {
            System.out.println("\nProcessando documento da Fila: " + filaImpressao.desenfileira().getNomeArquivo());
            System.out.println(filaImpressao);
        }
        if (!pilhaReimpressao.pilhaVazia()) {
            System.out.println("\nProcessando documento da Pilha: " + pilhaReimpressao.desempilha().getNomeArquivo());
            System.out.println(pilhaReimpressao);
        }

        // Consultar um documento
        System.out.println("\nConsultando 'Fatura_Cliente.pdf' na fila:");
        System.out.println(filaImpressao.consultaDocumento("Fatura_Cliente.pdf"));

        System.out.println("\nConsultando 'Desenho_Grafico.psd' na pilha:");
        System.out.println(pilhaReimpressao.consultaDocumento("Desenho_Grafico.psd"));

        System.out.println("\n--- SIMULAÇÃO CONCLUÍDA ---");
    }
}