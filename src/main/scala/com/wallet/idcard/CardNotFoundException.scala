package com.wallet.idcard

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.NOT_FOUND)
case class CardNotFoundException(msg:String) extends RuntimeException(msg)
