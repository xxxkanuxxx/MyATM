package ua.pti.myatm;

public class ATM {
    private double MoneyAmount=0;
    private Card CardInserted;

    ATM(){}

    public void insertCard(Card CardIn){
        CardInserted = CardIn;
    }

    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM){
         MoneyAmount += moneyInATM;
    }

    // Возвращает каоличестов денег в банкомате
    public double getMoneyInATM() {
         return MoneyAmount;
    }
        
    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode){
        try {

            if (card.isBlocked() || !card.checkPin(pinCode)) {
                return false;
            } else
                CardInserted = card;
        }
        catch (NullPointerException e){System.err.println("Check your PIN & whether your card is active");}
         //       throw new UnsupportedOperationException("Check your PIN & whether your card is active");
         //catch (UnsupportedOperationException e){System.err.println("Check your PIN & whether your card is active");};
         //throw new UnsupportedOperationException("Not yet implemented");
        return true;
    }
    
    //Возвращает сколько денег есть на счету
    public double checkBalance(){
        try {
            Account acc = CardInserted.getAccount();
            return acc.getBalance();
        }finally {}
        //catch (NullPointerException e){System.err.println("There is no card inserted in ATM");}
        //return 0;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount){
        try {
            Account acc = CardInserted.getAccount();
            if (amount < MoneyAmount){
                if (amount < acc.getBalance()){
                    MoneyAmount -= acc.withdrow(amount);
                    return amount;
                }
                else throw new UnsupportedOperationException("NotEnoughMoneyInAccount");
            }
            else
                throw new UnsupportedOperationException("NotEnoughMoneyInATM");
        }finally {}
        //catch (NullPointerException e){System.err.println("There is no card inserted in ATM");}
        //catch (UnsupportedOperationException e){System.err.println(e);}
        //return 0;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
