package com.wallet.weblogin

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._
import collection.JavaConverters._


@RestController()
@RequestMapping(value = Array("/api/v1"))
class WebLoginController @Autowired()(loginService: LoginService) {

  @RequestMapping(value = Array("users/{user_id}/weblogins"), method = Array(RequestMethod.POST),consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def createWebLogin(@PathVariable user_id: String, @RequestBody @Valid webLoginDTO: WebLoginDTO, result: BindingResult) = {
    result.hasErrors match {
      case true => throw WebLoginCreationException("Weblogin creation failed", result.getFieldErrors.asScala)
        case false => loginService.createWebLogin(user_id, webLoginDTO)
    }
  }

  @ExceptionHandler(Array(classOf[WebLoginCreationException]))
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleWebLoginCreationException(exception:WebLoginCreationException,req:HttpServletRequest) = {
    val errors = exception.errors.map { error =>
      Map("field" -> error.getField, "rejectedValue" -> error.getRejectedValue, "path" -> req.getRequestURI,"message"->error.getDefaultMessage).asJava
    }.asJava
    Map("errors"->errors).asJava
  }

  @RequestMapping(value = Array("users/{user_id}/weblogins"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def listAllWebLogins(@PathVariable user_id: String) = {
    loginService.fetchLoginsBy(user_id)match{
      case Some(webLogins:Seq[WebLogin]) =>
        if(webLogins.size>0)
          webLogins.asJava
        else
          new ResponseEntity[String](HttpStatus.NO_CONTENT)
      case None => new ResponseEntity[String](HttpStatus.NO_CONTENT)
    }
  }

  @RequestMapping(value = Array("users/{user_id}/weblogins/{login_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteWebLogin(@PathVariable user_id:String,@PathVariable login_id:String) = {
    loginService.removeLogin(user_id,login_id)
  }

}
