package kiloboltgame;

//http://www.kilobolt.com/unit-2-creating-a-game-i.html

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import kiloboltgame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {

	private Robot men;
	private Heliboy hb, hb2;
	private Image image, currentSprite, character, character2, character3, character4,character5,character6,
			characterDown, characterJumped, background, heliboy, heliboy2,
			heliboy3, heliboy4, heliboy5;
	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	private Animation anim, hanim;
	private int bgW = 2400;
	private int bgH = 480;

	public int getBgW() {
		return bgW;
	}

	public void setBgW(int bgW) {
		this.bgW = bgW;
	}

	public int getBgH() {
		return bgH;
	}

	public void setBgH(int bgH) {
		this.bgH = bgH;
	}

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Fazendo a Feira");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		character = getImage(base, "data/men8_01.png");
		character2 = getImage(base, "data/men8_02.png");
		character3 = getImage(base, "data/men8_03.png");
		character4 = getImage(base, "data/men8_04.png");
		character5 = getImage(base, "data/men8_05.png");
		character6 = getImage(base, "data/men8_06.png");
		

		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");

		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");

		background = getImage(base, "data/backgroundOK.png");

		anim = new Animation();
		anim.addFrame(character, 100);
		anim.addFrame(character2, 100);
		anim.addFrame(character3, 100);
		anim.addFrame(character4, 100);
		anim.addFrame(character5, 100);
		anim.addFrame(character6, 100);
		

		hanim = new Animation();
		hanim.addFrame(heliboy, 110);
		hanim.addFrame(heliboy2, 110);
		hanim.addFrame(heliboy3, 110);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);

		currentSprite = anim.getImage();
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2400, 0);
		hb = new Heliboy(540, 260);
		hb2 = new Heliboy(750, 260);
		men = new Robot();

		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		while (true) {
			men.update();
			if (men.isJumped()) {
				currentSprite = characterJumped;
			} else if (men.isJumped() == false && men.isDucked() == false) {
				currentSprite = anim.getImage();
			}

			ArrayList projectiles = men.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				if (p.isVisible() == true) {
					p.update();
				} else {
					projectiles.remove(i);
				}
			}

			hb.update();
			hb2.update();
			bg1.update();
			bg2.update();
			animate();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void animate() {
		anim.update(10);
		hanim.update(50);
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);

		ArrayList projectiles = men.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}

		g.drawImage(currentSprite, men.getCenterX() - 61,
				men.getCenterY() - 63, this);
		g.drawImage(hanim.getImage(), hb.getCenterX() - 48, hb.getCenterY() - 48, this);
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48, hb2.getCenterY() - 48, this);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Move up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			if (men.isJumped() == false) {
				men.setDucked(true);
				men.setSpeedX(0);
			}
			break;

		case KeyEvent.VK_LEFT:
			men.moveLeft();
			men.setMovingLeft(true);
			break;

		case KeyEvent.VK_RIGHT:
			men.moveRight();
			men.setMovingRight(true);
			break;

		case KeyEvent.VK_SPACE:
			men.jump();
			break;

		case KeyEvent.VK_CONTROL:
			if (men.isDucked() == false && men.isJumped() == false) {
				men.shoot();
			}
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Stop moving up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = anim.getImage();
			men.setDucked(false);
			break;

		case KeyEvent.VK_LEFT:
			men.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			men.stopRight();
			break;

		case KeyEvent.VK_SPACE:
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

}