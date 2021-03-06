package net.eventstore.client.message;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.Getter;
import net.eventstore.client.model.ParseException;
import net.eventstore.client.model.Message;
import net.eventstore.client.tcp.TcpCommand;
import net.eventstore.client.message.ClientMessageDtos.OperationResult;

/**
 * WriteEventsCompleted
 *
 * @author Stasys
 */
@Getter
public class DeleteStreamCompleted extends Message {

    private OperationResult result;

    public DeleteStreamCompleted() {
        super(TcpCommand.DeleteStreamCompleted);
    }

    @Override
    public void parse(byte[] data) throws ParseException {
        try {
            ClientMessageDtos.DeleteStreamCompleted dto = ClientMessageDtos.DeleteStreamCompleted.parseFrom(data);
            this.message = dto.getMessage();
            result = dto.getResult();
        } catch (InvalidProtocolBufferException ex) {
            throw new ParseException(ex);
        }
    }

    protected String toResultInfo() {
        return String.format("Received (%s): %s", this.getResult(), this.getMessage());
    }

    public boolean didSucceed() {
        if (getResult().equals(OperationResult.Success)) {
            return true;
        } else {
            return false;
        }
    }

}
