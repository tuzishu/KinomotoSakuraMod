package KinomotoSakuraMod.Powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class CustomPower extends AbstractPower
{
    public CustomPower(String id, String name, String img, PowerType powerType, AbstractCreature target)
    {
        this.ID = id;
        this.name = name;
        this.img = new Texture(img);
        this.type = powerType;
        this.owner = target;
    }
}
