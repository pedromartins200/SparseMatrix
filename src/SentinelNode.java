import java.util.Iterator;

/**
 * Extension of class BaseNode. Contains aditional information, more correctly a way to discover next sentinelNode
 * To avoid redundance
 * @author - Pedro Martins
 */
public class SentinelNode extends BaseNode{
    private int id;
    private SentinelNode next;

    /**
     * Constructor of this class. Id allows to locate more efficiently this object
     * @param id - id sentinelNode
     */
    public SentinelNode(int id){
        this.id = id;
        //do right and down to itself. When we find a next node, we will update
        this.setRight(this);
        this.setDown(this);
    }

    /**
     * This method allows to know the next element after this, that will the type BaseNode
     * @return - next BaseNode
     */
    public SentinelNode getNext() {
        return next;
    }
    
    /**
     * Updates the next value of this sentinelNode
     * @param next -value to put as next of this sentinelNode
     */
    public void setNext(SentinelNode next) {
        this.next = next;
    }
    
    
    
    @Override
    public Iterator<BaseNode> iterator() {
        if(this.iterationAxis == null)
            //if there isnt an iteration axis, it means we are in the sentinelNodes
            return new CircularIterator<SentinelNode,BaseNode>(this,new ForwardSentinelGetter(),true);
        else
        	//otherwise, use BaseNode iterator
            return super.iterator();
    }

    @Override
    public String toString() {
        return "SentinelNode{" +
                "id=" + id + "}";
    }
}
