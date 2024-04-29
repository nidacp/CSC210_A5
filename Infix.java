import java.util.ArrayDeque;

public class Infix {

    /**
     * Formats input into postfix-formatted deque and calls CalculatePostfix().
     * @param calc ArrayDeque of objects with numbers, operators, and parentheses
     * @return     result of calling calculatePostfix() with newly formatted deque
     */
    public static double calculateInfix(ArrayDeque<Object> calc) {
        ArrayDeque<Object> queue = new ArrayDeque<>();
        ArrayDeque<Object> stack = new ArrayDeque<>();

        while(!calc.isEmpty()) {
            Object temp = calc.getFirst();
            calc.remove();
            System.out.println("New while call. Temp = " + temp.toString());

            if(temp instanceof Double) {
                queue.add(temp);
            }
            else if(isOperator(temp)) {
                while(stack.size()>0 && isOperator(stack.peekLast()) && (((importance(stack.peekLast())-importance(temp))==0) && !isRightAssociative(temp) || (importance(stack.peekLast())-importance(temp))>0)) {
                    Object x = stack.removeLast();
                    queue.add(x);
                }
                stack.add(temp);
            }
            else if(temp.equals('(')) {
                stack.push(temp);
            }
            else if(temp.equals(')')) {
                while(!stack.peekLast().equals('(')) {
                    Object x = stack.removeLast();
                    queue.add(x);
                    if(stack.size()==0) {
                        throw new RuntimeException("Mismatched parentheses, missing left.");
                    }
                }
                stack.pop();
            }
            else {
                throw new RuntimeException(temp + " is not a valid input.");
            }

            System.out.println("    Queue now looks like " + queue);
            System.out.println("    Stack now looks like " + stack);
        }
        System.out.println("\n entering new while loop...");
        while(stack.size()!=0) {
            if(stack.peekLast().equals('(') || stack.peekLast().equals(')')) {
                throw new RuntimeException("Not expecting " + stack.peek() + ", mismatched parentheses.");
            }
            if(isOperator(stack.peekLast())){
                Object x = stack.pop();
                queue.add(x);
            }
            System.out.println("    Queue now looks like " + queue);
            System.out.println("    Stack now looks like " + stack);
        }
        System.out.println(queue);
        return Postfix.calculatePostfix(queue);
    }

    /**
     * Checks if an object is an operator (EMDAS)
     * @param temp  Object checked
     * @return      boolean true if the param is an operator, false if not
     */
    public static boolean isOperator(Object temp) {
        return (temp.equals('*') || temp.equals('/') || temp.equals('+') || temp.equals('-') || temp.equals('^'));
    }

    /**
     * 
     * @param temp  Object checked
     * @return      boolean true if the param is right associative, false if it's left associative
     */
    public static boolean isRightAssociative(Object temp) {
        return temp.equals('^');
    }

    /**
     * finds importance of an operator based on PEMDAS
     * @param temp operator
     * @return     int importance (or -1 if temp is not an operator)
     */
    public static int importance(Object temp) {
        if(temp.equals('^')) {
            return 3;
        }
        else if (temp.equals('*')) {
            return 2;
        }
        else if (temp.equals('/')) {
            return 2;
        }
        else if (temp.equals('+')) {
            return 1;
        }
        else if (temp.equals('-')) {
            return 1;
        }
        return -1;
    }

    public static void main(String[] args) {

        if (args.length == 0) {
          // If no arguments passed, print instructions
          System.err.println("Usage:  java Postfix <expr>");
        } else {
          ArrayDeque<Object> calc = Tokenizer.readTokens(args[0]);
          System.out.println(calculateInfix(calc));
        }
    }
}
