import java.util.UUID;
import java.util.ArrayList;

public class VolumeGroup extends LVMSystem {
    private ArrayList<PhysicalVolume> pvs;
    private ArrayList<LogicalVolume> lvs;

    public VolumeGroup(String name)
    {
        super(name, UUID.randomUUID());
    }

    public int getVGSize()
    {
        for (int i = 0; i < pvs.size(); i++)
        {

        }
    }

}

