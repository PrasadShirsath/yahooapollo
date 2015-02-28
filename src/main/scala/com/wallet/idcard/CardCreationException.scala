package com.wallet.idcard

import org.springframework.validation.FieldError

case class CardCreationException(msg:String,errors: Seq[FieldError]) extends RuntimeException(msg){

}