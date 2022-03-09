package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Bid implements Serializable {
    @Id @GeneratedValue
    private Long Id;
    private double Value;
    private LocalDateTime BiddingTime;
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Customer Buyer;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Product Article;

    public Bid() { }

    public Bid(double value, LocalDateTime biddingTime, Customer buyer, Product article) {
        Value = value;
        BiddingTime = biddingTime;
        Buyer = buyer;
        Article = article;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public LocalDateTime getBiddingTime() {
        return BiddingTime;
    }

    public void setBiddingTime(LocalDateTime biddingTime) {
        BiddingTime = biddingTime;
    }

    public Customer getBuyer() {
        return Buyer;
    }

    public void setBuyer(Customer buyer) {
        Buyer = buyer;
    }

    public Product getArticle() {
        return Article;
    }

    public void setArticle(Product article) {
        Article = article;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "Id=" + Id +
                ", Value=" + Value +
                ", BiddingTime=" + BiddingTime +
                ", Buyer=" + Buyer +
                ", Article=" + Article +
                '}';
    }
}
