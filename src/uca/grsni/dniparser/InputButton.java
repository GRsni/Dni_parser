package uca.grsni.dniparser;

import processing.core.PVector;
import processing.core.PApplet;

class InputButton {
	PApplet parent;
	String filename = "";

	Button inputB;

	InputButton(PApplet parent, PVector pos, String title, String content) {
		inputB = new Button(parent, pos, title, content, 120, 30);
		this.parent = parent;
	}

	void show() {
		inputB.show();
		renderFileName();
	}

	void renderFileName() {
		renderFileNameBox();
		parent.push();
		parent.textAlign(parent.LEFT, parent.CENTER);
		parent.textSize(14);
		parent.text(filename, inputB.pos.x + inputB.w + 10, inputB.pos.y + inputB.h / 2 - 2);
		parent.pop();
	}

	void renderFileNameBox() {
		parent.push();
		parent.fill(100);
		parent.noStroke();
		parent.rect(inputB.pos.x + inputB.w + 1, inputB.pos.y, 200, 30);
		parent.pop();
	}

	boolean inside(float x, float y) {
		return inputB.inside(x, y);
	}

	void setFileName(String filename) {
		this.filename = filename;
	}

	String getFileName() {
		return filename;
	}
}
