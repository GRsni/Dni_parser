package uca.grsni.dniparser;

import processing.core.PVector;
import processing.core.PApplet;

class InputButton {
	private DniParser parent;
	private String filename = "";
	private PVector pos;

	private Button inputB;
	public InputButton(DniParser parent, PVector pos, String title, String content) {
		PVector buttonPos=new PVector(pos.x+80, pos.y+15);
		inputB = new Button(parent, buttonPos, title, content, 120, 30, 0);
		this.pos=pos;
		this.parent = parent;
	}

	public void show() {
		inputB.show();
		renderFileName();
	}

	private void renderFileName() {
		renderContainerBox();
		parent.push();
		parent.textAlign(PApplet.LEFT, PApplet.CENTER);
		parent.textSize(14);
		parent.useTextFont(DniParser.font_small);
		parent.text(filename, pos.x-5 + inputB.w + 10, pos.y + inputB.h / 2 - 2);
		parent.pop();
	}

	private void renderContainerBox() {
		parent.push();
		parent.fill(170);
		parent.noStroke();
		parent.rect(pos.x + inputB.w, pos.y, 200, 30);
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
