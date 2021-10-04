package atm.view;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class ViewCash implements ViewInterface {

    @Override
    public void show() {
        var streamReader = new Scanner(System.in);
        System.out.print("Enter the cash volume or just push ENTER to cancel operation: ");
        consumer.accept(Optional.ofNullable(streamReader.nextBigInteger()));
    }

    @Override
    public void hide() {
        System.out.println("=========================");
    }

    protected ViewCash(Object consumer) {
        if (!(consumer instanceof Consumer))
            throw new ClassCastException("ViewCash: controller does not implements Consumer interface");
        @SuppressWarnings("unchecked") final var consumer_ = (Consumer<Optional<BigInteger>>) consumer;
        this.consumer = consumer_;
    }
    private final Consumer<Optional<BigInteger>> consumer;
}
