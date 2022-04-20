import java.io.File;
import java.io.IOException;
import java.util.*;

public class Logic {
    private ArrayList<PhysicalDrive> pdList = new ArrayList<PhysicalDrive>();
    private ArrayList<PhysicalVolume> pvList = new ArrayList<PhysicalVolume>();
    private ArrayList<VolumeGroup> vgList = new ArrayList<VolumeGroup>();
    private ArrayList<LogicalVolume> lvList = new ArrayList<LogicalVolume>();

    public void listDrives() {
        if (pdList.size() == 0) {
            System.out.println("ERROR: You have not installed any hard drives.");
        }
        for (PhysicalDrive physicalDrive : pdList) {
            System.out.println(physicalDrive.getName() + " [" + physicalDrive.getGb() + "G]");
        }
    }

    public void listPVS() {
        if (pvList.size() == 0) {
            System.out.println("ERROR: You have not created any Physical Volumes.");
        }
        for (PhysicalVolume physicalVolume : pvList) {
            System.out.print(physicalVolume.getName() + ":");
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
            System.out.println("ERROR: You have not created any Volume Groups.");
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
            System.out.println("ERROR: This drive has already been installed. ");
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
                    System.out.println("ERROR: There is already a Physical Volume with the name " + pvName + " installed.");
                    break;
                }
            }
            if (!duplicate) {
                boolean pdFound = false;
                for (int i = 0; i < pdList.size(); i++) {
                    if (pdList.get(i).getPv() != null && pdList.get(i).getName().equals(pdName)) {
                        System.out.println("ERROR: This hard drive is already associated with another Physical Volume. ");
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
                    System.out.println("ERROR: There is no hard drive named " + pdName + ".");
                }
            }
        } else {
            System.out.println("ERROR: You have not installed any Physical Volumes.");
        }
    }

    public void createVG(String c) {
        String rest  = c.substring(9);
        String vgName = rest.substring(0, rest.indexOf(" "));
        String pvName = rest.substring(rest.indexOf(" ") + 1);

        //first if-statement
        if(pvList.size() != 0){
            boolean duplicate = false;
            for(int i = 0; i < vgList.size(); i++){

                //second if-statement
                if(vgList.get(i).getName().equals(vgName)){
                    duplicate = true;
                    //System.out.println("Duplicate Status (second if statement): " + duplicate);
                    System.out.println("ERROR: There is already a Volume Group with the name " + vgName + ".");
                    break;
                }
            }


            if(!duplicate){
                boolean pvFound = false;
                for(int i = 0; i < pdList.size(); i++){
                    if(pvList.get(i).getName().equals(pvName) && pvList.get(i).getVg() != null){
                        System.out.println("ERROR: This Physical volume is already in Volume Group " + pvList.get(i).getVg().getName() + ".");
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
                    System.out.println("ERROR: There is no physical volume named " + pvName + ".");
                }
            }
        }
        else{
            System.out.println("ERROR: You do not have any physical volumes created");
        }
    }

    public void extendVG(String c) {
        String rest = c.substring(9);
        String vgName = rest.substring(0,rest.indexOf(" "));
        String pvName = rest.substring(rest.indexOf(" ") + 1);

        VolumeGroup VolG = null;
        PhysicalVolume PhysV = null;

        if(vgList.size() != 0 && pvList.size() != 0){
            boolean vgFound = false;
            for(int i = 0; i < vgList.size(); i++){
                if(vgList.get(i).getName().equals(vgName)){
                    vgFound = true;
                    VolG = vgList.get(i);
                }
            }

            if(vgFound == true){
                boolean add = false;
                for(int i = 0; i < pvList.size(); i++){
                    if(pvList.get(i).getVg() != null && pvList.get(i).getName().equals(pvName)){
                        System.out.println("ERROR: This Physical Volume is already in Volume Group " + pvList.get(i).getVg().getName() + ".");
                        break;
                    }
                    else if(pvList.get(i).getVg() == null && pvList.get(i).getName().equals(pvName)){
                        add = true;
                        PhysV = pvList.get(i);
                    }
                }

                if(add == true){
                    for(int i = 0; i < VolG.getPvList().size(); i++){
                        if(VolG.getPvList().get(i).getName().equals(pvName)){
                            add = false;
                        }
                    }

                    if(add == true){
                        VolG.addPv(PhysV);
                        PhysV.setVg(VolG);
                        System.out.println("Physical Volume " + pvName + " successfully extended to Volume Group " + vgName + ".");
                    }
                    else {
                        System.out.println("ERROR: This Physical Volume is already in Volume Group " + vgName + ".");
                    }

                }
                else{
                    System.out.println("ERROR: There is no Physical Volume named " + pvName + ".");
                }
            }
            else{
                System.out.println("ERROR: There are no Volume Groups named " + vgName + ".");
            }

        }
        else{
            if(vgList.size() == 0 && pvList.size() == 0){
                System.out.println("ERROR: You have not created any Physical Volumes or Volume Groups.");
            }
            else if(pvList.size() == 0){
                System.out.println("ERROR: You have not created any Physical Volumes. ");
            }
            else{
                System.out.println("ERROR: You have not created any Volume Groups.");
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


