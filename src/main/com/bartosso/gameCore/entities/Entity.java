package bartosso.gameCore.entities;

import bartosso.IO.Input;

import java.awt.*;

public abstract class Entity {

    private final EntityType type;

    float x;
    float y;

    Entity(EntityType type, float x, float y){
        this.type = type;
        this.x    = x;
        this.y    = y;
    }

    protected abstract void update(Input input);

    protected abstract void render(Graphics2D graphics2D);

}
