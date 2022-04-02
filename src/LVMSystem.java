import java.util.UUID;

public class LVMSystem {
    private String name;
    private UUID UUID;

    public LVMSystem(String name) {
        this.name = name;
        UUID = UUID.randomUUID();
    }

    public UUID getUUID() { return UUID; }

    public String getName() {
        return name;
    }

    public void setUUID(UUID nUUID)
    {
        UUID = nUUID;
    }

}
