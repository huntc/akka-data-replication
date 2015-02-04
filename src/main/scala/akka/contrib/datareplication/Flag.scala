/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */
package akka.contrib.datareplication

object Flag {
  /**
   * `Flag` that is initialized to `false`.
   */
  val empty = new Flag(false)
  def apply(): Flag = empty
  /**
   * Java API: `Flag` that is initialized to `false`.
   */
  def create(): Flag = empty

  // unapply from case class
}

/**
 * Implements a boolean flag CRDT that is initialized to `false` and
 * can be switched to `true`. `true` wins over `false` in merge.
 *
 * This class is immutable, i.e. "modifying" methods return a new instance.
 */
@SerialVersionUID(1L)
final case class Flag(enabled: Boolean) extends ReplicatedData with ReplicatedDataSerialization {

  type T = Flag

  def switchOn: Flag =
    if (enabled) this
    else Flag(true)

  override def merge(that: Flag): Flag =
    if (that.enabled) that
    else this
}

