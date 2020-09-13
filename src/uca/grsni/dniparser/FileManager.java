package uca.grsni.dniparser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.data.JSONObject;
import processing.data.Table;
import processing.data.TableRow;

public class FileManager {
	public DniParser parent;
	private File text, json;
	private File outputFolder;

	private static final String[] PRACTICA_KEYS = { "pId", "fecha", "enLab", "conManual", "datos", "valTeo",
			"ampliTeo", "val1", "val2", "ampliExp", "errPes", "errMat", "resValido", "preg1", "preg2", "preg3",
			"preg4" };

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
		if (outputFolder == null) {
			parent.addNewWarning("Debe seleccionar una carpeta de destino.", 100);
			return;
		}

		String pathToFolder = outputFolder.getAbsolutePath() + File.separator + "datos" + File.separator;

		JSONObject users = fileObject.getJSONObject("Users");
		ArrayList<String> idList = getIdsFromIDList(fileObject.getJSONObject("Ids"));

		for (String id : idList) {
			pathToFolder += id + File.separator;

			JSONObject studentData = users.getJSONObject(id);

			JSONObject practicas = studentData.getJSONObject("practicas");
			if (practicas != null) {
				JSONObject torsion = practicas.getJSONObject("torsion");
				if (torsion != null) {
					Table torsionTable = parseJSONDataIntoTable(torsion);

					parent.saveTable(torsionTable, pathToFolder + "torsion.csv");
				}

				JSONObject pandeo = practicas.getJSONObject("pandeo");
				if (pandeo != null) {
					Table pandeoTable = parseJSONDataIntoTable(pandeo);
					parent.saveTable(pandeoTable, pathToFolder + "pandeo.csv");
				}
			}

		}

	}

	private ArrayList<String> getIdsFromIDList(JSONObject object) {
		ArrayList<String> ids = new ArrayList<String>();
		ArrayList<String> idKeys = getKeysInJSONObject(object);

		for (String key : idKeys) {
			ids.add(object.getString(key));
		}

		return ids;
	}

	private ArrayList<String> getKeysInJSONObject(JSONObject object) {
		ArrayList<String> keys = new ArrayList<String>();

		Object[] keysArray = object.keys().toArray();

		for (Object key : keysArray) {
			keys.add((String) key);
		}
		return keys;
	}

	private Table parseJSONDataIntoTable(JSONObject data) {
		Table table = new Table();
		ArrayList<String> entriesKeys = getKeysInJSONObject(data);
		System.out.println(Arrays.toString(entriesKeys.toArray()));

		for (String key : PRACTICA_KEYS) {
			table.addColumn(key);
			System.out.println("Key: " + key);
		}

		for (String entryKey : entriesKeys) {
			JSONObject entry = data.getJSONObject(entryKey);
			TableRow row = table.addRow();
			turnPracticaItemToTableRow(entry, row);
		}

		return table;
	}

//	private static final String[] PRACTICA_KEYS = { "pId", "fecha", "enLab", "conManual", "datos", "valTeo",
//			"ampliTeo", "val1", "val2", "ampliExp", "errPes", "errMat", "resValido", "preg1", "preg2", "preg3",
//			"preg4" };
//	
	private void turnPracticaItemToTableRow(JSONObject practica, TableRow row) {
		row.setString(PRACTICA_KEYS[0], practica.getString(PRACTICA_KEYS[0])); //pId
		row.setString(PRACTICA_KEYS[1], getDateString(practica.getString(PRACTICA_KEYS[1]))); //fecha
		row.setString(PRACTICA_KEYS[2], Boolean.toString(practica.getBoolean(PRACTICA_KEYS[2])));//enLab
		row.setString(PRACTICA_KEYS[3], Boolean.toString(practica.getBoolean(PRACTICA_KEYS[3])));//conManual
		row.setString(PRACTICA_KEYS[4], practica.getString(PRACTICA_KEYS[4]));//datos
		row.setFloat(PRACTICA_KEYS[5], practica.getFloat(PRACTICA_KEYS[5]));//valTeo
		row.setFloat(PRACTICA_KEYS[6], practica.getFloat(PRACTICA_KEYS[6]));//ampliTeo
		row.setFloat(PRACTICA_KEYS[7], practica.getFloat(PRACTICA_KEYS[7]));//val1
		row.setFloat(PRACTICA_KEYS[8], practica.getFloat(PRACTICA_KEYS[8]));//val2
		row.setFloat(PRACTICA_KEYS[9], practica.getFloat(PRACTICA_KEYS[9]));//ampliExp
		row.setInt(PRACTICA_KEYS[10], practica.getInt(PRACTICA_KEYS[10]));//errPes
		row.setInt(PRACTICA_KEYS[11], practica.getInt(PRACTICA_KEYS[11]));//errMat
		row.setString(PRACTICA_KEYS[12], Boolean.toString(practica.getBoolean(PRACTICA_KEYS[12])));//resValido
		row.setString(PRACTICA_KEYS[13], Boolean.toString(practica.getBoolean(PRACTICA_KEYS[13])));//preg1
		row.setString(PRACTICA_KEYS[14], Boolean.toString(practica.getBoolean(PRACTICA_KEYS[14])));//2
		row.setString(PRACTICA_KEYS[15], Boolean.toString(practica.getBoolean(PRACTICA_KEYS[15])));//3
		row.setString(PRACTICA_KEYS[16], Boolean.toString(practica.getBoolean(PRACTICA_KEYS[16])));//4
		
	}
	
		private String getDateString(String expanded) {
			return expanded.replace(" ", "-").substring(0, 19);
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
