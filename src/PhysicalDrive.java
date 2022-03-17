import java.util.ArrayList;

public class PhysicalDrive {
    private String name;
    private int gb;
    private ArrayList<String> hardDrives;

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
    }


}
