package main;

import rsa.RSA;

import java.math.BigInteger;
import java.util.Random;



public class Main {
    public static BigInteger randomBigInteger(BigInteger minLimit, BigInteger maxLimit){

        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger res = new BigInteger(len, randNum);
        if (res.compareTo(minLimit) < 0)
            res = res.add(minLimit);
        if (res.compareTo(bigInteger) >= 0)
            res = res.mod(bigInteger).add(minLimit);
        return res;
    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
        Random random = new Random();
        BigInteger p, q, n, d, e, fi;
        p = BigInteger.probablePrime(1024,random);
        q = BigInteger.probablePrime(1024,random);
        n = p.multiply(q);
        fi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger message = randomBigInteger(BigInteger.ZERO,n);

        do {
            e = randomBigInteger(BigInteger.ZERO,fi);
        } while (!e.gcd(fi).equals(BigInteger.ONE));

        d = e.modInverse(fi);

        System.out.println("Message : " + message);

        BigInteger cryptotext = rsa.crypt(message, e, n);
        System.out.println("Cryptotext : " + cryptotext);

        long start = System. nanoTime();
        BigInteger messageDecrypted = rsa.decrypt(cryptotext,d,n);
        long end = System. nanoTime();
        long time = start-end;
        System.out.println("Decripted: " + messageDecrypted + " in "+ time + " mills");

        if (message.equals(messageDecrypted)) {
            System.out.println("Mesaj decriptat cu succes! ");
        }
        long start2 = System. nanoTime();
        BigInteger messageDecryptedCRT = rsa.CRT(d,p,q,cryptotext);
        long end2 = System.nanoTime();
        long time2 = start2-end2;
        System.out.println("Decypted with CRT: " + messageDecryptedCRT + " in " + time2 + " mills");

    }

}
