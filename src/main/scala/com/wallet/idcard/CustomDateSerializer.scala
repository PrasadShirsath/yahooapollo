package com.wallet.idcard

import java.text.SimpleDateFormat
import java.util.Date

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}

/**
 * Created by mrugen on 9/21/14.
 */
class CustomDateSerializer extends JsonDeserializer[Date]() {
  def deserialize(jp: JsonParser, ctxt: DeserializationContext):Date = {
      val sdf:SimpleDateFormat = new SimpleDateFormat("MM-dd-yyyy")
      val dateAsText: String = jp.getText
      sdf.parse(dateAsText)
  }
}
