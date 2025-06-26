package skypay.hotelreservation;
import java.util.Date;

public class Booking {
    private int bookingId;
    private int userId;
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;
    private int totalPrice;

    public Booking(int bookingId, int userId, int roomNumber, Date checkIn, Date checkOut, int totalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
    }

    public int getBookingId() { return bookingId; }
    public int getUserId() { return userId; }
    public int getRoomNumber() { return roomNumber; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
    public int getTotalPrice() { return totalPrice; }
}
