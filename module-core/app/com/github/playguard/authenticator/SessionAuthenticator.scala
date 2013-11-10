package com.github.playguard.authenticator

import com.github.playguard.{SecurityRegistry}
import com.github.playguard.exception.AuthenticationException
import com.github.playguard.model.Principal

object SessionAuthenticator extends Authenticator {

  val USER_NAME: String = "username"
  val PASSWORD: String = "password"

  private def encode(text: String) = SecurityRegistry.encoder.encode(text)

  def authenticate(authenticationParams: Map[String, String]): Principal = {
    val username = authenticationParams.getOrElse(USER_NAME, {
      throw new AuthenticationException(USER_NAME + " does not exist in request.");
    })
    val password = authenticationParams.getOrElse(PASSWORD, {
      throw new AuthenticationException(PASSWORD + " does not exist in request.");
    })

    SecurityRegistry.principalRepository.findPrincipal(username).map(p => {
      if (p.password == encode(password)) p else throw new AuthenticationException(String.format("For Username:%s Password is wrong.", username))
    })
      .getOrElse(throw new AuthenticationException(String.format("User with username:%s does not exist.", username)))
  }
}
