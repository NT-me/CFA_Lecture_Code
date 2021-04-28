package Tools;

import java.util.ArrayList;

public class BodyNode extends Node{
    public ArrayList<Node> iteration;

    public BodyNode(ArrayList<Node> iteration) {
        this.iteration = iteration;
    }

    public BodyNode() {
        super();
        this.iteration = new ArrayList<Node>();
    }

    public ArrayList<Node> getIteration() {
        return iteration;
    }

    public void setIteration(ArrayList<Node> iterations) {
        this.iteration = iterations;
    }

    public void addIteration(Node iteration){
        this.iteration.add(iteration);
    }
}
