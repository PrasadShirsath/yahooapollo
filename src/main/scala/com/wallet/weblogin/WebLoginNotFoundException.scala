package com.wallet.weblogin

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by mrugen on 9/22/14.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
case class WebLoginNotFoundException(msg: String) extends RuntimeException(msg){

}
