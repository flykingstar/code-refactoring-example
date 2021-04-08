package org.coderead.model;

/**
 * 表演
 *
 * @author kendoziyu
 * @since 2020/10/11 0011
 */
public class Performance {

    private String playId;

    private double audience;

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public double getAudience() {
        return audience;
    }

    public void setAudience(double audience) {
        this.audience = audience;
    }
}
