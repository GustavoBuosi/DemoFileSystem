package org.gbuosi.commands

class UnknownCommand extends Command {

  override def apply(state: State): State =
    state.setMessage("Unknown Command")
}
