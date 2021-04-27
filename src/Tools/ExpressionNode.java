package Tools;

import java.util.ArrayList;

public class ExpressionNode extends Node{
    public ArrayList<IterationNode> iteration;

    public ExpressionNode(ArrayList<IterationNode> iteration) {
        this.iteration = iteration;
    }

    public ExpressionNode() {
        super();
        this.iteration = new ArrayList<IterationNode>();
    }
}
