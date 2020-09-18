package uca.grsni.dniparser;

import processing.core.*;

import java.io.File;
import java.util.ArrayList;

public class DniParser extends PApplet {
	public static PFont font_small, font_big;
	private static InputButton textHandler, JSONHandler, folderHandler;
	public static boolean LOADED_FONTS = true;

	private static Button createNewFile, addStudents, extractDataFile, exitButton;
	public static FileManager manager;
	private static ArrayList<Warning> avisos = new ArrayList<Warning>();

	public static void main(String[] args) {
		PApplet.main(new String[] { uca.grsni.dniparser.DniParser.class.getName() });
	}

	public void settings() {
		size(800, 500);
	}

	public void setup() {
		surface.setTitle("Manual de laboratorio: Gestor de datos");
		manager = new FileManager(this);
		initFonts();
		initButtons();
	}

	private void initButtons() {
		textHandler = new InputButton(this, new PVector(30, 350), "", "Archivo .txt");
		JSONHandler = new InputButton(this, new PVector(30, 400), "", "Archivo .json");
		folderHandler = new InputButton(this, new PVector(30, 450), "", "Carpeta");

		createNewFile = new Button(this, new PVector(width / 2, 100), "",
				"Crear nuevo archivo para importar a la base de datos");
		addStudents = new Button(this, new PVector(width / 2, 150), "", "Añadir alumnos a la base de datos");
		extractDataFile = new Button(this, new PVector(width / 2, 200), "", "Obtener tabla de datos de los alumnos");
		exitButton = new Button(this, new PVector(40, 40), "", "Salir", 40, 30, 3);
	}

	private void initFonts() {
		if (fontExists("../Calibri-14.vlw")) {
			font_small = loadFont("Calibri-14.vlw");
			System.out.println("14 exists");
		} else {
			LOADED_FONTS = false;
		}
		if (fontExists("../Calibri-30.vlw")) {
			font_big = loadFont("Calibri-30.vlw");
		} else {
			LOADED_FONTS = false;
		}
		System.out.println("LOADED: " + LOADED_FONTS);
	}

	public void draw() {
		background(255);

		drawMenu();

		showWarnings();
		updateWarnings();
	}

	private void drawMenu() {
		push();
		fill(0);
		textAlign(CENTER, TOP);
		textSize(30);
		useTextFont(font_big);
		text("Elige la opción adecuada: ", width / 2, 30);
		pop();

		drawButtons();
	}

	private void drawButtons() {
		textHandler.show();
		JSONHandler.show();
		folderHandler.show();

		createNewFile.show();
		addStudents.show();
		extractDataFile.show();
		exitButton.show();
	}

	public void addNewWarning(String content, int lifetime) {
		avisos.add(new Warning(this, content));
	}

	private void showWarnings() {
		if (avisos.size() > 0) {
			avisos.get(0).show();
		}
	}

	private void updateWarnings() {
		if (avisos.size() > 0) {
			Warning a = avisos.get(0);
			a.update();
			if (a.toDestroy()) {
				avisos.remove(a);
			}
		}
	}

	public void mouseClicked() {
		if (textHandler.inside(mouseX, mouseY)) {
			selectInput("Elige el archivo de texto:", "selectTextFile");
		}
		if (JSONHandler.inside(mouseX, mouseY)) {
			selectInput("Elige el archivo JSON:", "selectJSONFile");
		}
		if (folderHandler.inside(mouseX, mouseY)) {
			selectFolder("Elige la carpeta de destino:", "selectOutputFolder");
		}
		if (createNewFile.inside(mouseX, mouseY)) {
			manager.createNewJSONFromText();
		}
		if (addStudents.inside(mouseX, mouseY)) {
			manager.appendNewStudentsToJSONFile();
		}
		if (extractDataFile.inside(mouseX, mouseY)) {
			manager.createStudentsDataTables();
		}
		if (exitButton.inside(mouseX, mouseY)) {
			exit();
		}
	}

