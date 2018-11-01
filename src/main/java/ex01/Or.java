package ex01;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Or implements BooleanExpression {

    final BooleanExpression leftOp;
    final BooleanExpression rightOp;


    public BooleanExpression getLeftOp() {
        return leftOp;
    }

    public BooleanExpression getRightOp() {
        return rightOp;
    }

    public Or(BooleanExpression leftOp, BooleanExpression rightOp) {
        this.leftOp=leftOp;
        this.rightOp=rightOp;
    }

    @Override
    public String toPostfixString() {
        StringBuilder sb = new StringBuilder();
        sb.append(leftOp.toPostfixString()+" ");
        sb.append(rightOp.toPostfixString()+" ");
        sb.append("| ");
        String result = sb.toString().trim().replaceAll("\\s{2,}", " ");
        return result;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> map) {

        return leftOp.evaluate(map) || rightOp.evaluate(map);
    }

    @Override
    public BooleanExpression toDNF() {
        return new Or(this.getLeftOp().toDNF(),this.getRightOp().toDNF());
    }

    @Override
    public ArrayList<BooleanExpression> disjunctiveTerms() {
        if(this.leftOp instanceof Or || this.rightOp instanceof Or){
            ArrayList<BooleanExpression> list = this.leftOp.disjunctiveTerms();
            ArrayList<BooleanExpression> newList = this.rightOp.disjunctiveTerms();
            list.addAll(newList);
            return list;
        }
        /*else if(this.leftOp instanceof Or && !(this.rightOp instanceof Or)){
            List<BooleanExpression> list = this.leftOp.disjunctiveTerms();
            list.addAll(Arrays.asList(this.rightOp));
            return list;
        }
        else if(!(this.leftOp instanceof Or) && this.rightOp instanceof Or){
            List<BooleanExpression> list = this.rightOp.disjunctiveTerms();
            list.addAll(Arrays.asList(this.leftOp));
            return list;

        }*/
        else {
            ArrayList<BooleanExpression> list = new ArrayList<>(Arrays.asList(leftOp, rightOp));
            return list;
        }
    }
}
