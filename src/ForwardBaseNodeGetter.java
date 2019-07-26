/**
 * Implementation of ForwardElementGetter to know getRight() and getDown() given the current axis (x or y)
 * @author - Pedro Martins
 */
public class ForwardBaseNodeGetter implements ForwardElementGetter<BaseNode, BaseNode> {

    private String axis;
    public ForwardBaseNodeGetter(String axis) {
        this.axis = axis;
    }

    @Override
    public BaseNode getForwardElement(BaseNode current) {
        return current.getNextByAxis(this.axis);
    }
}
