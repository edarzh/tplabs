package org.suai.lab5.graph;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractGraph implements Graph {
	protected int size;

	@Override
	public abstract void connect(int vertex1, int vertex2);

	@Override
	public abstract void disconnect(int vertex1, int vertex2);

	@Override
	public abstract int getEdge(int vertex1, int vertex2);

	@Override
	public final int getSize() {
		return this.size;
	}

	@Override
	public final void makeGraph(String filePath) {
		StringBuilder sb = new StringBuilder();

		sb.append("graph {\n");

		for (int i = 0; i < this.size - 1; i++) {
			for (int j = 1 + i; j < this.size; j++) {
				if (this.getEdge(i, j) == 1) {
					sb.append('\t')
							.append(i)
							.append(" -- ")
							.append(j)
							.append(";\n");
				}
			}
		}
		sb.append('}');

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(sb.toString());
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("IOException caught.");
			e.printStackTrace();
		}
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Graph other = (Graph) obj;

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.getEdge(i, j) != other.getEdge(i, j)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public final int hashCode() {
		int hash = 0;

		for (int i = 0; i < this.size; i++) {
			hash += this.getEdge(0, i) * 31;
		}

		return hash;
	}
}