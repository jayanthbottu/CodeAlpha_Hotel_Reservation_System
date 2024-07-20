import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class JavaHotel {
    private String name;
    private String location;
    private Map<Integer, Room> Rooms_Available;
    JavaHotel(String name, String location) {
        this.name = name;
        this.location = location;
        this.Rooms_Available = new HashMap<>();
    }
    void addRoom(Room room) {
        Rooms_Available.put(room.Room_Number, room);
    }
    Room getRoom(int Room_Number) {
        return Rooms_Available.get(Room_Number);
    }
    Map<Integer, Room> getRooms_Available() {
        return Rooms_Available;
    }
}
class Room {
    public int Room_Number;
    public String Room_Type; 
    public double Per_Stay; 
    public boolean Booked;
    Room(int Room_Number, String Room_Type, double Per_Stay) {
        this.Room_Number = Room_Number;
        this.Room_Type = Room_Type;
        this.Per_Stay = Per_Stay;
        this.Booked = false;
    }
}
class Reservation {
    public int reservationId;
    public String guestName;
    public Room reservedRoom;
    public String Check_in;
    public String Check_out;
    public double Cost_Total;

    Reservation(int reservationId, String guestName, Room reservedRoom, 
                String Check_in, String Check_out) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.reservedRoom = reservedRoom;
        this.Check_in = Check_in;
        this.Check_out = Check_out;
        this.Cost_Total = calculateCost_Total(reservedRoom, Check_in, Check_out);
    }

    private int calculateDays(String Check_in, String Check_out) {
        String[] checkInParts = Check_in.split("-");
        String[] checkOutParts = Check_out.split("-");
        int checkInYear = Integer.parseInt(checkInParts[2]);
        int checkInMonth = Integer.parseInt(checkInParts[1]);
        int checkInDay = Integer.parseInt(checkInParts[0]);
        int checkOutYear = Integer.parseInt(checkOutParts[2]);
        int checkOutMonth = Integer.parseInt(checkOutParts[1]);
        int checkOutDay = Integer.parseInt(checkOutParts[0]);
        return (checkOutYear - checkInYear) * 365 + (checkOutMonth - checkInMonth) * 30 + (checkOutDay - checkInDay);
    }
    private double calculateCost_Total(Room room, String Check_in, String Check_out) {
        int days = calculateDays(Check_in, Check_out);
        return room.Per_Stay * days;
    }
}

public class HotelReservationSystem {
    private static int nextReservationId = 1;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        JavaHotel javaHotel = new JavaHotel("Java Hotel", "Las Vegas City");
        javaHotel.addRoom(new Room(101, "Single", 100.0));
        javaHotel.addRoom(new Room(102, "Double", 150.0));
        javaHotel.addRoom(new Room(103, "Suite", 300.0));

        System.out.println("Welcome to the Java Hotel Reservation System!");

        while (true) { 
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Make a Reservation");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                System.out.print("Enter your name: ");
                String guestName = scanner.nextLine();
                System.out.println("\nAvailable Rooms:");
                for (Room room : javaHotel.getRooms_Available().values()) {
                    if (!room.Booked) {
                        System.out.println("Room " + room.Room_Number + " - " + room.Room_Type + " - $" + room.Per_Stay + " per night");
                    }
                }
                System.out.print("\nEnter the room number you'd like to reserve: ");
                int Room_Number = scanner.nextInt();
                scanner.nextLine(); 
                Room roomToReserve = javaHotel.getRoom(Room_Number);
                if (roomToReserve != null && !roomToReserve.Booked) {
                    System.out.print("Enter check-in date: ");
                    String Check_in = scanner.nextLine();
                    System.out.print("Enter check-out date: ");
                    String Check_out = scanner.nextLine();
                    Reservation reservation = new Reservation(nextReservationId++, guestName, roomToReserve, Check_in, Check_out);
                    roomToReserve.Booked = true;
                    System.out.println("\nReservation Successful!");
                    System.out.println("Reservation ID: " + reservation.reservationId);
                    System.out.println("Guest Name: " + reservation.guestName);
                    System.out.println("Room Number: " + reservation.reservedRoom.Room_Number);
                    System.out.println("Check-in Date: " + reservation.Check_in);
                    System.out.println("Check-out Date: " + reservation.Check_out);
                    System.out.println("Total Price: $" + reservation.Cost_Total);
                    System.out.println("\nPlease proceed to the front desk to make payment.");
                    System.out.println("Thank you for your reservation!");
                } else {
                    System.out.println("\nSorry, room " + Room_Number + " is not available.");
                }
            } else if (choice == 2) {
                System.out.println("\nAvailable Rooms:");
                for (Room room : javaHotel.getRooms_Available().values()) {
                    if (!room.Booked) {
                        System.out.println("Room " + room.Room_Number + " - " + room.Room_Type + " - $" + room.Per_Stay + " per night");
                    }
                }
            } else if (choice == 3) {
                System.out.println("\nThank you for using the Java Hotel Reservation System. Goodbye!");
                break;
            } else {
                System.out.println("\nInvalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}