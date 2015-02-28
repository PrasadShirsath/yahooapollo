package com.wallet.bank

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
case class BankAccountNotFoundException(message: String) extends RuntimeException(message)
