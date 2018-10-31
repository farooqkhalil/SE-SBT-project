package ex01;


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
    public String toPosFixString() {
        return  this.toString().replaceAll("\\s{2,}", " ");    }

    @Override
    public Boolean evaluate(Map<String, Boolean> map) {

        return leftOp.evaluate(map) || rightOp.evaluate(map);
    }

    @Override
    public BooleanExpression toDNF() {
        return new Or(this.getLeftOp().toDNF(),this.getRightOp().toDNF());
    }

    @Override
    public List<BooleanExpression> disjunctiveTerms() {
        if(this.leftOp instanceof Or || this.rightOp instanceof Or){
            List<BooleanExpression> list = this.leftOp.disjunctiveTerms();
            list.addAll(this.rightOp.disjunctiveTerms());
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
            return Arrays.asList(leftOp, rightOp);
        }
    }
}
