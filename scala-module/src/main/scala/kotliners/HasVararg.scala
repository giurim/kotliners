package kotliners

import scala.annotation.varargs

class HasVararg {
  val ex1 = callTheClassicVararg("a", "b", "c")
  val ex2 = callTheClassicVarargPlus("a", "b", "c")

  def callTheClassicVararg(args: String*): String = args.mkString(",")

  def callTheClassicVarargPlus(arg1: String, args: String*): String = arg1 + "," + args.mkString(",")

  @varargs
  def callTheVararg(args: String*): String = args.mkString(",")

  @varargs
  def callTheVarargPlus(arg1: String, args: String*): String = arg1 + "," + args.mkString(",")

  // val ex3 = callTheClassicVarargPlus(Seq("a", "b","c"):_*) // NOT WORKING

  // val ex4 = callTheVarargPlus(Seq("a", "b","c"):_*) // NOT WORKING
}
