package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class VisitorParking {
    private HashMap<String, TreeSet<LocalDate>> parkingRecord = new HashMap<>();

    public VisitorParking() {
    }

    public VisitorParking(String license) throws IllegalArgumentException {
        addVisitorReservation(license);
    }

    public VisitorParking(String license, LocalDate date) throws IllegalArgumentException {
        addVisitorReservation(license, date);
    }

    public void addVisitorReservation(String license) throws IllegalArgumentException {
        addVisitorReservation(license, LocalDate.now());
    }

    public void addVisitorReservation(String license, LocalDate date) throws IllegalArgumentException {
        String validLicense = Parking.standardizeAndValidateLicence(license);
        int count = 0;
        LocalDate today = LocalDate.now();

        for (HashMap.Entry<String, TreeSet<LocalDate>> entry : parkingRecord.entrySet()) {
            for (LocalDate value : entry.getValue()) {
                if (date.isEqual(value) || date.isEqual(value.plusDays(1)) || date.isEqual(value.plusDays(2))) {
                    count++;
                }
            }
        }
        if (count == 2 || date.isBefore(today)) {
            throw new IllegalArgumentException();
        }
        if (parkingRecord.containsKey(validLicense)) {
            if (licenceIsRegisteredForDate(validLicense, date)) {
                throw new IllegalArgumentException();
            } else {
                parkingRecord.get(validLicense).add(date);
            }
        } else {
            TreeSet<LocalDate> reservedDates = new TreeSet<>(Collections.reverseOrder());
            reservedDates.add(date);
            parkingRecord.put(validLicense, reservedDates);
        }
    }

    public boolean licenceIsRegisteredForDate(String license) throws IllegalArgumentException {
        return licenceIsRegisteredForDate(license, LocalDate.now());
    }

    public boolean licenceIsRegisteredForDate(String license, LocalDate date) throws IllegalArgumentException {
        String validLicense = Parking.standardizeAndValidateLicence(license);

        if (parkingRecord.containsKey(validLicense)) {
            for (LocalDate value : parkingRecord.get(validLicense)) {
                if (date.isEqual(value) || date.isEqual(value.plusDays(1)) || date.isEqual(value.plusDays(2))) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> getLicencesRegisteredForDate() {
        return getLicencesRegisteredForDate(LocalDate.now());
    }

    public ArrayList<String> getLicencesRegisteredForDate(LocalDate date) {
        ArrayList<String> licences = new ArrayList<>();

        for (HashMap.Entry<String, TreeSet<LocalDate>> entry : parkingRecord.entrySet()) {
            if (licenceIsRegisteredForDate(entry.getKey(), date)) {
                licences.add(entry.getKey());
            }
        }
        return licences;
    }

    public ArrayList<LocalDate> getStartDaysLicenceIsRegistered(String license) {
        ArrayList<LocalDate> startDays = new ArrayList<>();
        String validLicense = Parking.standardizeAndValidateLicence(license);
        TreeSet<LocalDate> startDates = parkingRecord.get(validLicense);

        Iterator<LocalDate> i = startDates.descendingIterator();

        while (i.hasNext()) {
            LocalDate date = i.next();
            startDays.add(date);
        }
        return startDays;
    }

    public ArrayList<LocalDate> getAllDaysLicenceIsRegistered(String license) {
        ArrayList<LocalDate> allDays = new ArrayList<>();
        String validLicense = Parking.standardizeAndValidateLicence(license);
        TreeSet<LocalDate> startDates = parkingRecord.get(validLicense);
        Iterator<LocalDate> i = startDates.descendingIterator();


        while (i.hasNext()) {
            LocalDate startDate = i.next();
            allDays.add(startDate);
            allDays.add(startDate.plusDays(1));
            allDays.add(startDate.plusDays(2));
        }
        return allDays;
    }

    public HashMap<String, TreeSet<LocalDate>> getParkingRecord() {
        return parkingRecord;
    }
}    