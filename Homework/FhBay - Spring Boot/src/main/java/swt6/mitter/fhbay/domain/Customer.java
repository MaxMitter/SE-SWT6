package swt6.mitter.fhbay.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String Name;

    @Column(nullable = false)
    private String Email;

    @Embedded
    @AttributeOverride(name = "zipCode", column = @Column(name = "shippingAddress_zipCode"))
    @AttributeOverride(name = "city", column = @Column(name = "shippingAddress_city"))
    @AttributeOverride(name = "street", column = @Column(name = "shippingAddress_street"))
    private Address shippingAddress;

    @Embedded
    @AttributeOverride(name = "zipCode", column = @Column(name = "billingAddress_zipCode"))
    @AttributeOverride(name = "city", column = @Column(name = "billingAddress_city"))
    @AttributeOverride(name = "street", column = @Column(name = "billingAddress_street"))
    private Address billingAddress;

    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            mappedBy = "Owner", orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    public Customer() {
    }

    public Customer(String name, String email, Address shippingAddress, Address billingAddress) {
        Name = name;
        this.Email = email;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
    }

    public void addPaymentMethod(PaymentMethod method) {
        if (method == null)
            throw new IllegalArgumentException("Payment method cannot be null");

        var cust = method.getOwner();
        if (cust != null)
            cust.getPaymentMethods().remove(method);
        this.paymentMethods.add(method);
        method.setOwner(this);
    }

    public boolean removePaymentMethod(PaymentMethod method) {
        if (method == null)
            throw new IllegalArgumentException("Payment method cannot be null");

        method.setOwner(null);
//        DaoFactory.getPaymentMethodDao().remove(method);
//        JpaUtil.commit();
        return this.paymentMethods.remove(method);
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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
