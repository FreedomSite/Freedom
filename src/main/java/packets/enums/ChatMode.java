package packets.enums;

public enum ChatMode
{
    ENABLED(0),
    COMMANDS_ONLY(1),
    HIDDEN(2);

    private int type;
    ChatMode(int type)
    {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static ChatMode findByInt(int i)
    {
        for (ChatMode values : ChatMode.values())
        {
            if (values.type == i)
            {
                return values;
            }
        }
        return null;
    }
}
