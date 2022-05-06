package org.suai.lab6.encodingconverter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class EncodingConverter {

	public static void convertEncoding(String inFile, String inFileEncoding, String outFile, String outFileEncoding) {
		try (Reader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(inFile)), inFileEncoding);
			 Writer writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(outFile)), outFileEncoding)) {

			int c;

			while ((c = reader.read()) >= 0) {
				writer.write(c);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFromFile(String fileName) {
		StringBuilder sb = new StringBuilder();

		try (Scanner scanFile = new Scanner(new File(fileName))) {
			while (scanFile.hasNextLine()) {
				sb.append(scanFile.nextLine()).append('\n');
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

	public static void writeToFile(String fileName, String data) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}