/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author KaNU
 */
public class ATMTest {

    @Test
    public void testGetMoneyInATM() {
        System.out.println("Get Money In ATM Where MoneyAmount = 1000");
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        double expResult = 1000;
        double result = atm.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testIsATMdefaultEmpty() {
        System.out.println("Get Money In ATM When ATM Is Empty");
        ATM atm = new ATM();
        double expResult = 0;
        double result = atm.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testValidateCardWithUnsetUpPin() {
        System.out.println("Validate Card With No Pin Set Up");
        Card mockedcard = mock(Card.class);
        int pinCode = 1234;
        ATM atm = new ATM();
        boolean expResult = false;
        boolean result = atm.validateCard(mockedcard, pinCode);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateCardWithCorrectPinAndCardUnlocked() {
        System.out.println("Validate Card With Valid Pin Card & Card Is Not Blocked");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.isBlocked()).thenReturn(false);
        int pinCode = 1234;
        ATM atm = new ATM();
        boolean expResult = true;
        boolean result = atm.validateCard(mockedcard, pinCode);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateCardWithUncorrectPin() {
        System.out.println("Validate Card With Uncorrect Pin");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(false);
        int pinCode = 1234;
        ATM atm = new ATM();
        boolean expResult = false;
        boolean result = atm.validateCard(mockedcard, pinCode);
        assertEquals(expResult, result);
    }
/*
    @Test(expected = NullPointerException.class )
    public void testCheckBalance() {
        System.out.println("checkBalance");
        ATM atm = new ATM();
        double expResult = 0.0;
        double result = atm.checkBalance();
        assertEquals(expResult, result, 0.0);
    }
*/
    @Test
    public void testCheckBalanceWithCardInserted() {
        System.out.println("Check Balance With Card Inserted");
        Card mockedcard = mock(Card.class);
        Account cardaccount = mock(Account.class);
        when(mockedcard.getAccount()).thenReturn(cardaccount);
        when(cardaccount.getBalance()).thenReturn(1500.0);
        ATM atm = new ATM();
        atm.insertCard(mockedcard);
        double expResult = 1500.0;
        double result = atm.checkBalance();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testGetCashWithEnoughMoneyInCardAndATM() {
        System.out.println("Get Cash With Enough Money In Both Card & ATM");
        Card mockedcard = mock(Card.class);
        Account cardaccount = mock(Account.class);
        when(mockedcard.getAccount()).thenReturn(cardaccount);
        when(cardaccount.getBalance()).thenReturn(1500.0);
        when(cardaccount.withdrow(1000)).thenReturn(1000.0);
        double amount = 1000.0;
        ATM atm = new ATM(2000);
        atm.insertCard(mockedcard);
        double expResult = 1000.0;
        double result = atm.getCash(amount);
        assertEquals(expResult, result, 0.0);
    }
/*
    @Test(expected = UnsupportedOperationException.class)
     public void testGetCashNotenoughMoneyInATMException() {
        System.out.println("getCash with not enough money in ATM");
        Card mockedcard = mock(Card.class);
        Account cardaccount = mock(Account.class);
        when(mockedcard.getAccount()).thenReturn(cardaccount);
        when(cardaccount.getBalance()).thenReturn(1500.0);
        when(cardaccount.withdrow(1000)).thenReturn(1000.0);
        double amount = 16000.0;
        ATM atm = new ATM(2000);
        atm.insertCard(mockedcard);
        //double expResult = 1000.0;
        double result = atm.getCash(amount);
        //assertEquals(expResult, result, 0.0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCashNotenoughMoneyOnAccountException() {
        System.out.println("Get Cash With Not Enough Money On Account");
        Card mockedcard = mock(Card.class);
        Account cardaccount = mock(Account.class);
        when(mockedcard.getAccount()).thenReturn(cardaccount);
        when(cardaccount.getBalance()).thenReturn(1500.0);
        when(cardaccount.withdrow(1000)).thenReturn(1000.0);
        double amount = 16000.0;
        ATM atm = new ATM(2000);
        atm.insertCard(mockedcard);
        //double expResult = 1000.0;
        double result = atm.getCash(amount);
        //assertEquals(expResult, result, 0.0);
    }

    @Test(expected = ArithmeticException.class)
    public void test() {
        int a = 1/0;
    }
*/
}
