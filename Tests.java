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
		// n vale a pena tar a por mais valores aqui, se um funciona entao claro
		// que os outros funcionam, e so fazer gets...

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
		assertTrue(EqualSparseMatrixMultiply(matrix, matrix2, 2)); // Multiplicar
																	// por 2
		assertTrue(EqualSparseMatrixMultiply(matrix, matrix3, 0)); // Multiplicar
																	// por 0
		assertTrue(EqualSparseMatrixMultiply(matrix, matrix4, 100)); // Multiplicar
																		// por
																		// 100
		assertTrue(EqualSparseMatrixMultiply(matrix, matrix5, -7)); // Multiplicar
																	// por -7
		assertTrue(EqualSparseMatrixMultiply(matrix, matrix, 1)); // Verificar
																	// se o
																	// output
																	// vem com
																	// ordem
																	// correta,
																	// caso de
																	// vezes 1

		// todos os casos possiveis presentes aqui
		// inteiro positivo, inteiro negativo e zero, e multiplicar por 1 a
		// ordem mantemse
		// pronto ok, por testar 4 nao significa que se verifique em todos os
		// numeros existentes
		// mas e estupido estar a testar mil numeros, o conceito vai ser sempre
		// o mesmo so muda os datanodes.getvalue()
	}

	@Test
	public void testAdd() {
		// atencao que consideramos y,x e nao x,y,
		// formas de interpretar o enunciado
		matrix.add(0, 0, 1);
		matrix.add(2, 2, 3);
		matrix.add(99, 9, -2);

		matrix2.add(0, 0, 1);
		matrix2.add(99, 9, 2);

		matrix4.add(0, 0, 2);
		matrix4.add(2, 2, 3);
		// caso em que a soma resulta em 0, neste caso nao se faz print ao 99 9
		// 0
		assertTrue(EqualSparseMatrixSum(matrix, matrix2, matrix4));

		matrix3.add(9, 9, -10);
		matrix3.add(1, 2, 3);
		matrix3.add(0, 0, 501);

		matrix5.add(0, 0, 502);
		matrix5.add(1, 2, 3);
		matrix5.add(2, 2, 3);
		matrix5.add(9, 9, -10);
		matrix5.add(99, 9, -2);
		// caso "normal" somou-se um elemento em comum: 0 0 1 com 0 0 501
		// o resto foi adicionar mais nodes
		assertTrue(EqualSparseMatrixSum(matrix, matrix3, matrix5));

	}

	@Test
	public void testTranspose() { // n ssei mt bem como fazer o teste disto, mas
									// pronto funciona XD
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
		//Bem isto basicamente foi 90% stackoverflow este metodo
		// mas resumidamente, os sample.txt contem os inputs
		// os outputs expectaveis estao nos checks.txt
		// corro o programa e meto o resultado nos outputs.txt
		// comparo o conteudo dos files check e output
		// se for true, funciona
		// se for false nao funciona, OBVIAMENTE XD
		
		//se quiser por mais testes, cria-se um novo sample.txt
		//um novo check.txt contendo o output expectavel
		//e penso eu que e criado automaticamente um output.txt , mas em todo caso cria-se um vazio
		// coloca-se o sample.txt dentro da string [] files, e o cria-se uma nova lista datacheck com o check.txt
		// o resto e simples
		
		//todos estes ficheiros irao encontrarse numa pasta "ficheiros_txt_teste" dentro do zip que acompanha este relatorio
		
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
			PrintWriter writer = new PrintWriter(outputfilename, "UTF-8"); //stack overflow
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
		//aqui esta um belo problema!! tal como em p2 nao sabemos o porque
		// de ao comparar duas listas com exatamente os mesmos elementos da false
		// dai a necessidade de fazer um metodo para comparar o conteudo de duas listas
		// o professor tiago candeias falou-me a mim (Pedro Martins) que talvez fosse de hashcode
		// mas sinceramente nao sei o que isso e
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
