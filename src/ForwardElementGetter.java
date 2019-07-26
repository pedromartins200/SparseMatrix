/**
 * Interface to force other classes to implement the method getdown(), getright() and getnext() in an abstract way
 * @author - Pedro Martins
 * @author - Tiago Matos
*/
public interface ForwardElementGetter<In extends Out,Out> {
    Out getForwardElement(In current);
}
