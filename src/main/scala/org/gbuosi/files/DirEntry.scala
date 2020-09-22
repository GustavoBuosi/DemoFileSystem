package org.gbuosi.files

abstract class DirEntry(val parentPath: String , val name: String) {

  def path: String = {
    val separatorifNecessary =
      if(Directory.ROOT_PATH.equals(parentPath)) ""
      else Directory.SEPARATOR
    separatorifNecessary + Directory.SEPARATOR + name
  }
  def asDirectory: Directory
  def getType: String
  def asFile: File
  def isFile: Boolean
  def isDirectory: Boolean

}
