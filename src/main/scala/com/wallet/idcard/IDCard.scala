package com.wallet.idcard

import java.text.SimpleDateFormat
import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@JsonSerialize(using = classOf[CustomIDCardSerializer])
@Document(collection = "idCards")
case class IDCard(@JsonProperty("card_id") var cardId: String = "c-%s".format(new Date().getTime.toString.drop(7).take(6)),
                  @JsonProperty("card_name")var cardName: String,
                  @JsonProperty("card_number") var cardNumber: String,
                  @BeanProperty @JsonProperty("expiration_date") var expirationDate: Date,
                  var userId:String) {

  @NotEmpty
  def getCardName() = cardName

  @NotEmpty
  def getCardNumber() = cardNumber

  @Id
  def getCardId() = cardId

}

class CustomIDCardSerializer extends JsonSerializer[IDCard] {
  def serialize(card: IDCard, jgen: JsonGenerator, provider: SerializerProvider){
    val simpleDateFormat: SimpleDateFormat = new SimpleDateFormat("MM-dd-yyyy")
    jgen.writeStartObject()
    jgen.writeStringField("card_id",card.cardId)
    jgen.writeStringField("card_name",card.getCardName())
    jgen.writeStringField("card_number",card.getCardNumber())
    if(card.expirationDate!=null){
    val formattedDateString: String = simpleDateFormat.format(card.getExpirationDate)
    jgen.writeStringField("expiration_date", formattedDateString)
    }
    jgen.writeEndObject()
  }
}
