public class LogicalVolume extends LVMSystem {
    private int gb;

    public LogicalVolume(String name, int gb)
    {
        super(name);
        this.gb = gb;
    }

    public int getGb() {
        return gb;
    }

}
