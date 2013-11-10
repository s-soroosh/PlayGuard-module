package com.github.playguard.encoding

import java.security.MessageDigest

abstract class DigestEncoder(digest: String) extends Encoder {
  def encode(in: String): String = java.security.MessageDigest.getInstance(digest).digest(in.getBytes).foldLeft("")((a, b) => a + Integer.toHexString((b & 0xff)))

}
object MD5Encoder extends DigestEncoder("MD5")
object SHA1Encoder extends DigestEncoder("SHA1")