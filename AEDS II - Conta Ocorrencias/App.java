import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) {
                break;
            }

            String[] numerosStr = linha.split(";");
            int[] numeros = new int[numerosStr.length];
            for (int i = 0; i < numerosStr.length; i++) {
                numeros[i] = Integer.parseInt(numerosStr[i]);
            }

            String numeroPesquisaStr = sc.nextLine();
            int x = Integer.parseInt(numeroPesquisaStr);

            int ocorrencias = contarOcorrencias(numeros, numeros.length, x);
            System.out.println(ocorrencias);
        }

        sc.close();
    }

    public static int contarOcorrencias(int[] A, int n, int x) {
        if (n == 0) {
            return 0;
        }
        int count = (A[n - 1] == x) ? 1 : 0;
        return count + contarOcorrencias(A, n - 1, x);
    }
}
