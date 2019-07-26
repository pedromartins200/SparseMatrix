import java.util.List;

/**
 * Read input and construct a sparseMatrix
 *
 * @author Pedro Martins
 *
 */
public class SparseMatrixReader {
	
    private SparseMatrixService sparseMatrixService = new SparseMatrixService();
    private List<String> metaData;
    

    public SparseMatrixReader(List<String> source) {
        this.metaData = source;
    }

    private SparseMatrix readMatrix(){
        String[] size = metaData.get(0).split(" ");
        SparseMatrix sparseMatrix = new SparseMatrix(Integer.valueOf(size[0]),Integer.valueOf(size[1]));
        int nonZeros = Integer.valueOf(size[2]);

        //Adiciona os DataNodes a saprse matrix
        for (int i = 1; i <= nonZeros; i++) {
            String[] data = metaData.get(i).split(" ");
            sparseMatrix.add(Integer.valueOf(data[1]),Integer.valueOf(data[0]),Integer.valueOf(data[2]));
        }

        //Constroi sublista comecando aqui e terminando no size
        metaData = metaData.subList(nonZeros + 1, metaData.size());

        //Devolve a sparse matrix
        return sparseMatrix;
    }
    private String readOperator(){
        String op = metaData.get(0);
        metaData = metaData.subList(1,metaData.size());
        return op;
    }

    private Integer readScalar(){
        int scalar = Integer.valueOf(metaData.get(0));
        metaData = metaData.subList(1,metaData.size());
        return scalar;
    }


    public SparseMatrix processOperation(List<String> metaData){
        SparseMatrix sparseMatrix = readMatrix();

        String op = readOperator();
        System.out.println(op);
        switch (op){
            //transposed matrix
            case "t":
                return sparseMatrixService.transpose(sparseMatrix);
                //multiply matrix
            case "*":
                int scalar = readScalar();
                return sparseMatrixService.multiplyByScalar(sparseMatrix,scalar);
            case "+":
                //read second matrix
                SparseMatrix sparseMatrix2 = readMatrix();
                //Sum and return second matrix
                return sparseMatrixService.add(sparseMatrix,sparseMatrix2);
            default:
                //any other case return error
            	System.out.println(op);
               return null;
        }
    }


    //validate input
    public static boolean validate(List<String> metaData){
        try{
            int matrixSize = Integer.valueOf(metaData.get(0).split(" ")[2]);

            String op = metaData.get(matrixSize + 1);
            System.out.println(op);

            switch (op) {
                case "t":
                    return true;
                case "*":
                    //Check the existence of a line below, and if we can convert it to integer
                    Integer.valueOf(metaData.get(matrixSize + 2));
                    return true;
                case "+":
                    //Alocate space for second matrix
                    //increase size of list string
                    int matrix2Size = Integer.valueOf(metaData.get(matrixSize + 2).split(" ")[2]);
                    metaData.get(matrixSize + 2 + matrix2Size);
                    return true;
                default:
                    throw new IllegalArgumentException();
            }
        }
        catch (Exception e){
            return false;
        }
    }

}
