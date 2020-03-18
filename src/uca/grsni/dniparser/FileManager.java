package uca.grsni.dniparser;

import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class FileManager {
	public DniParser parent;
	private InfoParser parser;

	JSONObject fileObject;
	ArrayList<String> validLines;

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
		JSONObject users = new JSONObject();
		JSONObject ids = new JSONObject();

		validLines = new ArrayList<String>();

		if (!loadLinesFromTextFile()) {
			return;
		}

		for (String dni : validLines) {
			addUserToJSON(users, ids, dni);
		}

		addAnonUserToJSON(users, ids);

		fileObject.setJSONObject("Users", users);
		fileObject.setJSONObject("Ids", ids);

		saveJSONFile(fileObject, "data/databaseFile" + System.currentTimeMillis() + ".json",
				"Generado nuevo archivo de datos.");
	}

	public void appendNewAlumniToJSONFile() {

		if (!loadJSONObjectsFromFile()) {
			return;
		}

		JSONObject users = fileObject.getJSONObject("Users");
		JSONObject ids = fileObject.getJSONObject("Ids");

		if (!loadLinesFromTextFile()) {
			return;
		}

		for (String dni : validLines) {
			if (fileObject.getJSONObject("Users").isNull(dni)) {
				addUserToJSON(users, ids, dni);
			}
		}
		saveJSONFile(fileObject, "data/databaseFile" + System.currentTimeMillis() + ".json",
				"Añadidos nuevos alumnos al archivo.");

	}

	private boolean loadLinesFromTextFile() {

		try {
			validLines = parser.getCorrectLines();
		} catch (NullPointerException e) {
			System.out.println(e.toString());
			parent.addNewWarning("Error al cargar el archivo de texto.", 80);
			return false;
		}
		return true;
	}

	private boolean loadJSONObjectsFromFile() {
		try {
			fileObject = PApplet.loadJSONObject(parser.getJSONFile());
		} catch (Exception e) {
			System.out.println(e.toString());
			parent.addNewWarning("Error al cargar el archivo JSON.", 80);
			return false;
		}
		return true;
	}

	private void addUserToJSON(JSONObject users, JSONObject ids, String uId) {
		JSONObject user = new JSONObject();
		user.setString("uId", uId);
		user.setInt("numP", 0);

		users.setJSONObject(uId, user);
		ids.setString(createRandomIndex(), uId);
	}

	private void addAnonUserToJSON(JSONObject users, JSONObject ids) {
		addUserToJSON(users, ids, "u99999999");
	}

	private String createRandomIndex() {
		return Long.toString(System.nanoTime()) + (int) (parent.random(1000000));
	}	

	private void saveJSONFile(JSONObject object, String filename, String warningContent) {
		parent.saveJSONObject(object, filename);
		parent.addNewWarning(warningContent, 100);
		System.out.println("Database file generated");
	}

}
