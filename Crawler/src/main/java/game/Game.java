package game;

import game.gfx.Bitmap;
import game.screen.GameScreen;
import game.screen.Screen;

public class Game {

    private Screen screen;

    public Game() { setScreen(new GameScreen()); }

    public void setScreen(Screen screen) {
        this.screen = screen;
        screen.init(this);
    }

    public void tick(InputHandler input) { screen.tick(input); }
    public void render(Bitmap screen) { this.screen.render(screen); }

}