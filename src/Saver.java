import java.io.*;
import java.util.*;

public class Saver {
    private static FileWriter fileWriter;
    private static FileReader fileReader;

    public static void readFromFile(String file, ArrayList<PhysicalDrive> pdList, ArrayList<PhysicalVolume> pvList, ArrayList<VolumeGroup> vgList, ArrayList<LogicalVolume> lvList) throws IOException {
        File myObj = new File(file);
        fileReader = new FileReader(file);
        if (myObj.exists())
        {
            String str = "";
            try (BufferedReader br = new BufferedReader(fileReader))
            {
                while ((str = br.readLine()) != null)
                {
                    String[] parse = str.split("\\|");
                    if(parse[0].equals("PD"))
                    {
                        PhysicalDrive temp = new PhysicalDrive(parse[1],Integer.parseInt(parse[2]));
                        pdList.add(temp);
                    }
                    if(parse[0].equals("PV"))
                    {
                        for(int x = 0; x < pdList.size();x++)
                        {
                            if(parse[2].equals(pdList.get(x).getName()))
                            {
                                PhysicalVolume temp = new PhysicalVolume(parse[1],pdList.get(x));
                                temp.setUUID(UUID.fromString(parse[3]));
                                pvList.add(temp);
                                pdList.get(x).setPv(temp);
                            }
                        }
                    }
                    if(parse[0].equals("VG"))
                    {
                        String[] VGListOfPV = new String[1];
                        if(parse[2].contains(","))
                        {
                            VGListOfPV = parse[2].split(",");
                        }
                        else
                        {
                            VGListOfPV[0] = parse[2];
                        }
                        int UUIDIndex = 3;
                        if(parse.length == 5)
                        {
                            UUIDIndex = 4;
                        }
                        VolumeGroup temp = null;

                        for(int x = 0; x < VGListOfPV.length; x++)
                        {
                            for(int i = 0; i < pvList.size();i++)
                            {
                                if(VGListOfPV[x].equals(pvList.get(i).getName()))
                                {
                                    if(vgList.size() == 0)
                                    {
                                        temp = new VolumeGroup(parse[1],pvList.get(i));
                                        pvList.get(i).setVg(temp);
                                        temp.setUUID(UUID.fromString(parse[UUIDIndex]));
                                        vgList.add(temp);
                                        break;
                                    }
                                    else {
                                        temp.addPv(pvList.get(i));
                                        pvList.get(i).setVg(temp);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(parse[0].equals("LV"))
                    {
                        LogicalVolume temp = new LogicalVolume(parse[1],Integer.parseInt(parse[2]));
                        temp.setUUID(UUID.fromString(parse[4]));
                        for(VolumeGroup vg : vgList)
                        {
                            if(vg.getName().equals(parse[3]))
                            {
                                vg.addLv(temp);
                            }
                        }
                        lvList.add(temp);
                    }
                }
            }
            catch (IOException e)
            {
                System.out.println("Error while reading a file.");
            }
        }
    }

    public static void writeToFile(String file,ArrayList<PhysicalDrive> PDList, ArrayList<PhysicalVolume> PVList, ArrayList<VolumeGroup> VGList, ArrayList<LogicalVolume> LVList)
    {
        try {
            fileWriter = new FileWriter(file);
            for(PhysicalDrive i : PDList)
            {
                fileWriter.write("PD");
                fileWriter.write("|");
                fileWriter.write(i.getName());
                fileWriter.write("|" + i.getGb() + "\n");
            }
            for(PhysicalVolume i : PVList)
            {
                fileWriter.write("PV");
                fileWriter.write("|");
                fileWriter.write(i.getName());
                fileWriter.write("|" + i.getPd().getName() + "|");
                fileWriter.write(i.getUUID() + "\n");
            }
            for(VolumeGroup i : VGList)
            {
                fileWriter.write("VG");
                fileWriter.write("|");
                fileWriter.write(i.getName() + "|");
                for(int x = 0; x < i.getPvList().size(); x++)
                {
                    if(x == i.getPvList().size()-1)
                    {
                        fileWriter.write(i.getPvList().get(x).getName());
                    }
                    else
                    {
                        fileWriter.write(i.getPvList().get(x).getName() + ",");
                    }
                }
                for(int x = 0; x < i.getLvList().size();x++)
                {
                    if(x == i.getLvList().size()-1)
                    {
                        fileWriter.write("|"+i.getLvList().get(x).getName());
                    }
                    else
                    {
                        fileWriter.write("|"+i.getLvList().get(x).getName() + ",");
                    }
                }
                fileWriter.write("|");
                fileWriter.write(i.getUUID() + "\n");
            }
            for(LogicalVolume i : LVList)
            {
                fileWriter.write("LV");
                fileWriter.write("|");
                fileWriter.write(i.getName());
                fileWriter.write("|" + i.getGb() + "|");
                for(VolumeGroup vg : VGList)
                {
                    for(int x = 0; x < vg.getLvList().size();x++)
                    {
                        if(vg.getLvList().get(x).getName().equals(i.getName()))
                        {
                            fileWriter.write(vg.getName());
                            break;
                        }
                    }
                }
                fileWriter.write("|"+i.getUUID() + "\n");
            }
            fileWriter.close();
            System.out.println("Data Successfully Saved");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void clearSave(String file)
    {
        try
        {
            fileWriter = new FileWriter(file);
            System.out.println("SAVE Cleared");
        }
        catch (IOException e)
        {
            System.out.println("Unable to delete SAVE.");
            e.printStackTrace();
        }

    }
}




