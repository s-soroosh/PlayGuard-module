package com.github.playguard.encoding

object PlainTextEncoder extends Encoder {
  def encode(in: String): String = in
}
