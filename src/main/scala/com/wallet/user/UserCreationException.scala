package com.wallet.user

import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST)
case class UserCreationException(message: String,errors:Seq[FieldError])extends RuntimeException(message) {

}
