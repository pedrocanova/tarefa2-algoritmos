import java.time.Duration; // Importa a classe para trabalhar com data e hora
import java.time.LocalDateTime;      // Importa a classe para calcular duração entre datas/horas

public class Documento {
    private String nomeArquivo;        // Nome do arquivo do documento
    private String nomeUsuario;        // Nome do usuário que solicitou a impressão/reimpressão
    private LocalDateTime horarioSolicitacao; // Horário em que o documento foi solicitado
    private LocalDateTime horarioProcessamento; // Horário em que o documento foi impresso/reimpresso

    public Documento(String nomeArquivo, String nomeUsuario) {
        this.nomeArquivo = nomeArquivo;
        this.nomeUsuario = nomeUsuario;
        this.horarioSolicitacao = LocalDateTime.now(); // Registra o horário atual
        this.horarioProcessamento = null; // Inicialmente, ainda não foi processado
    }

    // --- Métodos Getters ---
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public LocalDateTime getHorarioSolicitacao() {
        return horarioSolicitacao;
    }

    public LocalDateTime getHorarioProcessamento() {
        return horarioProcessamento;
    }

    // --- Métodos Setters ---

    public void setHorarioProcessamento(LocalDateTime horarioProcessamento) {
        this.horarioProcessamento = horarioProcessamento;
    }

    // --- Métodos de Cálculo ---

    public String calcularTempoDecorrido() {
        if (horarioProcessamento == null) {
            return "Documento ainda nao processado.";
        }
        Duration duracao = Duration.between(horarioSolicitacao, horarioProcessamento);
        long minutos = duracao.toMinutes();
        long segundosRestantes = duracao.getSeconds() % 60;
        return String.format("%d minuto(s) e %d segundo(s)", minutos, segundosRestantes);
    }

    @Override
    public String toString() {
        String processado = (horarioProcessamento != null) ?
                            "Processado em: " + horarioProcessamento.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                            " (Tempo: " + calcularTempoDecorrido() + ")" :
                            "Aguardando processamento...";

        return "Arquivo: '" + nomeArquivo + "' | Usuário: " + nomeUsuario +
               " | Solicitado em: " + horarioSolicitacao.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
               " | " + processado;
    }
}