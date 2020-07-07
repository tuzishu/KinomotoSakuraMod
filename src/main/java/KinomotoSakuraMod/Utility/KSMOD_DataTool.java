package KinomotoSakuraMod.Utility;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

public class KSMOD_DataTool
{
    /**
     * 通过输入一个伤害值，来返回一个用于DamageAllEnemy的伤害列表
     *
     * @param damage int 伤害值
     * @return int[] 伤害列表
     */
    public static int[] GetDamageList(int damage)
    {
        int size = AbstractDungeon.getMonsters().monsters.size();
        int[] damageList = new int[size];
        for (int i = 0; i < size; i++)
        {
            damageList[i] = damage;
        }
        return damageList;
    }

    /**
     * String类型的ArrayList列表中，是否包含目标字串
     *
     * @param stringList 目标数组
     * @param targetStr  目标字符串
     * @return 是否存在
     */
    public static boolean IsStringListContains(ArrayList<String> stringList, String targetStr)
    {
        for (String cardClassName : stringList)
        {
            if (cardClassName.equals(targetStr))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回List中的一个随机元素
     *
     * @param arrayList 目标数组
     * @param <T>       元素类型
     * @return 获取到的随机元素
     */
    public static <T> T GetRandomListElement(ArrayList<T> arrayList)
    {
        if (arrayList.size() > 0)
        {
            return arrayList.get(new Random().random(0, arrayList.size() - 1));
        }
        return null;
    }
}
