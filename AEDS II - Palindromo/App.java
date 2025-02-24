import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Palindromo a = new Palindromo();

        while (true) {
            //System.out.print("Qual palavra: ");
            String s = sc.nextLine();

            if (s.equals("FIM")) {
                sc.close();
                break;
            } else {
                boolean resultado = a.isPalindromo(s);
                if (resultado) {
                    System.out.println("SIM");
                } else {
                    System.out.println("NAO");
                }
            }
        }
    }

    public static class Palindromo {
        public boolean isPalindromo(String s) {
            return isPalindromoRecursivo(s);
        }

        private boolean isPalindromoRecursivo(String s) {
            if (s.length() <= 1) {
                return true;
            }
            if (s.charAt(0) != s.charAt(s.length() - 1)) {
                return false;
            }
            return isPalindromoRecursivo(s.substring(1, s.length() - 1));
        }
    }
}
