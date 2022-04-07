import java.io.File;
import java.io.IOException;
import java.util.*;

public class Logic {
    private ArrayList<PhysicalDrive> pdList = new ArrayList<PhysicalDrive>();
    private ArrayList<PhysicalVolume> pvList = new ArrayList<PhysicalVolume>();
    private ArrayList<VolumeGroup> vgList = new ArrayList<VolumeGroup>();
    private ArrayList<LogicalVolume> lvList = new ArrayList<LogicalVolume>();

    public ArrayList<PhysicalDrive> getPdList() {
        return pdList;
    }

    public ArrayList<PhysicalVolume> getPvList() {
        return pvList;
    }

    public ArrayList<VolumeGroup> getVgList() {
        return vgList;
    }

    public ArrayList<LogicalVolume> getLvList() {
        return lvList;
    }

    public void listDrives()
    {
        if (pdList.size() == 0)
        {
            System.out.println("You have not installed any hard drives.");
        }
        for (PhysicalDrive physicalDrive : pdList) {
            System.out.println(physicalDrive.getName() + " [" + physicalDrive.getGb() + "G]");
        }
    }

    public void listPVS()
    {
        if (pvList.size() == 0)
        {
            System.out.println("You have not created any Physical Volumes.");
        }
        for (PhysicalVolume physicalVolume : pvList)
        {
            System.out.println(physicalVolume.getName());
            System.out.print("[" + physicalVolume.getPd().getGb() + "] ");
            if (physicalVolume.getVg() != null)
            {
                System.out.print("[" + physicalVolume.getVg().getName() + "] ");
            }
            System.out.print("[" + physicalVolume.getUUID() + "] ");
            System.out.println();
        }
    }

    public void listVGS()
    {
        if (vgList.size() == 0)
        {
            System.out.println("You have not created any Volume Groups.");
        }
        for (VolumeGroup volumeGroup : vgList) {
            System.out.print(volumeGroup.getName() + ": ");
            System.out.print("total:[" + volumeGroup.getTotalSpace() + "G] ");
            System.out.print("available:[" + volumeGroup.getAvailableSpace() + "G] [");
            for (int x = 0; x < volumeGroup.getPvList().size(); x++) {
                if (x == volumeGroup.getPvList().size() - 1) {
                    System.out.print(volumeGroup.getPvList().get(x).getName() + "] ");
                } else {
                    System.out.print(volumeGroup.getPvList().get(x).getName() + ",");
                }
            }
            System.out.print("[" + volumeGroup.getUUID() + "]");
            System.out.println();
        }
    }

    public void choices(String choice)
    {

        if(choice.equals("list-drives"))
        {
            listDrives();
        }
        else if(choice.equals("pvlist"))
        {
            listPVS();
        }
        else if(choice.contains("vglist"))
        {
            VGList();
        }
        else if(choice.contains("install-drive"))
        {
            installDrive(choice);
        }
        else if(choice.contains("pvcreate"))
        {
            createPV(choice);
        }
        else if(choice.contains("vgcreate"))
        {
            createVG(choice);
        }
        else if(choice.contains("vgextend"))
        {
            extendVG(choice);
        }
        else if(choice.contains("lvcreate"))
        {
            createLV(choice);
        }
        else if (choice.contains("clearSave"))
        {
            Saver.clearSave("SAVE.txt");
        }
        else
        {
            System.out.println("ERROR: Invalid command!");
        }

    }
}
