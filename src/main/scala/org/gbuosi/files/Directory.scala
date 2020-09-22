package org.gbuosi.files

import org.gbuosi.filesystem.FilesystemException

import scala.annotation.tailrec

class Directory(override val parentPath: String,
                override val name: String,
                val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {
  def hasEntry(path: String): Boolean = findEntry(path) != null

  def getAllFoldersInPath: List[String] = {
    path.substring(1).split(Directory.SEPARATOR).toList.filter(x => !x.isEmpty)
  }

  def removeEntry (path: String): Directory = {
    if (!hasEntry(path)) this
    else new Directory(parentPath, name, contents.filter(x => !x.name.equals(path)))
  }

  def findDescendant(relativePath: String): Directory = {
    if (relativePath.isEmpty) this
    else findDescendant(relativePath.split(Directory.SEPARATOR).toList)
  }


  def findDescendant(path: List[String]): Directory = {
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory
  }

  def isRoot: Boolean = parentPath.isEmpty

  def isFile: Boolean = false

  def isDirectory: Boolean = this.getType == "Directory"

  def addEntry(newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents :+ newEntry)

  def findEntry(entryName: String): DirEntry = {
    @tailrec
    def findEntryHelper(name: String, contentList: List[DirEntry]): DirEntry =
      if (contentList.isEmpty) null
      else if (contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name, contentList.tail)

    findEntryHelper(entryName,this.contents)
  }

  def asDirectory: Directory = this

  def replaceEntry(entryName: String, newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents.filter(e => !e.name.equals(entryName)) :+ newEntry)
  def asFile: File = throw new FilesystemException("Directory cannot be cast to File")
  def getType: String = "Directory"

}


object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"


  def ROOT: Directory = Directory.empty("", "")

  /*
   We are going to avoid instantiating new objects by hand.
   Instead, we will call Directory.empty
   */
  def empty(parentPath: String, name: String) =
    new Directory(parentPath, name, List())

}