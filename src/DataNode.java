/**
 * Extension of the BaseNode class. Contains a few more variables, row, col, value.
 * @author - Pedro Martins
 */
public class DataNode extends BaseNode{
    private int row;
    private int col;
    private int value;

    /**
     * constructor of this class
     * @param row - datanode line
     * @param col - datanode column
     * @param value - datanode value
     */
    public DataNode(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }
    
    /**
     * returns the row of this object
     * @return - datanode row
     */
    public int getRow() {
        return row;
    }
    
    /**
     * sets row of this object
     * @param row - row
     */
    public void setRow(int row) {
        this.row = row;
    }
    
    /**
     * gets column of this object
     * @return - datanode column
     */
    public int getCol() {
        return col;
    }
    
    /**
     * atualiza o valor da column desta classe
     * @param col - column a atualizar
     */
    public void setCol(int col) {
        this.col = col;
    }
    
    /**
     * returns value of this object
     * @return - datanode value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * sets value of this object
     * @param value - value
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    //This way its possivel given the axis of the datanode, to obtain the corresponding row and column
    //In my opinion it's faster and more efficient this way
    public int getAxis(String axis){
    	//if axis is X
        if(axis.equals("x")){
        	// we only care about rows
            return getRow();
          // otherwise we only care about columns
        } else if (axis.equals("y")){
            return getCol();
        }
        throw new IllegalArgumentException("Eixo errado " + axis);
    }

    @Override
    public String toString() {
        return "DataNode{R" + row + ":C" + col + " = " + value + "}";
    }




}
