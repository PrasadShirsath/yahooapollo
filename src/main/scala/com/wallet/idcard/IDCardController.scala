package com.wallet.idcard

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import com.wallet.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._

import scala.collection.JavaConverters._

@RestController
@RequestMapping(Array("/api/v1"))
class IDCardController @Autowired()(userService: UserService,cardService:CardService) {

  @RequestMapping(value = Array("/users/{user_id}/idcards"), method = Array(RequestMethod.POST),consumes=Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def createIdCard( @PathVariable user_id: String,@RequestBody @Valid cardDTO: IDCardDTO, result: BindingResult) = {
    result.hasErrors match {
      case true => {
          throw CardCreationException("Validation failed on Card Creation",result.getFieldErrors.asScala)
      }
      case false => {
        cardService.createCard(cardDTO, user_id)
      }
    }
  }

  @ExceptionHandler(Array(classOf[CardCreationException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  def handleCardCreationException(exception: CardCreationException,request: HttpServletRequest ) = {
    val errors = exception.errors.map { error =>
      Map("field" -> error.getField, "rejectedValue" -> error.getRejectedValue, "path" -> request.getRequestURI,"message"->error.getDefaultMessage).asJava
    }.asJava
    Map("errors"->errors).asJava
  }

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value=Array("/users/{user_id}/idcards"),method=Array(RequestMethod.GET))
  def listCards(@PathVariable user_id:String)= {
     cardService.fetchCardsByUserId(user_id) match {
       case Some(cards:Seq[IDCard])=> {
         if(cards.size>0)
           cards.asJava
         else
           new ResponseEntity[String](HttpStatus.NO_CONTENT)
       }
       case None => new ResponseEntity[String](HttpStatus.NO_CONTENT)
     }
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @RequestMapping(value=Array("/users/{user_id}/idcards/{card_id}"),method=Array(RequestMethod.DELETE))
  def removeCard(@PathVariable user_id:String,@PathVariable card_id:String) = {
      cardService.removeCard(user_id,card_id)
  }
}
