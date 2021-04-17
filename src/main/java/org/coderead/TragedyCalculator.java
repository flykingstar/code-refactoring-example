package org.coderead;public class TragedyCalculator{	public TragedyCalculator()	{	}private int getTragedyVolumeCredits(org.coderead.model.Performance performance) {
        return java.lang.Math.max(performance.getAudience() - 30, 0);
    }private int getTragedyAmount(org.coderead.model.Performance performance) {
        int thisAmount;
        thisAmount = 40000;
        if (performance.getAudience() > 30) {
            thisAmount += 1000 * (performance.getAudience() - 30);
        }
        return thisAmount;
    }}