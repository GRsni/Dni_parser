package uca.grsni.dniparser;

import processing.core.PApplet;
import processing.core.PVector;

class Button {
	private PApplet parent;
	private String title = "", content = "";
	public PVector pos;
	public float w = 100, h = 30;
	private int cornerR;

	public Button(PApplet parent, PVector pos, String title, String content) {
		this(parent, pos, title, content, 100, 30, 0);
	}

	public Button(PApplet parent, PVector pos, String title, String content, float w, float h, int corner) {
		this.parent = parent;
		this.pos = pos;
		this.title = title;
		this.content = content;
		this.w = w;
		this.h = h;
		this.cornerR=corner;
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
		parent.fill(0, 133, 120);
		parent.rect(pos.x, pos.y, w, h, cornerR);
		parent.pop();
	}

	private void renderTitle() {
		parent.push();
		parent.textSize(14);
		parent.fill(0);
		parent.textAlign(PApplet.LEFT, PApplet.BOTTOM);
		parent.text(title, pos.x + 10, pos.y - 5);
		parent.pop();
	}

	private void renderContent() {
		parent.push();
		parent.textAlign(PApplet.CENTER);
		parent.textSize(15);
		parent.fill(255);
		parent.text(content, pos.x + w / 2, pos.y + h / 2 + 4);
		parent.pop();
	}

	public boolean inside(float x, float y) {
		return x > pos.x && x < pos.x + w && y > pos.y && y < pos.y + h;
	}
}
