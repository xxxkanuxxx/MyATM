/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author KaNU
 */
public class ATMTest {

    @Test
    public void testGetMoneyInATM() {
        System.out.println("getMoneyInATM Where MoneyAmount=1000");
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        double expResult = 1000;
        double result = atm.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testIsATMdefaultEmpty() {
        System.out.println("getMoneyInATM When ATM is Empty");
        ATM atm = new ATM();
        double expResult = 0;
        double result = atm.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testValidateCardWithUnsetUpPin() {
        System.out.println("validateCard With no Pin set up");
        Card mockedcard = mock(Card.class);
        int pinCode = 1234;
        ATM atm = new ATM();
        boolean expResult = false;
        boolean result = atm.validateCard(mockedcard, pinCode);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateCardWithCorrectPinAndItsUnlocked() {
        System.out.println("validateCard with valid card");
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
        System.out.println("validateCard With Uncorrect Pin");
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
    public void testGetCash() {
        System.out.println("getCash with enough money in both card & ATM");
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

}
