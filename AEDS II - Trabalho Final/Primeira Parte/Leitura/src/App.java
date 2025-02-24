import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Map<String, Medalhista> athletes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\arthu\\OneDrive\\Desktop\\All\\my\\software_engineering\\Atividades\\3° Periodo - 2024\\AEDS II - Medallists\\Primeira Parte\\Leitura\\src\\tmp\\medallists.csv"));
             Scanner scanner = new Scanner(System.in)) {

            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                TipoMedalha medalType = TipoMedalha.valueOf(data[1]);
                LocalDate medalDate = LocalDate.parse(data[2]);
                String gender = data[3];
                LocalDate birthDate = LocalDate.parse(data[4]);
                String country = data[5];
                String discipline = data[6];
                String event = data[7];

                Medalhista athlete = athletes.computeIfAbsent(name,
                        k -> new Medalhista(name, gender, birthDate, country));

                athlete.incluirMedalha(new Medalha(medalType, medalDate, discipline, event));
            }

            String input;
            while (!(input = scanner.nextLine()).equalsIgnoreCase("FIM")) {
                String[] inputData = input.split(",");
                Medalhista athlete = athletes.get(inputData[0].trim());
                if (athlete != null) {
                    System.out.println(athlete.relatorioDeMedalhas(TipoMedalha.valueOf(inputData[1].trim())));
                } else {
                    System.out.println("Medalhista não encontrado.");
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
