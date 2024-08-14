import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Accommodation {
    private int accommodationNumber;
    private String accommodationType;
    private double accommodationPrice;
    private boolean isAvailable;

    public Accommodation(int accommodationNumber, String accommodationType, double accommodationPrice) {
        this.accommodationNumber = accommodationNumber;
        this.accommodationType = accommodationType;
        this.accommodationPrice = accommodationPrice;
        this.isAvailable = true;
    }

    public int getAccommodationNumber() {
        return accommodationNumber;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public double getAccommodationPrice() {
        return accommodationPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean availability) {
        isAvailable = availability;
    }

    @Override
    public String toString() {
        return "Accommodation " + accommodationNumber + " (" + accommodationType + ") - $" + accommodationPrice;
    }
}

class Reservation {
    private int reservationId;
    private Accommodation accommodation;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalCost;

    public Reservation(int reservationId, Accommodation accommodation, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
        this.reservationId = reservationId;
        this.accommodation = accommodation;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalCost = accommodation.getAccommodationPrice() * getNumberOfNights();
    }

    public int getReservationId() {
        return reservationId;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public String getGuestName() {
        return guestName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    private long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        return "\n\tReservation ID: " + reservationId + "\n" +
                "\tAccommodation: " + accommodation + "\n" +
                "\tGuest Name: " + guestName + "\n" +
                "\tCheck-in Date: " + checkInDate + "\n" +
                "\tCheck-out Date: " + checkOutDate + "\n" +
                "\tTotal Cost: $" + totalCost;
    }
}

class HotelSystem {
    private List<Accommodation> accommodations;
    private List<Reservation> reservations;
    private Map<String, Double> paymentMethods;
    private int nextReservationId;

    public HotelSystem() {
        accommodations = new ArrayList<>();
        reservations = new ArrayList<>();
        paymentMethods = new HashMap<>();
        nextReservationId = 1;

        accommodations.add(new Accommodation(101, "Single", 100.0));
        accommodations.add(new Accommodation(102, "Double", 150.0));
        accommodations.add(new Accommodation(103, "Suite", 250.0));

        paymentMethods.put("Cash", 0.0);
        paymentMethods.put("Credit Card", 2.5);
        paymentMethods.put("Debit Card", 1.5);
    }

    public void searchAccommodations(String accommodationType) {
        System.out.println("\n\t\tAvailable accommodations in " + accommodationType + " category: ");
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getAccommodationType().equalsIgnoreCase(accommodationType) && accommodation.isAvailable()) {
                System.out.println("\t\t" + accommodation);
            }
        }
    }

    public boolean makeBooking(int accommodationNumber, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getAccommodationNumber() == accommodationNumber && accommodation.isAvailable()) {
                Reservation reservation = new Reservation(nextReservationId++, accommodation, guestName, checkInDate, checkOutDate);
                reservations.add(reservation);
                accommodation.setAvailability(false);
                System.out.println("\n\tBooking made successfully! Reservation ID: " + reservation.getReservationId());
                return true;
            }
        }
        System.out.println("\n\tAccommodation not available or not found!");
        return false;
    }
}

