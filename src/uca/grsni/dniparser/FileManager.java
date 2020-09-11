package uca.grsni.dniparser;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.data.Table;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class FileManager {
	public DniParser parent;
	private File text, json;
	private File outputFolder;

	public FileManager(DniParser parent) {
		this.parent = parent;
	}

	public void setParserTextFile(File file) {
		this.text = file;
	}

	public void setParserJSONFile(File file) {
		this.json = file;
	}

	public void setOutputFolder(File folder) {
		outputFolder = folder;
	}

	public void createNewJSONFromText() {
		JSONObject fileObject = new JSONObject();
		JSONObject users = new JSONObject();
		JSONObject ids = new JSONObject();

		ArrayList<String> validLines = loadLinesFromTextFile();

		if (validLines == null) {
			parent.addNewWarning("Error cargando los datos de texto.", 100);
			return;
		}

		for (String dni : validLines) {
			addUserToJSON(users, ids, dni);
		}

		// Se añade el usuario anonimo
		addUserToJSON(users, ids, "u99999999");

		fileObject.setJSONObject("Users", users);
		fileObject.setJSONObject("Ids", ids);

		saveJSONFile(fileObject, "data/databaseFile" + System.currentTimeMillis() + ".json",
				"Generado nuevo archivo de datos.");
	}

	public void appendNewStudentsToJSONFile() {
		JSONObject fileObject = loadJSONObjectsFromFile();
		if (fileObject == null) {
			parent.addNewWarning("Error cargando datos de archivo JSON", 100);
			return;
		}

		JSONObject users = fileObject.getJSONObject("Users");
		JSONObject ids = fileObject.getJSONObject("Ids");

		ArrayList<String> validLines = loadLinesFromTextFile();
		if (validLines == null) {
			parent.addNewWarning("Error cargando los datos de texto", 100);
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

	public void createStudentsDataTables() {
		JSONObject fileObject = loadJSONObjectsFromFile();
		if (fileObject == null) {
			parent.addNewWarning("Error cargando datos de archivo JSON", 100);
			return;
		}

		JSONObject users = fileObject.getJSONObject("Users");
		ArrayList<String> idList = getListOfIDsFromJSON(fileObject);

		for (String id : idList) {
			JSONObject studentData = users.getJSONObject(id);

			JSONObject practicas = studentData.getJSONObject("practicas");
			JSONObject torsion = practicas.getJSONObject("torsion");
			if (torsion != null) {
				Table datosTorsion = parseJSONDataIntoTable(torsion);
			}

			JSONObject pandeo = practicas.getJSONObject("pandeo");

		}

		// selectFolder("Elige la carpeta donde guardar los datos",
		// "selectOutputFolder");

	}

	private ArrayList<String> getListOfIDsFromJSON(JSONObject fileObject) {
		ArrayList<String> idList = new ArrayList<String>();
		JSONObject ids = fileObject.getJSONObject("Ids");

		Object[] keys = ids.keys().toArray();

		for (Object key : keys) {
			idList.add(ids.getString((String) key));
		}

		return idList;
	}

	private Table parseJSONDataIntoTable(JSONObject data) {
		Table tabla = new Table();
		Object[] keys = data.keys().toArray();

		for (Object key : keys) {
			tabla.addColumn((String));
		}

		return null;
	}

	private JSONObject loadJSONObjectsFromFile() {
		JSONObject fileObject;
		try {
			fileObject = PApplet.loadJSONObject(json);
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
		return fileObject;
	}

	private ArrayList<String> loadLinesFromTextFile() {
		ArrayList<String> validLines;
		try {
			validLines = InfoParser.getCorrectLines(text);
		} catch (NullPointerException e) {
			System.out.println(e.toString());
			return null;
		}
		return validLines;
	}

	private void addUserToJSON(JSONObject users, JSONObject ids, String uId) {
		JSONObject user = new JSONObject();
		user.setString("uId", uId);
		user.setInt("numP", 0);

		users.setJSONObject(uId, user);
		ids.setString(createRandomIndex(), uId);
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
