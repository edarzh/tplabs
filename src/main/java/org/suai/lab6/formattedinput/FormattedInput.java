package org.suai.lab6.formattedinput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FormattedInput {

	public static Object[] scanf(String format) {
		List<Object> vals = new ArrayList<>();

		StringTokenizer formatTokens = new StringTokenizer(format);
		Scanner scan = new Scanner(System.in);

		boolean inputIsCorrect = true;

		while (formatTokens.hasMoreTokens() && inputIsCorrect) {
			String curToken = formatTokens.nextToken();

			switch (curToken.charAt(1)) {
				case 'd' -> {
					if (scan.hasNextInt()) {
						vals.add(scan.nextInt());
					} else {
						inputIsCorrect = false;
					}
				}
				case 's' -> {
					if (scan.hasNext()) {
						vals.add(scan.next());
					} else {
						inputIsCorrect = false;
					}
				}
				case 'c' -> {
					if (scan.hasNext()) {
						vals.add(scan.next()
										 .charAt(0));
					} else {
						inputIsCorrect = false;
					}
				}
				case 'f' -> {
					if (scan.hasNextDouble()) {
						vals.add(scan.nextDouble());
					} else {
						inputIsCorrect = false;
					}
				}
			}
		}

		if (!inputIsCorrect) {
			throw new IllegalArgumentException("Bad format.");
		}

		return vals.toArray();
	}

	public static Object[] sscanf(String format, String in) {
		List<Object> vals = new ArrayList<>();

		StringTokenizer formatTokens = new StringTokenizer(format);
		Scanner scan = new Scanner(in);

		boolean inputIsCorrect = true;

		while (formatTokens.hasMoreTokens() && inputIsCorrect) {
			String curToken = formatTokens.nextToken();

			switch (curToken.charAt(1)) {
				case 'd' -> {
					if (scan.hasNextInt()) {
						vals.add(scan.nextInt());
					} else {
						inputIsCorrect = false;
					}
				}
				case 's' -> {
					if (scan.hasNext()) {
						vals.add(scan.next());
					} else {
						inputIsCorrect = false;
					}
				}
				case 'c' -> {
					if (scan.hasNext()) {
						vals.add(scan.next()
										 .charAt(0));
					} else {
						inputIsCorrect = false;
					}
				}
				case 'f' -> {
					if (scan.hasNextDouble()) {
						vals.add(scan.nextDouble());
					} else {
						inputIsCorrect = false;
					}
				}
			}
		}

		if (!inputIsCorrect) {
			throw new IllegalArgumentException("Bad format.");
		}

		return vals.toArray();
	}
}