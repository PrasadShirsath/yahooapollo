package com.wallet.weblogin

import org.springframework.data.mongodb.repository.MongoRepository


trait WebLoginRepository extends MongoRepository[WebLogin,String]{
  def findByUserId(userId: String): java.util.List[WebLogin]
}
