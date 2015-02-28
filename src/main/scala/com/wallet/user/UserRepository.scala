package com.wallet.user

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component

@Component("userRepository")
trait UserRepository extends MongoRepository[User,String]{
  def findByUserId(userId:String):User
}
