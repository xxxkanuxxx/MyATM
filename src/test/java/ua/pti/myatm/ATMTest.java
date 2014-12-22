/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.internal.matchers.Null;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author KaNU
 */

public class ATMTest {

    @Test
    public void testGetMoneyInATM() throws NegativeAmount{
        System.out.println("Get Money In ATM Where MoneyAmount = 1000");
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        double expResult = 1000;
        double result = atm.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testSettingZeroToATMAmount() throws NegativeAmount{
        System.out.println("Get Money In ATM When ATM Is Empty");
        ATM atm = new ATM(0);
        double expResult = 0;
        double result = atm.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    @Test (expected = NegativeAmount.class)
    public void testSettingNegativeAmountToATM() throws NegativeAmount {
        System.out.println("Setting Negative Amount To ATM");
        ATM atm = new ATM(-110);
    }

    @Test
    public void testValidateCardWithCorrectPINandValidCard() throws NoCardInserted {
        System.out.println("Validation card in ATM");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.isBlocked()).thenReturn(false);
        ATM atm = new ATM();
        boolean expResult = true;
        boolean result = atm.validateCard(mockedcard,1234);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateCardWithUnsetUpPin() throws NoCardInserted{
        System.out.println("Validate Card With No Pin Set Up");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(false);
        int pinCode = 1234;
        ATM atm = new ATM();
        boolean expResult = false;
        boolean result = atm.validateCard(mockedcard, pinCode);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateBlockedCard() throws NoCardInserted{
        System.out.println("Validate Blocked Card");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.isBlocked()).thenReturn(true);
        int pinCode = 1234;
        ATM atm = new ATM();
        boolean expResult = false;
        boolean result = atm.validateCard(mockedcard,pinCode);
        assertEquals(expResult, result);
    }

    @Test(expected = NoCardInserted.class)
    public void testValidateCardWithNocardInitialized() throws NoCardInserted {
        System.out.println("Validate Card With No Card Initialized");
        Card card = null;;
        ATM atm = new ATM();
        boolean result = atm.validateCard(card,1234);
    }

    @Test
    public void testValidateCardWithCorrectPinAndCardUnlocked() throws NoCardInserted {
        System.out.println("Validate Card With Valid Pin Card & Card Is Not Blocked");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.isBlocked()).thenReturn(false);
        int pinCode = 1234;
        ATM atm = new ATM();
        boolean expResult = true;
        boolean result = atm.validateCard(mockedcard,pinCode);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateCardWithUncorrectPin() throws NoCardInserted{
        System.out.println("Validate Card With Uncorrect Pin");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(false);
        int pinCode = 1234;
        ATM atm = new ATM();
        boolean expResult = false;
        boolean result = atm.validateCard(mockedcard,pinCode);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateCardIsThereCheckingPIN() throws NoCardInserted{
        System.out.println("Validate Card for existing card PIN check");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(false);
        int pinCode = 1234;
        ATM atm = new ATM();
        boolean result = atm.validateCard(mockedcard,pinCode);
        verify(mockedcard, atLeastOnce()).checkPin(pinCode);
    }

    @Test
    public void testValidateCardIsThereCheckingIsCardBlocked() throws NoCardInserted{
        System.out.println("Validate Card for existing card block check");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1234)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(false);
        int pinCode = 1234;
        ATM atm = new ATM();
        boolean result = atm.validateCard(mockedcard,pinCode);
        verify(mockedcard, atLeastOnce()).isBlocked();
    }

    @Test(expected = NoCardInserted.class )
    public void testCheckBalanceWithNoCardInserted() throws NoCardInserted {
        System.out.println("Check Balance With No Card Inserted");
        ATM atm = new ATM();
        double result = atm.checkBalance();
    }

    @Test
    public void testCheckBalanceWithCardInserted() throws NoCardInserted{
        System.out.println("Check Balance With Card Inserted");
        Card mockedcard = mock(Card.class);
        Account cardaccount = mock(Account.class);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.getAccount()).thenReturn(cardaccount);
        when(cardaccount.getBalance()).thenReturn(1500.0);
        ATM atm = new ATM();
        atm.validateCard(mockedcard, 1234);
        double expResult = 1500.0;
        double result = atm.checkBalance();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testGetCashWithEnoughMoneyInCardAndATM() throws NegativeAmount, NotEnoughMoneyInATM, NoCardInserted, NotEnoughMoneyInAccount {
        System.out.println("Get Cash With Enough Money In Both Card & ATM");
        Card mockedcard = mock(Card.class);
        Account cardaccount = mock(Account.class);
        when(mockedcard.isBlocked()).thenReturn(false);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.getAccount()).thenReturn(cardaccount);
        when(cardaccount.getBalance()).thenReturn(1500.0);
        when(cardaccount.withdrow(1000)).thenReturn(1000.0);
        double amount = 1000.0;
        ATM atm = new ATM(2000);
        boolean t = atm.validateCard(mockedcard,1234);
        double expResult = 1500.0;
        double result = atm.getCash(amount);
        assertEquals(expResult, result, 0.0);
    }

    @Test(expected = NotEnoughMoneyInATM.class)
     public void testGetCashNotEnoughMoneyInATMException() throws NegativeAmount, NotEnoughMoneyInATM, NoCardInserted, NotEnoughMoneyInAccount {
        System.out.println("Get cash with not enough money in ATM");
        Card mockedcard = mock(Card.class);
        Account cardaccount = mock(Account.class);
        when(mockedcard.isBlocked()).thenReturn(false);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.getAccount()).thenReturn(cardaccount);
        when(cardaccount.getBalance()).thenReturn(1500.0);
        double amount = 1200.0;
        ATM atm = new ATM(1000);
        boolean t = atm.validateCard(mockedcard,1234);
        double result = atm.getCash(amount);
    }

