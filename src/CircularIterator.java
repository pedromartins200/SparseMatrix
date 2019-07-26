import java.util.Iterator;

/**
 + generic class that implements an iterator on a given set of nodes, beggining in a node and ending in that same node
 * so basically, it will be a circular iterator
 * @param <InNode> - type of current element
 * @param <OutNode> - type of next element
 * @author - Pedro Martins
 */
public class CircularIterator<InNode extends OutNode,OutNode> implements Iterator<OutNode> {

	//head - where we begin and end iteration
    private InNode head;
    //current node
    private OutNode current;

    //we will use this class to find next element.
    private ForwardElementGetter<InNode,OutNode> nextGetter;

    //passhead is a way to know how many times we have passed through the head
    // if its bigger than 1, it means we have gone more than 1 times through the head
    private int passHead = 0;
    // have we passed through head?
    private boolean repeatHead;
    
    
    /**
     * Constructor of this class
     * @param head - First element
     * @param nextGetter - Next element
     * @param repeatHead - Have we passed through head?
     */
    public CircularIterator(InNode head,ForwardElementGetter<InNode,OutNode> nextGetter,boolean repeatHead){
        this.head = head;
        this.current = head;
        this.nextGetter = nextGetter;
        this.repeatHead = repeatHead;
    }

    /**
     + We can exit if we have passed through head 2 times, obviously.
     * @return - true if next, false otherwise
     */
    @Override
    public boolean hasNext() {
    	// have we passed through head?
        if(repeatHead){
        	// lets confirm it was only one time
            return passHead < 2;
        }
        else {
            // if we have passed only once, and current node is not head, then obviously there is a next node
        	// if could be a sentinel node or base node.
            return (passHead == 1) != (current == this.head);
        }
    }
    
    // ovverride of iterator methods

    @Override
    @SuppressWarnings("unchecked")
    public OutNode next() {
		InNode current = (InNode) this.current;
        //obter o proximo elemento
        this.current = nextGetter.getForwardElement(current);
        // quantas vezes passei pelo head? +=1
        if(current == this.head)
            passHead += 1;
        return current;
    }
}