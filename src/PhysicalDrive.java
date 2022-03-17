import java.util.UUID;

public class PhysicalDrive extends SuperClass{
    private int gb;

    public PhysicalDrive(String name, UUID UUID, int gb) {
        super(name, UUID);
        this.gb = gb;
    }


}
