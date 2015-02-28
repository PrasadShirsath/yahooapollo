package com.wallet.bank

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.data.annotation.Id

import scala.beans.BeanProperty

@JsonSerialize(using = classOf[CustomBankAccountSerializer])
case class BankAccount(@JsonProperty("ba_id") var baId:String,
                       @BeanProperty @JsonProperty("account_name") var accountName:String,
                       @JsonProperty("routing_number") routingNumber:String,
                       @JsonProperty("account_number") accountNumber:String,
                       @JsonProperty("user_id") var userId:String)(){
  @NotEmpty
  def getRoutingNumber() = routingNumber

  @NotEmpty
  def getAccountNumber() = accountNumber

  @Id
  def getBaId() = baId
}

class CustomBankAccountSerializer extends JsonSerializer[BankAccount] {
  def serialize(account: BankAccount, jgen: JsonGenerator, provider: SerializerProvider){
    jgen.writeStartObject()
    jgen.writeStringField("ba_id",account.getBaId)
    jgen.writeStringField("routing_number",account.getRoutingNumber())
    jgen.writeStringField("account_number",account.getAccountNumber())
    if(account.accountName!=null){
      jgen.writeStringField("account_name", account.accountName)
    }
    jgen.writeEndObject()
  }
}