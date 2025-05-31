// Exemplo de como usar (não faz parte das classes que você vai entregar)
public class TesteFilaImpressao {
    public static void main(String[] args) {
        Fila filaImpressao = new Fila(3); // Fila com capacidade para 3 documentos

        System.out.println("--- Testando a Fila de Impressão ---");

        // Enfileirando documentos
        filaImpressao.enfileira(new Documento("Orcamento.pdf", "Ana"));
        filaImpressao.enfileira(new Documento("Relatorio_Q1.docx", "Bruno"));
        System.out.println(filaImpressao); // Mostra a fila atual

        System.out.println("\nConsultando 'Orcamento.pdf':");
        System.out.println(filaImpressao.consultaDocumento("Orcamento.pdf"));

        System.out.println("\nEnfileirando mais um documento (atingindo capacidade):");
        filaImpressao.enfileira(new Documento("Contrato.txt", "Carla"));
        System.out.println(filaImpressao);

        System.out.println("\nTentando enfileirar mais um (deve dar erro):");
        try {
            filaImpressao.enfileira(new Documento("Novo_Projeto.pptx", "Daniel"));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nDesenfileirando o primeiro documento (Orcamento.pdf):");
        Documento docImpresso1 = filaImpressao.desenfileira();
        System.out.println("Documento impresso: " + docImpresso1); // Deve mostrar o tempo de espera
        System.out.println(filaImpressao); // Mostra a fila após a impressão

        System.out.println("\nDesenfileirando o proximo (Relatorio_Q1.docx):");
        Documento docImpresso2 = filaImpressao.desenfileira();
        System.out.println("Documento impresso: " + docImpresso2);
        System.out.println(filaImpressao);

        System.out.println("\nConsultando 'Contrato.txt':");
        System.out.println(filaImpressao.consultaDocumento("Contrato.txt"));

        System.out.println("\nDesenfileirando o ultimo (Contrato.txt):");
        Documento docImpresso3 = filaImpressao.desenfileira();
        System.out.println("Documento impresso: " + docImpresso3);
        System.out.println(filaImpressao); // Fila deve estar vazia

        System.out.println("\nTentando desenfileirar de uma fila vazia (deve dar erro):");
        try {
            filaImpressao.desenfileira();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}