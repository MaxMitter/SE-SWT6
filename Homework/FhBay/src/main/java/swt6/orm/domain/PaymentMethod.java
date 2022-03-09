package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("p")
public abstract class PaymentMethod implements Serializable {
    @Id
    @GeneratedValue
    private Long Id;
    @ManyToOne
    private Customer Owner;

    public PaymentMethod() {
    }

    public PaymentMethod(Customer owner) {
        Owner = owner;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Customer getOwner() {
        return Owner;
    }

    public void setOwner(Customer owner) {
        Owner = owner;
    }
}
