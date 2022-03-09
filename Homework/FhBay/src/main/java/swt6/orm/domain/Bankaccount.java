package swt6.orm.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("b")
public class Bankaccount extends PaymentMethod{
    @Column(nullable = false)
    private String Iban;
    private String Bic;

    public Bankaccount() { }

    public Bankaccount(String iban, String bic, String ownerName) {
        super(ownerName);
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
