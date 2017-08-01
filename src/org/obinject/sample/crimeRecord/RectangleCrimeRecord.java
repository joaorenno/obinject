/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.crimeRecord;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import org.obinject.block.Page;
import org.obinject.block.PullPage;
import org.obinject.block.PushPage;
import org.obinject.meta.Rectangle;
import org.obinject.meta.Uuid;
import org.obinject.storage.KeyStructure;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import static java.lang.Math.abs;

/**
 *
 * @author Mariana
 */
public class RectangleCrimeRecord extends EntityCrimeRecord implements Rectangle<RectangleCrimeRecord> {

    private double preservedDistance;
    private double[] extensions = new double[2];

    public static final Uuid classId = Uuid.fromString("1A8B7F78-5029-59D8-2B89-48686C0356A7");

    public RectangleCrimeRecord() {
        this.extensions[0] = 0;
        this.extensions[1] = 0;
    }

    public RectangleCrimeRecord(EntityCrimeRecord entity) {
        super(entity);
        this.extensions[0] = 0;
        this.extensions[1] = 0;
    }

    @Override
    public boolean pullKey(byte array[], int position) {
        PullPage pull = new PullPage(array, position);
        this.setLatitude(pull.pullDouble());
        this.setLongitude(pull.pullDouble());
        this.extensions[0] = pull.pullDouble();
        this.extensions[1] = pull.pullDouble();
        return true;
    }

    @Override
    public void pushKey(byte array[], int position) {
        PushPage push = new PushPage(array,position);
        push.pushDouble(this.getLatitude());
        push.pushDouble(this.getLongitude());
        push.pushDouble(this.extensions[0]);
        push.pushDouble(this.extensions[1]);
    }

    @Override
    public int sizeOfKey() {
        return (4 * Page.sizeOfDouble);
    }

    @Override
    public KeyStructure<RectangleCrimeRecord> getKeyStructure() {
        return null;
    }

    @Override
    public double distanceTo(RectangleCrimeRecord obj) {
        return sqrt(
                ((this.getLatitude() - obj.getLatitude()) * (this.getLatitude() - obj.getLatitude()))
                + ((this.getLongitude() - obj.getLongitude()) * (this.getLongitude() - obj.getLongitude())));
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
    public Uuid getClassID() {
        return  classId;
    }

    @Override
    public double getExtension(int axis) {
        if (axis > 1) {
            // Invalid axis
            return 0;
        }
        return this.extensions[axis];
    }

    @Override
    public double getOrigin(int axis) {
        switch (axis) {
            case 1:
                return this.getLatitude();
            case 2:
                return this.getLongitude();
            case 3:
                //Invalid
                return 0;
        }
        return 0;
    }

    @Override
    public int numberOfDimensions() {
        return 2;
    }

    @Override
    public void setExtension(int axis, double value) {
        if (axis < 2) {
            this.extensions[axis] = value;
        }
    }

    @Override
    public void setOrigin(int axis, double value) {
        switch (axis) {
            case 1:
                this.setLatitude(value);
                break;
            case 2:
                this.setLongitude(value);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean hasSameKey(RectangleCrimeRecord key) {
        return ((abs(this.getLatitude() - key.getLatitude()) < EntityCrimeRecord.PRECISION) &&
                (abs(this.getLongitude()- key.getLongitude()) < EntityCrimeRecord.PRECISION));
    }
};