	public void mousePressed() {
		if (textHandler.inside(mouseX, mouseY)) {
			textHandler.isClicked(true);
		}
		if (JSONHandler.inside(mouseX, mouseY)) {
			JSONHandler.isClicked(true);
		}
		if (folderHandler.inside(mouseX, mouseY)) {
			folderHandler.isClicked(true);
		}
		if (createNewFile.inside(mouseX, mouseY)) {
			createNewFile.isClicked(true);
		}
		if (addStudents.inside(mouseX, mouseY)) {
			addStudents.isClicked(true);
		}
		if (extractDataFile.inside(mouseX, mouseY)) {
			extractDataFile.isClicked(true);
		}
		if (exitButton.inside(mouseX, mouseY)) {
			exitButton.isClicked(true);
		}
	}

	public void mouseReleased() {
		if (textHandler.inside(mouseX, mouseY)) {
			textHandler.isClicked(false);
		}
		if (JSONHandler.inside(mouseX, mouseY)) {
			JSONHandler.isClicked(false);
		}
		if (folderHandler.inside(mouseX, mouseY)) {
			folderHandler.isClicked(false);
		}
		if (createNewFile.inside(mouseX, mouseY)) {
			createNewFile.isClicked(false);
		}
		if (addStudents.inside(mouseX, mouseY)) {
			addStudents.isClicked(false);
		}
		if (extractDataFile.inside(mouseX, mouseY)) {
			extractDataFile.isClicked(false);
		}
		if (exitButton.inside(mouseX, mouseY)) {
			exitButton.isClicked(false);
		}
	}

	public void keyPressed() {
		if (key == '1') {
			manager.createNewJSONFromText();
		} else if (key == '2') {
			manager.appendNewStudentsToJSONFile();
		} else if (key == '3') {
			manager.createStudentsDataTables();
		}
	}

	public void selectTextFile(File selection) {
		if (selection == null) {
			println("No file selected");
			addNewWarning("Archivo no seleccionado.", 100);
		} else {
			String filePath = selection.getAbsolutePath();
			println("user selected: " + filePath);
			if (checkFileExtension(filePath, ".txt")) {
				textHandler.setFileName(selection.getName());
				manager.setParserTextFile(selection);
			} else {
				addNewWarning("Error al leer el archivo de texto.", 150);
			}
		}
	}

	public void selectJSONFile(File selection) {
		if (selection == null) {
			println("No file selected");
			addNewWarning("Archivo no seleccionado.", 100);
		} else {
			String filePath = selection.getAbsolutePath();
			println("user selected: " + filePath);
			if (checkFileExtension(filePath, ".json")) {
				JSONHandler.setFileName(selection.getName());
				manager.setParserJSONFile(selection);
			} else {
				addNewWarning("Error al leer el archivo .json de la base de datos.", 150);
			}
		}
	}

	public void selectOutputFolder(File folder) {
		if (folder == null) {
			println("No folder selected");
			addNewWarning("Carpeta no seleccionada.", 100);
		} else {
			String folderPath = folder.getAbsolutePath();
			println("user selected folder:" + folderPath);
			if (folder.isDirectory()) {
				manager.setOutputFolder(folder);
				folderHandler.setFileName(folder.getName());
			}
		}
	}

	private boolean checkFileExtension(String file, String ext) {
		int lastIndex = file.lastIndexOf(".");
		if (lastIndex == -1) {
			return false;
		} else {
			String extension = file.substring(lastIndex);
			return extension.equals(ext);
		}
	}

	public void useTextFont(PFont font) {
		if (DniParser.LOADED_FONTS) {
			textFont(font);
		}
	}

	public static boolean fontExists(String filename) {
		File file = new File(filename);
		return file.exists();
	}

	class COLORS {
		public final static int PRIMARY = 0xff008577;
		public final static int PRIMARY_DARK = 0xff003d35;
		public final static int ACCENT = 0xffaaaaaa;
	}
}
