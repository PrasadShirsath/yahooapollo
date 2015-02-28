package com.wallet.idcard

import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.hibernate.validator.constraints.NotEmpty

case class IDCardDTO(@JsonProperty("card_name") cardName:String,
                     @JsonProperty("card_number")cardNumber:String,
                     @JsonProperty("expiration_date") @JsonDeserialize(using = classOf[CustomDateSerializer]) expirationDate:Date) {
  @NotEmpty(message = "Card name cannot be empty, must be specified")
  def getCardName = cardName

  @NotEmpty(message="IDCard Number cannot be empty")
  def getCardNumber = cardNumber


}




