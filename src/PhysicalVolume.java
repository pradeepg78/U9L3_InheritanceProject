import java.util.UUID;

public class PhysicalVolume extends SuperClass{
    private String hardDriveLocation;

    public PhysicalVolume(String name, UUID UUID, String hardDriveLocation) {
        super(name, UUID);
        this.hardDriveLocation = hardDriveLocation;
    }


}
