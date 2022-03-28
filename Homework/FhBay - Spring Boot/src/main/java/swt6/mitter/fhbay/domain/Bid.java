package swt6.mitter.fhbay.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Bid implements Serializable {
    @Id @GeneratedValue
    private Long id;
    private double value;
    private LocalDateTime biddingTime;
    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Customer buyer;
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Product article;

    public Bid() { }

    public Bid(double value, LocalDateTime biddingTime, Customer buyer, Product article) {
        this.value = value;
        this.biddingTime = biddingTime;
        this.buyer = buyer;
        this.article = article;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(LocalDateTime biddingTime) {
        this.biddingTime = biddingTime;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    public Product getArticle() {
        return article;
    }

    public void setArticle(Product article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "Id=" + id +
                ", Value=" + value +
                ", BiddingTime=" + biddingTime +
                ", Buyer=" + buyer +
                ", Article=" + article +
                '}';
    }
}
