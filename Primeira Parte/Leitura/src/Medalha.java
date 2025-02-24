import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Medalha {
    private TipoMedalha metalType;
    private LocalDate medalDate;
    private String discipline;
    private String event;

    public Medalha(TipoMedalha tipo, LocalDate data, String disciplina, String evento) {
        this.metalType = tipo;
        this.medalDate = data;
        this.discipline = disciplina;
        this.event = evento;
    }

    public TipoMedalha getTipo() {
        return metalType;
    }

    public LocalDate getMedalDate() {
        return medalDate;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getEvent() {
        return event;
    }

    @Override
    public String toString() {
        String dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(medalDate);
        return metalType + " medalha, " + discipline + ", " + event + ", " + dataFormatada;
    }
}
