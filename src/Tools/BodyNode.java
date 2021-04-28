package Tools;

import java.util.ArrayList;

public class BodyNode extends Node{
    public ArrayList<IterationNode> iteration;

    public BodyNode(ArrayList<IterationNode> iteration) {
        this.iteration = iteration;
    }

    public BodyNode() {
        super();
        this.iteration = new ArrayList<IterationNode>();
    }
}
