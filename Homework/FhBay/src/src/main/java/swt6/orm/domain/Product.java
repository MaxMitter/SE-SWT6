package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Product implements Serializable {
    @Id @GeneratedValue
    private Long Id;
    private String Name;
    private String Description;
    private double StartingBid;
    private double FinalBid;
    private LocalDateTime AuctionStart;
    private LocalDateTime AuctionEnd;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Customer Seller;
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Customer Buyer;
    private ProductStatus Status;

    public Product() { }

    public Product(String name, String description, double startingBid, double finalBid, LocalDateTime auctionStart, LocalDateTime auctionEnd, Customer seller, Customer buyer, ProductStatus status) {
        Name = name;
        Description = description;
        StartingBid = startingBid;
        FinalBid = finalBid;
        AuctionStart = auctionStart;
        AuctionEnd = auctionEnd;
        Seller = seller;
        Buyer = buyer;
        Status = status;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getStartingBid() {
        return StartingBid;
    }

    public void setStartingBid(double startingBid) {
        StartingBid = startingBid;
    }

    public double getFinalBid() {
        return FinalBid;
    }

    public void setFinalBid(double finalBid) {
        FinalBid = finalBid;
    }

    public LocalDateTime getAuctionStart() {
        return AuctionStart;
    }

    public void setAuctionStart(LocalDateTime auctionStart) {
        AuctionStart = auctionStart;
    }

    public LocalDateTime getAuctionEnd() {
        return AuctionEnd;
    }

    public void setAuctionEnd(LocalDateTime auctionEnd) {
        AuctionEnd = auctionEnd;
    }

    public Customer getSeller() {
        return Seller;
    }

    public void setSeller(Customer seller) {
        Seller = seller;
    }

    public Customer getBuyer() {
        return Buyer;
    }

    public void setBuyer(Customer buyer) {
        Buyer = buyer;
    }

    public ProductStatus getStatus() {
        return Status;
    }

    public void setStatus(ProductStatus status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", StartingBid=" + StartingBid +
                ", FinalBid=" + FinalBid +
                ", AuctionStart=" + AuctionStart +
                ", AuctionEnd=" + AuctionEnd +
                ", Seller=" + Seller +
                ", Buyer=" + Buyer +
                ", Status=" + Status +
                '}';
    }
}
