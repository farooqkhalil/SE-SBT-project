package ex01;

import java.util.Map;

public class Var implements BooleanExpression {

    final String name;

    public Var(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toPosFixString() {
        return  this.toString().replaceAll("\\s{2,}", " ");
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> map) {
        return map.get(this.getName());
    }

    @Override
    public BooleanExpression toDNF() {
        return this;
    }
}
