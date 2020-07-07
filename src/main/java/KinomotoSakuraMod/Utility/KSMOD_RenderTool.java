package KinomotoSakuraMod.Utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class KSMOD_RenderTool
{
    /**
     * 用Texture材质生成Atlas贴图
     *
     * @param texture 材质图片
     * @return 贴图纹理
     */
    public static TextureAtlas.AtlasRegion GetAtlasRegion(Texture texture)
    {
        return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
    }

    /**
     * 重设atlas
     *
     * @param atlasRegion     目标atlas资源
     * @param proportionToTop 显示比率
     * @return 贴图纹理
     */
    public static TextureAtlas.AtlasRegion SetAtlasRegion(TextureAtlas.AtlasRegion atlasRegion, float proportionToTop)
    {
        int activeHeight = (int) (atlasRegion.getTexture().getHeight() * proportionToTop);
        atlasRegion.setRegion(0, 0, atlasRegion.getTexture().getWidth(), activeHeight);
        return atlasRegion;
    }

    /**
     * 用Texture材质生成Atlas贴图
     *
     * @param texture         材质图片
     * @param proportionToTop 显示比率
     * @return 贴图纹理
     */
    public static TextureAtlas.AtlasRegion GetAtlasRegion(Texture texture, float proportionToTop)
    {
        int activeHeight = (int) (texture.getHeight() * proportionToTop);
        return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), activeHeight);
    }

    /**
     * 用Texture材质生成Atlas贴图
     *
     * @param atlasRegion     目标atlas
     * @param backHeight      背图高度
     * @param bottomAnchor    底部在背图中的坐标
     * @param proportionToTop 显示比率
     * @return 贴图纹理
     */
    public static TextureAtlas.AtlasRegion SetAtlasRegion(TextureAtlas.AtlasRegion atlasRegion, float backHeight, float bottomAnchor, float proportionToTop)
    {
        float backActiveHeight = backHeight * proportionToTop;
        if (backActiveHeight >= backHeight - bottomAnchor)
        {
            atlasRegion.setRegion(0, 0, atlasRegion.getTexture().getWidth(), atlasRegion.getTexture().getHeight());
        }
        else if (backActiveHeight < backHeight - bottomAnchor && backActiveHeight > backHeight - bottomAnchor - atlasRegion.getTexture().getHeight())
        {
            float activeHeight = atlasRegion.getTexture().getHeight() * proportionToTop - (backHeight - bottomAnchor - atlasRegion.getTexture().getHeight());
            atlasRegion.setRegion(0, 0, atlasRegion.getTexture().getWidth(), (int) activeHeight);
        }
        else
        {
            atlasRegion.setRegion(0, 0, atlasRegion.getTexture().getWidth(), 0);
        }
        return atlasRegion;
    }

    /**
     * 用Texture材质生成Atlas贴图
     *
     * @param texture         材质图片
     * @param backHeight      背图高度
     * @param bottomAnchor    底部在背图中的坐标
     * @param proportionToTop 显示比率
     * @return 贴图纹理
     */
    public static TextureAtlas.AtlasRegion GetAtlasRegion(Texture texture, float backHeight, float bottomAnchor, float proportionToTop)
    {
        float backActiveHeight = backHeight * proportionToTop;
        if (backActiveHeight >= backHeight - bottomAnchor)
        {
            return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
        }
        else if (backActiveHeight < backHeight - bottomAnchor && backActiveHeight > backHeight - bottomAnchor - texture.getHeight())
        {
            float activeHeight = texture.getHeight() * proportionToTop - (backHeight - bottomAnchor - texture.getHeight());
            return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), (int) activeHeight);
        }
        else
        {
            return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), 0);
        }
    }
}
