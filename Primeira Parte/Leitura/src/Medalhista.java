import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Medalhista {
    private static final int MAX_MEDALHAS = 8;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String country;
    private Medalha[] medals;
    private int medalCount;

    public Medalhista(String nome, String genero, LocalDate nascimento, String pais) {
        this.name = nome;
        this.gender = genero;
        this.birthDate = nascimento;
        this.country = pais;
        this.medals = new Medalha[MAX_MEDALHAS];
        this.medalCount = 0;
    }

    public int incluirMedalha(Medalha medalha) {
        if (medalCount < MAX_MEDALHAS) {
            medals[medalCount] = medalha;
            medalCount++;
        }
        return medalCount;
    }

    public int totalMedalhas() {
        return medalCount;
    }

    public String relatorioDeMedalhas(TipoMedalha tipo) {
        StringBuilder relatorio = new StringBuilder();

        relatorio.append(this.toString());

        boolean possuiMedalha = false;

        for (int i = 0; i < medalCount; i++) {
            if (medals[i].getTipo() == tipo) {
                relatorio.append("\n").append(medals[i].getTipo()).append(" - ")
                        .append(medals[i].getDiscipline()).append(" - ")
                        .append(medals[i].getEvent()).append(" - ")
                        .append(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(medals[i].getMedalDate()));
                possuiMedalha = true;
                break;
            }
        }

        if (!possuiMedalha) {
            relatorio.append("\nNao possui medalha de ").append(tipo);
        }

        return relatorio.toString().trim();
    }

    public String getPais() {
        return country;
    }

    public LocalDate getNascimento() {
        return LocalDate.from(birthDate);
    }

    @Override
    public String toString() {
        String dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(birthDate);
        return String.format("%s, %s. Nascimento: %s. Pais: %s", name, gender.toUpperCase(), dataFormatada, country);
    }
}
