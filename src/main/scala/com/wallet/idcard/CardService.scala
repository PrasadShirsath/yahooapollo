package com.wallet.idcard

import com.wallet.user.{User, UserNotFoundException, UserService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CardService@Autowired()(userService:UserService,cardRepo:CardRepository) {
  def fetchCardsByUserId(userId: String):Option[Seq[IDCard]] = {
    userService.fetchById(userId) match {
      case Some(user:User)=> {
        import scala.collection.JavaConversions._
        Option(cardRepo.findByUserId(userId))
      }
      case None => throw UserNotFoundException("User with Id %s not found, please sign up".format(userId))
    }
  }

  def createCard(cardDTO: IDCardDTO,userId:String):IDCard = {
    userService.fetchById(userId) match{
      case Some(user:User) =>{
        val createdCard: IDCard = IDCard(userId = userId,cardName = cardDTO.getCardName,cardNumber = cardDTO.getCardNumber,expirationDate = cardDTO.expirationDate)
        cardRepo.save(createdCard)
        createdCard
      }
      case None => throw UserNotFoundException("User with Id %s not found, please sign up".format(userId))
    }

  }

  def removeCard(userId: String, cardId: String):Unit = {
    userService.fetchById(userId) match {
      case Some(user:User)=> {
        if(cardRepo.findOne(cardId)!=null){
          cardRepo.delete(cardId)
        }
        else{
          throw new CardNotFoundException("Card with Id: %s not found".format(cardId))
        }
      }
      case None => throw UserNotFoundException("User with Id %s not found, please sign up".format(userId))
    }
  }
}
