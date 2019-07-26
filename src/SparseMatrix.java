import java.util.Iterator;

/**
 * Representation of the data structure, that is the base of my sparsematrix implementation
 * @author Pedro Martins
 */


public class SparseMatrix implements Iterable<DataNode> {
	
	// initially columns and rows = 0
	private int rows = 0;
	private int columns = 0;
	// a sparsematrix will always have a sentinelHead that is the first element
	private SentinelNode sentinelHead;

	public SparseMatrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		// First thing we do is creating the sentinelNodes and their connections
		this.addSentinel(new SentinelNode(0));
		for (int i = Math.max(rows, columns) - 1; i > 0; i--) {
			this.addSentinel(new SentinelNode(i));
		}
	}

	// This method will insert a datanode in each axis, and we insert by column/row
	/**
	 * add a datanode to this.sparsematrix
	 * @param x - row
	 * @param y - column
	 * @param value - value
	 */
	public void add(int x, int y, int value) {
		// Exclude zeros
		if (value == 0)
			return;

		// Check if x and y are correct
		if (x >= this.columns)
			throw new IllegalStateException("There are just " + this.columns + " columns in matrix");
		if (y >= this.rows)
			throw new IllegalStateException("There are just " + this.rows + " rows in matrix");

		// Create datanode
		DataNode newDataNode = new DataNode(y, x, value);

		// Insert datanode in each axis
		insertByAxis(newDataNode, "x", x, y);
		insertByAxis(newDataNode, "y", y, x);
	}

	/**
	 * Uses getXY to find the shortest path, using the sentinelNodes it's way faster
	 * @param x - row to find
	 * @param y - column to find
	 * @return - returns datanode value found
	 */
	public int getXY(int x, int y) {
		int sentinelPos;
		int iterPos;
		String iterAxis;

		// Since this is a sparsematrix we should find the closest sentinelNode
		// This makes finding way faster.
		// This is the point of using sentinelNodes
		if (x < y) {
			sentinelPos = x;
			iterPos = y;
			iterAxis = "x";
		} else {
			sentinelPos = y;
			iterPos = x;
			iterAxis = "y";
		}

		//Using the sentinelNode, we only need to find the DataNode with the correspondent axis
		SentinelNode sentinelNode = getSentiel(sentinelPos);
		sentinelNode.setIterationAxis(iterAxis);
		//Loop through all Nodes present in this sentinelNode, circularList of this sentinel
		for (BaseNode baseNode : sentinelNode) {
			//when we find a datanode
			if (baseNode instanceof DataNode) {
				// lets confirm its a datanode
				DataNode dataNode = (DataNode) baseNode;
				//if axis of datanode is the same as iteration axis
				if (dataNode.getAxis(iterAxis) == iterPos)
					//then yes, this datanode is in row x and column y, return its value
					return dataNode.getValue();
			}
		}
		return 0;
	}

	// Determines the number of DataNodes present in this.sparsematrix
	// Used in Tests.java
	public int numberDataNodes() {
		int result = 0;
		for (DataNode dataNode : this) {
			result++;
		}
		return result;
	}

	//get size of this.sparsematrix
	public SparseMatrix sizeOf() {
		return new SparseMatrix(this.rows, this.columns);
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public SentinelNode getHeadSentinel() {
		return this.sentinelHead;
	}

	

	//private methods...
	
	/**
	 * This method is used in the construction of the sparsematrix sentinelNodes
	 * @param sentinelNode - sentinelnode to insert
	 */
	private void addSentinel(SentinelNode sentinelNode) {
		// if the matrix doesnt have any sentinelHead
		if (sentinelHead == null) {
			// then create a sentinelHead
			this.sentinelHead = sentinelNode;
			// put next as itself
			this.sentinelHead.setNext(this.sentinelHead);
		} else {
			//otherwise, next element will be the element in the argument
			sentinelNode.setNext(this.sentinelHead.getNext());
			// circular loop. 
			this.sentinelHead.setNext(sentinelNode);
		}
	}

	// Find i-th sentinel
	/**
	 * Used in this.getXY to find the closest sentinelNode
	 * @param n - position of the sentinelNode in the sparse matrix
	 * @return - returns sentinelNode found
	 */
	private SentinelNode getSentiel(int n) {
		SentinelNode current = this.sentinelHead;

		for (int i = 0; i < n; i++) {
			// while we are not in the row or desired column
			// go to next sentinel
			current = current.getNext();
		}
		// return foud sentinelNode
		return current;
	}
	// This way its possible to insert DataNodes given their axis
	private void insertByAxis(DataNode dataNode, String axis, int axisPos, int oppositePos) {
		SentinelNode head = getSentiel(axisPos);
		BaseNode current = head;
		DataNode next;

		while (true) {
			// if next Node is a sentinelNode, then we have higher index. Insert in last
			if (current.getNextByAxis(axis) == head) {
				insertBetween(dataNode, current, head, axis);
				break;
			} else {
				next = (DataNode) current.getNextByAxis(axis);

				if (next.getAxis(axis) > oppositePos) {
					// check if next DataNode has higher index
					insertBetween(dataNode, current, next, axis);
					break;
				} else if (next.getAxis(axis) == oppositePos) {
					// in this case, we only need to edit the existing Node
					next.setValue(dataNode.getValue());
					break; 
				}
				current = next;
			}
		}
	}

	/**
	 * Permits inserting a datanode between two other BaseNodes
	 * @param dataNode - datanode to insert
	 * @param before - BaseNode before
	 * @param after - BaseNode after
	 * @param axis - axis to insert dataNode
	 */
	private void insertBetween(DataNode dataNode, BaseNode before, BaseNode after, String axis) {
		dataNode.setNextByAxis(after, axis);
		before.setNextByAxis(dataNode, axis);
	}
	@Override
	public Iterator<DataNode> iterator() {
		return new SparseMatrixIterator(this);
	}

	@Override
	public String toString() {
		return "SparseMatrix{" + "rows=" + rows + ", columns=" + columns + '}';
	}
}

