import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class Tests {
	SparseMatrix matrix = new SparseMatrix(10, 100);
	SparseMatrix matrix2 = new SparseMatrix(10, 100);
	SparseMatrix matrix3 = new SparseMatrix(10, 100);
	SparseMatrix matrix4 = new SparseMatrix(10, 100);
	SparseMatrix matrix5 = new SparseMatrix(10, 100);
	private SparseMatrixService sparseMatrixService = new SparseMatrixService();

	@Test
	public void testMatrixConstructor() {
		matrix.add(0, 0, 1);
		matrix.add(2, 2, 3);
		assertEquals(matrix.getXY(0, 0), 1);
		assertEquals(matrix.getXY(2, 2), 3);
	}

	@Test
	public void testMultiply() {
		matrix.add(0, 0, 1);
		matrix.add(2, 2, 3);
		matrix2.add(0, 0, 2);
		matrix2.add(2, 2, 6);
		matrix4.add(0, 0, 100);
		matrix4.add(2, 2, 300);
		matrix5.add(0, 0, -7);
		matrix5.add(2, 2, -21);
		assertTrue(EqualSparseMatrixMultiply(matrix, matrix2, 2));
		assertTrue(EqualSparseMatrixMultiply(matrix, matrix3, 0));
		assertTrue(EqualSparseMatrixMultiply(matrix, matrix4, 100)); 
		assertTrue(EqualSparseMatrixMultiply(matrix, matrix, 1)); 
	}

	@Test
	public void testAdd() {
		matrix.add(0, 0, 1);
		matrix.add(2, 2, 3);
		matrix.add(99, 9, -2);

		matrix2.add(0, 0, 1);
		matrix2.add(99, 9, 2);

		matrix4.add(0, 0, 2);
		matrix4.add(2, 2, 3);

		assertTrue(EqualSparseMatrixSum(matrix, matrix2, matrix4));

		matrix3.add(9, 9, -10);
		matrix3.add(1, 2, 3);
		matrix3.add(0, 0, 501);

		matrix5.add(0, 0, 502);
		matrix5.add(1, 2, 3);
		matrix5.add(2, 2, 3);
		matrix5.add(9, 9, -10);
		matrix5.add(99, 9, -2);

		assertTrue(EqualSparseMatrixSum(matrix, matrix3, matrix5));

	}

	@Test
	public void testTranspose() { 	
		List<String> rawData = new ArrayList<String>();
		List<String> rawData2 = new ArrayList<String>();
		rawData.add("10 100 3");
		rawData.add("1 0 1");
		rawData.add("2 4 3");
		rawData.add("9 78 -8123");
		rawData.add("t");
		rawData2.add("100 10 3");
		rawData2.add("0 1 1");
		rawData2.add("4 2 3");
		rawData2.add("78 9 -8123");
		SparseMatrixReader sparseMatrixReader = new SparseMatrixReader(rawData);
		SparseMatrix sparseMatrix = sparseMatrixReader.processOperation(rawData);
		rawData.clear();
		rawData.add(sparseMatrix.getRows() + " " + sparseMatrix.getColumns() + " " + sparseMatrix.numberDataNodes());
		// System.out.println(sparseMatrix.getRows() + " " +
		// sparseMatrix.getColumns() + " " + sparseMatrix.numberDataNodes());
		for (DataNode dataNode : sparseMatrix) {
			rawData.add(dataNode.getRow() + " " + dataNode.getCol() + " " + dataNode.getValue());
			// System.out.println(dataNode.getRow() + " " + dataNode.getCol() +
			// " " + dataNode.getValue());
		}
		assertTrue(rawData.equals(rawData2));

	}

	@Test
	public void testFinalResult() throws FileNotFoundException, UnsupportedEncodingException { 
		
		String[] files = new String[] { "sample1.txt", "sample2.txt", "sample3.txt", "sample4.txt", "sample5.txt" };
		List<String> datacheck1 = readFile("check1.txt");
		List<String> datacheck2 = readFile("check2.txt");
		List<String> datacheck3 = readFile("check3.txt");
		List<String> datacheck4 = readFile("check4.txt");
		List<String> datacheck5 = readFile("check5.txt");
		int i = 0;

		for (String file : files) {
			i++;
			List<String> rawData = readFile(file);
			List<String> output = new ArrayList<String>();
			System.out.println(rawData + " " + "====>>> CORRETO");
			SparseMatrixReader sparseMatrixReader = new SparseMatrixReader(rawData);

			SparseMatrix sparseMatrix = sparseMatrixReader.processOperation(rawData);
			output.add(sparseMatrix.getRows() + " " + sparseMatrix.getColumns() + " " + sparseMatrix.numberDataNodes());
			for (DataNode dataNode : sparseMatrix) {
				output.add(dataNode.getRow() + " " + dataNode.getCol() + " " + dataNode.getValue());
			}
			String outputfilename = new String("sampleoutput" + "" + i + ".txt");
			PrintWriter writer = new PrintWriter(outputfilename, "UTF-8");
			writer.println(output);
			writer.close();
		}
		
		List<String> dataoutput1 = readFile("sampleoutput1.txt");
		List<String> dataoutput2 = readFile("sampleoutput2.txt");
		List<String> dataoutput3 = readFile("sampleoutput3.txt");
		List<String> dataoutput4 = readFile("sampleoutput4.txt");
		List<String> dataoutput5 = readFile("sampleoutput5.txt");
		assertTrue(dataoutput1.equals(datacheck1));
		assertTrue(dataoutput2.equals(datacheck2));
		assertTrue(dataoutput3.equals(datacheck3));
		assertTrue(dataoutput4.equals(datacheck4));
		assertTrue(dataoutput5.equals(datacheck5));

	}

	private static List<String> readFile(String fileName) {
		List<String> linhas = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				linhas.add(sCurrentLine);
			}
			return linhas;
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Unable to open/read file");
	}

	private boolean EqualMatrix(SparseMatrix matrix, SparseMatrix matrix2) {
		List<DataNode> nonZeroes = new ArrayList<>();
		List<DataNode> nonZeroes2 = new ArrayList<>();
		for (DataNode dataNode : matrix) {
			nonZeroes.add(dataNode);
		}
		for (DataNode dataNode : matrix2) {
			nonZeroes2.add(dataNode);
		}
		if (nonZeroes.equals(nonZeroes2)) {
			return true;
		}
		return false;
	}

	private boolean EqualsLists(List<Integer> list, List<Integer> list2) { 
		for (int i = 0; i < list.size(); i++) {
			int k = list.get(i);
			int k1 = list2.get(i);
			if (k != k1) {
				return false;
			}
		}
		return true;
	}

	private boolean EqualSparseMatrixMultiply(SparseMatrix matrix, SparseMatrix matrix2, int scalar) {
		matrix = sparseMatrixService.multiplyByScalar(matrix, scalar);
		List<Integer> nonZeroes = new ArrayList<>();
		List<Integer> nonZeroes2 = new ArrayList<>();
		for (DataNode dataNode : matrix) {
			nonZeroes.add(dataNode.getValue());
		}
		for (DataNode dataNode : matrix2) {
			nonZeroes2.add(dataNode.getValue());
		}
		if (EqualsLists(nonZeroes, nonZeroes2)) {
			return true;
		}
		return false;

	}

	private boolean EqualSparseMatrixSum(SparseMatrix matrix, SparseMatrix matrix2, SparseMatrix matrix3) {
		matrix = sparseMatrixService.add(matrix, matrix2);
		List<Integer> nonZeroes = new ArrayList<>();
		List<Integer> nonZeroes2 = new ArrayList<>();
		for (DataNode dataNode : matrix) {
			nonZeroes.add(dataNode.getValue());
		}
		for (DataNode dataNode : matrix3) {
			nonZeroes2.add(dataNode.getValue());
		}
		if (EqualsLists(nonZeroes, nonZeroes2)) {
			return true;
		}
		return false;

	}

	private boolean checkTranspose(DataNode k, DataNode p) {
		if (k.getCol() == p.getRow() && k.getRow() == p.getCol() && k.getValue() == p.getValue()) {
			return true;
		}
		return false;
	}

	private boolean EqualSparseMatrixTranspose(SparseMatrix matrix, SparseMatrix matrix2) {
		List<DataNode> nonZeroes = new ArrayList<>();
		List<DataNode> nonZeroes2 = new ArrayList<>();
		int i = 0;
		for (DataNode dataNode : matrix) {
			nonZeroes.add(dataNode);
		}
		for (DataNode dataNode : matrix2) {
			nonZeroes2.add(dataNode);
		}
		for (DataNode dataNode : nonZeroes) {
			if (!checkTranspose(nonZeroes.get(i), nonZeroes2.get(i))) {
				return false;
			}
			i++;
		}
		return true;
	}

}
