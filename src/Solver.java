import java.util.ArrayList;
import java.util.List;

/**
 * Classe that solves the problem
 * @author - Pedro Martins
 *
 */
public class Solver {
	private List<String> inputs;

	public Solver(List<String> inputs) {
		this.inputs = inputs;
	}
	
	
	public void solve() {
        //Process input from stdin with SparseMatrixReader
        SparseMatrixReader sparseMatrixReader = new SparseMatrixReader(this.inputs);
        //First lets create the sentinelNodes
        try {
        SparseMatrix sparseMatrix = sparseMatrixReader.processOperation(this.inputs); 
        

        //Add all non null values to this list
        List<DataNode> nonZeroes = new ArrayList<>();
        for (DataNode dataNode : sparseMatrix) {
            nonZeroes.add(dataNode);
        }

        System.out.println("\nOutput");
        System.out.println("-----");

        //Output to stdout result
        System.out.println(sparseMatrix.getRows() + " " + sparseMatrix.getColumns() + " " + nonZeroes.size());
        for (DataNode nonZeroe : nonZeroes) {
            System.out.println(nonZeroe.getRow() + " " + nonZeroe.getCol() + " " + nonZeroe.getValue());
        }
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
}