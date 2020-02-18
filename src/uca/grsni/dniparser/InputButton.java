package uca.grsni.dniparser;

import processing.core.PVector;
import processing.core.PApplet;

class InputButton {
	PApplet parent;
	String filename = "";

	Button inputB;

	public InputButton(PApplet parent, PVector pos, String title, String content) {
		inputB = new Button(parent, pos, title, content, 120, 30, 0);
		this.parent = parent;
	}

	public void show() {
		inputB.show();
		renderFileName();
	}

	private void renderFileName() {
		renderFileNameBox();
		parent.push();
		parent.textAlign(PApplet.LEFT, PApplet.CENTER);
		parent.textSize(14);
		parent.text(filename, inputB.pos.x + inputB.w + 10, inputB.pos.y + inputB.h / 2 - 2);
		parent.pop();
	}

	private void renderFileNameBox() {
		parent.push();
		parent.fill(170);
		parent.noStroke();
		parent.rect(inputB.pos.x + inputB.w, inputB.pos.y, 200, 30);
		parent.pop();
	}

	public boolean inside(float x, float y) {
		return inputB.inside(x, y);
	}

	public void setFileName(String filename) {
		this.filename = filename;
	}

	public String getFileName() {
		return filename;
	}
}
