/**
 * Implement the mathematical operations
 * @author - Pedro Martins
 */
public class SparseMatrixService {

    public SparseMatrix multiplyByScalar(SparseMatrix sparseMatrix, int scalar){
     
        SparseMatrix newMatrix = sparseMatrix.sizeOf();

        for (DataNode dataNode : sparseMatrix) {
            newMatrix.add(dataNode.getCol(),dataNode.getRow(),dataNode.getValue() * scalar);
        }
        return newMatrix;
    }


    public SparseMatrix transpose(SparseMatrix sparseMatrix){
        SparseMatrix newMatrix = new SparseMatrix(sparseMatrix.getColumns(),sparseMatrix.getRows());

        for (DataNode dataNode : sparseMatrix) {
            //switch rows and columns
            newMatrix.add(dataNode.getRow(),dataNode.getCol(),dataNode.getValue());
        }
        return newMatrix;
    }

    public SparseMatrix add(SparseMatrix sparseMatrix1,SparseMatrix sparseMatrix2){

        //check same size
        if(sparseMatrix1.getRows() != sparseMatrix2.getRows())
            throw new IllegalArgumentException("Different sizes");
        if(sparseMatrix1.getColumns() != sparseMatrix2.getColumns())
            throw new IllegalArgumentException("Different sizes");


        SparseMatrix newMatrix = sparseMatrix1.sizeOf();

        for (DataNode dataNode : sparseMatrix1) {

            int add = sparseMatrix2.getXY(dataNode.getCol(),dataNode.getRow());


            newMatrix.add(dataNode.getCol(),dataNode.getRow(),dataNode.getValue() + add);
        }

        //loop second matrix
        for (DataNode dataNode : sparseMatrix2) {

            int add = sparseMatrix1.getXY(dataNode.getCol(),dataNode.getRow());

            if(add == 0){
                newMatrix.add(dataNode.getCol(),dataNode.getRow(),dataNode.getValue());
            }
        }
        return newMatrix;
    }

}
