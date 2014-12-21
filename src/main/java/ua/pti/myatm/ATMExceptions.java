package ua.pti.myatm;

/**
 * Created by KaNU on 21.12.2014.
 */
class NoCardInserted extends Exception {
    NoCardInserted(String msg) {
        super(msg);
    }

}
class NotEnoughMoneyInAccount extends Exception {
    NotEnoughMoneyInAccount(String msg) {
        super(msg);
    }
}
class NotEnoughMoneyInATM extends Exception {
    NotEnoughMoneyInATM(String msg) {
        super(msg);
    }
}

class NegativeAmount extends Exception {
    NegativeAmount(String msg) {
        super(msg);
    }
}