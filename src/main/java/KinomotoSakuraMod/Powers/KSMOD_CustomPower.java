package KinomotoSakuraMod.Powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class KSMOD_CustomPower extends AbstractPower
{
    public KSMOD_CustomPower(String id, String name, String img, PowerType powerType, AbstractCreature target, int amount)
    {
        this.ID = id;
        this.name = name;
        this.img = new Texture(img);
        this.type = powerType;
        this.owner = target;
        this.amount = amount;
        this.updateDescription();
    }

    public KSMOD_CustomPower(String id, String name, String imgPath, PowerType powerType, AbstractCreature target, int amount, boolean useCustomTexture)
    {
        this.ID = id;
        this.name = name;
        this.type = powerType;
        this.owner = target;
        this.amount = amount;
        this.updateDescription();
        if (useCustomTexture)
        {
            Texture image48 = ImageMaster.loadImage(imgPath);
            this.region48 = new TextureAtlas.AtlasRegion(image48, 0, 0, image48.getWidth(), image48.getHeight());
            Texture image128 = ImageMaster.loadImage(imgPath.replaceAll(".png", "_p.png"));
            this.region128 = new TextureAtlas.AtlasRegion(image128, 0, 0, image128.getWidth(), image128.getHeight());
        }
        else
        {
            this.loadRegion(imgPath);
        }
    }
}
