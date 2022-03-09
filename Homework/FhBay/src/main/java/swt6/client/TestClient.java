package swt6.client;

import swt6.orm.domain.Bankaccount;
import swt6.orm.domain.PaymentMethod;
import swt6.orm.repository.PaymentMethodRepository;

public class TestClient {

    public static void main(String[] args) {
        var repo = new PaymentMethodRepository();

        var method = new Bankaccount("Max Mustermann", "AT47700000000000", "ATSBGRAI09");
        repo.addPaymentMethod(method);
    }
}
