/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.obinject.sample.crimeRecord;

/**
 *
 * @author Mariana
 */
public class CrimeRecord {
    
    private int ID;
    private String caseNumber;
    private String block;
    private String IUCR;
    private short locationDescription;
    private boolean arrest;
    private byte communityArea;
    private long XCoord;
    private long YCoord;
    private double latitude;
    private double longitude;
    
    public static String LocationDescriptions[] = {"ABANDONED BUILDING","AIRCRAFT",
    "AIRPORT BUILDING NON-TERMINAL - NON-SECURE AREA",
    "AIRPORT BUILDING NON-TERMINAL - SECURE AREA",
    "AIRPORT EXTERIOR - NON-SECURE AREA","AIRPORT EXTERIOR - SECURE AREA",
    "AIRPORT PARKING LOT","AIRPORT TERMINAL LOWER LEVEL - NON-SECURE AREA",
    "AIRPORT TERMINAL LOWER LEVEL - SECURE AREA",
    "AIRPORT TERMINAL MEZZANINE - NON-SECURE AREA",
    "AIRPORT TERMINAL UPPER LEVEL - NON-SECURE AREA",
    "AIRPORT TERMINAL UPPER LEVEL - SECURE AREA",
    "AIRPORT TRANSPORTATION SYSTEM (ATS)",
    "AIRPORT VENDING ESTABLISHMENT","AIRPORT/AIRCRAFT","ALLEY","ANIMAL HOSPITAL",
    "APARTMENT","APPLIANCE STORE","ATHLETIC CLUB","ATM (AUTOMATIC TELLER MACHINE)",
    "AUTO","BANK","BAR OR TAVERN","BARBER SHOP/BEAUTY SALON","BARBERSHOP",
    "BASEMENT","BOAT/WATERCRAFT","BOWLING ALLEY","BRIDGE","CAR WASH","CEMETARY",
    "CHA APARTMENT","CHA HALLWAY/STAIRWELL/ELEVATOR","CHA PARKING LOT",
    "CHA PARKING LOT/GROUNDS","CHURCH/SYNAGOGUE/PLACE OF WORSHIP",
    "CLEANING STORE","COIN OPERATED MACHINE","COLLEGE/UNIVERSITY GROUNDS",
    "COLLEGE/UNIVERSITY RESIDENCE HALL","COMMERCIAL / BUSINESS OFFICE",
    "CONSTRUCTION SITE","CONVENIENCE STORE","CREDIT UNION","CTA BUS",
    "CTA BUS STOP","CTA GARAGE / OTHER PROPERTY","CTA PLATFORM","CTA STATION",
    "CTA TRACKS - RIGHT OF WAY","CTA TRAIN","CURRENCY EXCHANGE","DAY CARE CENTER",
    "DELIVERY TRUCK","DEPARTMENT STORE","DRIVEWAY","DRIVEWAY - RESIDENTIAL",
    "DRUG STORE","FACTORY/MANUFACTURING BUILDING","FEDERAL BUILDING",
    "FIRE STATION","FOREST PRESERVE","GANGWAY","GARAGE","GAS STATION",
    "GAS STATION DRIVE/PROP.","GOVERNMENT BUILDING","GOVERNMENT BUILDING/PROPERTY",
    "GROCERY FOOD STORE","HALLWAY","HIGHWAY/EXPRESSWAY","HOSPITAL BUILDING/GROUNDS",
    "HOTEL/MOTEL","HOUSE","JAIL / LOCK-UP FACILITY","LAKEFRONT/WATERFRONT/RIVERBANK",
    "LIBRARY","MEDICAL/DENTAL OFFICE","MOVIE HOUSE/THEATER","NEWSSTAND",
    "NURSING HOME","NURSING HOME/RETIREMENT HOME","OTHER",
    "OTHER COMMERCIAL TRANSPORTATION","OTHER RAILROAD PROP / TRAIN DEPOT",
    "PARK PROPERTY","PARKING LOT","PARKING LOT/GARAGE(NON.RESID.)","PAWN SHOP",
    "POLICE FACILITY/VEH PARKING LOT","POOL ROOM","PORCH","RESIDENCE",
    "RESIDENCE-GARAGE","RESIDENCE PORCH/HALLWAY","RESIDENTIAL YARD (FRONT/BACK)",
    "RESTAURANT","RETAIL STORE","SAVINGS AND LOAN","SCHOOL, PRIVATE, BUILDING",
    "SCHOOL, PRIVATE, GROUNDS","SCHOOL, PUBLIC, BUILDING","SCHOOL, PUBLIC, GROUNDS",
    "SIDEWALK","SMALL RETAIL STORE","SPORTS ARENA/STADIUM","STREET","TAVERN",
    "TAVERN/LIQUOR STORE","TAXICAB","VACANT LOT","VACANT LOT/LAND",
    "VEHICLE-COMMERCIAL","VEHICLE - DELIVERY TRUCK","VEHICLE NON-COMMERCIAL",
    "VESTIBULE","WAREHOUSE","YARD"};
    
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getIUCR() {
        return IUCR;
    }

    public void setIUCR(String IUCR) {
        this.IUCR = IUCR;
    }

    public short getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(short locationDescription) {
        this.locationDescription = locationDescription;
    }

    public boolean isArrest() {
        return arrest;
    }

    public void setArrest(boolean arrest) {
        this.arrest = arrest;
    }

    public byte getCommunityArea() {
        return communityArea;
    }

    public void setCommunityArea(byte communityArea) {
        this.communityArea = communityArea;
    }

    public long getXCoord() {
        return XCoord;
    }

    public void setXCoord(long XCoord) {
        this.XCoord = XCoord;
    }

    public long getYCoord() {
        return YCoord;
    }

    public void setYCoord(long YCoord) {
        this.YCoord = YCoord;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    
    public static int GetLocationDescriptionIndex(String locationDescription){
        for (int i = 0; i < LocationDescriptions.length; i++) {
            if(locationDescription.equals(LocationDescriptions[i]))
                return i;
        }
        return 0;
    }
    
    static String GetLocationDescriptionString(int index){
        return LocationDescriptions[index];
    }
};


