package skypay.hotelreservation;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Service {
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();
    private int bookingIdCounter = 0;

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        if (!rooms.stream().anyMatch(r -> r.getRoomNumber() == roomNumber)) {
            rooms.add(new Room(roomNumber, roomType, roomPricePerNight));
        }
    }

    public void setUser(int userId, int balance) {
        if (!users.stream().anyMatch(u -> u.getUserId() == userId)) {
            users.add(new User(userId, balance));
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        if (checkIn.after(checkOut)) throw new IllegalArgumentException("Check-in must be before check-out");
        User user = users.stream().filter(u -> u.getUserId() == userId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Room room = rooms.stream().filter(r -> r.getRoomNumber() == roomNumber).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        long diff = checkOut.getTime() - checkIn.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long nights = days > 0 ? days - 1 : 0;
        if (nights < 0) throw new IllegalArgumentException("Check-out must be after check-in with at least one night");
        int totalPrice = (int) nights * room.getPricePerNight();

        if (totalPrice > user.getBalance()) throw new IllegalStateException("Insufficient balance");
        if (bookings.stream().anyMatch(b -> b.getRoomNumber() == roomNumber &&
                !checkOut.before(b.getCheckIn()) && !checkIn.after(b.getCheckOut())))
            throw new IllegalStateException("Room is not available for the period");

        user.setBalance(user.getBalance() - totalPrice);
        bookings.add(new Booking(bookingIdCounter++, userId, roomNumber, checkIn, checkOut, totalPrice));
    }

    public void printAll() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Rooms:");
        rooms.forEach(r -> System.out.printf("Room %d: Type=%s, Price=%d%n", r.getRoomNumber(), r.getRoomType(), r.getPricePerNight()));
        System.out.println("Bookings (latest to oldest):");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            Booking b = bookings.get(i);
            User u = users.stream().filter(u2 -> u2.getUserId() == b.getUserId()).findFirst().get();
            Room r = rooms.stream().filter(r2 -> r2.getRoomNumber() == b.getRoomNumber()).findFirst().get();
            System.out.printf("Booking %d: User=%d (Balance=%d), Room=%d (Type=%s, Price=%d), Check-in=%s, Check-out=%s, Total=%d%n",
                    b.getBookingId(), b.getUserId(), u.getBalance(), b.getRoomNumber(), r.getRoomType(), r.getPricePerNight(),
                    sdf.format(b.getCheckIn()), sdf.format(b.getCheckOut()), b.getTotalPrice());
        }
    }

    public void printAllUsers() {
        System.out.println("Users (latest to oldest):");
        for (int i = users.size() - 1; i >= 0; i--) {
            User u = users.get(i);
            System.out.printf("User %d: Balance=%d%n", u.getUserId(), u.getBalance());
        }
    }

    public ArrayList<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

    public ArrayList<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }
}