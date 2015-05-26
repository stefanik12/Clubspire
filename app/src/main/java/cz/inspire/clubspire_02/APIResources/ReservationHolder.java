package cz.inspire.clubspire_02.APIResources;

import cz.inspire.clubspire_02.POJO.Reservation;

/**
 * Created by michal on 5/26/15.
 */
public class ReservationHolder {
    public static Reservation getReservation() {
        return reservation;
    }

    public static void setReservation(Reservation reservation) {
        ReservationHolder.reservation = reservation;
    }

    private static Reservation reservation;

    public static String getReservationActivityId() {
        return reservationActivityId;
    }

    public static void setReservationActivityId(String reservationActivityId) {
        ReservationHolder.reservationActivityId = reservationActivityId;
    }

    private static String reservationActivityId;

}
