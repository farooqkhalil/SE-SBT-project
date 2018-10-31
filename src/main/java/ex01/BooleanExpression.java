package ex01;

import java.util.*;

public interface BooleanExpression {

    public String toPosFixString();

    public Boolean evaluate(Map<String, Boolean> map);

    public BooleanExpression toDNF();

    public default List<BooleanExpression> disjunctiveTerms(){
        List<BooleanExpression> list = Arrays.asList(this);
        return list;
    }



    public static BooleanExpression parseExpression(String string) throws IllegalArgumentException {

        Stack<BooleanExpression> stack = new Stack<>();
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {

            char ch = chars[i];
            BooleanExpression left;
            BooleanExpression right;
            BooleanExpression op;

            switch (ch) {
                case ' ':
                    break;
                case '&':
                    try {
                        right = stack.pop();
                        left = stack.pop();
                    } catch (IllegalArgumentException ex) {
                        throw ex;
                    }
                    And and = new And(left, right);
                    stack.push(and);
                    break;
                case '|':
                    try {
                        right = stack.pop();
                        left = stack.pop();
                    } catch (IllegalArgumentException ex) {
                        throw ex;
                    }

                    Or or = new Or(left, right);
                    stack.push(or);
                    break;
                case '!':
                    try {
                        op = stack.pop();
                    } catch (IllegalArgumentException ex) {
                        throw ex;
                    }
                    Not not = new Not(op);
                    break;
                default:
                    StringBuilder sb = new StringBuilder();
                    i = findNextSpaceCharAndBuildString(chars, i, sb);
                    stack.push(new Var(sb.toString()));
                    break;
            }

        }

        return checkStackandReturnResult(stack);
    }

    public static BooleanExpression checkStackandReturnResult(Stack<BooleanExpression> stack) {
        try {
            BooleanExpression result = stack.pop();
            if (stack.empty()) {
                return result;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    public static int findNextSpaceCharAndBuildString(char[] chars, int i, StringBuilder sb) {
        int c;
        for (c = i; c < chars.length; c++) {
            if (chars[c] == ' ') {
                return c;
            } else {
                sb.append(chars[c]);
            }
        }
        return c;
    }
}
