package KinomotoSakuraMod.Utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

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
     * @param texture            材质图片
     * @param proportionToBottom 显示比率
     * @return 贴图纹理
     */
    public static TextureAtlas.AtlasRegion GetAtlasRegionBottom(Texture texture, float proportionToBottom)
    {
        int activeHeight = (int) (texture.getHeight() * proportionToBottom);
        return new TextureAtlas.AtlasRegion(texture, 0, texture.getHeight() - activeHeight, texture.getWidth(), activeHeight);
    }

    /**
     * 用Texture材质生成Atlas贴图
     *
     * @param atlasRegion     目标atlas
     * @param backHeight      背图高度
     * @param anchor          底部在背图中的坐标
     * @param proportionToTop 显示比率
     * @return 贴图纹理
     */
    public static TextureAtlas.AtlasRegion SetAtlasRegion(TextureAtlas.AtlasRegion atlasRegion, float backHeight, float anchor, float proportionToTop)
    {
        float backActiveHeight = backHeight * proportionToTop;
        float bottomHeight = backHeight * 0.5F - anchor;
        float topHeight = backHeight * 0.5f + anchor - atlasRegion.getTexture().getHeight();

        if (backActiveHeight >= backHeight * 0.5F + anchor)
        {
            atlasRegion.setRegion(0, 0, atlasRegion.getTexture().getWidth(), atlasRegion.getTexture().getHeight());
        }
        else if (backActiveHeight < backHeight - bottomHeight && backActiveHeight > topHeight)
        {
            int activeHeight = MathUtils.ceil(MathUtils.clamp(backActiveHeight - topHeight, 0F, atlasRegion.getTexture().getHeight()));
            atlasRegion.setRegion(0, 0, atlasRegion.getTexture().getWidth(), activeHeight);
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
     * @param anchor          底部在背图中的坐标
     * @param proportionToTop 显示比率
     * @return 贴图纹理
     */
    public static TextureAtlas.AtlasRegion GetAtlasRegion(Texture texture, float backHeight, float anchor, float proportionToTop)
    {
        float backActiveHeight = backHeight * proportionToTop;
        float bottomHeight = backHeight * 0.5F - anchor;
        float topHeight = backHeight * 0.5f + anchor - texture.getHeight();

        if (backActiveHeight >= backHeight * 0.5F + anchor)
        {
            return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
        }
        else if (backActiveHeight < backHeight - bottomHeight && backActiveHeight > topHeight)
        {
            int activeHeight = (int) MathUtils.clamp(backActiveHeight - topHeight, 0F, texture.getHeight());
            return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), activeHeight);
        }
        else
        {
            return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), 0);
        }
    }
}
