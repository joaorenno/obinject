package org.obinject.sbbd2013.geonet;

import org.obinject.device.File;
import org.obinject.block.PullPage;
import org.obinject.block.PushPage;
import org.obinject.meta.Rectangle;
import org.obinject.block.Page;
import org.obinject.storage.AbstractKeyStructure;
import org.obinject.storage.RTree;

public class RectLatLongCoordGeonet extends EntityCoordGeonet implements Rectangle<RectLatLongCoordGeonet>, Comparable<RectLatLongCoordGeonet>
{

    public static final RTree<RectLatLongCoordGeonet> keyStructure =
            new RTree<RectLatLongCoordGeonet>(new File("rectlatlongcity.rtree", 1024))
            {
            };

    public RectLatLongCoordGeonet()
    {
    }

    public RectLatLongCoordGeonet(CoordGeonet coord)
    {
        super(coord);
    }

    @Override
    public double distanceTo(RectLatLongCoordGeonet metric)
    {
        return Math.sqrt(
                Math.pow(this.getLatitude() - metric.getLatitude(), 2)
                + Math.pow(this.getLongitude() - metric.getLongitude(), 2));
    }

    @Override
    public double getOrigin(int idx)
    {
        if (idx == 0)
        {
            return this.getLatitude();
        } else
        {
            return this.getLongitude();
        }
    }

    @Override
    public int numberOfDimensions()
    {
        return 2;
    }

    @Override
    public boolean pullKey(byte[] array, int position)
    {
        PullPage pull = new PullPage(array, position);
        this.setLatitude(pull.pullDouble());
        this.setLongitude(pull.pullDouble());
        this.setWidth(pull.pullDouble());
        this.setHeight(pull.pullDouble());
        return true;
    }

    @Override
    public void pushKey(byte[] array, int position)
    {
        PushPage push = new PushPage(array, position);
        push.pushDouble(this.getLatitude());
        push.pushDouble(this.getLongitude());
        push.pushDouble(this.getWidth());
        push.pushDouble(this.getHeight());
    }

    @Override
    public void setOrigin(int idx, double value)
    {
        if (idx == 0)
        {
            this.setLatitude(value);
        } else
        {
            this.setLongitude(value);
        }
    }

    @Override
    public int sizeOfKey()
    {
        return Page.sizeOfDouble * 4;
    }

    @Override
    public double getExtension(int axis)
    {
        if (axis == 0)
        {
            return this.getWidth();
        } else
        {
            return this.getHeight();
        }
    }

    @Override
    public void setExtension(int axis, double value)
    {
        if (axis == 0)
        {
            this.setWidth(value);
        } else
        {
            this.setHeight(value);
        }
    }

    @Override
    public AbstractKeyStructure<RectLatLongCoordGeonet> getKeyStructure() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getPreservedDistance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPreservedDistance(double distance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(RectLatLongCoordGeonet o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasSameKey(RectLatLongCoordGeonet key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
