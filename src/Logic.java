import java.io.File;
import java.io.IOException;
import java.util.*;

public class Logic {
    private ArrayList<PhysicalDrive> pdList = new ArrayList<PhysicalDrive>();
    private ArrayList<PhysicalVolume> pvList = new ArrayList<PhysicalVolume>();
    private ArrayList<VolumeGroup> vgList = new ArrayList<VolumeGroup>();
    private ArrayList<LogicalVolume> lvList = new ArrayList<LogicalVolume>();

  /*  public ArrayList<PhysicalDrive> getPdList() {
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
    } */

    public void listDrives() {
        if (pdList.size() == 0) {
            System.out.println("You have not installed any hard drives.");
        }
        for (PhysicalDrive physicalDrive : pdList) {
            System.out.println(physicalDrive.getName() + " [" + physicalDrive.getGb() + "G]");
        }
    }

    public void listPVS() {
        if (pvList.size() == 0) {
            System.out.println("You have not created any Physical Volumes.");
        }
        for (PhysicalVolume physicalVolume : pvList) {
            System.out.println(physicalVolume.getName());
            System.out.print("[" + physicalVolume.getPd().getGb() + "] ");
            if (physicalVolume.getVg() != null) {
                System.out.print("[" + physicalVolume.getVg().getName() + "] ");
            }
            System.out.print("[" + physicalVolume.getUUID() + "] ");
            System.out.println();
        }
    }

