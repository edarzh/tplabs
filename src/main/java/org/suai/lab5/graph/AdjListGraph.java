package org.suai.lab5.graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdjListGraph extends AbstractGraph {
	private final List<List<Integer>> adjList;

	public AdjListGraph(int size) {
		this.size = size;
		this.adjList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			adjList.add(new ArrayList<>());
		}
	}

	public AdjListGraph(String filePath) {
		this.adjList = new ArrayList<>();

		try (Scanner scanFile = new Scanner(new BufferedReader(new FileReader(filePath)))) {
			for (int i = 0; scanFile.hasNextLine(); i++) {
				this.adjList.add(new ArrayList<>());

				Scanner scanLine = new Scanner(scanFile.nextLine());

				while (scanLine.hasNextInt()) {
					this.adjList.get(i).add(scanLine.nextInt());
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

		this.size = adjList.size();
	}

	public void connect(int vertex1, int vertex2) {
		if (!this.adjList.get(vertex1).contains(vertex2)) {
			this.adjList.get(vertex1).add(vertex2);
			this.adjList.get(vertex2).add(vertex1);
		}
	}

	public void disconnect(int vertex1, int vertex2) {
		if (this.adjList.get(vertex1).contains(vertex2)) {
			this.adjList.get(vertex1).remove((Object) vertex2);
			this.adjList.get(vertex2).remove((Object) vertex1);
		}
	}

	public int getEdge(int vertex1, int vertex2) {
		if (this.adjList.get(vertex1).contains(vertex2)) {
			return 1;
		}
		return 0;
	}

	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();

		for (var i : this.adjList) {
			for (var j : i) {
				sb.append(j).append(' ');
			}
			sb.append('\n');
		}

		return sb.toString();
	}
}