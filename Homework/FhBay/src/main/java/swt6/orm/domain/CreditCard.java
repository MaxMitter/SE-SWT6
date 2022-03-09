package swt6.orm.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("c")
public class CreditCard extends PaymentMethod {
    private String CardNumber;
    private String ExpirationDate;
    private String CcvNumber;

    public CreditCard() { }

    public CreditCard(String ownerName, String cardNumber, String expirationDate, String ccvNumber) {
        super(ownerName);
        CardNumber = cardNumber;
        ExpirationDate = expirationDate;
        CcvNumber = ccvNumber;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }

    public String getCcvNumber() {
        return CcvNumber;
    }

    public void setCcvNumber(String ccvNumber) {
        CcvNumber = ccvNumber;
    }
}
