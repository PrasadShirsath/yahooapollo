package com.wallet.bank

import java.util.Date

import com.wallet.user.{User, UserNotFoundException, UserService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BankingService@Autowired()(userService:UserService,bankRepo:BankRepository,routingService:RoutingServiceClient) {

  def deleteAccount(userId: String, accountID: String):Unit = {
    userService.fetchById(userId) match {
      case Some(user:User)=> {
        if(bankRepo.findOne(accountID)!=null){
          bankRepo.delete(accountID)
        }else{
          throw new BankAccountNotFoundException("Bank Account with Id:%s not found".format(accountID))
        }
      }
      case None => throw UserNotFoundException("User with Id %s not found, please sign up".format(userId))
    }
  }


  def fetchAccounts(userId: String):Option[Seq[BankAccount]] = {
      userService.fetchById(userId) match {
        case Some(user:User) => {
          import scala.collection.JavaConversions._
          Option(bankRepo.findByUserId(userId))
        }
        case _ => throw UserNotFoundException("User with Id %s not found")
      }
  }

  def createAccount(user_id: String,account:BankAccount) = {
      userService.fetchById(user_id)match{
        case Some(user:User) =>{
          routingService.authenticateRoutingNumber(account.getRoutingNumber) match {
            case Some(rr: RoutingResponse)=>account.baId = "b-%s".format(new Date().getTime)
                                        account.userId = user_id
                                        account.accountName = rr.customer_name
                                        bankRepo.save(account)
            case _=> throw new InvalidRoutingNumberException("Routing Number %s is incorrect, provide valid routing number ".format(account.getRoutingNumber))
          }
        }
        case None => throw UserNotFoundException("User with ID %s not found".format(user_id))
      }

  }

}
