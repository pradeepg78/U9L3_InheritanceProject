import java.util.*;

public class PhysicalDrive {
    private String name;
    private int gb;
    private PhysicalVolume pv;
    //private ArrayList<String> hardDrives;

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

    public void setPv(PhysicalVolume nPv)
    {
        pv = nPv;
    }


    /*
    public ArrayList<String> getHardDrives() {
        return hardDrives;
    }

    public ArrayList<String> addToList()
    {
        for (int i = 0; i < hardDrives.size(); i++)
        {
            if (!hardDrives.get(i).equals(name))
            {
                hardDrives.add(name);
            }
            else
            {
                System.out.println("This hard drive already exists");
                break;
            }
        }
    } */


}
