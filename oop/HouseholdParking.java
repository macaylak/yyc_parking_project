package edu.ucalgary.oop;
import java.time.LocalDate;
import java.util.*;

public class HouseholdParking extends CalgaryProperty {
    // Each residential property is allowed one street parking permit
    private LinkedList<String> residentLicences = new LinkedList<String>();
    private int maxLicences = 3;
    private VisitorParking visitors = new VisitorParking();

    public HouseholdParking(int taxRollNumber, String zoning, String streetName, int buildingNumber, String postCode, String buildingAnnex) throws IllegalArgumentException {
        super(taxRollNumber, zoning, streetName, buildingNumber, postCode, buildingAnnex);
        this.visitors = new VisitorParking();
    }

    public HouseholdParking(int taxRollNumber, String zoning, String streetName, int buildingNumber, String postCode) throws IllegalArgumentException {
        super(taxRollNumber, zoning, streetName, buildingNumber, postCode);
        this.visitors = new VisitorParking();
    }

    /*
     * Add a licence to the first empty spot in residentLicences, or replace the most recent
     * Ignore if the licence is already stored
     * @param licence - The licence plate to be added
     * @throws IllegalArgumentException if licence plate isn't a valid Alberta licence
     */
    // Adds or replaces the resident licence
    public void addOrReplaceResidentLicence(String licence) {
        // Standardize and validate the licence
        String standardizedLicence = Parking.standardizeAndValidateLicence(licence);

        // Check that there is only one resident licence
        if (residentLicences.size() == 1) {
            residentLicences.set(0, standardizedLicence);
        } else {
            residentLicences.add(standardizedLicence);
        }
    }

    /**
     * Add a visitor reservation to the visitor parking object
     * @param licence - The licence plate of the visitor
     */
    public void addVisitorReservation(String licence) {
        this.visitors.addVisitorReservation(licence);
    }

    /**
     * Add a visitor reservation to the visitor parking object
     * @param licence - The licence plate of the visitor
     * @param date - The date of the reservation
     */
    public void addVisitorReservation(String licence, LocalDate date) {
        this.visitors.addVisitorReservation(licence, date);
    }

    /**
     * Get the visitor parking object associated with this household
     * @return the visitor parking object
     */
    public VisitorParking getVisitors() {
        return this.visitors;
    }

    public ArrayList<String> getLicencesRegisteredForDate(LocalDate date) {
    	return visitors.getLicencesRegisteredForDate(date);
    }
    
    
    // Returns the resident licence
    public String getResidentLicence() {
        if (residentLicences.size() == 1) {
            return residentLicences.get(0);
        } else {
            return "";
        }
    }

    // Removes the resident licence
    public void removeResidentLicence() {
        if (residentLicences.size() == 1) {
            residentLicences.remove(0);
        }
    }

    public ArrayList<String> getLicencesRegisteredForDate() {
        return getLicencesRegisteredForDate(LocalDate.now());
    }

    public boolean licenceIsRegisteredForDate(String licence) {
        return licenceIsRegisteredForDate(licence, LocalDate.now());
    }

    public boolean licenceIsRegisteredForDate(String licence, LocalDate date) {
        boolean isRegistered = residentLicences.contains(licence);
        if (!isRegistered) {
            isRegistered = this.visitors.licenceIsRegisteredForDate(licence, date);
        }
        return isRegistered;
    }
    
    

    public List<LocalDate> getAllDaysLicenceIsRegistered(String licence) {
        List<LocalDate> registeredDays = new ArrayList<>();
        if (residentLicences.contains(licence)) {
            registeredDays.add(LocalDate.now());
        }
        registeredDays.addAll(this.visitors.getAllDaysLicenceIsRegistered(licence));
        Collections.sort(registeredDays);
        return registeredDays;
    }
    

    public List<LocalDate> getStartDaysLicenceIsRegistered(String licence) {
        List<LocalDate> registeredDays = this.getAllDaysLicenceIsRegistered(licence);
        if (!registeredDays.isEmpty()) {
            LocalDate startDate = registeredDays.get(0);
            List<LocalDate> startDays = new ArrayList<>();
            startDays.add(startDate);
            for (int i = 1; i < registeredDays.size(); i++) {
                LocalDate currentDate = registeredDays.get(i);
                if (currentDate.isAfter(startDate.plusDays(i))) {
                    startDate = currentDate;
                    startDays.add(startDate);
                }
            }
            return startDays;
        }
        return null;
    }
    
    


    
}
