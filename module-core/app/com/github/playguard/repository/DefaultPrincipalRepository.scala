package com.github.playguard.repository

import com.github.playguard.Principal


object DefaultPrincipalRepository extends PrincipalRepository {
  def findPrincipal(username: String): Option[Principal] = {
    if (username == "soroosh")

      Some(new Principal {
        def name: String = "soroosh"
        def password: String = "123456"
        def enable: Boolean = true
      })

    else None
  }
}