    @Test(expected = NotEnoughMoneyInAccount.class)
    public void testGetCashNotenoughMoneyOnAccountException() throws NegativeAmount, NotEnoughMoneyInATM, NoCardInserted, NotEnoughMoneyInAccount {
        System.out.println("Get Cash With Not Enough Money On Account");
        Card mockedcard = mock(Card.class);
        Account cardaccount = mock(Account.class);
        when(mockedcard.getAccount()).thenReturn(cardaccount);
        when(cardaccount.getBalance()).thenReturn(1500.0);
        when(cardaccount.withdrow(1000)).thenReturn(1000.0);
        double amount = 1600.0;
        ATM atm = new ATM(2000);
        boolean t = atm.validateCard(mockedcard,1234);
        double result = atm.getCash(amount);
    }

    @Test
    public void testGetCashCheckingOrderOfMethods() throws NegativeAmount, NotEnoughMoneyInATM, NoCardInserted, NotEnoughMoneyInAccount {
        System.out.println("Get Cash test for right order of methods");
        Card mockedcard = mock(Card.class);
        Account cardaccount = mock(Account.class);
        when(mockedcard.isBlocked()).thenReturn(false);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.getAccount()).thenReturn(cardaccount);
        when(cardaccount.getBalance()).thenReturn(1500.0);
        when(cardaccount.withdrow(1000)).thenReturn(1000.0);
        double amount = 1000.0;
        ATM atm = new ATM(2000);
        boolean t = atm.validateCard(mockedcard,1234);
        double result = atm.getCash(amount);
        InOrder inOrder = inOrder(mockedcard, cardaccount);
        inOrder.verify(mockedcard).getAccount();
        inOrder.verify(cardaccount).getBalance();
        inOrder.verify(cardaccount).withdrow(1000);
    }

    @Test
    public void testGetCashWithAmountEquelsATMandAccountamounts() throws NegativeAmount, NotEnoughMoneyInATM, NoCardInserted, NotEnoughMoneyInAccount {
        System.out.println("Get Cash with exact same amount in ATM and card account");
        Card mockedcard = mock(Card.class);
        Account cardaccount = mock(Account.class);
        when(mockedcard.isBlocked()).thenReturn(false);
        when(mockedcard.checkPin(1234)).thenReturn(true);
        when(mockedcard.getAccount()).thenReturn(cardaccount);
        when(cardaccount.getBalance()).thenReturn(1500.0);
        when(cardaccount.withdrow(1500)).thenReturn(1500.0);
        double amount = 1500.0;
        ATM atm = new ATM(1500);
        boolean t = atm.validateCard(mockedcard,1234);
        double expResult = 1500.0;
        double result = atm.getCash(amount);
        assertEquals(expResult, result, 0.0);
    }

    @Test(expected = NoCardInserted.class)
    public void testGetCashWithNoCardInserted() throws NegativeAmount, NotEnoughMoneyInATM, NoCardInserted, NotEnoughMoneyInAccount {
        System.out.println("Get Cash With Not Enough Money On Account");
        ATM atm = new ATM(1000);
        double amount = 1000;
        double result = atm.getCash(amount);
    }

    @Test
    public void testValidateCardWithTripleUncorectPIN() throws NoCardInserted {
        System.out.println("Validation card in ATM");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1111)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(false);
        ATM atm = new ATM();
        boolean result = atm.validateCard(mockedcard,1111);
        result = atm.validateCard(mockedcard,1111);
        result = atm.validateCard(mockedcard,1111);
        verify(mockedcard, atLeastOnce()).block();
    }

    @Test
    public void testValidateCardWithUncorectPINFiveTimes() throws NoCardInserted {
        System.out.println("Validation card in ATM");
        Card mockedcard = mock(Card.class);
        when(mockedcard.checkPin(1111)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(false);
        ATM atm = new ATM();
        boolean result = atm.validateCard(mockedcard,1111);
        result = atm.validateCard(mockedcard,1111);
        result = atm.validateCard(mockedcard,1111);
        result = atm.validateCard(mockedcard,1111);
        result = atm.validateCard(mockedcard,1111);
        verify(mockedcard, atLeastOnce()).block();
    }

    @Test
    public void testValidateCardWithUncorectPINTwoTimesWithUncorrectPIN() throws NoCardInserted {
        System.out.println("Validation card in ATM");
        Card mockedcard = mock(Card.class);
        Card elsecard = mock(Card.class);
        when(mockedcard.checkPin(1111)).thenReturn(false);
        when(mockedcard.isBlocked()).thenReturn(false);
        when(mockedcard.getCardID()).thenReturn(1);
        when(elsecard.checkPin(1111)).thenReturn(false);
        when(elsecard.isBlocked()).thenReturn(false);
        when(elsecard.getCardID()).thenReturn(2);
        ATM atm = new ATM();
        boolean result = atm.validateCard(mockedcard,1111);
        result = atm.validateCard(mockedcard,1111);
        result = atm.validateCard(elsecard,1111);
        result = atm.validateCard(elsecard,1111);
        verify(mockedcard, never()).block();
        verify(elsecard, never()).block();
    }


}
