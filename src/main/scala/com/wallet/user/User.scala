package com.wallet.user

import java.text.SimpleDateFormat
import java.util.Date

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat

import scala.beans.BeanProperty


@JsonSerialize(using= classOf[UserToJsonSerializer])
@Document(collection = "users")
case class User(@JsonProperty("user_id") var userId: String = "u-%s".format(new Date().getTime.toString.drop(7).take(6)),
                @JsonProperty(value = "email") var email: String,
                @JsonProperty(value = "password") var password: String,
                @JsonProperty("name") @BeanProperty var name: String,
                @JsonProperty("createdAt") var createdAt: Date = new Date(),
                @JsonProperty("updatedAt") var updatedAt: Date = new Date()
                 ) {
  @Id
  def getUserId() = userId

  @NotEmpty
  def getEmail() = email

  @NotEmpty
  def getPassword() = password

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  def getCreatedAt() = createdAt


}


class UserToJsonSerializer extends JsonSerializer[User] {
  def serialize(user: User, jgen: JsonGenerator, provider: SerializerProvider) = {
    jgen.writeStartObject()
    jgen.writeStringField("user_id",user.getUserId())
    jgen.writeStringField("email",user.getEmail())
    jgen.writeStringField("password",user.getPassword())
    val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    jgen.writeStringField("created_at",sdf.format(user.getCreatedAt()))
    jgen.writeEndObject()
  }
}

//{
//"user_id" : "u-123456",
//“email”: “John.Smith@Gmail.com”,
//“password”: “secret”,
//"created_at" : "2014-09-16T13:28:06.419Z"
//}





