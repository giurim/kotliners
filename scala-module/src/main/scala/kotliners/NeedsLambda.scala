package kotliners

class NeedsLambda {
  def callTheLambda(f: String => String, s: String): String = f(s)
}
