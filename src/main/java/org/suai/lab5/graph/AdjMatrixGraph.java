package org.suai.lab5.graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AdjMatrixGraph extends AbstractGraph {
	private int[][] adjMatrix;

	public AdjMatrixGraph(int size) {
		this.size = size;
		this.adjMatrix = new int[size][size];
	}

	public AdjMatrixGraph(String filePath) {
		try (Scanner scanFile = new Scanner(new BufferedReader(new FileReader(filePath)));
			 Scanner scanFirstLine = new Scanner(new BufferedReader(new FileReader(filePath)))) {
			StringTokenizer FirstLine = new StringTokenizer(scanFirstLine.nextLine());
			this.size = FirstLine.countTokens();
			this.adjMatrix = new int[this.size][this.size];

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (scanFile.hasNextInt()) {
						adjMatrix[i][j] = scanFile.nextInt();
					} else {
						throw new IllegalArgumentException("Bad input file.");
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

	}

	public void connect(int vertex1, int vertex2) {
		this.adjMatrix[vertex1][vertex2] = 1;
		this.adjMatrix[vertex2][vertex1] = 1;
	}

	public void disconnect(int vertex1, int vertex2) {
		this.adjMatrix[vertex1][vertex2] = 0;
		this.adjMatrix[vertex2][vertex1] = 0;
	}

	public int getEdge(int row, int column) {
		return this.adjMatrix[row][column];
	}

	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				sb.append(this.getEdge(i, j)).append(" ");
			}
			sb.append('\n');
		}

		return sb.toString();
	}
}