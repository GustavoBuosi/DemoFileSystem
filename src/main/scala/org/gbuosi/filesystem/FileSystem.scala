package org.gbuosi.filesystem

import java.util.Scanner
import org.gbuosi.commands.{Command, State}
import org.gbuosi.files.Directory

object FileSystem extends App {
  val root = Directory.ROOT

  // [1,2,3,4]
  // 0 (op) 1 => 1
  // 1 (op) 2 => 3
  // ...
  // until you sum 10
  // foldLeft takes a function and applies like that
  // List(1,2,3,4).foldLeft(0)((x,y) => x+y)

  io.Source.stdin.getLines().foldLeft(State(root,root))((currentState, newLine) => {
    currentState.show
    Command.from(newLine).apply(currentState)
  })

//  var state = State(root,root)
//  val scanner = new Scanner(System.in)
//
//  while (true) {
//    state.show
//    val input = scanner.nextLine()
//    state = Command.from(input).apply(state)
//  }

}
