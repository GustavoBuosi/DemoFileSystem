package org.gbuosi.files

import org.gbuosi.filesystem.FilesystemException


class File(override val parentPath: String, override val name: String, val contents: String) extends DirEntry (parentPath,name){

  def asDirectory: Directory = throw new FilesystemException("A file cannot be converted to a directory")
  def asFile: File = this
  def getType: String = "File"
  def isDirectory: Boolean = false
  def isFile: Boolean = true

  def setContents(newContents: String): File = new File(parentPath,name,newContents)
  def appendContents(newContents: String): File = setContents(contents + "\n" + newContents)


}

object File {
  val TOUCH = "touch"
  def empty(parentPath: String , name: String): File = new File(parentPath, name, "")
}