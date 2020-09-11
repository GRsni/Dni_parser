package uca.grsni.dniparser;

import processing.core.PApplet;

public class Warning {
	PApplet parent;
	String content;
	int fadeout, maxLife;
	float rectWidth, rectHeight;

	public Warning(PApplet parent, String content, int fadeoutTime) {
		this.parent = parent;
		this.content = content;
		this.maxLife = this.fadeout = fadeoutTime;
		this.rectWidth = parent.textWidth(content) + 30;
		this.rectHeight = 50;
	}

	Warning(PApplet parent, String content) {
		this(parent, content, 100);
	}

	public void show() {
		parent.push();
		parent.rectMode(PApplet.CENTER);
		parent.fill(50);
		parent.rect(parent.width / 2, parent.height / 2, rectWidth, rectHeight);
		parent.fill(255);
		parent.textAlign(PApplet.CENTER);
		parent.text(content, parent.width / 2, parent.height / 2 + 10);
		showBar();
		parent.pop();
	}

	private void showBar() {
		parent.push();
		parent.stroke(0xff44b81a);
		parent.strokeWeight(5);
		parent.strokeCap(PApplet.SQUARE);
		float barLength = PApplet.map(fadeout, maxLife, 0, rectWidth, 0);
		parent.line((parent.width - rectWidth) / 2, parent.height / 2 + rectHeight / 2 - 2,
				(parent.width - rectWidth) / 2 + barLength, parent.height / 2 + rectHeight / 2 - 2);
		parent.pop();
	}

	public void update() {
		fadeout--;

	}

	public boolean toDestroy() {
		return fadeout <= 0;
	}
}
