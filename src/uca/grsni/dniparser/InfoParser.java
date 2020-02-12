package uca.grsni.dniparser;

import java.io.File;

import processing.core.PApplet;
import processing.data.JSONObject;

import uca.grsni.dniparser.Aviso;
import uca.grsni.dniparser.DniParser;

class InfoParser {
	PApplet parent;
	File text, json;

	public InfoParser(PApplet parent) {
		this.parent = parent;
	}

	void setTextFile(File file) {
		this.text = file;
	}

	void setJSONFile(File file) {
		this.json = file;
	}

	void createNewJSONFromText() {
		String[] lines;
		try {
			lines = getTextLines(text);
		} catch (NullPointerException e) {
			DniParser.avisos.add(new Aviso(parent, "Debes cargar un archivo de texto.", 80));
			return;
		}
		JSONObject json = new JSONObject();
		JSONObject users = new JSONObject();
		JSONObject ids = new JSONObject();

		int startLine = isFirstLineValid(lines);

		for (int i = startLine; i < lines.length; i++) {
			if (checkForID(lines[i])) {
				String dni = lines[i];
				addUserToJSON(users, ids, dni);
			}
		}

		addAnonUserToJSON(users, ids);
		parent.println(createRandomIndex());
		ids.setString(createRandomIndex(), "u99999999");

		json.setJSONObject("Users", users);
		json.setJSONObject("Ids", ids);

		parent.saveJSONObject(json, "data/database" + System.currentTimeMillis() + ".json");
		DniParser.avisos.add(new Aviso(parent, "Archivo para la base de datos generado", 100));
		parent.println("Database file generated");
	}

	void appendNewAlumniToJSONFile() {
	}

	int isFirstLineValid(String[] lines) {
		int startLine = 0;
		if (!checkForID(lines[0])) {
			startLine = 1;
		}
		return startLine;
	}

	boolean checkForID(String line) {
		char[] lineCharArray = line.toLowerCase().toCharArray();
		return lineCharArray[0] == 'u';
	}

	String[] getTextLines(File file) {
		return parent.loadStrings(file);
	}

	void addUserToJSON(JSONObject userList, JSONObject idList, String uId) {
		JSONObject user = new JSONObject();
		user.setString("uId", uId);
		user.setInt("numP", 0);

		userList.setJSONObject(uId, user);
		idList.setString(createRandomIndex(), uId);
	}

	void addAnonUserToJSON(JSONObject list, JSONObject idList) {
		addUserToJSON(list, idList, "u99999999");
	}

	String createRandomIndex() {
		return Long.toString(System.nanoTime()) + (int) (parent.random(1000000));
	}
}
