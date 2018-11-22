import java.util.*;

class logic {
  static boolean evaluate(String exp, Map<String, Integer> vars,
                                 boolean solve, int subproof) {
    // PRE: #( brackets == #) brackets
    exp = utilities.no_spaces(exp);
    // START PRINT SECTION
    if (solve && subproof == 0) {
      System.out.println("\nSolution steps for expression '" + exp + "':\n");
      for (String key : vars.keySet()) {
        System.out.println(key + " = " + vars.get(key));
      }
      System.out.println();
    }
    // END PRINT SECTION

    // EVALUATING SUB-EXPRESSIONS
    int recent_open = 0;
    for (int i = 0; i < exp.length(); i++) {
      if (exp.charAt(i) == '(') {
        recent_open = i;
      } else if (exp.charAt(i) == ')') {
        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println(exp + " becomes:");
        }
        // END PRINT SECTION

        exp = exp.substring(0, recent_open) +
                utilities.bool_to_char(evaluate(
                        exp.substring(recent_open + 1, i),
                        vars, solve, subproof + 1)) +
                exp.substring(i + 1);
        i = 0;
        recent_open = 0;

        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println(exp);
        }
        // END PRINT SECTION
      }
    }

    // EVALUATING CONSTANTS
    if (exp.equals("1") || exp.equals("0")) {
      return exp.equals("1");
    }

    // EVALUATING VARIABLES
    if (vars.containsKey(exp)) {
      // START PRINT SECTION
      if (solve) {
        for (int sub = 0; sub < subproof; sub++) {
          System.out.print("  ");
        }
        System.out.println("Variable '" + exp + "' evaluates to " +
                utilities.bool_to_char(vars.get(exp).equals(1)) + ".");
      }
      // END PRINT SECTION

      return vars.get(exp).equals(1);
    }

    // EVALUATING OPERATIONS
    if (exp.indexOf('(') == -1) {
      if (exp.contains("<=>")) {
        // IFF logic

        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println("Operation " + exp + " becomes:");
        }
        // END PRINT SECTION

        int iff_index = exp.indexOf("<=>");
        String op1 = exp.substring(0, iff_index);
        if (op1.charAt(0) != '(') {
          op1 = '(' + op1 + ')';
        }
        op1 = Character.toString(
                utilities.bool_to_char(evaluate(op1, vars, solve, subproof + 1)));
        String op2 = exp.substring(iff_index + 3);
        if (op2.charAt(0) != '(') {
          op2 = '(' + op2 + ')';
        }
        op2 = Character.toString(
                utilities.bool_to_char(evaluate(op2, vars, solve, subproof + 1)));

        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println(op1 + "<=>" + op2 + ", which evaluates to:");
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println(utilities.bool_to_char(
                  op1.equals(op2)));
        }
        // END PRINT SECTION

        return op1.equals(op2);
      } else if (exp.contains("->")) {
        // IMPLIES logic

        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println("Operation " + exp + " becomes:");
        }
        // END PRINT SECTION

        int implies_index = exp.indexOf("->");
        String op1 = exp.substring(0, implies_index);
        if (op1.charAt(0) != '(') {
          op1 = '(' + op1 + ')';
        }
        op1 = Character.toString(
                utilities.bool_to_char(evaluate(op1, vars, solve, subproof + 1)));
        String op2 = exp.substring(implies_index + 2);
        if (op2.charAt(0) != '(') {
          op2 = '(' + op2 + ')';
        }
        op2 = Character.toString(
                utilities.bool_to_char(evaluate(op2, vars, solve, subproof + 1)));

        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println(op1 + "->" + op2 + ", which evaluates to:");
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println(utilities.bool_to_char(
                  op1.equals("0") || op2.equals("1")));
        }
        // END PRINT SECTION

        return op1.equals("0") || op2.equals("1");
      } else if (exp.indexOf('|') >= 0) {
        // OR logic

        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println("Operation " + exp + " becomes:");
        }
        // END PRINT SECTION

        int or_index = exp.indexOf('|');
        String op1 = exp.substring(0, or_index);
        if (op1.charAt(0) != '(') {
          op1 = '(' + op1 + ')';
        }
        op1 = Character.toString(
                utilities.bool_to_char(evaluate(op1, vars, solve, subproof + 1)));
        String op2 = exp.substring(or_index + 1);
        if (op2.charAt(0) != '(') {
          op2 = '(' + op2 + ')';
        }
        op2 = Character.toString(
                utilities.bool_to_char(evaluate(op2, vars, solve, subproof + 1)));

        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println(op1 + '|' + op2 + ", which evaluates to:");
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println(utilities.bool_to_char(
                  op1.equals("1") || op2.equals("1")));
        }
        // END PRINT SECTION

        return op1.equals("1") || op2.equals("1");
      } else if (exp.indexOf('&') >= 0) {
        // AND logic

        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println("Operation " + exp + " becomes:");
        }
        // END PRINT SECTION

        int and_index = exp.indexOf('&');
        String op1 = exp.substring(0, and_index);
        if (op1.charAt(0) != '(') {
          op1 = '(' + op1 + ')';
        }
        op1 = Character.toString(
                utilities.bool_to_char(evaluate(op1, vars, solve, subproof + 1)));
        String op2 = exp.substring(and_index + 1);
        if (op2.charAt(0) != '(') {
          op2 = '(' + op2 + ')';
        }
        op2 = Character.toString(
                utilities.bool_to_char(evaluate(op2, vars, solve, subproof + 1)));

        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println(op1 + '&' + op2 + ", which evaluates to:");
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println(utilities.bool_to_char(
                  op1.equals("1") && op2.equals("1")));
        }
        // END PRINT SECTION

        return op1.equals("1") && op2.equals("1");
      } else if (exp.length() >= 1 && exp.charAt(0) == '!') {
        // NOT logic

        // START PRINT SECTION
        if (solve) {
          for (int sub = 0; sub < subproof; sub++) {
            System.out.print("  ");
          }
          System.out.println("Operation " + exp + " becomes:");
        }
        // END PRINT SECTION

        if (exp.charAt(1) == '0') {
          // START PRINT SECTION
          if (solve) {
            for (int sub = 0; sub < subproof; sub++) {
              System.out.print("  ");
            }
            System.out.println("1 as it is the negation of 0.");
          }
          // END PRINT SECTION

          return true;
        } else if (exp.charAt(1) == '1') {
          // START PRINT SECTION
          if (solve) {
            for (int sub = 0; sub < subproof; sub++) {
              System.out.print("  ");
            }
            System.out.println("0 as it is the negation of 1.");
          }
          // END PRINT SECTION

          return false;
        } else {
          // START PRINT SECTION
          if (solve) {
            evaluate(exp.substring(1), vars, true, subproof + 1);
            for (int sub = 0; sub < subproof; sub++) {
              System.out.print("  ");
            }
            System.out.println(utilities.bool_to_char(
                    !vars.get(exp.substring(1)).equals(1)) +
                    " as it is the negation of " +
                    exp.substring(1) + ".");
          }
          // END PRINT SECTION

          return !vars.get(exp.substring(1)).equals(1);
        }
      }
    }

    return false;
  }

  static List<String> parse_variables(String exp) {
    List<String> vars = new ArrayList<>();

    for (int i = 0; i < exp.length(); i++) {
      int j = i;
      while (j < exp.length()) {
        if (exp.toUpperCase().charAt(j) >= 'A' &&
                exp.toUpperCase().charAt(j) <= 'Z') {
          j++;
        } else if (j > i) {
          if (!vars.contains(exp.substring(i, j))) {
            vars.add(exp.substring(i, j));
          }
          i += (j - i) - 1;
          j = exp.length();
        } else {
          j = exp.length();
        }
      }
    }

    return vars;
  }

  static Map<String, Integer> assign(List<String> vars) {
    Scanner sc = new Scanner(System.in);
    List<Integer> vals = new ArrayList<>();

    while (vals.size() < vars.size()) {
      System.out.println("Assign a value to '" + vars.get(vals.size()) +
              "' (0 for false, 1 for true):");
      vals.add(sc.nextInt());
    }

    Map<String, Integer> map = new HashMap<>();

    for (int i = 0; i < vars.size(); i++) {
      map.put(vars.get(i), vals.get(i));
    }

    return map;
  }
}
