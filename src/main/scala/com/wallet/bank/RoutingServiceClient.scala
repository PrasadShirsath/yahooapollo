package com.wallet.bank

import com.fasterxml.jackson.databind.ObjectMapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class RoutingServiceClient @Autowired()(restTemplate:RestTemplate)  {
  def authenticateRoutingNumber(routingNumber: String):Option[RoutingResponse] = {
    val response = restTemplate.getForEntity("http://www.routingnumbers.info/api/data.json?rn=%s".format(routingNumber.trim),classOf[String])
    val mapper = new ObjectMapper()
    import com.fasterxml.jackson.module.scala.DefaultScalaModule
    mapper.registerModule(DefaultScalaModule)
    val routingResponse: RoutingResponse = mapper.readValue(response.getBody,classOf[RoutingResponse])

    routingResponse.code match {
      case 200 => Option(routingResponse)
      case _ => None
    }
  }

}

case class RoutingResponse(address:String,
                           institution_status_code:String,
                           code:Int,
                           office_code:String,
                           telephone:String,
                           data_view_code:String,
                           state:String,
                           change_date:String,
                           record_type_code:String,
                           new_routing_number:String,
                           rn:String,
                           zip:String,
                           city:String,
                           customer_name:String,
                           message:String,
                           routing_number:String)

