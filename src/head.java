import java.util.*;

public class head {
  public static void main(String[] args) {
    if (args.length != 1 || !args[0].equals("rerun")) {
      System.out.println("\n\nPROPOSITIONAL LOGIC EVALUATOR");
      System.out.println("Jordan Bunke, 2018, Imperial College London (DoC)");
    }
    System.out.println("\n\nType a logical expression:\n" +
            "(Use ! for NOT, & for AND, | for OR, -> for IMPLIES, <=> for IFF)");
    Scanner sc = new Scanner(System.in);
    String expression = sc.nextLine();
    expression += " ";

    System.out.println("\nCommand List:\n(1) 'truth table'\n(2) 'evaluate'" +
    "\n(3) 'solve'");

    switch (sc.nextLine()) {
      case "truth table":
      case"1":
        truth_table(expression);
        break;
      case "evaluate":
      case "2":
        List<String> vars = logic.parse_variables(expression);
        Map<String, Integer> map = logic.assign(vars);
        boolean res = logic.evaluate(expression, map, false, 0);

        if (res) {
          System.out.println("Expression is TRUE (1) in environment.");
        } else {
          System.out.println("Expression is FALSE (0) in environment.");
        }
        break;
      case "solve":
      case "3":
        vars = logic.parse_variables(expression);
        map = logic.assign(vars);
        logic.evaluate(expression, map, true, 0);
    }

    System.out.println("\nType 'r' to run again.");

    if (sc.nextLine().equals("r")) {
      String[] main_args = {"rerun"};
      main(main_args);
    }
  }

  private static void truth_table(String expression) {
    List<String> vars = logic.parse_variables(expression);
    Map<String, Integer> map = new HashMap<>();

    for (String var : vars) {
      map.put(var, 0);
    }

    System.out.println();
    System.out.println();

    // TRUTH TABLE HEADER LINE
    System.out.println("TRUTH TABLE:");
    System.out.print("RESULT |");
    for (String var : vars) {
      System.out.print(var + " |");
    }
    System.out.println();

    // TRUTH TABLE BARRIER LINE
    System.out.print("-------|");
    for (String var : vars) {
      for (int j = 0; j < var.length(); j++) {
        System.out.print("-");
      }
      System.out.print("-|");
    }
    System.out.println();

    // TRUTH TABLE COMPUTATION
    int done = 0;
    while (done < Math.pow(2, vars.size())) {
      char res = utilities.bool_to_char(logic.evaluate(expression, map,
              false, 0));

      // PRINT COMPUTATION TO TABLE
      System.out.print(res + "      |");
      for (String var : vars) {
        System.out.print(map.get(var));
        for (int j = 1; j < var.length(); j++) {
          System.out.print(" ");
        }
        System.out.print(" |");
      }
      System.out.println();

      // INCREMENTATION AND VARIABLE ASSIGNMENT
      done++;
      for (int i = 0; i < vars.size(); i++) {
        map.put(vars.get(i), (done / (int) Math.pow(2, i)) % 2);
      }
    }
  }
}