package com.wallet.bank

import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST)
case class BankAccountCreationException(msg:String,errors:Seq[FieldError]) extends RuntimeException(msg){

}
