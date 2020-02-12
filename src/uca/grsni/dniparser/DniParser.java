package uca.grsni.dniparser;

import processing.core.*;

import java.io.File;
import java.util.ArrayList;

import uca.grsni.dniparser.Aviso;
import uca.grsni.dniparser.Button;
import uca.grsni.dniparser.InputButton;
import uca.grsni.dniparser.InfoParser;

public class DniParser extends PApplet {
	private static InputButton textHandler, JSONHandler;

	private static Button createNewFile, addAlumni, extractDataFile;
	public static InfoParser parser;
	public static ArrayList<Aviso> avisos = new ArrayList<Aviso>();

	public static void main(String[] args) {
		PApplet.main(new String[] { uca.grsni.dniparser.DniParser.class.getName() });
	}

	public void settings() {
		size(800, 500);
	}

	public void setup() {
		parser = new InfoParser(this);
		initButtons();
	}

	public void initButtons() {
		textHandler = new InputButton(this, new PVector(30, 450), "Archivo .txt", "Elige el archivo");
		JSONHandler = new InputButton(this, new PVector(440, 450), "Archivo .json", "Elige el archivo");
		createNewFile = new Button(this, new PVector(700, 100), "", "1", 30, 30);
		addAlumni = new Button(this, new PVector(700, 150), "", "2", 30, 30);
		extractDataFile = new Button(this, new PVector(700, 200), "", "3", 30, 30);
	}

	public void draw() {
		background(255);

		drawMenu();

		updateAvisos();
	}

	public void mouseClicked() {
		if (textHandler.inputB.inside(mouseX, mouseY)) {
			selectInput("Elige el archivo de texto:", "selectTextFile");
		}
		if (JSONHandler.inside(mouseX, mouseY)) {
			selectInput("Elige el archivo JSON:", "selectJSONFile");
		}
		if (createNewFile.inside(mouseX, mouseY)) {
			parser.createNewJSONFromText();
		}
		if (addAlumni.inside(mouseX, mouseY)) {
			parser.appendNewAlumniToJSONFile();
		}
	}

	public void keyPressed() {
		if (key == '1') {// nuevo archivo para DB
			parser.createNewJSONFromText();
		} else if (key == '2') {
			parser.appendNewAlumniToJSONFile();
		} else if (key == '3') {
		}
	}

	private void drawMenu() {
		push();
		fill(0);
		textAlign(CENTER, TOP);
		textSize(30);
		text("Elige la opción adecuada: ", width / 2, 30);
		textSize(20);
		text("Crear nuevo archivo para importar a la base de datos:", width / 2, 100);
		text("Añadir alumnos a la base de datos:", width / 2, 150);
		text("Obtener tabla de datos de los alumnos:", width / 2, 200);
		pop();

		drawButtons();
	}

	private void drawButtons() {
		textHandler.show();
		JSONHandler.show();
		createNewFile.show();
		addAlumni.show();
		extractDataFile.show();
	}

	private void updateAvisos() {
		if (avisos.size() > 0) {
			Aviso a = avisos.get(0);
			a.show();
			a.update();
			if (a.toDestroy()) {
				avisos.remove(a);
			}
		}
	}

	public void selectTextFile(File selection) {
		if (selection == null) {
			println("No file selected");
		} else {
			String filePath = selection.getAbsolutePath();
			println("user selected: " + filePath);
			if (checkFileExtension(filePath, ".txt")) {
				textHandler.setFileName(selection.getName());
				parser.setTextFile(selection);
			} else {
				avisos.add(new Aviso(this, "Error al leer el archivo de texto.", 150));
			}
		}
	}

	public void selectJSONFile(File selection) {
		if (selection == null) {
			println("No file selected");
		} else {
			String filePath = selection.getAbsolutePath();
			println("user selected: " + filePath);
			if (checkFileExtension(filePath, ".json")) {
				JSONHandler.setFileName(selection.getName());
				parser.setJSONFile(selection);
			} else {
				avisos.add(new Aviso(this, "Error al leer el archivo .json de la base de datos.", 150));
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
}
