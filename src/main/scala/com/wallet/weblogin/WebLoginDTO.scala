package com.wallet.weblogin

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.NotEmpty

case class WebLoginDTO(@JsonProperty("url")url:String,
                       @JsonProperty("login")login:String,
                       @JsonProperty("password")password:String) {

  @NotEmpty
  def getUrl() = url

  @NotEmpty
  def getLogin() = login

  @NotEmpty
  def getPassword() = password

}
