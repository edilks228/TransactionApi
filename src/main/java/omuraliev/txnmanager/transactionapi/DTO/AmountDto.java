package omuraliev.txnmanager.transactionapi.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class AmountDto {
    @Positive(message = "Amount must be positive")
    @Schema(description = "amount money", example = "10000")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