/**
 * Allows looping through all DataNodes of the sparsematrix using the sentinelNodes
 */
class SparseMatrixIterator implements Iterator<DataNode> {

	private SentinelNode sentinelHead;
	private SentinelNode currentSentinel;
	private Iterator<BaseNode> sentinelIterator;
	private DataNode next;

	public SparseMatrixIterator(SparseMatrix sparseMatrix) {
		//get sentinelHead
		this.sentinelHead = sparseMatrix.getHeadSentinel();
		// sentinelNode is the head obtained previously
		this.currentSentinel = this.sentinelHead;
		// get closest datanode
		advance();
	}

	private void advance() {
		if (this.sentinelIterator == null) {
			this.currentSentinel.setIterationAxis("y");
			//iterate over all baseNodes
			this.sentinelIterator = ((BaseNode) this.currentSentinel).iterator();
		}

		while (true) {
			// if there is next element
			if (this.sentinelIterator.hasNext()) {
				BaseNode baseNode = this.sentinelIterator.next();
				// and is a datanode
				if (baseNode instanceof DataNode) {
					// then next node is a datanode
					this.next = (DataNode) baseNode;
					return;
				}
			} else {
				// otherwise next will be itself
				this.currentSentinel = this.currentSentinel.getNext();

				// but if we have already found head previously
				if (this.currentSentinel == this.sentinelHead) {
					// return and exit
					next = null;
					return;
				} else {
					// otherwise neew iterator
					this.currentSentinel.setIterationAxis("y");
					//ireate over all basenodes
					this.sentinelIterator = ((BaseNode) currentSentinel).iterator();
				}
			}
		}
	}

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public DataNode next() {
		DataNode next = this.next;
		advance();
		return next;
	}
}