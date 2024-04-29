import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/** 
 * Class to interpret and compute the result of arithmetic expressions 
 * in POSTFIX format - 
 */
public class Postfix {

  /**
   * Calculates post-fix operation using inputted numbers and operators
   * @param calc  deque of numbers and operators of the inptted expression
   * @return      double result of calculation
   */
  public static double calculatePostfix(ArrayDeque<Object> calc) {

    ArrayDeque<Double> storing = new ArrayDeque<>();

    while(!calc.isEmpty()) {
      Object temp = calc.getFirst();

      if(temp instanceof Double) {
        storing.push((Double)(temp));
        try {
          double x = storing.pop();
          double y = storing.pop();
        } catch (Exception e) {
          throw new NoSuchElementException("Too many operators, not expecting " + temp);
        }
      }
      else if(temp.equals('*')) {
        double x = storing.pop();
        double y = storing.pop();
        storing.push(y*x);
      }
      else if(temp.equals('/')) {
        double x = storing.pop();
        double y = storing.pop();
        storing.push(y/x);
      }
      else if(temp.equals('+')) {
        double x = storing.pop();
        double y = storing.pop();
        storing.push(y+x);
      }
      else if(temp.equals('-')) {
        double x = storing.pop();
        double y = storing.pop();
        storing.push(y-x);
      }
      else if(temp.equals('^')) {
        double x = storing.pop();
        double y = storing.pop();
        storing.push(Math.pow(y,x));
      }
      else {
        throw new RuntimeException(temp + " is not a valid symbol.");
      }
      calc.remove();
    }
    if(storing.size()!=1) {
      throw new RuntimeException("Wrong number of operators.");
    }
    System.out.println("\n FINAL ANSWER: \n" + storing.peek().toString());
    return storing.peek();
  }

  /** Run short test */
  public static void main(String[] args) {

    if (args.length == 0) {
      // If no arguments passed, print instructions
      System.err.println("Usage:  java Postfix <expr>");
    } else {
      ArrayDeque<Object> calc = Tokenizer.readTokens(args[0]);
      System.out.println(calculatePostfix(calc));
    }
  }

}