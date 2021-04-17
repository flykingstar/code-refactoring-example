package org.coderead;public class ComedyCalculator{	public ComedyCalculator()	{	}private double getComedyVolumeCredits(org.coderead.model.Performance performance) {
        return java.lang.Math.max(performance.getAudience() - 30, 0) + java.lang.Math.floor(performance.getAudience() / 5);
    }private int getComedyAmount(org.coderead.model.Performance performance) {
        int thisAmount;
        thisAmount = 30000;
        if (performance.getAudience() > 20) {
            thisAmount += 10000 + 500 * (performance.getAudience() - 20);
        }
        thisAmount += 300 * performance.getAudience();
        return thisAmount;
    }}