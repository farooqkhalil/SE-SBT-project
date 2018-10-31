package ex01;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class And implements BooleanExpression {

    final BooleanExpression leftOp;
    final BooleanExpression rightOp;

    public BooleanExpression getLeftOp() {
        return leftOp;
    }

    public And(BooleanExpression leftOp, BooleanExpression rightOp) {
        this.rightOp=rightOp;
        this.leftOp=leftOp;
    }

    public BooleanExpression getRightOp() {
        return rightOp;
    }

    @Override
    public String toPosFixString() {
        return  this.toString().replaceAll("\\s{2,}", " ");

    }

    @Override
    public Boolean evaluate(Map<String, Boolean> map) {

        return leftOp.evaluate(map) && rightOp.evaluate(map);
    }

    @Override
    public BooleanExpression toDNF() {
        if(this.getLeftOp().toDNF() instanceof Or || this.getRightOp().toDNF() instanceof Or){
            List<BooleanExpression> rightList=this.getRightOp().disjunctiveTerms();
            List<BooleanExpression> leftList=this.getLeftOp().disjunctiveTerms();
            List<BooleanExpression> list=null;
            rightList.forEach(rb -> {
                BooleanExpression rightBoolean = rb;
                leftList.forEach(lb -> {
                    list.add(new And(lb,rightBoolean));
                });
            });
            BooleanExpression result = list.remove(0);
            for(BooleanExpression expression: list){
                result=new Or(result,expression);
            }
            return result;
        }
        else{
            return new And(this.getLeftOp(), this.getRightOp());
        }
    }
}
