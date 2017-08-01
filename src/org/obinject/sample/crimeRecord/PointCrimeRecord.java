/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.crimeRecord;

import static java.lang.Math.sqrt;
import org.obinject.block.Page;
import org.obinject.block.PullPage;
import org.obinject.block.PushPage;
import org.obinject.meta.Point;
import org.obinject.meta.Uuid;
import org.obinject.storage.KeyStructure;

/**
 *
 * @author Mariana
 */
public class PointCrimeRecord extends EntityCrimeRecord implements Point<PointCrimeRecord> {

    private double preservedDistance;
    public static final Uuid classId = Uuid.fromString("2B894868-6C03-56A7-08CD-B7FF3477D43F");

    public PointCrimeRecord() {
    }

    public PointCrimeRecord(EntityCrimeRecord entity) {
        super(entity);
    }

    @Override
    public boolean pullKey(byte array[], int position) {
        PullPage pull = new PullPage(array, position);
        this.setXCoord(pull.pullLong());
        this.setYCoord(pull.pullLong());
        return true;
    }

    @Override
    public void pushKey(byte array[], int position) {
        PushPage push = new PushPage(array, position);
        push.pushLong(this.getXCoord());
        push.pushLong(this.getYCoord());
    }

    @Override
    public int sizeOfKey() {
        return (Page.sizeOfLong + Page.sizeOfLong);
    }

    @Override
    public KeyStructure<PointCrimeRecord> getKeyStructure() {
        return null;
    }

    @Override
    public double distanceTo(PointCrimeRecord obj) {
        double distance = 0;
        distance += (this.getXCoord() - obj.getXCoord())
                * (this.getXCoord() - obj.getXCoord());
        distance += (this.getYCoord() - obj.getYCoord())
                * (this.getYCoord() - obj.getYCoord());
        return sqrt(distance);
    }

    @Override
    public Uuid getClassID() {
        return classId;
    }

    @Override
    public double getPreservedDistance() {
        return this.preservedDistance;
    }

    @Override
    public void setPreservedDistance(double distance) {
        this.preservedDistance = distance;
    }

    @Override
    public double getOrigin(int axis) {
        switch (axis) {
            case 0:
                return (double) this.getXCoord();
            case 1:
                return (double) this.getYCoord();
            default:
                return 0;
        }
    }

    @Override
    public int numberOfDimensions() {
        return 2;
    }

    @Override
    public void setOrigin(int axis, double value) {
        switch (axis) {
            case 0:
                this.setXCoord((long) value);
            case 1:
                this.setYCoord((long) value);
            default:
                break;
        }
    }

    @Override
    public boolean hasSameKey(PointCrimeRecord key) {
        return ((this.getXCoord() == key.getXCoord())
                && (this.getYCoord() == key.getYCoord()));

    }

};
