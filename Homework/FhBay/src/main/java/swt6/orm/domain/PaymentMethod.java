package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("p")
public abstract class PaymentMethod implements Serializable {
    @Id @GeneratedValue
    private Long Id;
    private String OwnerName;

    public PaymentMethod(String ownerName) {
        this.setOwnerName(ownerName);
    }

    public PaymentMethod() { }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }
}
