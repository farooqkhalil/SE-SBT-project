package ex01;

import java.util.*;

public interface BooleanExpression {

    public String toPostfixString();

    public Boolean evaluate(Map<String, Boolean> map);

    public BooleanExpression toDNF();

    public default ArrayList<BooleanExpression> disjunctiveTerms(){
        ArrayList<BooleanExpression> list = new ArrayList<>(Arrays.asList(this));
        return list;
    }



    public static BooleanExpression parseExpression(String string) throws IllegalArgumentException {

        Stack<BooleanExpression> stack = new Stack<>();
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {

            char ch = chars[i];
            BooleanExpression op;

            switch (ch) {
                case ' ':
                    break;
                case '&':
                    setLeftAndRightOp(stack,"And");
                    break;

                case '|':
                    setLeftAndRightOp(stack, "Or");
                    break;

                case '!':
                    try {
                        if(stack.size()>0){
                            op = stack.pop();
                        }
                        else{
                            throw new IllegalArgumentException();
                        }
                    } catch (IllegalArgumentException ex) {
                        throw ex;
                    }
                    Not not = new Not(op);
                    stack.push(not);
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

    public static void setLeftAndRightOp(Stack<BooleanExpression> stack, String operation){
        try {
            if(stack.size()<2){
                throw new IllegalArgumentException();
            }
            else{
                BooleanExpression right = stack.pop();
                BooleanExpression left = stack.pop();
                BooleanExpression booleanExpression = (operation=="And")? new And(left,right): new Or(left,right);
                stack.push(booleanExpression);
            }
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }
}
