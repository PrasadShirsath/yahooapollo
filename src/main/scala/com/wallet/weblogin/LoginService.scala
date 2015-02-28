package com.wallet.weblogin

import com.wallet.user.{User, UserNotFoundException, UserService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LoginService @Autowired()(userService:UserService,webLoginRepository:WebLoginRepository) {

  def removeLogin(userId: String, loginId: String):Unit = {
     userService.fetchById(userId) match {
       case Some(user:User) => {
          if(webLoginRepository.findOne(loginId)!=null)
            webLoginRepository.delete(loginId)
          else
            throw new WebLoginNotFoundException("WebLogin with Id: %s".format(loginId))
       }
       case None => throw UserNotFoundException("User with ID %s not found".format(userId))

     }
  }

  def fetchLoginsBy(userId: String):Option[Seq[WebLogin]] = {
    userService.fetchById(userId)match{
      case Some(user:User)=>{
        import scala.collection.JavaConversions._
        Option(webLoginRepository.findByUserId(userId))
      }
      case None => throw UserNotFoundException("User with ID %s not found".format(userId))
    }
  }

  def createWebLogin(userId: String, login: WebLoginDTO):WebLogin = {
     userService.fetchById(userId) match {
       case Some(user:User) =>{
         val webLogin: WebLogin = WebLogin(url = login.url, login = login.login, password = login.password, userId = userId)
         webLoginRepository.save(webLogin)
         webLogin
       }
       case None => throw UserNotFoundException("User with ID %s not found".format(userId))
     }
  }


}
