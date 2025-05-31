
/**
 * Classe para testar a implementação da Pilha de Reimpressão Emergencial.
 * Simula as operações de empilhar, desempilhar, consultar e verificar o estado da pilha.
 */
public class TestePilhaReimpressao {

    public static void main(String[] args) {
        // 1. Criar uma instância da sua Pilha com uma capacidade definida
        System.out.println("--- INICIANDO TESTE DA PILHA DE REIMPRESSAO ---");
        Pilha pilhaEmergencial = new Pilha(3); // Pilha com capacidade para 3 documentos

        System.out.println("\nEstado inicial da pilha:");
        System.out.println(pilhaEmergencial); // Deve mostrar "Pilha de reimpressão vazia."

        // 2. Testar empilhar documentos
        System.out.println("\n--- Teste: Empilhando documentos ---");
        Documento doc1 = new Documento("Relatorio_Falho.pdf", "Gerente");
        pilhaEmergencial.empilha(doc1);
        System.out.println("Empilhado: " + doc1.getNomeArquivo());
        System.out.println(pilhaEmergencial);

        Documento doc2 = new Documento("Banner_Erros.jpg", "Designer");
        pilhaEmergencial.empilha(doc2);
        System.out.println("Empilhado: " + doc2.getNomeArquivo());
        System.out.println(pilhaEmergencial);

        Documento doc3 = new Documento("Flyer_Manchado.psd", "Marketing");
        pilhaEmergencial.empilha(doc3);
        System.out.println("Empilhado: " + doc3.getNomeArquivo());
        System.out.println(pilhaEmergencial);

        // 3. Testar empilhar em pilha cheia (deve lançar RuntimeException)
        System.out.println("\n--- Teste: Empilhar em pilha cheia ---");
        Documento doc4 = new Documento("Cartao_Impressao.ai", "Vendas");
        try {
            pilhaEmergencial.empilha(doc4);
            System.out.println("ERRO: Deveria ter lançado excecao de pilha cheia!");
        } catch (RuntimeException e) {
            System.out.println("Sucesso! Excecao capturada: " + e.getMessage());
        }
        System.out.println(pilhaEmergencial); // A pilha não deve ter mudado

        // 4. Testar consulta de documento
        System.out.println("\n--- Teste: Consulta de documentos ---");
        System.out.println(pilhaEmergencial.consultaDocumento("Banner_Erros.jpg"));
        System.out.println(pilhaEmergencial.consultaDocumento("Flyer_Manchado.psd"));
        System.out.println(pilhaEmergencial.consultaDocumento("Documento_Inexistente.pdf")); // Documento que não está na pilha

        // 5. Testar desempilhar documentos (LIFO)
        System.out.println("\n--- Teste: Desempilhando documentos (LIFO) ---");
        Documento docRemovido1 = pilhaEmergencial.desempilha();
        System.out.println("Desempilhado (topo): " + docRemovido1.getNomeArquivo());
        System.out.println("Detalhes do documento reimpresso: " + docRemovido1); // Verifica tempo decorrido
        System.out.println(pilhaEmergencial);

        Documento docRemovido2 = pilhaEmergencial.desempilha();
        System.out.println("Desempilhado (novo topo): " + docRemovido2.getNomeArquivo());
        System.out.println("Detalhes do documento reimpresso: " + docRemovido2);
        System.out.println(pilhaEmergencial);

        // 6. Testar desempilhar o último documento
        System.out.println("\n--- Teste: Desempilhar o ultimo documento ---");
        Documento docRemovido3 = pilhaEmergencial.desempilha();
        System.out.println("Desempilhado (último): " + docRemovido3.getNomeArquivo());
        System.out.println("Detalhes do documento reimpresso: " + docRemovido3);
        System.out.println(pilhaEmergencial); // Deve mostrar "Pilha de reimpressão vazia."

        // 7. Testar desempilhar de pilha vazia (deve lançar RuntimeException)
        System.out.println("\n--- Teste: Desempilhar de pilha vazia ---");
        try {
            pilhaEmergencial.desempilha();
            System.out.println("ERRO: Deveria ter lancado excecao de pilha vazia!");
        } catch (RuntimeException e) {
            System.out.println("Sucesso! Excecao capturada: " + e.getMessage());
        }

        System.out.println("\n--- FIM DO TESTE DA PILHA DE REIMPRESSAO ---");
    }
}