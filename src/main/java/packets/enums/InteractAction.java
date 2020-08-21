package packets.enums;

public enum InteractAction
{

    INTERACT(0),
    ATTACK(1),
    INTERACT_AT(2);


    private int type;
    InteractAction(int type)
    {
        this.type = type;
    }
}
