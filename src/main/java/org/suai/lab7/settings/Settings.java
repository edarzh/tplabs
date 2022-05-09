package org.suai.lab7.settings;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Settings {
	private final Map<String, Integer> data;

	public Settings() {
		this.data = new HashMap<>();
	}

	public void put(String parameter, int value) {
		this.data.putIfAbsent(parameter, value);
	}

	public int get(String parameter) {
		return this.data.get(parameter);
	}

	public void set(String parameter, int value) {
		this.data.replace(parameter, value);
	}

	public void delete(String parameter) {
		this.data.remove(parameter);
	}

	public void loadFromTextFile(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String content = br.lines()
					.collect(Collectors.joining())
					.replaceAll("[{}\":,]", "");

			Scanner scan = new Scanner(content);
			while (scan.hasNext()) {
				this.put(scan.next(), scan.nextInt());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveToTextFile(String fileName) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
			bw.write(this.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadFromBinaryFile(String fileName) {
		try (Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(fileName)),
										StandardCharsets.UTF_16)) {
			while (scan.hasNext()) {
				this.put(scan.next(), scan.nextInt());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void saveToBinaryFile(String fileName) {
		try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
			for (HashMap.Entry<String, Integer> pair : this.data.entrySet()) {
				dos.writeChars(pair.getKey());
				dos.writeChar(' ');
				dos.writeChars(pair.getValue()
									   .toString());
				dos.writeChar(' ');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{\n");

		for (HashMap.Entry<String, Integer> pair : this.data.entrySet()) {
			sb.append("\t\"")
					.append(pair.getKey())
					.append("\": ")
					.append(pair.getValue())
					.append(",\n");
		}

		int trailingCommaIndex = sb.lastIndexOf(",");
		if (trailingCommaIndex != -1) {
			sb.deleteCharAt(trailingCommaIndex);
		}

		sb.append('}');

		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (this.getClass() != o.getClass()) {
			return false;
		}

		return this.data.equals(((Settings) o).data);
	}

	@Override
	public int hashCode() {
		return this.data.hashCode() * 31;
	}
}