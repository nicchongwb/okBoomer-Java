package entities.items;

import entities.Entity;
import okBoomer.Handler;

public abstract class Item extends Entity {
    public static final int DEFAULT_ITEM_WIDTH = 64,
            DEFAULT_ITEM_HEIGHT = 64;

    public Item(Handler handler, int x, int y, int width, int height) {
        super(handler, x, y, width, height);
    }

}
