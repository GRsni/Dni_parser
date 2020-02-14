package uca.grsni.dniparser;

import processing.core.PApplet;

public class Warning {
	PApplet parent;
	String content;
	int fadeout, max_life;

	Warning(PApplet parent, String content, int fadeout) {
		this.parent = parent;
		this.content = content;
		this.fadeout = fadeout;
		this.max_life = fadeout;
	}

	Warning(PApplet parent, String content) {
		this(parent, content, 100);
	}

	public void show() {
		parent.push();
		parent.rectMode(PApplet.CENTER);
		parent.stroke(211, 81, 81, PApplet.map(fadeout, max_life, 0, 255, 0));
		parent.strokeWeight(5);
		parent.fill(50, calculateContentFill());
		parent.rect(parent.width / 2, parent.height / 2, parent.textWidth(content) + 15, 50);
		parent.fill(255, calculateContentFill());
		parent.textAlign(PApplet.CENTER);
		parent.text(content, parent.width / 2, parent.height / 2 + 10);
		parent.pop();
	}

	public void update() {
		fadeout--;
	}

	private int calculateContentFill() {
		if (fadeout > max_life / 2) {
			return 255;
		} else {
			return (int) PApplet.map(fadeout, max_life / 2, 0, 255, 0);
		}
	}

	public boolean toDestroy() {
		return fadeout <= 0;
	}
}
