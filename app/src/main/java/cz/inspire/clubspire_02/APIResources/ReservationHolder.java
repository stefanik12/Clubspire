package cz.inspire.clubspire_02.APIResources;

import cz.inspire.clubspire_02.POJO.Reservation;

/**
 * Created by michal on 5/26/15.
 */
public class ReservationHolder {

    private static Reservation reservation;
    private static String reservationActivityId;
    private static String reservationActivityName;
    private static Integer iconId;
    private static String iconUrl;
    private static String reservationId;

    public static String getReservationId() {
        return reservationId;
    }

    public static void setReservationId(String reservationId) {
        ReservationHolder.reservationId = reservationId;
    }

    public static void setIconUrl(String iconUrl) {
        ReservationHolder.iconUrl = iconUrl;
    }

    public static String getIconUrl() {
        return iconUrl;
    }

    public static int getIconId() {
        return iconId;
    }

    public static void setIconId(Integer iconId) {
        ReservationHolder.iconId = iconId;
    }

    public static String getReservationActivityName() {
        return reservationActivityName;
    }

    public static void setReservationActivityName(String reservationActivityName) {
        ReservationHolder.reservationActivityName = reservationActivityName;
    }

    public static String getReservationActivityId() {
        return reservationActivityId;
    }

    public static void setReservationActivityId(String reservationActivityId) {
        ReservationHolder.reservationActivityId = reservationActivityId;
    }

    public static Reservation getReservation() {
        return reservation;
    }

    public static void setReservation(Reservation reservation) {
        ReservationHolder.reservation = reservation;
    }

    public static void clear(){
        reservation = null;
        reservationActivityId = null;
        reservationActivityName = null;
        iconId = null;
        reservationId = null;
    }

}
