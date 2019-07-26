/**
 * Implementation of the interface ForwardElementGetter, to guarantee that getnext() is only about sentinelNodes
 * @author - Pedro Martins
 */
public class ForwardSentinelGetter implements ForwardElementGetter<SentinelNode, BaseNode> {
    @Override
    public BaseNode getForwardElement(SentinelNode current) {
        return current.getNext();
    }
}
