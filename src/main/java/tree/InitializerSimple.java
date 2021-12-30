package tree;

import tree.Expression.*;

public class InitializerSimple extends Initializer
{
    // Structure
    public Expression expression;

    // Creation
    public InitializerSimple(Expression expr)
    {
        this.expression = expr;
    }

    // Reporting
    public void report(int sh)
    {
        expression.report(sh);
    }

}
