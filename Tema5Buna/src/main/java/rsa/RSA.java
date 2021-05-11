package rsa;
// Java Program to Implement the RSA Algorithm

import java.math.*;
import java.util.*;

public class RSA {
    BigInteger computeD(BigInteger e, BigInteger fi) {
        BigInteger d;
        BigInteger k;

        k = BigInteger.ONE;

        while (true) {
            k = k.add(fi);

            if (k.mod(e).equals(BigInteger.ZERO)) {
                d = k.divide(e);
                return d;
            }
        }
    }


    public BigInteger crypt(BigInteger text, BigInteger e, BigInteger n) {
        BigInteger res;
        BigInteger t;
        t = text;
        res = t.mod(n).modPow(e, n);
        return res;
    }



    public BigInteger decrypt(BigInteger text, BigInteger d, BigInteger n) {
        BigInteger res;
        res = text.mod(n).modPow(d, n);
        return res;
    }

    public List<BigInteger> euclidAlg(BigInteger a, BigInteger b) {
        List<BigInteger> list = new ArrayList<>();
        if (a.equals(BigInteger.ZERO)) {
            list.add(b);
            list.add(BigInteger.ZERO);
            list.add(BigInteger.ONE);
            return list;
        }
        list = euclidAlg(b.mod(a), a);

        BigInteger g = list.get(0);
        BigInteger y = list.get(1);
        BigInteger x = list.get(2).subtract(b.divide(a).multiply(y));
        list.clear();
        list.add(g);
        list.add(x);
        list.add(y);
        return list;

    }

    public BigInteger CRT(BigInteger d, BigInteger p, BigInteger q, BigInteger c) {
        BigInteger dp = d.mod(p.subtract(BigInteger.ONE));
        BigInteger dq = d.mod(q.subtract(BigInteger.ONE));
        List<BigInteger> list = euclidAlg(q, p);

        BigInteger x = list.get(1);

        BigInteger z = x.mod(p);
        BigInteger m1 = c.modPow(dp, p);
        BigInteger m2 = c.modPow(dq, q);
        BigInteger h = (z.multiply(m1.subtract(m2))).mod(p);
        BigInteger m = (m2.add(h.multiply(q))).mod(p.multiply(q));
        return m;
    }







}