package com.github.playguard.model

trait PrincipalWithRole extends Principal {
  def roles: List[Role]
}
