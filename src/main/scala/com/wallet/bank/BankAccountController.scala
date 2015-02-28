package com.wallet.bank

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._

import scala.collection.JavaConverters._


@RestController
@RequestMapping(Array("/api/v1"))
class BankAccountController @Autowired()(bankingService:BankingService) {

  @RequestMapping(value = Array("users/{user_id}/bankaccounts"),method = Array(RequestMethod.POST),consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def createBankAccount(@PathVariable user_id:String,@RequestBody @Valid bankAccount:BankAccount ,result:BindingResult) = {
    result.hasErrors match{
      case false => bankingService.createAccount(user_id,bankAccount)
      case true => throw  BankAccountCreationException("Bank Account creation failed",result.getFieldErrors.asScala)
    }
  }

  @ExceptionHandler(value = Array(classOf[BankAccountCreationException]))
  def handleBankAccountCreationException(exception:BankAccountCreationException,req:HttpServletRequest)={
    val errors = exception.errors.map { error =>
      Map("field" -> error.getField, "rejectedValue" -> error.getRejectedValue, "path" -> req.getRequestURI,"message"->error.getDefaultMessage).asJava
    }.asJava
    Map("errors"->errors).asJava
  }

  @RequestMapping(value=Array("users/{user_id}/bankaccounts"),method=Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def listBankAccounts(@PathVariable user_id:String) = {
      bankingService.fetchAccounts(user_id) match {
        case Some(accounts: Seq[BankAccount]) => {
          if(!accounts.isEmpty) accounts.asJava else new ResponseEntity[String](HttpStatus.NO_CONTENT)
        }
        case None => new ResponseEntity[String](HttpStatus.NO_CONTENT)
      }
  }

  @RequestMapping(value = Array("users/{user_id}/bankaccounts/{ba_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteBankAccount(@PathVariable user_id:String,@PathVariable ba_id:String)= {
    bankingService.deleteAccount(user_id,ba_id)
  }

}