    public void listVGS() {
        if (vgList.size() == 0) {
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

    public void installDrive(String c) {
        boolean error = false;
        String r = c.substring(14);
        String name = r.substring(0, r.indexOf(" "));
        int size = Integer.parseInt(r.substring(r.indexOf(" ") + 1, r.length() - 1));
        PhysicalDrive drive = new PhysicalDrive(name, size);

        for (int i = 0; i < pdList.size(); i++) {
            if (pdList.get(i).getName().equals(name)) {
                error = true;
            }
        }

        if (error == false) {
            pdList.add(drive);
            System.out.println("Drive " + name + " has been successfully installed. ");
        } else {
            System.out.println();
            System.out.println("This drive has already been installed. ");
            System.out.println();
        }
    }

    public void createPV(String c) {
        String rest = c.substring(9);
        String pvName = (rest.substring(0, rest.indexOf(" ")));
        String pdName = (rest.substring(rest.indexOf(" ") + 1));

        if (pdList.size() != 0) {
            boolean duplicate = false;
            for (int i = 0; i < pvList.size(); i++) {
                if (pvName.equals(pvList.get(i).getName())) {
                    duplicate = true;
                    System.out.println("There is already a Physical Volume with the name " + pvName + " installed.");
                    break;
                }
            }
            if (!duplicate) {
                boolean pdFound = false;
                for (int i = 0; i < pdList.size(); i++) {
                    if (pdList.get(i).getPv() != null && pdList.get(i).getName().equals(pdName)) {
                        System.out.println("This hard drive is already associated with another Physical Volume. ");
                        pdFound = true;
                    }
                    if (pdList.get(i).getPv() == null && pdList.get(i).getName().equals(pdName)) {
                        PhysicalVolume newPV = new PhysicalVolume(pvName, pdList.get(i));
                        pdList.get(i).setPv(newPV);
                        pvList.add(newPV);
                        System.out.println("Physical Volume " + pvName + " has been successfully created.");
                        pdFound = true;
                    }
                }
                if (!pdFound) {
                    System.out.println("There is no hard drive named " + pdName + ".");
                }
            }
        } else {
            System.out.println("You currently do not have any Physical Drives installed.");
        }
    }

    public void createVG(String c) {
        String rest  = c.substring(9);
        String vgName = rest.substring(0, rest.indexOf(" "));
        String pvName = rest.substring(rest.indexOf(" ") + 1);

        if(pvList.size() != 0){
            boolean duplicate = false;
            for(int i = 0; i < vgList.size(); i++){
                if(vgList.get(i).getName().equals(vgName)){
                    duplicate = true;
                    System.out.println("There is already a Volume Group with the name " + vgName);
                    break;
                }
            }
            if(!duplicate){
                boolean pvFound = false;
                for(int i = 0; i < pdList.size(); i++){
                    if(pvList.get(i).getName().equals(pvName) && pvList.get(i).getVg() != null){
                        System.out.println("This Physical volume is already in Volume Group " + pvList.get(i).getVg().getName());
                        pvFound = true;
                    }
                    if(pvList.get(i).getVg() == null && pvList.get(i).getName().equals(pvName)){
                        VolumeGroup newVG = new VolumeGroup(vgName, pvList.get(i));
                        vgList.add(newVG);
                        pvList.get(i).setVg(newVG);
                        System.out.println("Volume group " + vgName + " successfully created");
                        pvFound = true;
                    }
                }
                if(!pvFound){
                    System.out.println("There is no physical volume named " + pvName);
                }
            }
        }
        else{
            System.out.println("You do not have any physical volumes created");
        }
    }

    public void extendVG(String c) {
        String rest = c.substring(9);
        String vgName = rest.substring(0, rest.indexOf(" "));
        String pvName = rest.substring(rest.indexOf(" "));

        VolumeGroup VG = null;
        PhysicalVolume PV = null;
        //int PVListSize = pvList.size();

        if (vgList.size() != 0 && pvList.size() != 0) {
            boolean foundVG = false;
            for (VolumeGroup volumeGroup : vgList) {
                if (volumeGroup.getName().equals(vgName)) {
                    foundVG = true;
                    VG = volumeGroup;
                }
            }
            if (foundVG) {
                boolean add = false;
                for(int i = 0; i < pvList.size(); i++){
                    if(pvList.get(i).getVg() != null && pvList.get(i).getName().equals(pvName)){
                        System.out.println("This Physical Volume is already in Volume Group " + pvList.get(i).getVg().getName());
                        break;
                    }
                    else if(pvList.get(i).getVg() == null && pvList.get(i).getName().equals(pvName)){
                        add = true;
                        PV = pvList.get(i);
                    }
                }
                if (add) {
                    for (int i = 0; i < VG.getPvList().size(); i++) {
                        if (VG.getPvList().get(i).getName().equals(pvName)) {
                            add = false;
                        }
                    }
                    if (add) {
                        System.out.println("Successfully extended PV " + pvName + " to VG " + vgName);
                        VG.addPv(PV);
                        PV.setVg(VG);
                    } else {
                        System.out.println("ERROR: PV " + pvName + " is already in this VG (" + vgName + ")!");
                    }
                } else {
                    System.out.println("ERROR: There are no PVs named" + pvName);
                }
            } else {
                System.out.println("ERROR: There are no VGs named" + vgName);
            }
        } else {
            if (vgList.size() == 0 && pvList.size() == 0) {
                System.out.println("ERROR: There is are no VGs created to extend from and PVs created to add to");
            } else if (vgList.size() == 0) {
                System.out.println("ERROR: There are no VGs created to extend from");
            } else {
                System.out.println("ERROR: There are no PVs created to add to");
            }
        }
    }

    public void createLV(String c) {
        int index = c.indexOf(" ") + 1;
        int endIndexOfNameLV = c.substring(index).indexOf(" ") + index;
        int endIndexOfSpace = c.substring(endIndexOfNameLV + 1).indexOf(" ") + endIndexOfNameLV;
        String nameLV = c.substring(index, (endIndexOfNameLV));
        int space = Integer.parseInt(c.substring(endIndexOfNameLV + 1, endIndexOfSpace));
        String nameVG = c.substring(endIndexOfSpace + 2);

        LogicalVolume LV = null;
        VolumeGroup VG = null;
        if (vgList.size() != 0) {
            boolean foundLV = false;
            if (lvList.size() != 0) {
                for (int i = 0; i < vgList.size(); i++) {
                    for (int x = 0; x < vgList.get(i).getLvList().size(); x++) {
                        LogicalVolume temp = vgList.get(i).getLvList().get(x);
                        if (temp.getName().equals(nameLV))
                        {
                            System.out.println("ERROR: The LV " + nameLV + " already exists in VG " + vgList.get((i)).getName() + ".");
                            foundLV = true;
                            break;
                        }
                    }
                }
            }
            if (!foundLV)
            {
                for (int i = 0; i < vgList.size(); i++) {
                    VG = vgList.get(i);
                    if (VG.getName().equals(nameVG)) {
                        if (VG.getAvailableSpace() - space >= 0) {
                            LV = new LogicalVolume(nameLV, space);
                            VG.addLv(LV);
                            lvList.add(LV);
                            System.out.println("Successfully created LV " + nameLV + " to VG " + nameVG + ".");
                        } else {
                            System.out.println("ERROR: Not enough available space remaining! " + VG.getAvailableSpace() + " left in " + nameVG + ".");
                        }
                    }
                }
            }
        } else {
            System.out.println("ERROR: There are no VGs to assign LV " + nameLV + " to.");
        }
    }

    public void listLVS() {
        for (LogicalVolume logicalVolume : lvList) {
            String name = logicalVolume.getName();
            int size = logicalVolume.getGb();
            UUID ID = logicalVolume.getUUID();
            String nameVG = "";
            for (VolumeGroup volumegroup : vgList) {
                for (LogicalVolume logicalVolume2 : volumegroup.getLvList()) {
                    if (logicalVolume2.getName().equals(name)) {
                        nameVG = volumegroup.getName();
                    }
                }
            }
            System.out.println(name + ": [" + size + "] [" + nameVG + "] [" + ID + "]");
        }
    }

    public void choices(String choice) {
        if (choice.equals("list-drives")) {
            listDrives();
        } else if (choice.equals("pvlist")) {
            listPVS();
        } else if (choice.contains("vglist")) {
            listVGS();
        } else if (choice.contains("install-drive")) {
            installDrive(choice);
        } else if (choice.contains("pvcreate")) {
            createPV(choice);
        } else if (choice.contains("vgcreate")) {
            createVG(choice);
        } else if (choice.contains("vgextend")) {
            extendVG(choice);
        } else if (choice.contains("lvcreate")) {
            createLV(choice);
        } else if (choice.equals("lvlist")) {
            listLVS();
        } else if (choice.contains("clearSave")) {
            Saver.clearSave("SAVE.txt");
        } else {
            System.out.println("ERROR: Invalid command!");
        }
/*
        public void saveData()
        {
            Saver.writeToFile("SAVE.txt", pdList, pvList, vgList, lvList);
        }

        public void getData() throws IOException {
        try {
            File myObj = new File("SAVE.txt");
            if (myObj.createNewFile()) {
                System.out.println("SAVE created: " + myObj.getName());
                Saver.readFromFile("SAVE.txt", pdList, pvList, vgList, lvList);
            } else {
                System.out.println("SAVE Exists.");
                Saver.readFromFile("SAVE.txt", pdList, pvList, vgList, lvList);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
 */
    }
}


