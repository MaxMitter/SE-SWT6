package swt6.orm.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue
    private Long Id;
    private String Name;
    @OneToOne
    private Address shippingAddress;
    @OneToOne(cascade = CascadeType.ALL)
    private Address billingAddress;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAYMENTMETHOD_ID")
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    public Customer() { }

    public Customer(String name, Address shippingAddress, Address billingAddress, Set<PaymentMethod> paymentMethods) {
        Name = name;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.paymentMethods = paymentMethods;
    }

    public void addPaymentMethod(PaymentMethod method) {
        if (method == null)
            throw new IllegalArgumentException("Payment method cannot be null");

        this.paymentMethods.add(method);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(Set<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", shippingAddress=" + shippingAddress +
                ", billingAddress=" + billingAddress +
                ", paymentMethods=" + paymentMethods +
                '}';
    }
}
