package org.gbuosi.commands

import org.gbuosi.files.{DirEntry, Directory}

abstract class CreateEntry(name: String) extends Command{

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage("Directory" + name + " already exists")
    }
    else if (name.contains((Directory.SEPARATOR))) {
      // mkdir -p /something/somethingelse
      // will not be implemented yet
      state.setMessage(name + "must not contain separators!")
    }
    else if (checkIllegal(name)) {
      state.setMessage(name +
        ": Illegal entry name"
      )
    }
    else {
      doCreateEntry(state, name)
    }
  }

  def doCreateEntry(state: State, name: String): State = {
    def updateStructure(currentDirectory: Directory, path: List[String],
                        newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry,
          path.tail,newEntry))
        /*
         /a/b
            (contents)
            (new entry) /e
        currentDirectory = /a
        path ["b"]
        */

      }
    }

    val wd = state.wd
    //Directory structure is immutable!
    // 1. all the directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath
    // 2. create new directory entry in the wd
    // TODO implement this
    val newEntry: DirEntry = createSpecificEntry(state)
//    val newDir = Directory.empty(wd.path, name)
    // 3. update the whole directory structure starting from /
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    // 4. get the new wd instance given wd's full path in the new directory structure
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)

  }
  // Method to be implemented by MkDir and Touch
  def createSpecificEntry(state: State): DirEntry

  def checkIllegal(str: String): Boolean =
    str.contains(".")
}
