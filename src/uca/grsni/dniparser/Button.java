package uca.grsni.dniparser;

import processing.core.PApplet;
import processing.core.PVector;

class Button {
	PApplet parent;
	String title = "", content = "";
	PVector pos;
	float w = 100, h = 30;

	Button(PApplet parent, PVector pos, String title, String content) {
		this(parent, pos, title, content, 100, 30);
	}

	Button(PApplet parent, PVector pos, String title, String content, float w, float h) {
		this.parent = parent;
		this.pos = pos;
		this.title = title;
		this.content = content;
		this.w = w;
		this.h = h;
	}

	void show() {
		renderButton();
		renderTitle();
		renderContent();
	}

	void renderButton() {
		parent.push();
		parent.strokeWeight(1);
		parent.stroke(255);
		parent.fill(0);
		parent.rect(pos.x, pos.y, w, h);
		parent.pop();
	}

	void renderTitle() {
		parent.push();
		parent.textSize(14);
		parent.fill(0);
		parent.textAlign(parent.LEFT, parent.BOTTOM);
		parent.text(title, pos.x + 10, pos.y - 5);
		parent.pop();
	}

	void renderContent() {
		parent.push();
		parent.textAlign(parent.CENTER);
		parent.textSize(14);
		parent.fill(255);
		parent.text(content, pos.x + w / 2, pos.y + h / 2 + 4);
		parent.pop();
	}

	boolean inside(float x, float y) {
		return x > pos.x && x < pos.x + w && y > pos.y && y < pos.y + h;
	}
}
