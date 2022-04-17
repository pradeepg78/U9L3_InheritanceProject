public class PhysicalDrive {
    private String name;
    private int gb;
    private PhysicalVolume pv;

    public PhysicalDrive(String name,int gb) {
        this.name = name;
        this.gb = gb;
    }

    public String getName() {
        return name;
    }

    public int getGb() {
        return gb;
    }

    public PhysicalVolume getPv() { return pv; }

    public void setPv(PhysicalVolume pv)
    {
        this.pv = pv;
    }
}
