package com.wallet.bank

import org.springframework.data.mongodb.repository.MongoRepository

trait BankRepository extends MongoRepository[BankAccount,String]{
  def findByUserId(userId: String): java.util.List[BankAccount]

}
