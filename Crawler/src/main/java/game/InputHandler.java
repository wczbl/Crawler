package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    private static final List<Key> keys = new ArrayList<>();
    public Key up = new Key(KeyEvent.VK_W, KeyEvent.VK_NUMPAD8, KeyEvent.VK_UP);
    public Key down = new Key(KeyEvent.VK_S, KeyEvent.VK_NUMPAD2, KeyEvent.VK_DOWN);
    public Key left = new Key(KeyEvent.VK_A, KeyEvent.VK_NUMPAD4, KeyEvent.VK_LEFT);
    public Key right = new Key(KeyEvent.VK_D, KeyEvent.VK_NUMPAD6, KeyEvent.VK_RIGHT);
    public Key turnLeft = new Key(KeyEvent.VK_Q);
    public Key turnRight = new Key(KeyEvent.VK_E);
    public Key pause = new Key(KeyEvent.VK_P, KeyEvent.VK_PAUSE, KeyEvent.VK_ESCAPE);
    public Key ability = new Key(KeyEvent.VK_R, KeyEvent.VK_V, KeyEvent.VK_X);
    public Key attack = new Key(KeyEvent.VK_SPACE, KeyEvent.VK_C, KeyEvent.VK_Z);

    public int x;
    public int y;

    public boolean leftClicked;
    public boolean leftPressed;
    private boolean m0p;

    public boolean rightClicked;
    public boolean rightPressed;
    private boolean m1p;

    public InputHandler(Canvas canvas) {
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) { toggle(e, true); }
    public void keyReleased(KeyEvent e) { toggle(e, false); }
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        x = e.getX() / GameComponent.SCALE;
        y = e.getY() / GameComponent.SCALE;

        m0p = e.getButton() == 1;
        m1p = e.getButton() == 3;
    }

    public void mouseReleased(MouseEvent e) {
        x = e.getX() / GameComponent.SCALE;
        y = e.getY() / GameComponent.SCALE;

        m0p = false;
        m1p = false;
    }

    public void mouseMoved(MouseEvent e) {
        x = e.getX() / GameComponent.SCALE;
        y = e.getY() / GameComponent.SCALE;
    }

    private void toggle(KeyEvent e, boolean pressed) {
        for (Key key : keys) {
            key.toggle(e.getKeyCode(), pressed);
        }
    }

    public void tick() {
        leftClicked = !leftPressed && m0p;
        rightClicked = !rightPressed && m1p;
        leftPressed = m0p;
        rightPressed = m1p;

        for (Key key : keys) {
            key.tick();
        }
    }

    public class Key {
        private List<int[]> keyCodes = new ArrayList<>();
        private int absorbs;
        private int presses;

        public boolean down;
        public boolean clicked;

        public Key(int... codes) {
            keyCodes.add(codes);
            keys.add(this);
        }

        public void tick() {
            if (presses > absorbs) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
            }
        }

        public void toggle(int key, boolean pressed) {
            for (int[] codes : keyCodes) {
                for (int keyCode : codes) {
                    if (keyCode == key) {
                        down = pressed;
                        if (pressed) presses++;
                        return;
                    }
                }
            }
        }

    }

}