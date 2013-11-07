package com.github.playguard.authenticator

import com.github.playguard.Principal


trait Authenticator {
  def authenticate(authenticationParams: Map[String, String]):Principal;

}
