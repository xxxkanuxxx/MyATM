package ua.pti.myatm;

public class ATM {
    private double moneyAmount =0;
    private Card cardInserted;
    private boolean isCardIn = false;
    private int tries = 0;
    private int cardID = 0;

    ATM(){}

    //Задать количество денег в банкомате
    ATM(double moneyInATM) throws NegativeAmount {
        if (moneyInATM >= 0)
            moneyAmount = moneyInATM;
        else
            throw new NegativeAmount("You can't set ATM with negative amount");
    }

    // Возвращает каоличестов денег в банкомате
    public double getMoneyInATM() {
         return moneyAmount;
    }
        
    //С вызова данного метода начинается работа с картой
    //Метод принимает аргументом пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode) throws NoCardInserted {
        try {
            cardInserted = card;
            isCardIn = true;

            if (cardID != card.getCardID()) {
                tries = 0;
                cardID = card.getCardID();
            }

            if (cardInserted.isBlocked() || !cardInserted.checkPin(pinCode)) {
                tries += 1;
                if (tries >= 3)
                    card.block();
                return false;
            }

            else {
                return true;
            }
        }
        catch (NullPointerException e){
            throw new NoCardInserted("Insert card, please");
        }
    }
    
    //Возвращает сколько денег есть на счету
    public double checkBalance() throws NoCardInserted {
        try {
            Account acc = cardInserted.getAccount();
            return acc.getBalance();
        }
        catch (NullPointerException e){throw new NoCardInserted("There is no card in ATM");}
    }

    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount) throws NoCardInserted, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
        try {
            Account acc = cardInserted.getAccount();
            if (amount <= moneyAmount) {
                if (amount <= acc.getBalance()) {
                    moneyAmount -= acc.withdrow(amount);
                    return acc.getBalance();
                }
                else throw new NotEnoughMoneyInAccount("There is not enough money on your account");
            }
            else {
                throw new NotEnoughMoneyInATM("There is not enough money in ATM");
            }
            }
            catch (NullPointerException e){
            throw new NoCardInserted("There is no card in ATM");
        }
    }
}
