package com.wallet.bank

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
case class InvalidRoutingNumberException(msg: String) extends RuntimeException(msg){

}
