import java.util.*;

public class VolumeGroup extends LVMSystem {
    private int totalSpace;
    private int availableSpace;
    private ArrayList<PhysicalVolume> pvList = new ArrayList<PhysicalVolume>();
    private ArrayList<LogicalVolume> lvList = new ArrayList<LogicalVolume>();

    public VolumeGroup(String name, PhysicalVolume pv)
    {
        super(name);
        pvList.add(pv);
        totalSpace = pv.getPd().getGb();
        availableSpace = pv.getPd().getGb();

    }

    public int getTotalSpace() {
        return totalSpace;
    }

    public int getAvailableSpace() {
        return availableSpace;
    }

    public ArrayList<PhysicalVolume> getPvList() {
        return pvList;
    }

    public void addPv (PhysicalVolume pv)
    {
        pvList.add(pv);
        totalSpace += pv.getPd().getGb();
        availableSpace += pv.getPd().getGb();
    }

    public ArrayList<LogicalVolume> getLvList() {
        return lvList;
    }

    public void addLv(LogicalVolume lv)
    {
        lvList.add(lv);
        availableSpace = availableSpace - lv.getGb();
    }

}

