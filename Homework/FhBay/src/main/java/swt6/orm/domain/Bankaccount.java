package swt6.orm.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("b")
public class Bankaccount extends PaymentMethod{
    private String Iban;
    private String Bic;

    public Bankaccount() { }

    public Bankaccount(Customer owner, String iban, String bic) {
        super(owner);
        Iban = iban;
        Bic = bic;
    }

    public String getIban() {
        return Iban;
    }

    public void setIban(String iban) {
        Iban = iban;
    }

    public String getBic() {
        return Bic;
    }

    public void setBic(String bic) {
        Bic = bic;
    }
}
