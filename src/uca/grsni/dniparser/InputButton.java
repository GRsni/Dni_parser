package uca.grsni.dniparser;

import processing.core.PVector;
import processing.core.PApplet;

class InputButton {
	private PApplet parent;
	private String filename = "";

	private Button inputB;
	//TODO: add PVector to inputButton
	public InputButton(PApplet parent, PVector pos, String title, String content) {
		PVector buttonPos=new PVector(pos.x, pos.y+15);
		inputB = new Button(parent, buttonPos, title, content, 120, 30, 0);
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
		parent.text(filename, inputB.pos.x-5 + inputB.w + 10, inputB.pos.y + inputB.h / 2 - 2);
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
	
	public void isClicked(boolean state) {
		inputB.isClicked(state);
	}

	public void setFileName(String filename) {
		this.filename = filename;
	}

	public String getFileName() {
		return filename;
	}
}
