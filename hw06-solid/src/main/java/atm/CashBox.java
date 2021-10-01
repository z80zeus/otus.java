package atm;

import java.math.BigInteger;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class CashBox {
    public void giveOut(BigInteger cash) {
        for (var bankNote : bankNotes) {
            var num = cash.divide(BigInteger.valueOf(bankNote));
            if (!num.equals(BigInteger.valueOf(0)))
                System.out.println("Bank note " + bankNote + ": " + num);
            cash = cash.remainder(BigInteger.valueOf(bankNote));
        }
    }

    public void open() {
        System.out.println("Cashbox is opened.");
    }

    public Map<Integer, Integer> takeIn() {
        var rtn = new TreeMap<Integer,Integer>();
        var rnd = new Random();
        for (var bankNote: bankNotes) {
            var num = rnd.nextInt(10);
            rtn.put(bankNote, num);
        }
        return rtn;
    }

    private static final int[] bankNotes = { 5000, 1000, 500, 100, 50, 10, 5, 1 };
}
