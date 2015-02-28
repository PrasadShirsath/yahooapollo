package com.wallet.idcard

import org.springframework.data.mongodb.repository.MongoRepository

trait CardRepository extends MongoRepository[IDCard,String] {
  def findByUserId(userId: String): java.util.List[IDCard]

}
