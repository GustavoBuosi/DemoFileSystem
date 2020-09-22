package org.gbuosi.commands

import org.gbuosi.files.{DirEntry, Directory}

class Mkdir(name: String) extends CreateEntry(name){

  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.wd.path,name)
}
