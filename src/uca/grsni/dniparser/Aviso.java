package uca.grsni.dniparser;

import processing.core.PApplet;

public class Aviso {
	PApplet parent;
	String content;
	int fadeout, max_life;

	Aviso(PApplet parent, String content, int fadeout) {
		this.parent = parent;
		this.content = content;
		this.fadeout = fadeout;
		this.max_life = fadeout;
	}

	Aviso(PApplet parent, String content) {
		this(parent, content, 100);
	}

	void show() {
		parent.push();
		parent.rectMode(parent.CENTER);
		parent.stroke(0xD35151, parent.map(fadeout, max_life, 0, 255, 0));
		parent.strokeWeight(5);
		parent.fill(50, calculateContentFill());
		parent.rect(parent.width / 2, parent.height / 2, parent.textWidth(content) + 15, 50);
		parent.fill(255, calculateContentFill());
		parent.textAlign(parent.CENTER);
		parent.text(content, parent.width / 2, parent.height / 2 + 10);
		parent.pop();
	}

	void update() {
		fadeout--;
	}

	int calculateContentFill() {
		if (fadeout > max_life / 2) {
			return 255;
		} else {
			return (int) parent.map(fadeout, max_life / 2, 0, 255, 0);
		}
	}

	boolean toDestroy() {
		return fadeout <= 0;
	}
}
