package skypay.hotelreservation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HotelReservationTest {
    public static void main(String[] args) throws Exception {
        Service service = new Service();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.MASTER, 3000);
        service.setUser(1, 15000);
        service.setUser(2, 10000);

        service.bookRoom(1, 2, sdf.parse("30/06/2026"), sdf.parse("07/07/2026"));
        try { service.bookRoom(1, 2, sdf.parse("07/07/2026"), sdf.parse("30/06/2026")); } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        service.bookRoom(1, 1, sdf.parse("07/07/2026"), sdf.parse("08/07/2026"));
        try { service.bookRoom(2, 1, sdf.parse("07/07/2026"), sdf.parse("09/07/2026")); } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        service.bookRoom(2, 3, sdf.parse("07/07/2026"), sdf.parse("08/07/2026"));
        service.setRoom(1, RoomType.MASTER, 10000);

        service.printAllUsers();
        service.printAll();
    }
}