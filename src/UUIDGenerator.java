import java.util.UUID;

public class UUIDGenerator {
    public static void main(String[] args) {
        UUID u = UUID.randomUUID();
        System.out.println(u.toString());
    }
}

