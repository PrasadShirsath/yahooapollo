package com.wallet.user

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.NotEmpty

case class UserDTO(@JsonProperty("email") email: String,
                   @JsonProperty("password") password: String,
                   @JsonProperty("name") name: String=""
                    ) {
  @NotEmpty
  def getEmail = email

  @NotEmpty
  def getPassword = password

}
