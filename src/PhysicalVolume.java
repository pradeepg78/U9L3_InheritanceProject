import java.util.UUID;

public class PhysicalVolume extends LVMSystem {
    private String hardDriveLocation;

    public PhysicalVolume(String name, String hardDriveLocation) {
        super(name, UUID.randomUUID());
        this.hardDriveLocation = hardDriveLocation;
    }


}
