package uca.grsni.dniparser;

import processing.core.PApplet;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.io.File;
import java.util.ArrayList;

public class FileManager {
	public DniParser parent;
	private InfoParser parser;

	JSONObject fileObject, users;
	JSONArray ids;

	public FileManager(DniParser parent) {
		this.parent = parent;
		parser = new InfoParser(parent);
	}

	public void setParserTextFile(File file) {
		parser.setTextFile(file);
	}

	public void setParserJSONFile(File file) {
		parser.setJSONFile(file);
	}

	public void createNewJSONFromText() {
		fileObject = new JSONObject();
		users = new JSONObject();
		ids = new JSONArray();
		ArrayList<String> validLines;

		try {
			validLines = parser.getCorrectLines();
		} catch (NullPointerException e) {
			parent.addNewWarning("Debes cargar un archivo de texto.", 80);
			return;
		}

		for (String dni : validLines) {
			addUserToJSON(users, ids, dni);
		}

		addAnonUserToJSON(users, ids);

		fileObject.setJSONObject("Users", users);
		fileObject.setJSONArray("Ids", ids);

		parent.saveJSONObject(fileObject, "data/databaseFile.json");
		parent.addNewWarning("Archivo para la base de datos generado", 100);
		PApplet.println("Database file generated");
	}

	public void appendNewAlumniToJSONFile() {
		try {
			fileObject = PApplet.loadJSONObject(parser.json);
			users=fileObject.getJSONObject("Users");
			ids=fileObject.getJSONArray("Ids");
		} catch (Exception e) {
			PApplet.print(e.toString());
			parent.addNewWarning("Debes cargar un archivo .json.", 80);
		}
		

	}

	private void addUserToJSON(JSONObject users, JSONArray ids, String uId) {
		JSONObject user = new JSONObject();
		user.setString("uId", uId);
		user.setInt("numP", 0);

		
		users.setJSONObject(uId, user);
		ids.append(uId);
	}

	private void addAnonUserToJSON(JSONObject users, JSONArray ids) {
		addUserToJSON(users, ids, "u99999999");
	}

	private String createRandomIndex() {
		return Long.toString(System.nanoTime()) + (int) (parent.random(1000000));
	}
}
