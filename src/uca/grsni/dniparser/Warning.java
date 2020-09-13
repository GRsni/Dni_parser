package uca.grsni.dniparser;

import processing.core.PApplet;
import processing.core.PVector;
import uca.grsni.dniparser.DniParser.COLORS;

public class Warning {
	PApplet parent;
	String content;
	PVector pos;
	float w, h;
	int fadeout, max_life;

	public Warning(PApplet parent, String content, PVector pos, float w, float h, int fadeout) {
		this.parent = parent;
		this.content = content;
		this.fadeout = fadeout;
		this.max_life = fadeout;
		this.pos = pos;
		this.w = w;
		this.h = h;
	}

	Warning(PApplet parent, String content) {
		this(parent, content, new PVector(parent.width / 2, parent.height / 2), parent.textWidth(content) + 15, 35,
				100);
	}

	public void show() {
		renderBox();
		renderLifeBar();
		renderContent();
	}

	private void renderBox() {
		parent.push();
		parent.rectMode(PApplet.CENTER);
		parent.stroke(COLORS.PRIMARY);
		parent.fill(COLORS.PRIMARY);
		parent.rect(pos.x, pos.y, w, h);
		parent.pop();
	}

	private void renderLifeBar() {
		parent.push();
		parent.strokeWeight(2);
		parent.stroke(COLORS.PRIMARY_DARK);
		parent.strokeWeight(5);
		parent.strokeCap(PApplet.SQUARE);
		float yOffset = h / 2 - 2;
		parent.line(pos.x - w / 2, pos.y + yOffset, pos.x + calcLineBarLength() - w / 2, pos.y + yOffset);
		parent.pop();
	}

	public void renderContent() {
		parent.push();
		parent.fill(255);
		parent.textAlign(PApplet.CENTER, PApplet.BOTTOM);
		parent.textFont(DniParser.font_small);
		parent.textSize(14);
		parent.text(content, parent.width / 2, parent.height / 2 + 10);
		parent.pop();
	}

	public void update() {
		fadeout--;
	}

	private float calcLineBarLength() {
		return PApplet.map(fadeout, max_life, 0, w, 0);
	}

	public boolean toDestroy() {
		return fadeout <= 0;
	}
}
