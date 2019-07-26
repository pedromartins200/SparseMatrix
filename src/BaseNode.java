import java.util.Iterator;

/**
 * Class that represents a node. DataNode and SentinelNode are subclasses of this.
 */
public abstract class BaseNode implements Iterable<BaseNode> {
    private BaseNode down;
    private BaseNode right;
    
    // current iteration axis
    protected String iterationAxis;

    // getters/setters
    public BaseNode getDown() {
        return down;
    }
    public void setDown(BaseNode down) {
        this.down = down;
    }
    public BaseNode getRight() {
        return right;
    }
    public void setRight(BaseNode right) {
        this.right = right;
    }


    //This way, its possible to determine what is the next down or right with axis givene in input (x or y)
    public BaseNode getNextByAxis(String axis){
        if(axis.equals("x")){
            return this.getDown();
        }
        else if( axis.equals("y")){
            return this.getRight();
        }
        throw new IllegalArgumentException();
    }

    //set axis
    public void setNextByAxis(BaseNode next,String axis){
        if(axis.equals("x")){
            setDown(next);
        }
        else if( axis.equals("y")){
            setRight(next);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // doing set to iteration axis can be done here before getting iteration of down and rights
    public void setIterationAxis(String axis){
        this.iterationAxis = axis;
    }
    
    //We should find the iteration axis before looping over our basenodes
    @Override
    public Iterator<BaseNode> iterator() {
        return new CircularIterator<BaseNode,BaseNode>(this,new ForwardBaseNodeGetter(this.iterationAxis),true);
    }
        
}




