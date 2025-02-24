import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class App {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();

    while (!input.equals("0")) {


      String[] parts = input.split(" ");
      int numVars = Integer.parseInt(parts[0]);

      Map<Character, Boolean> variableValues = new HashMap<>();
      for (int i = 0; i < numVars; i++) {
        variableValues.put((char) ('A' + i), parts[i + 1].equals("1"));
      }

      StringBuilder expressionBuilder = new StringBuilder();
      for (int i = numVars + 1; i < parts.length; i++) {
        expressionBuilder.append(parts[i]).append(" ");
      }
      String expression = expressionBuilder.toString().trim();

      try {
        boolean result = evaluateExpression(expression, variableValues);
        if (result) {
          System.out.println(1);
        } else {
          System.out.println(0);
        }
      } catch (Exception e) {
        System.out.println("Erro ao avaliar a expressão: " + e.getMessage());
      }

      input = scanner.nextLine();
    }
    scanner.close();
  }

  private static boolean evaluateExpression(String expression, Map<Character, Boolean> variables) throws Exception {
    expression = expression.trim();

    if (expression.startsWith("not(")) {
      String innerExpr = expression.substring(4, expression.length() - 1);
      return !evaluateExpression(innerExpr, variables);
    }
    else if (expression.startsWith("and(") || expression.startsWith("or(")) {
      String operator = expression.startsWith("and(") ? "and" : "or";
      String innerExpr = expression.substring(operator.length() + 1, expression.length() - 1);

      String[] parts = splitByComma(innerExpr);

      if (operator.equals("and")) {
        boolean result = true;
        for (String part : parts) {
          result = result && evaluateExpression(part, variables);
        }
        return result;
      } else {
        boolean result = false;
        for (String part : parts) {
          result = result || evaluateExpression(part, variables);
        }
        return result;
      }
    }
    else if (expression.length() == 1 && Character.isLetter(expression.charAt(0))) {
      Character var = expression.charAt(0);
      if (!variables.containsKey(var)) {
        throw new Exception("Variável " + var + " não encontrada.");
      }
      return variables.get(var);
    }
    else {
      throw new Exception("Expressão inválida ou malformada: " + expression);
    }
  }

  private static String[] splitByComma(String expression) {
    int depth = 0;
    StringBuilder current = new StringBuilder();
    List<String> result = new ArrayList<>();

    for (char c : expression.toCharArray()) {
      if (c == '(') {
        depth++;
      } else if (c == ')') {
        depth--;
      }

      if (c == ',' && depth == 0) {
        result.add(current.toString().trim());
        current = new StringBuilder();
      } else {
        current.append(c);
      }
    }

    result.add(current.toString().trim());
    return result.toArray(new String[0]);
  }
}
