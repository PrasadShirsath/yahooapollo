package com.wallet.user

import java.text.SimpleDateFormat
import java.util
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._
import org.springframework.web.context.request.WebRequest

import scala.collection.JavaConverters._

@RestController()
@RequestMapping(Array("/api/v1"))
class UserController @Autowired()(userService: UserService) {

  @RequestMapping(value = Array("/users"), method = Array(RequestMethod.POST), consumes = Array("application/json"), produces = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def createUser(@RequestBody @Valid userDTO: UserDTO, result: BindingResult) = {
    result.hasErrors match {
      case true => throw UserCreationException("User creation failed", result.getFieldErrors.asScala)
      case false => userService.createUser(userDTO)
    }
  }

  @ExceptionHandler(Array(classOf[UserCreationException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  def raiseUserException(exception: UserCreationException, request: HttpServletRequest) = {
    val errors = exception.errors.map { error =>
      Map("field" -> error.getField, "rejectedValue" -> error.getRejectedValue, "path" -> request.getRequestURI, "message" -> error.getDefaultMessage).asJava
    }.asJava
    Map("errors" -> errors).asJava
  }

  @RequestMapping(value = Array("/users/{userId}"), method = Array(RequestMethod.GET))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def fetchUser(@PathVariable userId: String, webRequest: WebRequest) = {
    userService.fetchById(userId) match {
      case Some(user: User) => {
        webRequest.getHeader("If-None-Match") match {
          case etagFromClient: String => webRequest.getHeader("If-Modified-Since") match {
            case timeSince: String => if (webRequest.checkNotModified(user.hashCode.toString)){new ResponseEntity[String](HttpStatus.NOT_MODIFIED)} else{user}
            case _ => sendRegularResponse(user)
          }
          case _ => sendRegularResponse(user)
        }
      }
      case _ => throw UserNotFoundException("User with Id: %s not found".format(userId))
    }

  }

  def sendRegularResponse(user: User): ResponseEntity[User] = {

    val etagValue: util.List[String] = util.Arrays.asList[String](user.hashCode.toString)
    val sdf = new SimpleDateFormat("E,d MMM Y HH:mm:ss z")
    val lastModifiedValue: util.List[String] = util.Arrays.asList[String](sdf.format(user.updatedAt))
    val headerValues = new util.HashMap[String, util.List[String]]()
    headerValues.put("Etag", etagValue)
    headerValues.put("Last-Modified", lastModifiedValue)
    headerValues.put("Cache-Control",util.Arrays.asList("max-age=1000"))
    val headers = new HttpHeaders();
    headers.putAll(headerValues);
    new ResponseEntity[User](user, headers, HttpStatus.OK)

  }

  @RequestMapping(value = Array("/users/{userId}"), method = Array(RequestMethod.PUT))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def updateUser(@RequestBody @Valid userDTO: UserDTO, @PathVariable userId: String) = {
    userService.update(userDTO, userId) match {
      case Some(user: User) => user
      case _ => throw UserNotFoundException("User with Id: %s not found".format(userId))
    }
  }

}
