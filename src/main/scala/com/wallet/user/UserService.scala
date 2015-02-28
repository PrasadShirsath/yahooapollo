package com.wallet.user

import java.util.Date

import com.wallet.idcard.IDCard
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserService @Autowired()(userRepository:UserRepository) {

  val cards = collection.mutable.Map[String,collection.mutable.ArrayBuffer[IDCard]]()

  def update(userUpdate: UserDTO, userId: String): Option[User] = {
    fetchById(userId) match {
      case Some(user: User) => {
        user.email = userUpdate.email
        user.password = userUpdate.password
        user.name=userUpdate.name
        user.updatedAt = new Date()
        Some(userRepository.save(user))
      }
      case _ => None
    }
  }


  def fetchById(userId: String): Option[User] =   userRepository.findByUserId(userId) match{case user:User=> Option(user); case _=>None}

  def createUser(userDTO: UserDTO): User = {
    val creationDateTime: Date = new Date()
    val createdAt = creationDateTime
    val updatedAt = creationDateTime
    val user: User = User(email=userDTO.email,password=userDTO.password,name=userDTO.name)
    userRepository.save(user)
    user
  }

}
