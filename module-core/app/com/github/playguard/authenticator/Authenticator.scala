package com.github.playguard.authenticator

import com.github.playguard.model.Principal


trait Authenticator {
  def authenticate(authenticationParams: Map[String, String]):Principal;

}
