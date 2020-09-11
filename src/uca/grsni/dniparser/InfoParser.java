package uca.grsni.dniparser;

import java.io.File;
import java.util.ArrayList;

import processing.core.PApplet;

class InfoParser {
	DniParser parent;

	public InfoParser(DniParser parent) {
		this.parent = parent;
	}

	public static ArrayList<String> getCorrectLines(File text) {
		ArrayList<String> goodLines = new ArrayList<String>();

		String[] lines = getLinesFromText(text);
		for (int i = 0; i < lines.length; i++) {
			String possibleId = findValidIDInLine(lines[i]);
			if (!possibleId.equals("")) {
				goodLines.add(possibleId);
			}
		}

		return goodLines;
	}

	public static String[] getLinesFromText(File text) {
		return PApplet.loadStrings(text);
	}

	private static String findValidIDInLine(String line) {
		return UserIdExtractor.extract(line.toLowerCase());
	}

}
