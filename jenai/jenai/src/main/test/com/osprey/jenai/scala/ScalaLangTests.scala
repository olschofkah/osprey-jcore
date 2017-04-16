package com.osprey.jenai.scala

import org.scalatest.{FunSuite, Matchers}

import scala.util.Random

/**
  * Created by Goliaeth on 4/12/2017.
  */
class ScalaLangTests extends FunSuite with Matchers {


  test("Testing random behavior") {

    val rando = new Random()

    0 to 1000 map (_ => rando.nextInt()) map (i => if (i < 0) -1 else 1) foreach (x => println(x))

  }
}
