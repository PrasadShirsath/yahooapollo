package com.wallet.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User Not Found in the System")
case class UserNotFoundException(message: String) extends RuntimeException(message) {

}
