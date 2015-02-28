package com.wallet.weblogin

import java.util.Date

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "webLogins")
case class WebLogin(@JsonProperty("login_id")  var loginId:String="l-%s".format(new Date().getTime().toString.drop(7).take(6)),
                    @JsonProperty("url")url:String,
                    @JsonProperty("login")login:String,
                    @JsonProperty("password")password:String,
                    @JsonIgnore() var userId:String) {

  @Id
  def getLoginId() = loginId

  def getUrl() = url

  def getLogin() = login

  def getPassword() = password

}
