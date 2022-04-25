package client

import shared.SharedMessages
import org.scalajs.dom

object AppMain {

  def main(args: Array[String]): Unit = {
    dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
  }
}
