package com.statickidz.calculadoraprimos.lib;

/**
 *
 * @title Prime Number Generator
 * @author Adrián Barrio Andrés
 * @site https://statickidz.com
 */
public class PrimeNumberGenerator {

    private int start;
    private int end;

    private long[] generatedNumbers;

    public PrimeNumberGenerator() {
        this.start = 1;
        this.end = 100;
        this.generateNumbers();
    }

    public PrimeNumberGenerator(int end) {
        this.start = 1;
        this.end = end;
        this.generateNumbers();
    }

    public PrimeNumberGenerator(int start, int end) {
        if(start == 0)
            start = 1;
        this.start = start;
        this.end = end;
        this.generateNumbers();
    }

    public int getNumberFromPosition(int position) {
        return (int) ((position > this.end) ? 0 : generatedNumbers[position]);
    }

    public int getPositionFromNumber(int number) {
        if(isPrime(number)) {
            for(int i = 0; i < generatedNumbers.length; i++) {
                if(generatedNumbers[i] == number) return i;
            }
        }
        return 0;
    }

    public boolean isPrime(long number) {
        if(number > 2 && (number & 1) == 0)
            return false;
        for(int i = 3; i * i <= number; i += 2)
            if (number % i == 0)
                return false;
        return true;
    }

    private void generateNumbers() {
        int position = 1;
        generatedNumbers = new long[this.end];
        for(int i = this.start; i < this.end; i++) {
            if(isPrime(i)) {
                generatedNumbers[position] = i;
                position++;
            }
        }
    }

    public long[] getGeneratedNumbers() {
        return generatedNumbers;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        if(start == 0)
            start = 1;
        this.start = start;
        this.generateNumbers();
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
        this.generateNumbers();
    }

}
