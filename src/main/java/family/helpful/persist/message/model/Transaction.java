package family.helpful.persist.message.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transaction extends BasicModel{

    @JsonIgnoreProperties( {"sentTransactions","receivedTransactions",
            "problemContents", "solutionContents", "problemTitles", "solutionTitles"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private User sender;

    @JsonIgnoreProperties( {"sentTransactions","receivedTransactions",
            "problemContents", "solutionContents", "problemTitles", "solutionTitles"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private User receiver;

    String objectType;
    Long   objectId;

    Long amount;

    String status;

    Long lastThankAmountSender;
    Long lastThankAmountReceiver;
    Long lastThankAmountObject;
    public Long getLastThankAmountSender() {
        return lastThankAmountSender;
    }

    public void setLastThankAmountSender(Long lastThankAmountSender) {
        this.lastThankAmountSender = lastThankAmountSender;
    }

    public Long getLastThankAmountReceiver() {
        return lastThankAmountReceiver;
    }

    public void setLastThankAmountReceiver(Long lastThankAmountReceiver) {
        this.lastThankAmountReceiver = lastThankAmountReceiver;
    }

    public Long getLastThankAmountObject() {
        return lastThankAmountObject;
    }

    public void setLastThankAmountObject(Long lastThankAmountObject) {
        this.lastThankAmountObject = lastThankAmountObject;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}
