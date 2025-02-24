import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Medalhista {
    /** Para criar o vetor com no máximo 8 medalhas */
    private static final int MAX_MEDALHAS = 8;
    /** Nome do medalhista */
    private String name;
    /** Gênero do medalhista */
    private String gender;
    /** Data de nascimento do medalhista */
    private LocalDate birthDate;
    /** País do medalhista */
    private String country;
    /** Coleção de medalhas do medalhista */
    private Medalha[] medals;
    /** Contador de medalhas e índice para controlar o vetor de medalhas */
    private int medalCount;

    /**
     * Cria um medalhista olímpico. Nenhum dado precisa ser validado.
     * 
     * @param nome       Nome do medalhista no formato "SOBRENOME nome"
     * @param genero     Gênero do medalhista
     * @param nascimento Data de nascimento do medalhista
     * @param pais       País do medalhista (conforme dados originais, em inglês)
     */
    public Medalhista(String nome, String genero, LocalDate nascimento, String pais) {
        this.name = nome;
        this.gender = genero;
        this.birthDate = nascimento;
        this.country = pais;
        this.medals = new Medalha[MAX_MEDALHAS];
        this.medalCount = 0;
    }

    /**
     * Inclui uma medalha na coleção do medalhista. Retorna a quantidade atual de
     * medalhas do atleta.
     * 
     * @param medalha A medalha a ser armazenada.
     * @return A quantidade total de medalhas do atleta após a inclusão.
     */
    public int incluirMedalha(Medalha medalha) {
        if (medalCount < MAX_MEDALHAS) {
            medals[medalCount] = medalha;
            medalCount++;
        }
        return medalCount;
    }

    /**
     * Total de medalhas do atleta. É um número maior ou igual a 0.
     * 
     * @return Inteiro com o total de medalhas do atleta (>=0)
     */
    public int totalMedalhas() {
        return medalCount;
    }

    /**
     * Retorna um relatório das medalhas do atleta conforme o tipo solicitado pelo
     * parâmetro. Veja no enunciado da atividade o formato correto deste relatório.
     * Em caso de não possuir medalhas deste tipo, a resposta deve ser "Nao possui
     * medalha de TIPO".
     * 
     * @param tipo Tipo da medalha para o relatório
     * @return Uma string, multilinhas, com o relatório de medalhas daquele tipo.
     */
    public String relatorioDeMedalhas(TipoMedalha tipo) {
        StringBuilder relatorio = new StringBuilder();
        boolean possuiMedalha = false;

        for (int i = 0; i < medalCount; i++) {
            if (medals[i].getTipo() == tipo) {
                relatorio.append(medals[i].toString()).append("\n");
                possuiMedalha = true;
            }
        }

        if (!possuiMedalha) {
            return "Nao possui medalha de " + tipo;
        }

        return relatorio.toString().trim();
    }

    /**
     * Deve retornar os dados pessoais do medalhista, sem as medalhas, conforme
     * especificado no enunciado da atividade.
     * 
     * @return String de uma linha, com os dados do medalhista, sem dados da
     *         medalha.
     */
    @Override
    public String toString() {
        return name + " (" + gender + "), " + birthDate + ", " + country;
    }

    public String getNome() {
        return name;
    }

    public String getGenero() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getCountry() {
        return country;
    }
}

enum TipoMedalha {
    OURO, PRATA, BRONZE
}

/**
 * Representa uma medalha obtida nos Jogos Olímpicos de Paris em 2024.
 */
class Medalha {
    /** Tipo/cor da medalha conforme o enumerador */
    private TipoMedalha metalType;
    /** Data de obtenção da medalha */
    private LocalDate medalDate;
    /** Disciplina da medalha, conforme arquivo de dados */
    private String discipline;
    /** Evento da medalha, conforme arquivo de dados */
    private String event;

    /** Cria uma medalha com os dados do parâmetro. Nenhum dado é validado */
    public Medalha(TipoMedalha tipo, LocalDate data, String disciplina, String evento) {
        this.metalType = tipo;
        this.medalDate = data;
        this.discipline = disciplina;
        this.event = evento;
    }

    /**
     * Retorna o tipo de medalha, conforme o enumerador
     * 
     * @return TipoMedalha (enumerador) com o tipo/cor desta medalha
     */
    public TipoMedalha getTipo() {
        return metalType;
    }

