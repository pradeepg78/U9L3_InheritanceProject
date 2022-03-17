import java.util.ArrayList;
import java.util.UUID;

public class PhysicalVolume extends SuperClass{
    private String hardDriveLocation;

    public PhysicalVolume(String name, String hardDriveLocation) {
        super(name, UUID.randomUUID());
        this.hardDriveLocation = hardDriveLocation;
    }


}
