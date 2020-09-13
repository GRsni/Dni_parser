package uca.grsni.dniparser;

import processing.core.PApplet;
import processing.core.PVector;
import uca.grsni.dniparser.DniParser.COLORS;

class Button {
	private PApplet parent;
	private String title = "", content = "";
	public PVector pos;
	public float w = 100, h = 30;
	private int cornerR;
	private boolean clicked = false;

	public Button(PApplet parent, PVector pos, String title, String content) {
		this(parent, pos, title, content, parent.textWidth(content) + 30, 30, 3);
	}

	public Button(PApplet parent, PVector pos, String title, String content, float w, float h, int corner) {
		this.parent = parent;
		this.pos = pos;
		this.title = title;
		this.content = content;
		this.w = w;
		this.h = h;
		this.cornerR = corner;
	}

	public void show() {
		renderButton();
		renderTitle();
		renderContent();
	}

	private void renderButton() {
		parent.push();
		parent.strokeWeight(1);
		parent.noStroke();
		parent.rectMode(PApplet.CENTER);
		if (clicked) {
			parent.fill(COLORS.ACCENT);
		} else {
			parent.fill(COLORS.PRIMARY);
		}
		parent.rect(pos.x, pos.y, w, h, cornerR);
		parent.pop();
	}

	private void renderTitle() {
		parent.push();
		parent.textSize(14);
		parent.textFont(DniParser.font_small);
		parent.fill(0);
		parent.textAlign(PApplet.LEFT, PApplet.BOTTOM);
		parent.text(title, pos.x + 10, pos.y - 5);
		parent.pop();
	}

	private void renderContent() {
		parent.push();
		parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		parent.textSize(15);
		parent.textFont(DniParser.font_small);
		parent.fill(255);
		parent.text(content, pos.x, pos.y);
		parent.pop();
	}

	public boolean inside(float x, float y) {
		return x > pos.x - w / 2 && x < pos.x + w / 2 && y > pos.y - h / 2 && y < pos.y + h / 2;
	}

	public void isClicked(boolean state) {
		clicked = state;
	}
}
