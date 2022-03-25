package swt6.mitter.fhbay.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import swt6.mitter.fhbay.exceptions.BiddingNotOpenException;
import swt6.mitter.fhbay.logic.service.BidService;

@ShellComponent
public class BidCommands {

    @Autowired
    private BidService bidService;

    @ShellMethod("Adds a bid to a product")
    public String bid(long productId, long userId, double value) {
        try {
            bidService.addBid(productId, userId, value);
        } catch (BiddingNotOpenException ex) {
            return "Bidding for this product is currently not possible";
        } catch (Exception ex) {
            return "Adding bid failed, please try again";
        }

        return "Bid added";
    }
}
