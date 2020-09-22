package org.gbuosi.commands
import org.gbuosi.files.{DirEntry, File}

class Touch(name: String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State): DirEntry = {
    File.empty(state.wd.path,name)
  }

}
