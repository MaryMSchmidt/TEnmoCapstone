package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.NOT_FOUND, reason = "Transfer information given was not valid.")


public class TransferInvalidCreationException extends Exception{
    public TransferInvalidCreationException() {
        super("Transfer information given was not valid.");
    }

    //TODO start back here
}
