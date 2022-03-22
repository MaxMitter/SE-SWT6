package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @Fetch(FetchMode.SELECT)
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer Owner;

    public PaymentMethod() {
    }

    public PaymentMethod(Customer owner) {
        Owner = owner;
    }

    public void attachOwner(Customer customer) {
        if (this.Owner != null) {
            this.Owner.getPaymentMethods().remove(this);
        }
        this.Owner = customer;
        this.Owner.getPaymentMethods().add(this);
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
