package org.obinject.sample.meu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.obinject.annotation.Number;
import org.obinject.annotation.Order;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Sort;
import org.obinject.annotation.commnon.IndexesOfSort;
import org.obinject.annotation.Unique;

@Persistent
public class MeuExemplo {

    private boolean campo1;
    @Sort(number = Number.Four, order = Order.First)
    private byte campo2;
    @Sort(number = Number.One, order = Order.First)
    @Sort(number = Number.Four, order = Order.Second)
    private char campo3;
    @Sort(number = Number.Four, order = Order.Third)
    private Calendar campo4;
    @Sort(number = Number.Four, order = Order.Fourth)
    private Date campo5;
    @Sort(number = Number.Four, order = Order.Fifth)
    private double campo6;
    @Unique
    @Sort(number = Number.Three, order = Order.First)
    @Sort(number = Number.Four, order = Order.Sixteenth)
    private float campo7;
    @Unique
    @Sort(number = Number.Four, order = Order.Seventh)
    private int campo8;
    @Sort(number = Number.Two, order = Order.First)
    @Sort(number = Number.Four, order = Order.Eighth)
    private long campo9;
    @Sort(number = Number.Two, order = Order.Second)
    @Sort(number = Number.Four, order = Order.Ninth)
    private short campo10;
    private List<MeuExemplo> campo11 = new ArrayList<>();
    @Sort(number = Number.One, order = Order.Second)
    @Sort(number = Number.Three, order = Order.Second)
    @Sort(number = Number.Four, order = Order.Tenth)
    private String campo12;
    private double[] campo13 = new double[2];

    public boolean isCampo1() {
        return campo1;
    }

    public void setCampo1(boolean campo1) {
        this.campo1 = campo1;
    }

    public byte getCampo2() {
        return campo2;
    }

    public void setCampo2(byte campo2) {
        this.campo2 = campo2;
    }

    public char getCampo3() {
        return campo3;
    }

    public void setCampo3(char campo3) {
        this.campo3 = campo3;
    }

    public Calendar getCampo4() {
        return campo4;
    }

    public void setCampo4(Calendar campo4) {
        this.campo4 = campo4;
    }

    public Date getCampo5() {
        return campo5;
    }

    public void setCampo5(Date campo5) {
        this.campo5 = campo5;
    }

    public double getCampo6() {
        return campo6;
    }

    public void setCampo6(double campo6) {
        this.campo6 = campo6;
    }

    public float getCampo7() {
        return campo7;
    }

    public void setCampo7(float campo7) {
        this.campo7 = campo7;
    }

    public int getCampo8() {
        return campo8;
    }

    public void setCampo8(int campo8) {
        this.campo8 = campo8;
    }

    public long getCampo9() {
        return campo9;
    }

    public void setCampo9(long campo9) {
        this.campo9 = campo9;
    }

    public short getCampo10() {
        return campo10;
    }

    public void setCampo10(short campo10) {
        this.campo10 = campo10;
    }

    public List<MeuExemplo> getCampo11() {
        return campo11;
    }

    public void setCampo11(List<MeuExemplo> campo11) {
        this.campo11 = campo11;
    }

    public String getCampo12() {
        return campo12;
    }

    public void setCampo12(String campo12) {
        this.campo12 = campo12;
    }

    public double[] getCampo13() {
        return campo13;
    }

    public void setCampo13(double[] campo13) {
        this.campo13 = campo13;
    }

}
