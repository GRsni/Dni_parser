package uca.grsni.dniparser;

import java.io.File;
import java.util.ArrayList;

import processing.core.PApplet;

class InfoParser {
	DniParser parent;
	File text, json;

	public InfoParser(DniParser parent) {
		this.parent = parent;
	}

	public void setTextFile(File file) {
		this.text = file;
	}

	public File getTextFile() {
		return text;
	}

	public void setJSONFile(File file) {
		this.json = file;
	}

	public File getJSONFile() {
		return json;
	}

	public ArrayList<String> getCorrectLines() {
		ArrayList<String> goodLines = new ArrayList<String>();

		String[] lines = getLinesFromText();
		for (int i = 0; i < lines.length; i++) {
			String possibleId = findValidIDInLine(lines[i]);
			if (!possibleId.equals("")) {
				goodLines.add(possibleId);
			}
		}

		return goodLines;
	}

	public String[] getLinesFromText() {
		return PApplet.loadStrings(text);
	}

	private String findValidIDInLine(String line) {
		return UserIdExtractor.extract(line.toLowerCase());
	}

}
