package omuraliev.txnmanager.transactionapi.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransferRequest {
    @NotNull(message = "From User ID is required")
    @Positive(message = "From User ID must be positive")
    @Schema(description = "id of sender", example = "1")
    private Long fromUserId;

    @NotNull(message = "To User ID is required")
    @Positive(message = "To User ID must be positive")
    @Schema(description = "id of recipient", example = "2")
    private Long toUserId;

    @Positive(message = "Amount must be positive")
    @Schema(description = "amount money", example = "2")
    private BigDecimal amount;



    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
