import java.util.UUID;

public class PhysicalVolume extends LVMSystem {
    private PhysicalDrive pd;
    private VolumeGroup vg;

    public PhysicalVolume(String name, PhysicalDrive pd) {
        super(name);
        this.pd = pd;
    }

    public PhysicalDrive getPd() {
        return pd;
    }

    public VolumeGroup getVg() {
        return vg;
    }

    public void setVg(VolumeGroup vg) {
        this.vg = vg;
    }

   // public int getGb() {
    //    return pd.getGb();
   // }
}