    /**
     * Retorna uma string com o "relatório" da medalha de acordo com o especificado
     * no enunciado do problema.
     * Contém uma linha que já formata a data da medalha no formato brasileiro. O
     * restante deve ser implementado.
     */
    @Override
    public String toString() {
        String dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(medalDate); // formata a data em
                                                                                            // DD/MM/AAAA
        return metalType + " medalha, " + discipline + ", " + event + ", " + dataFormatada;
    }
}

public class SelectionSortApp {
    public static void main(String[] args) {
        Map<String, Medalhista> medalhistas = carregarMedalhistas("/tmp/medallists.csv");
        List<Medalhista> medalhistasList = new ArrayList<>(medalhistas.values());
        processarEntradaUsuario(medalhistasList);
    }

    private static Map<String, Medalhista> carregarMedalhistas(String caminhoArquivo) {
        Map<String, Medalhista> medalhistas = new HashMap<>();
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            leitor.readLine();
            while ((linha = leitor.readLine()) != null) {
                adicionarMedalhista(linha, medalhistas);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return medalhistas;
    }

    private static void adicionarMedalhista(String linha, Map<String, Medalhista> medalhistas) {
        String[] dados = linha.split(",");
        String nome = dados[0];
        TipoMedalha tipoMedalha = null;
        try {
            tipoMedalha = TipoMedalha.valueOf(dados[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de medalha inválido: " + dados[1]);
            return;
        }

        LocalDate dataMedalha = LocalDate.parse(dados[2]);
        String genero = dados[3];
        LocalDate nascimento = LocalDate.parse(dados[4]);
        String pais = dados[5];
        String disciplina = dados[6];
        String evento = dados[7];

        Medalhista medalhista = medalhistas.computeIfAbsent(nome, k -> new Medalhista(nome, genero, nascimento, pais));
        Medalha medalha = new Medalha(tipoMedalha, dataMedalha, disciplina, evento);
        medalhista.incluirMedalha(medalha);
    }

    private static void processarEntradaUsuario(List<Medalhista> medalhistas) {
        Scanner scanner = new Scanner(System.in);
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        List<Medalhista> medalhistasSelecionados = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            String nome = scanner.nextLine().trim();
            for (Medalhista medalhista : medalhistas) {
                if (medalhista.getNome().equalsIgnoreCase(nome)) {
                    medalhistasSelecionados.add(medalhista);
                    break;
                }
            }
        }

        ordenarMedalhistas(medalhistasSelecionados);

        for (Medalhista medalhista : medalhistasSelecionados) {
            exibirRelatorioMedalhista(medalhista);
        }

        scanner.close();
    }

    private static void ordenarMedalhistas(List<Medalhista> medalhistas) {
        SelectionSort<Medalhista> ordenator = new SelectionSort<>();
        Medalhista[] medalhistasArray = medalhistas.toArray(new Medalhista[0]);
        ordenator.ordenar(medalhistasArray);

        medalhistas.clear();
        for (Medalhista medalhista : medalhistasArray) {
            medalhistas.add(medalhista);
        }
    }

    private static void exibirRelatorioMedalhista(Medalhista medalhista) {
        System.out.println(medalhista.getNome() + ", " + medalhista.getGenero() + ". Nascimento: " +
                medalhista.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                ". Pais: " + medalhista.getCountry());

        System.out.println();
    }
}

class SelectionSort<T> implements IOrdenator<T> {
    @Override
    public void ordenar(T[] elementos) {
        for (int i = 0; i < elementos.length - 1; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < elementos.length; j++) {
                if (compare((Medalhista) elementos[j], (Medalhista) elementos[indiceMenor]) < 0) {
                    indiceMenor = j;
                }
            }
            if (indiceMenor != i) {
                T temp = elementos[i];
                elementos[i] = elementos[indiceMenor];
                elementos[indiceMenor] = temp;
            }
        }
    }

    private int compare(Medalhista m1, Medalhista m2) {
        int birthDateCompare = m1.getBirthDate().compareTo(m2.getBirthDate());
        if (birthDateCompare != 0)
            return birthDateCompare;

        return m1.getNome().compareToIgnoreCase(m2.getNome());
    }
}

interface IOrdenator<T> {
    void ordenar(T[] elementos);
}
