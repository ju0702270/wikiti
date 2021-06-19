package com.rochez.wikiTI.exception;

import com.rochez.wikiTI.utility.StringUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author : Rochez Justin
 * @Creation : 01/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Exception pour personnaliser le message d'erreur
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorMsgException extends Exception{


    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ErrorMsgException(String message) {
        super(message);
    }
}
