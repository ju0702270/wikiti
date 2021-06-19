package com.rochez.wikiTI.exception;


import com.rochez.wikiTI.utility.StringUtil;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * @Author : Rochez Justin
 * @Creation : 01/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe D'exception, cette classe est uniquement là pour spécifier une duplication de mail ou de matricule chez les utilisateurs
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DataIntergityException extends DataIntegrityViolationException {

    public DataIntergityException() {
        super(StringUtil.bundle.getString("error.duplicate"));
    }
}
