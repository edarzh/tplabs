package org.suai.lab7.graph;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class StoreGraph {
	final int[][] matrix;
	final int size;

	public StoreGraph(int size) {
		this.size = size;
		this.matrix = new int[size][size];
	}

	public void loadFromTextFile(File file) {
		try (Scanner input = new Scanner(new BufferedReader(new FileReader(file)))) {

			for (int i = 0; i < this.size; i++) {
				for (int j = 0; j < this.size; j++) {
					this.matrix[i][j] = input.nextInt();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveToTextFile(File file) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.write(this.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadFromBinaryFile(File file) {
		try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
			for (int i = 0; i < this.size; i++) {
				for (int j = 0; j < this.size; j++) {
					this.matrix[i][j] = dis.readInt();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveToBinaryFile(File file) {
		try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
			for (int i = 0; i < this.size; i++) {
				for (int j = 0; j < this.size; j++) {
					dos.writeInt(this.matrix[i][j]);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				sb.append(this.matrix[i][j]).append(' ');
			}
			sb.append('\n');
		}

		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		StoreGraph that = (StoreGraph) o;

		if (size != that.size) {
			return false;
		}
		return Arrays.deepEquals(matrix, that.matrix);
	}

	@Override
	public int hashCode() {
		int result = Arrays.deepHashCode(matrix);
		result = 31 * result + size;
		return result;
	}
}