package ex01;

import java.util.Map;

public class Not implements BooleanExpression {

    final BooleanExpression op;

    public Not(BooleanExpression op) {
        this.op=op;
    }

    public BooleanExpression getOp() {
        return op;
    }

    @Override
    public String toPosFixString() {
        return  this.toString().replaceAll("\\s{2,}", " ");
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> map) {

        return !(op.evaluate(map));
    }

    @Override
    public BooleanExpression toDNF() {
        if(this.op instanceof Var){
            return this;
        }
        else if(this.op instanceof Not){
            return ((Not) this.op).getOp().toDNF();
        }
        else if(this.op instanceof And){
            return new Or(((And) this.op).getLeftOp().toDNF(), ((And) this.op).getRightOp().toDNF());
        }
        else {
            return new And(((Or) this.op).getLeftOp().toDNF(), ((Or) this.op).getRightOp().toDNF());
        }
    }
}
