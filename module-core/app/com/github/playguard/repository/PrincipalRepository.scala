package com.github.playguard.repository

import com.github.playguard.model.Principal


trait PrincipalRepository {
  def findPrincipal(username: String): Option[Principal]
}
