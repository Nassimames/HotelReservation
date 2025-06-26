package skypay.hotelreservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {
    private Service service;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        service = new Service();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    void testSetRoomAndUser() throws Exception {
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setUser(1, 5000);
        assertEquals(1, service.getRooms().size());
        assertEquals(1, service.getUsers().size());
    }

    @Test
    void testBookRoomSuccess() throws Exception {
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setUser(1, 5000);
        service.bookRoom(1, 1, sdf.parse("01/07/2026"), sdf.parse("02/07/2026"));
        assertEquals(1, service.getBookings().size());
        assertTrue(service.getUsers().get(0).getBalance() < 5000);
    }

    @Test
    void testBookRoomInsufficientBalance() {
        service.setRoom(1, RoomType.STANDARD, 2000);
        service.setUser(1, 1000);
        assertThrows(IllegalStateException.class, () -> {
            try {
                service.bookRoom(1, 1, sdf.parse("01/07/2026"), sdf.parse("03/07/2026"));
            } catch (Exception e) {
                throw e; // Re-lance l'exception pour le test
            }
        });
    }
}