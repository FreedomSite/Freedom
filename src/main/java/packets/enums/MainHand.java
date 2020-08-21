package packets.enums;

public enum MainHand
{
    LEFT(0),
    RIGHT(1);

    private int type;
    MainHand(int type)
    {
        this.type = type;
    }

    public static MainHand findByInt(int i)
    {
        for (MainHand values : MainHand.values())
        {
            if (values.type == i)
            {
                return values;
            }
        }
        return null;
    }

}
