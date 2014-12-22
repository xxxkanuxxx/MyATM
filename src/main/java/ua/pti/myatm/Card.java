package ua.pti.myatm;

public interface Card {
    //Заблокирована ли карта или нет
    public boolean isBlocked();

    //Возвращает счет связанный с данной картой
    public Account getAccount();

    //Проверяет корректность пин-кода
    public boolean checkPin(int pinCode);

    //block card
    public void block();

    //get card id
    public int getCardID();
}
